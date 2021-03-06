package api;

import java.io.IOException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.serverClient.ChatMessage;
import chat.serverClient.Client;

import com.sun.org.apache.xml.internal.utils.StopParseException;


public class managerBroker {
	//this class is coonect from brokers
	public final static int BUY = 1;
	public final static int SELL = 2;

	public final static int MKT = 1;
	public final static int STP = 2;
	public final static int STP_LIMIT = 3;
	public final static int LIMIT = 4;

	public final static int ENTER_ORDER = 1;
	public final static int STOP_ORDER = 2;
	public final static int TK_ORDER = 3;
	
	private static Logger Logger;

	private int threadId;
	private IbConnector IB_Broker;
	private String msgReturn;
	private Client clientToBroker;
	

	public managerBroker(int threadId,int portToBrokerServer)
	{//init BROKER class

		Logger = LoggerFactory.getLogger(managerBroker.class);
		
		this.threadId = threadId;

		String serverAddress = "localhost";
		clientToBroker = new Client(serverAddress, portToBrokerServer, String.valueOf(this.threadId));

	}

	private String sendRequestToBroker(String stRequest)
	{//sent the order to broker via client
		
		if(!this.clientToBroker.start())//make a connction
		{
			Logger.info("can't set order - Broker un-available");
			return "-1";
		}
		this.clientToBroker.sendMessage(new ChatMessage(ChatMessage.MESSAGE, stRequest));//sent the msg

		String stResponse = waitForAnswerFromBroker();//wait for the id in Broker

		this.clientToBroker.disconnect();
		return stResponse;
	}
	private String waitForAnswerFromBroker()
	{//the function read the input from the server
		
		String msg = "";
		while(true) {
			try {

				msg = (String) this.clientToBroker.sInput.readObject();
				Logger.info("message from serverChat: " + msg);
				this.clientToBroker.sendMessage(new ChatMessage(2, ""));//logout
				break;
			}
			catch(IOException e) {
				this.clientToBroker.disconnect();
				break;
			}
			// can't happen with a String object but need the catch anyhow
			catch(ClassNotFoundException e2) {
			}
		}
		return msg;

	}
	private int setActionByEnterOrder(int actionEnter)
	{
		int newAction = 0;//for stop/take profit order
		if (actionEnter == BUY)
		{ newAction = SELL;}
		else if (newAction == SELL)
		{ newAction = BUY;}
		
		return newAction;
		
	}
	public int placeOrderByType(String symbol, Order orderObj,int orderType)
	{//place an order to Broker by type(enter,stop,tk)
		
		if (ENTER_ORDER == orderType)
		{
			OrderToExecute enterOrder = new OrderToExecute(symbol,
					orderObj.getAction(),
					orderObj.getQuantity(),
					-1,//ocaGruop
					orderObj.getEnter().getTypeOrdr(),
					orderObj.getEnter().getEnterPrice(),
					orderObj.getEnter().getLimitPrice());

			String enterOrderJson = enterOrder.convertToJSON("placeNewOrder",this.threadId);
			Logger.info("send enter order to broker. " + enterOrderJson);
			String stReturnEnter = sendRequestToBroker(enterOrderJson);
			Logger.info("enter order id: " + stReturnEnter);
			return Integer.parseInt(stReturnEnter);
		}
		
		if (STOP_ORDER == orderType)
		{
			//stop order
			OrderToExecute stopOrder = new OrderToExecute(symbol,
					setActionByEnterOrder(orderObj.getAction()),
					orderObj.getQuantity(),
					orderObj.getOca(),
					orderObj.getStop().getTypeOrdr(),
					orderObj.getStop().getStopPrice(),
					-1);

			String stopOrderJson = stopOrder.convertToJSON("placeNewOrder",this.threadId);
			Logger.info("send stop order to broker. " + stopOrderJson);
			String stReturnStop = sendRequestToBroker(stopOrderJson);
			Logger.info("stop order id: " + stReturnStop);
			return Integer.parseInt(stReturnStop);
		}
		
		if (TK_ORDER == orderType)
		{
			//takeProfit order
			OrderToExecute takeProfitOrder = new OrderToExecute(symbol,
					setActionByEnterOrder(orderObj.getAction()),
					orderObj.getQuantity(),
					orderObj.getOca(),
					orderObj.getTakeProfit().getTypeOrdr(),
					orderObj.getTakeProfit().getTakeProfitPrice(),
					-1);

			String takeProfitOrderJson = takeProfitOrder.convertToJSON("placeNewOrder",this.threadId);
			Logger.info("send TK order to broker. " + takeProfitOrderJson);
			String stReturnTakeProfit = sendRequestToBroker(takeProfitOrderJson);
			Logger.info("TK order id: " + stReturnTakeProfit);		
			return Integer.parseInt(stReturnTakeProfit);
		}
		
		return -1;
	}
	public void placeOrder(String symbol, Order orderObj)
	{//place a order in broker. the function return the id of the order in the broker

		//enter order
		OrderToExecute enterOrder = new OrderToExecute(symbol,
				orderObj.getAction(),
				orderObj.getQuantity(),
				-1,
				orderObj.getEnter().getTypeOrdr(),
				orderObj.getEnter().getEnterPrice(),
				orderObj.getEnter().getLimitPrice());

		String enterOrderJson = enterOrder.convertToJSON("placeNewOrder",this.threadId);
		System.out.println(enterOrderJson);
		String stReturnEnter = sendRequestToBroker(enterOrderJson);
		System.out.println(stReturnEnter);


		int newAction = 0;//for stop/take profit order
		if (orderObj.getAction() == BUY)
		{ newAction = SELL;}
		if (orderObj.getAction() == SELL)
		{ newAction = BUY;}

		//stop order
		OrderToExecute stopOrder = new OrderToExecute(symbol,
				newAction,
				orderObj.getQuantity(),
				orderObj.getOca(),
				orderObj.getStop().getTypeOrdr(),
				orderObj.getStop().getStopPrice(),
				-1);

		String stopOrderJson = stopOrder.convertToJSON("placeNewOrder",this.threadId);
		System.out.println(stopOrderJson);
		String stReturnStop = sendRequestToBroker(stopOrderJson);
		System.out.println(stReturnStop);
		
		
		//takeProfit order
		OrderToExecute takeProfitOrder = new OrderToExecute(symbol,
				newAction,
				orderObj.getQuantity(),
				orderObj.getOca(),
				orderObj.getTakeProfit().getTypeOrdr(),
				orderObj.getTakeProfit().getTakeProfitPrice(),
				-1);

		String takeProfitOrderJson = takeProfitOrder.convertToJSON("placeNewOrder",this.threadId);
		System.out.println(takeProfitOrderJson);
		String stReturnTakeProfit = sendRequestToBroker(takeProfitOrderJson);
		System.out.println(stReturnTakeProfit);
		
		//convert the orders to json
		JSONObject objOrders = new JSONObject();
	      try {
	    	  objOrders.put("enterOrder", stReturnEnter);
	    	  objOrders.put("stopOrder", stReturnStop);
	    	  objOrders.put("takeprofitOrder", stReturnTakeProfit);
	    
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    System.out.println(objOrders.toString());
	}
	
	public void getCashInAccount()
	{
		this.IB_Broker.getCashInAccount();
	}
	public void cancelOrder(int orderIdServer)
	{
		JSONObject obj = new JSONObject();

		try {
			obj.put("orderIdServer", orderIdServer);
			obj.put("function", "cancelOrder");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		Logger.info("cancelOrder by id server. " + obj.toString());
		String stResponse = sendRequestToBroker(obj.toString());
		Logger.info("response from serverChat: " + stResponse);
	}
	public void getAllPostion()
	{
		this.IB_Broker.getAllPostion();
	}
	public void reqForMaxRisk(double maxRisk)
	{//maxRisk - in 10 (not 0.1)

		//this.IB_Broker.reqAccountUpdates(maxRisk);
		JSONObject obj = new JSONObject();

		try {
			obj.put("maxRisk", maxRisk);
			obj.put("function", "riskIsOK");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		System.out.println(sendRequestToBroker(obj.toString()));
	}
	public void checkOrderStatus()
	{
		JSONObject obj = new JSONObject();

		try {
			obj.put("function", "checkStatus");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		System.out.println(sendRequestToBroker(obj.toString()));
		
	}
	
	public void updateOrder(String symbol, Order orderObj,int orderToUpdate)
	{
		
		notUsed.updateOrder updateOrderTemp  = new notUsed.updateOrder(symbol, orderObj, orderToUpdate);
		
		String OrderJson = updateOrderTemp.convertToJSON("updateOrder",this.threadId);
		System.out.println(OrderJson);
		String stReturnStop = sendRequestToBroker(OrderJson);
		
	}

	/////////*************test!
	public static void main (String args[])
	{


		int threadId = 2;
		int portNumber = 1500;
		managerBroker manBroker = new managerBroker(threadId,portNumber);



		String symbol = "GE";
		int id = 1;
		int quantity = 100;
		int action = BUY;
		long time = 12111;
		int counterArr = 5;

		//enter order
		int typeOrderEnter = MKT;
		//int typeOrderEnter = STP;
		double enterPrice = 30;
		double limitOrder = 31;
		//double limitOrder = -1;

		//stop order
		double stopPrice = 20;

		//tk order
		double takeProfitPrice = 40;



		Order tempOrder = new Order(id, quantity, action, time,counterArr, 
				typeOrderEnter, enterPrice, limitOrder,
				stopPrice,
				takeProfitPrice);

/*		//update takeprofit
		tempOrder.getTakeProfit().setIdServer(42);
		tempOrder.getTakeProfit().setTakeProfitPrice(184);
		manBroker.updateOrder(symbol, tempOrder,3);*/
		
		/*//update stoploss
		tempOrder.getStop().setIdServer(41);
		tempOrder.getStop().setStopPrice(155);
		manBroker.updateOrder(symbol, tempOrder,2);*/
		
		//update enter
/*		tempOrder.getEnter().setIdServer(43);
		tempOrder.getEnter().setEnterPrice(170);
		tempOrder.getEnter().setLimitPrice(172);
		manBroker.updateOrder(symbol, tempOrder,1);*/
		
		//**
		int y = manBroker.placeOrderByType(symbol,tempOrder,2);
		System.out.println("order STOP id:" + y);
		int x = manBroker.placeOrderByType(symbol,tempOrder,3);
		System.out.println("order TK id:" + x);
		//manBroker.cancelOrder(28);
		//manBroker.reqForMaxRisk(10);
		
		//manBroker.getCashInAccount();
		//manBroker.getAllPostion();
		//manBroker.reqAccountUpdates(10);
		//manBroker.disconnectFromBroker();
		
		//manBroker.checkOrderStatus();



	} // end main
}
