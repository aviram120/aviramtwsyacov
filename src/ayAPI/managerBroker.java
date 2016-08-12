package ayAPI;

import java.util.Random;

import api2.FutureOrder;
import api2.Order;
import api2.OrderToExecute;

public class managerBroker {
//this class is coonect from brokers
	public final static int BUY = 1;
	public final static int SELL = 2;

	public final static int MKT = 1;
	public final static int STP = 2;
	public final static int STP_LIMIT = 3;
	public final static int LIMIT = 4;


	private IbConnector IB_Broker;

	public managerBroker()
	{//init BROKER class
		
		int connectionId = 21;
		this.IB_Broker = new IbConnector(connectionId);

	}

	public void placeOrder(String symbol, Order orderObj)
	{//place a order in broker. the function return the id of the order in the broker
		
		//return this.IB_Broker.placeNewOrder(symbol,orderObj);//return the id in broker
		OrderToExecute enterOrder = new OrderToExecute(symbol,
														orderObj.getAction(),
														orderObj.getQuantity(),
														-1,
														orderObj.getEnter().getTypeOrdr(),
														orderObj.getEnter().getEnterPrice(),
														orderObj.getEnter().getLimitPrice());
		
		int enterOrderNum = this.IB_Broker.placeNewOrder(enterOrder);
		
		int newAction = 0;//for stop/take profit order
		if (orderObj.getAction() == BUY)
		{ newAction = SELL;}
		if (orderObj.getAction() == SELL)
		{ newAction = BUY;}
		
		OrderToExecute stopOrder = new OrderToExecute(symbol,
				newAction,
				orderObj.getQuantity(),
				orderObj.getOca(),
				orderObj.getStop().getTypeOrdr(),
				orderObj.getStop().getStopPrice(),
				-1);
		int stopOrderNum = this.IB_Broker.placeNewOrder(stopOrder);
		
		OrderToExecute takeProfitOrder = new OrderToExecute(symbol,
				newAction,
				orderObj.getQuantity(),
				orderObj.getOca(),
				orderObj.getTakeProfit().getTypeOrdr(),
				orderObj.getTakeProfit().getTakeProfitPrice(),
				-1);
		
		int tkOrderNum = this.IB_Broker.placeNewOrder(takeProfitOrder);
		
		System.out.println(enterOrderNum + "|" + stopOrderNum + "|" + tkOrderNum);
		
		
		
	}
	public void cancelOrder(int idServer)
	{
		this.IB_Broker.cancelOrder(idServer);
	}
	public void disconnectFromBroker()
	{
		this.IB_Broker.disconnectFromBroker();
	}



/////////*************test!
	public static void main (String args[])
	{
		
		
		
		managerBroker manBroker = new managerBroker();

				
		String symbol = "BAC";
		int id = 1;
		int quantity = 100;
		int action = BUY;
		long time = 12111;
		
		//enter order
		int typeOrderEnter = STP_LIMIT;
		double enterPrice = 14.80;
		double limitOrder = 15;
		
		//stop order
		double stopPrice = 10;
		
		//tk order
		double takeProfitPrice = 20;

				

		Order tempOrder = new Order(id, quantity, action, time, 
				typeOrderEnter, enterPrice, limitOrder,
				stopPrice,
				takeProfitPrice);

			

		manBroker.placeOrder(symbol,tempOrder);
		//System.out.println(idServer);
		
		//manBroker.cancelOrder(2);
		
		manBroker.disconnectFromBroker();
		
		

	} // end main
}
