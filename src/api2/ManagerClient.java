package api2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ayAPI.Indicators;
import ayAPI.barByInterval;
import ayAPI.globalVar;
import ayAPI.localVar;




public class ManagerClient {
//this class is manage the data from broker and run the ALGORITHM,
// and make the orders to broker.
// save a list with all the orders of this symbol
		
	public final int INSIDE_BAR = 1;
	public final int MOVING_AVR = 2;
	public final int LONG = 1;
	public final int SHORT = 2;

	public final int BUY = 1;
	public final int SELL = 2;
	
	public final int ACTIVE = 1;
	public final int WAIT = 2;
	public final int CLOSED = 3;
	public final int CANCEl = 4;
	
	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;
	


	private globalVar objGlobal;
	private localVar objLocal;
	private int threadId;
	private String symbol;
	private int interval;

	private List<Order> listOrders;//list of all the order by sybmol
	private barByInterval[] arrData;//the graph
	private int counter;

	private Indicators indicatorsClass;


	public ManagerClient(int threadId, globalVar tempGlobal, localVar tempLocal)
	{
		this.objGlobal = tempGlobal;
		this.objLocal = tempLocal;
		this.threadId = threadId;
		this.symbol = tempLocal.getSymbol();
		this.interval = tempLocal.getInterval();

		int barToAdd = 0;
		if (tempLocal.getStrategy() == INSIDE_BAR)
		{barToAdd = 3;}
		else
		{barToAdd = tempLocal.getMovingAvrBar();}

		arrData = new barByInterval[390/interval+barToAdd];
		counter = 0;

		indicatorsClass = new Indicators();//class for indicators
	}
	public void addBarToGraph(barByInterval tempBar)
	{//the function add the data to arrData that save the graph
		
		arrData[counter] = new barByInterval(tempBar);

		System.err.println("barByInterval from ManagerClient: symbol:"+ arrData[counter].getSymbol() +" , high:"+ arrData[counter].getHigh() +" , low: "+arrData[counter].getLow()+" ,open: "+arrData[counter].getOpen()+" , close: "+arrData[counter].getClose()+" , vol: "+arrData[counter].getVolume());

		
		//TODO- read from file - to get update
		//algoTrade(false);
		counter++;
	}








