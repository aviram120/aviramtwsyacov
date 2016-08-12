package ayAPI;

import java.util.Random;

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

	public int placeOrder(String symbol, order orderObj, int ocaGroupNum)
	{//place a order in broker. the function return the id of the order in the broker
		
		return this.IB_Broker.placeNewOrder(symbol,orderObj,ocaGroupNum);//return the id in broker
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
		
		//make random num for ocaGroup
		Random rand = new Random();
		int  ocaGroupNum = rand.nextInt(99999) + 999;
		
		

		int id =1;
		int typeOrder = STP;
		double enterPrice = 16;
		double limitOrder = 17;
		double stop = 10;
		double takeProfit = 20;
		long volume = 50000;
		int quantity = 100;
		int action = BUY;
		int counterBar = 1;
		long time = 12111;
		int status = 2;

		

		order tempOrder = new order(id,  typeOrder, enterPrice, limitOrder, stop,takeProfit, volume, quantity,action, counterBar,time, status);

		int idServer = manBroker.placeOrder(symbol, tempOrder,ocaGroupNum);
		System.out.println(idServer);
		
		manBroker.cancelOrder(2);
		
		manBroker.disconnectFromBroker();
		
		

	} // end main
}
