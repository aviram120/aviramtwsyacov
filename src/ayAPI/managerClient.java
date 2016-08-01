package ayAPI;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;



public class managerClient {

	private int portSocket;
	private int interval;

	private barByInterval[] arrData;
	private int counter;

	private List<symbolIndex> listSymbol;//list of all the order by sybmol

	private String serverName ="localhost";
	private Indicators indicatorsClass;

	public managerClient(int interval,int portSocket)
	{
		this.interval = interval;
		this.portSocket = portSocket;

		listSymbol = new LinkedList<symbolIndex>();

		arrData = new barByInterval[390/interval];
		counter = 0;

		indicatorsClass = new Indicators();//class for indicators
	}

	public void getData()
	{//the function start the socket to get data from broker

		try
		{
			System.out.println("Connecting to " + serverName +" on port " + portSocket);
			Socket client = new Socket(serverName, portSocket);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			DataInputStream in;
			while (true)
			{				
				in = new DataInputStream(client.getInputStream());

				arrData[counter] = new barByInterval(in.readUTF());
				
				System.err.println("barByInterval from client: symbol:"+ arrData[counter].getSymbol() +" , high:"+ arrData[counter].getHigh() +" , low: "+arrData[counter].getLow()+" ,open: "+arrData[counter].getOpen()+" , close: "+arrData[counter].getClose()+" , vol: "+arrData[counter].getVolume());

				//indicatorsClass.calculateVWAP(arrData, counter);

				counter++;

			}
			// client.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}



	}




	private void algoTrade(globalVar globalVarObj, localVar localVarObj, boolean haveFuterOrder, int indexOfSymbol)
	{
		double deltaHighToLow = arrData[counter].getHigh() - arrData[counter].getLow();

		if ((arrData[counter].getVolume() >= localVarObj.getMinVolume()) && 
				(deltaHighToLow >= localVarObj.getMinBarSize()) &&
				(deltaHighToLow <= localVarObj.getMaxBarSize()))
		{
			if (haveFuterOrder)
			{
				//TODO-find the futer orders in the list			
			}


			//==============movingAver strategy==============
			if (localVarObj.getStrategy() == localVarObj.MOVING_AVR)
			{
				double movingAver5 = indicatorsClass.calculateMovingAverage(arrData, 5 , counter);
				double movingAver10 = indicatorsClass.calculateMovingAverage(arrData, 10 , counter);

				if ((arrData[counter].getHigh()>movingAver5) && (arrData[counter].getHigh()>movingAver10))
				{
					
					System.err.println("from algoTrade:"+ "strategy: "+"movingAver"+ 
							" ,time: "+arrData[counter].getTime() +
							" ,open: "+arrData[counter].getOpen()+
							" ,high: "+arrData[counter].getHigh()+
							" ,low: "+arrData[counter].getLow()+
							" ,close: "+arrData[counter].getClose()+
							" ,volume: "+arrData[counter].getVolume());
					
					

				}
			}


			//==============inside-bar strategy==============
			if (localVarObj.getStrategy() == localVarObj.INSIDE_BAR)
			{
				if ((arrData[counter].getHigh() <= arrData[counter-1].getHigh()) && (arrData[counter].getLow() >= arrData[counter-1].getLow()))
				{
					System.err.println("from algoTrade:"+ "strategy: "+"inside bar"+ 
							" ,time: "+arrData[counter].getTime() +
							" ,open: "+arrData[counter].getOpen()+
							" ,high: "+arrData[counter].getHigh()+
							" ,low: "+arrData[counter].getLow()+
							" ,close: "+arrData[counter].getClose()+
							" ,volume: "+arrData[counter].getVolume());

				}
			}
		}



	}
	private order setNewOrder(globalVar globalVarObj, localVar localVarObj, double high, double low)
	{
		order tempOrder = null;
		double deltaHighToLow = high - low;
		double enterPrice = 0;
		double stopPrice;
		double takeProfitPrice;
		int quantity;
		double limitOrder;
		
		//globalVarObj.getOrderType();centToGiveup,
		
		//localVarObj: direction, addCentToBreak
		
		if (localVarObj.getDirection() == localVarObj.LONG)
		{
			
			if (globalVarObj.getOrderType() == globalVarObj.MARKET)
			{
				//enter - price
				enterPrice = high + localVarObj.getAddCentToBreak();
				
				//stop - price
				//stopPrice = enterPrice - (globalVarObj.getBarTriger() * (deltaHighToLow + localVarObj.getAddCentToBreak()));
				stopPrice = high - (globalVarObj.getBarTriger() * deltaHighToLow);
				
				//take - profit
				//takeProfitPrice = enterPrice + (deltaHighToLow * localVarObj.getPe());
				takeProfitPrice = high + (deltaHighToLow * localVarObj.getPe());
				
				//quantity
				quantity = (int)(localVarObj.getRiskPerTransactionsDolars() / (deltaHighToLow));
				
				if (quantity < 100)
				{
					quantity = (int) (localVarObj.getMaxRiskPerTransactionsDolars() / (deltaHighToLow));
				}
			}
			
			if (globalVarObj.getOrderType() == globalVarObj.STOP_LIMIT)
			{
				//enter - price
				enterPrice = high + localVarObj.getAddCentToBreak();
				
				//limit order
				limitOrder = high + (deltaHighToLow * globalVarObj.getCentToGiveup());
				
				//stop - price
				//stopPrice = enterPrice - (enterPrice * globalVarObj.getBarTriger());
				stopPrice = high - (deltaHighToLow * globalVarObj.getBarTriger());

				
				//take - profit
				//takeProfitPrice = enterPrice + (deltaHighToLow * localVarObj.getPe());
				takeProfitPrice = high + (deltaHighToLow * localVarObj.getPe());
				
				//quantity
				quantity = (int)(localVarObj.getRiskPerTransactionsDolars() / (deltaHighToLow));
				
				if (quantity < 100)
				{
					quantity = (int) (localVarObj.getMaxRiskPerTransactionsDolars() / (deltaHighToLow));
				}
			}
			
			
			
			
			
		}
		
		if (localVarObj.getDirection() == localVarObj.SHORT)
		{
			
		}
		
		
		//stop
		
		//take-profit
		
		//quntuty
		
		
		return tempOrder;
		
	}
	
	private void canSearchForNewOrder(globalVar globalVarObj, localVar localVarObj)
	{

		if (!globalVarObj.getStopRobot())
		{
			if (checkEarlierTime(globalVarObj.getTimeStopAddOrder()))
			{
				//TODO-if add max risk
				if (true)
				{	

					int answer = isHaveOpenOrder2(localVarObj.getSymbol());//read the Documentation of the funtion
					if (answer == -1)//don't exeist in the listSymbol
					{
						algoTrade(globalVarObj,localVarObj,false,-1);
					}

					if (listSymbol.get(answer).getNumOfActiveOrCloseOrders() < localVarObj.getMaxTransactionsPerDay())
					{
						if (answer == -2)//exeist in the listSymbol and don't have futer orders
						{
							algoTrade(globalVarObj,localVarObj,false,-1);
						}
						else// (answer>=0) => exeist in the listSymbol and have futer orders (indicet the index in listSymbol)
						{
							algoTrade(globalVarObj,localVarObj,true,answer);
						}
					}



					

				}
			}
		}

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


	//function of "listSymbol"
	private void addOrder(String symbol, order newOrder)
	{/*the function check id the 'symbol' is in the list - if "true" - add to listSymbol.
	   else make a new object in listSymbol - and add the order
	 */


		for (int i = 0; i<listSymbol.size(); i++)
		{
			if (listSymbol.get(i).getSymbol() == symbol)//the symbol is in the list
			{
				listSymbol.get(i).addNewOrderToSymbol(newOrder);

				break;
			}
		}

		//is not found in list - add new symbol to list		
		symbolIndex tempNewSymbol= new symbolIndex(symbol,newOrder);//add new symbol(and order) to list
		this.listSymbol.add(tempNewSymbol);


	}
	private boolean isHaveOpenOrder(String symbolToCheak)//TODO- nned to delete this function
	{//the function cheak is there is active/wait order for the symbol- input


		for (int i = 0; i<listSymbol.size(); i++)
		{
			if (listSymbol.get(i).getSymbol() == symbolToCheak)//the symbol is in the list
			{
				/*	if (listSymbol.get(i).getNumOfActiveOrWaitOrders() > 0)
				{
					return true;
				}
				else
				{
					return false;
				}*/
				return listSymbol.get(i).searchIfHaveOpenOrder();
			}
		}

		//the symbol isn't in list
		return false;

	}

	private int isHaveOpenOrder2(String symbolToCheak)
	{/*the function cheak is there is active/wait order for the symbol- input
		if there is no symbol in list return -1
		if the symbol in list but don't hava futer order return -2
		else - return the index of the symbol in the list
	 */



		for (int i = 0; i<listSymbol.size(); i++)
		{
			if (listSymbol.get(i).getSymbol() == symbolToCheak)//the symbol is in the list
			{
				if (listSymbol.get(i).isHaveFuterOrders())
				{
					return i; //hava futer orders
				}
				else
				{
					return -2;//don't have futer orders
				}

			}
		}


		return -1;//the symbol isn't in list

	}
}