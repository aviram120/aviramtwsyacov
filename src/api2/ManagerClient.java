package api2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ayAPI.Indicators;
import ayAPI.barByInterval;
import ayAPI.globalVar;
import ayAPI.localVar;
import ayAPI.managerBroker;
import ayAPI.order;




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
	
	private static Logger Logger;

	private globalVar objGlobal;
	private localVar objLocal;
	private int threadId;
	private String symbol;
	private int interval;

	private List<Order> listOrders;//list of all the order by sybmol
	private barByInterval[] arrData;//the graph
	private int counter;

	private Indicators indicatorsClass;
	private managerBroker managerBroker;


	public ManagerClient(int threadId, globalVar tempGlobal, localVar tempLocal,int portNumberToserverChat)
	{
		Logger = LoggerFactory.getLogger(ManagerClient.class);
		
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
		managerBroker = new managerBroker(threadId,portNumberToserverChat);
		
	}
	public void addBarToGraph(barByInterval tempBar)
	{//the function add the data to arrData that save the graph
		
		arrData[counter] = new barByInterval(tempBar);

		String dataFromBroker = "barByInterval = time:"+ arrData[counter].getTimeInNY() +" , high:"+ arrData[counter].getHigh() +" , low: "+arrData[counter].getLow()+" ,open: "+arrData[counter].getOpen()+" , close: "+arrData[counter].getClose()+" , vol: "+arrData[counter].getVolume();
		//System.err.println(dataFromBroker);
		Logger.info(dataFromBroker);
		
		//TODO- read from file - to get update
		//canSearchForNewOrder();
		algoTrade(false, -1);//only for test
		
		//TODO - 
		//updateStatusOrders();
		//updateStopOrders();
		
		counter++;
	}








	//*******************************************************
	//**************ALGORITHM
	//*******************************************************
	private void algoTrade(boolean haveFuterOrder,int indexInList)
	{//the function search for triger in graph, if find set a new order.
		//the function input: 1. flag if the order allrdy have trigr and need to improve the triger(if true 'indexInList' is the index of the order in listOrder)
		
		double deltaHighToLow = arrData[counter].getHigh() - arrData[counter].getLow();

		if ((arrData[counter].getVolume() >= this.objLocal.getMinVolume()) && 
				(deltaHighToLow >= this.objLocal.getMinBarSize()) &&
				(deltaHighToLow <= this.objLocal.getMaxBarSize()))
		{
			System.out.println("in algoTrade");
			Logger.info("in algoTrade");
			//==============movingAver strategy==============
			if (this.objLocal.getStrategy() == this.objLocal.MOVING_AVR)
			{
				double movingAver = indicatorsClass.calculateMovingAverage(arrData, this.objLocal.getMovingAvrBar() , counter);
				//double movingAver10 = indicatorsClass.calculateMovingAverage(arrData, 10 , counter);

				if (movingAver != -1)
				{
					if ((arrData[counter].getHigh()>movingAver) && (arrData[counter].getLow()<movingAver))
					{
						String stOrderMovingAver = "from algoTrade:"+ "strategy: "+"movingAver"+ 
								" ,time: "+arrData[counter].getTime() +
								" ,open: "+arrData[counter].getOpen()+
								" ,high: "+arrData[counter].getHigh()+
								" ,low: "+arrData[counter].getLow()+
								" ,close: "+arrData[counter].getClose()+
								" ,volume: "+arrData[counter].getVolume();
						
						//System.err.println(stOrderMovingAver);
						Logger.info("movingAver set order:"+stOrderMovingAver);				
						
						if (haveFuterOrder)//update order
						{
							updateOrder(indexInList, arrData[counter].getHigh(), arrData[counter].getLow(),arrData[counter].getTime());
						}
						else//new order
						{
							setNewOrder(arrData[counter].getHigh(), arrData[counter].getLow(),arrData[counter].getTime());
						}

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
						String stOrderInsideBar = "from algoTrade:"+ "strategy: "+"inside bar"+ 
								" ,time: "+arrData[counter-1].getTime() +
								" ,open: "+arrData[counter-1].getOpen()+
								" ,high: "+arrData[counter-1].getHigh()+
								" ,low: "+arrData[counter-1].getLow()+
								" ,close: "+arrData[counter-1].getClose()+
								" ,volume: "+arrData[counter-1].getVolume();
						
						Logger.info("inside-bar set order:"+stOrderInsideBar);
						//System.err.println(stOrderInsideBar);
						
						
						if (haveFuterOrder)//update order
						{
							updateOrder(indexInList, arrData[counter-1].getHigh(), arrData[counter-1].getLow(),arrData[counter-1].getTime());
						}
						else
						{//set new order
							setNewOrder(arrData[counter-1].getHigh(),arrData[counter-1].getLow(),arrData[counter].getTime());
						}
					
					}
				}
			}
		}

	}
	private void updateOrder(int indexInList,double high, double low,long time)
	{//the function update the order. indexInList- index of the order in the listOrder
		
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

			if (this.objGlobal.getOrderType() == this.objGlobal.STOP_LIMIT)
			{
				limitOrder = enterPrice + (deltaHighToLow * this.objGlobal.getCentToGiveup());//limit order
			}

			System.err.println("in updateOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + this.listOrders.get(indexInList).getQuantity()
					+ " limitOrder: " + limitOrder
					);
			
			//update order in list
			listOrders.get(indexInList).updateOrder(time,this.counter,enterPrice,limitOrder,stopPrice,takeProfitPrice);
			//TODO- send order to BROKER - update order

		}

		//for short
		if (this.objLocal.getDirection() == this.objLocal.SHORT)
		{

			enterPrice = low - this.objLocal.getAddCentToBreak();//enter - price
			stopPrice = low + (this.objLocal.getBarTriger() * deltaHighToLow);//stop - price
			takeProfitPrice = low - (deltaHighToLow * this.objLocal.getPe());//take - profit

			if (this.objGlobal.getOrderType() == this.objGlobal.STOP_LIMIT)
			{
				limitOrder = enterPrice - (deltaHighToLow * this.objGlobal.getCentToGiveup());//limit order
			}

			System.err.println("in setNewOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + this.listOrders.get(indexInList).getQuantity()
					+ " limitOrder: " + limitOrder
					);
			//update order in list
			listOrders.get(indexInList).updateOrder(time,this.counter,enterPrice,limitOrder,stopPrice,takeProfitPrice);
			//TODO- send order to BROKER - update order
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

			String stNewLongOrder = "in setNewOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + quantityInt
					+ " limitOrder: " + limitOrder;
			
			//System.err.println(stNewLongOrder);
			Logger.info("setNewOrder set long order:"+stNewLongOrder);
			
			Order tempOrder = new Order(listOrders.size()+1,quantityInt,BUY,time,this.counter,
					this.objGlobal.getOrderType(),enterPrice,limitOrder, 
					stopPrice,
					takeProfitPrice
					);
			
			//managerBroker.placeOrder(this.symbol, tempOrder);
			//TODO- send order to BROKER- need to pars the response with id of the orders
			listOrders.add(tempOrder);
			//TODO- add to orders the idSrver of the order
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

			String stNewOrderShort = "in setNewOrder: " + "enterPrice: " + enterPrice
					+ " stopPrice: " + stopPrice
					+ " takeProfitPrice: " + takeProfitPrice
					+ " quantity: " + quantityInt
					+ " limitOrder: " + limitOrder;
			
			//System.err.println(stNewOrderShort);
			Logger.info("setNewOrder set short order:"+stNewOrderShort);

			Order tempOrder = new Order(listOrders.size()+1,quantityInt,SELL,time,this.counter,
					this.objGlobal.getOrderType(),enterPrice,limitOrder, 
					stopPrice,
					takeProfitPrice
					);
			
			//managerBroker.placeOrder(this.symbol, tempOrder);
			//TODO- send order to BROKER- need to pars the response with id of the orders
			listOrders.add(tempOrder);

		}


	}
	private void canSearchForNewOrder()
	{//the function decide if can set new order - if yes call to 'algoTrade' function
		//Additionally if have orders that 'wait' try to find new triger

		if (!this.objGlobal.getStopRobot())
		{
			if (checkEarlierTime(this.objGlobal.getTimeStopAddOrder()))
			{
				//TODO-if add max risk
				if (true)
				{	
					if (listOrders.size() == 0)//where is no order
					{
						algoTrade(false,-1);//run alogTread
						return;
					}
					
					int counActiveOrders = countActiveOrders();//number of the active orders in list
					int countUnActiveOrders = listOrders.size() - counActiveOrders;//number of the unactive orders in list(orders that finish)
					
					if ((counActiveOrders + countUnActiveOrders) < this.objLocal.getMaxTransactionsPerDay())
					{
						if (counActiveOrders == 0)//there is no future orders that wait
						{//run alogTread
							algoTrade(false,-1);
							return;
						}
					}	
				}
				
				//Future orders- try to find new trigr
				for (int i = 0; i<listOrders.size(); i++)
				{
					if (listOrders.get(i).getEnter().getStatus() == WAIT)//if the enter order is wait to execute
					{
						int numOfBar = this.counter - listOrders.get(i).getCounterArr();//number of bar that pass since placing order
						if (numOfBar < objLocal.getNumBarToCancelDeal())
						{
							algoTrade(true,i);//to find a new bar
						}
						else
						{
							cancelOrder(i);
						}
					}
				}
				
			}
		}
	}
	private void updateStatusOrders()
	{//the function update status orders(enter/stop/takeProfit) in listOrder
		
		for (Order orderTemp:listOrders)
		{
			if (orderTemp.getEnter().getStatus() == WAIT)//if ENTER order is wait
			{
				if (orderTemp.getAction() == orderTemp.BUY)//for buy
				{
					if (arrData[counter].getHigh() > orderTemp.getEnter().getEnterPrice())//order is active
					{
						System.out.println("order is active");
						orderTemp.orderIsActive(counter);//update orders status						
					}					
				}
				else///for sell
				{
					if (arrData[counter].getLow() < orderTemp.getEnter().getEnterPrice())
					{
						System.out.println("order is active");
						orderTemp.orderIsActive(counter);//update orders status	
					}
				}
			}
			
			if (orderTemp.getStop().getStatus() == ACTIVE)//if STOP order is active
			{
				if (orderTemp.getAction() == BUY)//for buy
				{
					if (arrData[counter].getLow() < orderTemp.getStop().getStopPrice())
					{
						System.out.println("order is not active - in stop");
						orderTemp.closedOrder();
					}
				}
				else///for sell
				{
					if (arrData[counter].getHigh() > orderTemp.getStop().getStopPrice())
					{
						System.out.println("order is not active - in stop");
						orderTemp.closedOrder();
					}
				}	
			}
			
			if (orderTemp.getTakeProfit().getStatus() == ACTIVE)//if TAKE_PROFIT order is active
			{
				if (orderTemp.getAction() == BUY)//for buy
				{
					if (arrData[counter].getHigh() > orderTemp.getTakeProfit().getTakeProfitPrice())
					{
						System.out.println("order is not active - in take profit");
						orderTemp.closedOrder();
					}
				}
				else///for sell
				{
					if (arrData[counter].getLow() < orderTemp.getTakeProfit().getTakeProfitPrice())
					{
						System.out.println("order is not active - in take profit");
						orderTemp.closedOrder();
					}
				}	
				
			}
			
			
			
			
			
			
		}//end for
		
		
		
	}
	private void updateStopOrders()
	{//function check if can update stop of all orders that active
		
		for (Order orderTemp:listOrders)
		{
			if (orderTemp.isActive())
			{
				//TODO- fix to when the order start
				int numOfBar = this.counter - orderTemp.getCounterArrWhenOrderIsActive();//number of bar that past from the deal start
				
				if (numOfBar >= objGlobal.getDefineNextStop())
				{
					if (objGlobal.getStopType() == objGlobal.STOP_LOW_OR_HIGHT)
					{
						if(objLocal.getDirection() == objLocal.LONG)
						{
							//TODO- add delta-talk with hagi
							double lowOfBar = arrData[orderTemp.getCounterArr()].getLow() - objLocal.getExtarPrice();
							if (arrData[counter].getClose() <= lowOfBar)
							{//close the order in market
								
								//TODO-sent to broket to closed the deal
								orderTemp.closedOrder();
							}
							else
							{
								orderTemp.getStop().setStopPrice(lowOfBar);
								//TODO- sent to broker to update the stop price
							}	
						}
						else//for short
						{
							//TODO- add delta-talk with hagi
							double highOfBar = arrData[orderTemp.getCounterArr()].getHigh() + objLocal.getExtarPrice();
							if (arrData[counter].getClose() >= highOfBar)
							{//close the order in market

								//TODO-sent to broket to closed the deal
								orderTemp.closedOrder();
							}
							else
							{
								orderTemp.getStop().setStopPrice(highOfBar);
								//TODO- sent to broker to update the stop price		
							}
							
						}
					}
					else//objGlobal.STOP_IN_CLOSED
					{
						if(objLocal.getDirection() == objLocal.LONG)
						{
							double lowOfBarTriger = arrData[orderTemp.getCounterArr()].getLow();
							double lowCurrentBar = arrData[counter].getLow();
							
							if (lowOfBarTriger > lowCurrentBar)
							{//set new stop
								
								//TODO- add delta-talk with hagi
								double newStop = lowCurrentBar - objLocal.getExtarPrice();
								orderTemp.getStop().setStopPrice(newStop);
								//TODO- sent to broker to update the stop price		
							}
						}
						else//for short
						{
							double highOfBarTriger = arrData[orderTemp.getCounterArr()].getHigh();
							double highCurrentBar = arrData[counter].getHigh();
							
							if (highOfBarTriger < highCurrentBar)
							{
								//TODO- add delta-talk with hagi
								double newStop = highCurrentBar + objLocal.getExtarPrice();
								orderTemp.getStop().setStopPrice(newStop);
								//TODO- sent to broker to update the stop price
							}
						}			
					}
					
				}	
			}	
		}//end for
		
	}
	
	private void cancelOrder(int indexInList) 
	{//the funcion update the status of the object at list
		//and send to broker , cancel the order
		
		listOrders.get(indexInList).cencelOrder();
		//TODO - cancel order in broker
		
		
	}
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
	{//the function return number of the order that: 
		//1.Transactions entered into action
		//2.Transactions awaiting execution trigger
		
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