	//*******************************************************
	//**************ALGORITHM
	//*******************************************************
	private void algoTrade(boolean haveFuterOrder)
	{
		double deltaHighToLow = arrData[counter].getHigh() - arrData[counter].getLow();

		if ((arrData[counter].getVolume() >= this.objLocal.getMinVolume()) && 
				(deltaHighToLow >= this.objLocal.getMinBarSize()) &&
				(deltaHighToLow <= this.objLocal.getMaxBarSize()))
		{
			System.out.println("in algoTrade");
			if (haveFuterOrder)
			{//
				//TODO-find the futer orders in the list			
			}


			//==============movingAver strategy==============
			if (this.objLocal.getStrategy() == this.objLocal.MOVING_AVR)
			{
				double movingAver = indicatorsClass.calculateMovingAverage(arrData, this.objLocal.getInterval() , counter);
				//double movingAver10 = indicatorsClass.calculateMovingAverage(arrData, 10 , counter);

				if (movingAver != -1)
				{
					if ((arrData[counter].getHigh()>movingAver) && (arrData[counter].getLow()<movingAver))
					{

						System.err.println("from algoTrade:"+ "strategy: "+"movingAver"+ 
								" ,time: "+arrData[counter].getTime() +
								" ,open: "+arrData[counter].getOpen()+
								" ,high: "+arrData[counter].getHigh()+
								" ,low: "+arrData[counter].getLow()+
								" ,close: "+arrData[counter].getClose()+
								" ,volume: "+arrData[counter].getVolume());

						setNewOrder(arrData[counter].getHigh(), arrData[counter].getLow(),arrData[counter].getTime());



					}
				}
			}


			//==============inside-bar strategy==============
			if (this.objLocal.getStrategy() == this.objLocal.INSIDE_BAR)
			{
				if (counter>0)
				{

					if ((arrData[counter].getHigh() <= arrData[counter-1].getHigh()) && (arrData[counter].getLow() >= arrData[counter-1].getLow()))
					{
						System.err.println("from algoTrade:"+ "strategy: "+"inside bar"+ 
								" ,time: "+arrData[counter-1].getTime() +
								" ,open: "+arrData[counter-1].getOpen()+
								" ,high: "+arrData[counter-1].getHigh()+
								" ,low: "+arrData[counter-1].getLow()+
								" ,close: "+arrData[counter-1].getClose()+
								" ,volume: "+arrData[counter-1].getVolume());

						setNewOrder(arrData[counter-1].getHigh(),arrData[counter-1].getLow(),arrData[counter].getTime());

					}
				}
			}
		}

	}
	private void setNewOrder(double high, double low,long time)
	{//the calculate the orders by the data
		
		double deltaHighToLow = high - low;
		double enterPrice = 0;
		double stopPrice;
		double takeProfitPrice;
		double quantityDouble;
		int quantityInt;
		double limitOrder = -1;

		//for long
		if (this.objLocal.getDirection() == this.objLocal.LONG)
		{	
			enterPrice = high + this.objLocal.getAddCentToBreak();//enter - price
			stopPrice = high - (this.objLocal.getBarTriger() * deltaHighToLow);//stop - price
			takeProfitPrice = high + (deltaHighToLow * this.objLocal.getPe());//take - profit

			//quantity
			quantityDouble = this.objLocal.getRiskPerTransactionsDolars() / (deltaHighToLow);
			quantityInt = (int) quantityDouble;
			if (quantityInt < 100)
			{
				quantityDouble = this.objLocal.getMaxRiskPerTransactionsDolars() / (deltaHighToLow);
				quantityInt = (int) quantityDouble;
			}

			if (this.objGlobal.getOrderType() == this.objGlobal.STOP_LIMIT)
			{
				limitOrder = enterPrice + (deltaHighToLow * this.objGlobal.getCentToGiveup());//limit order
			}

			System.err.println("in setNewOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + quantityInt
					+ " limitOrder: " + limitOrder
					);

			Order tempOrder = new Order(listOrders.size()+1,quantityInt,BUY,time,
					this.objGlobal.getOrderType(),enterPrice,limitOrder, 
					stopPrice,
					takeProfitPrice
					);
			//TODO- send order to BROKER
			listOrders.add(tempOrder);
		}

		//for short
		if (this.objLocal.getDirection() == this.objLocal.SHORT)
		{

			enterPrice = low - this.objLocal.getAddCentToBreak();//enter - price
			stopPrice = low + (this.objLocal.getBarTriger() * deltaHighToLow);//stop - price
			takeProfitPrice = low - (deltaHighToLow * this.objLocal.getPe());//take - profit

			//quantity
			quantityDouble = this.objLocal.getRiskPerTransactionsDolars() / (deltaHighToLow);
			quantityInt = (int) quantityDouble;
			if (quantityInt < 100)
			{
				quantityDouble = this.objLocal.getMaxRiskPerTransactionsDolars() / (deltaHighToLow);
				quantityInt = (int) quantityDouble;
			}

			if (this.objGlobal.getOrderType() == this.objGlobal.STOP_LIMIT)
			{
				limitOrder = enterPrice - (deltaHighToLow * this.objGlobal.getCentToGiveup());//limit order
			}

			System.err.println("in setNewOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + quantityInt
					+ " limitOrder: " + limitOrder
					);

			/*public Order(int id, int quantity, int action, long time,
			int typeOrdrEnter,double enterPrice,double limitPrice,//for enterPrice
			double stopPrice,
			double takeProfitPrice
			)*/
			Order tempOrder = new Order(listOrders.size()+1,quantityInt,SELL,time,
					this.objGlobal.getOrderType(),enterPrice,limitOrder, 
					stopPrice,
					takeProfitPrice
					);
			//TODO- send order to BROKER
			listOrders.add(tempOrder);

		}


	}
	private void canSearchForNewOrder()
	{

		if (!this.objGlobal.getStopRobot())
		{
			if (checkEarlierTime(this.objGlobal.getTimeStopAddOrder()))
			{
				//TODO-if add max risk
				if (true)
				{	
					if (listOrders.size() == 0)//where is no order
					{
						algoTrade(false);//run alogTread
						return;
					}
					
					int counActiveOrders = countActiveOrders();//number of the active orders in list
					int countUnActiveOrders = listOrders.size() - counActiveOrders;//number of the unactive orders in list(orders that finish)
					
					if ((counActiveOrders + countUnActiveOrders) < this.objLocal.getMaxTransactionsPerDay())
					{
						if (counActiveOrders == 0)//there is no future orders that active
						{//run alogTread
							algoTrade(false);
							return;
						}
					}
					
					for (int i = 0; i<listOrders.size(); i++)
					{
						if (listOrders.get(i).getEnter().getStatus() == WAIT)//if the enter order is wait to execute
						{
							algoTrade(true);//to find a new bar 
						}
						
					}
				}
			}
		}
	}//finish class	
	private boolean checkEarlierTime(String timeToBreak)
	{//the function get time in format(hh:mm), and return true if current time bar is before the input time

		long currTimeLong = arrData[counter].getTime()*1000L;;

		Date dateCurr = new Date(currTimeLong);

		//time to break
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date dateToBreak = null;
		try {
			dateToBreak = formatter.parse(timeToBreak);
			//copy YYYY-MM-DD
			dateToBreak.setYear(dateCurr.getYear());
			dateToBreak.setMonth(dateCurr.getMonth());
			dateToBreak.setDate(dateCurr.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (dateCurr.before(dateToBreak))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private int countActiveOrders()
	{
		int countActiveOrders = 0;
		for (int i = 0; i < listOrders.size(); i++)
		{
			if (listOrders.get(i).isActive())
			{
				countActiveOrders++;
			}
		}
		return countActiveOrders;
		
	}


		










}//end class
