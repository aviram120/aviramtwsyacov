package ayAPI;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.TagValue;
import com.ib.client.CommissionReport;
import com.ib.client.UnderComp;
import com.ib.controller.ApiController.ITimeHandler;

// RealTimeBars Class is an implementation of the 
// IB API EWrapper class

public class managerRealTimeData implements EWrapper
{
	
	private int nextOrderID = 0;// Keep track of the next ID
	//private EClientSocket client = null;// The IB API Client Socket object

	private EClientSocket client;// The IB API Client Socket object


	private fiveSec[] arrFiveSec;//array to hold all 5-sec values.
	private int countResponseFromTws;
	private barByInterval barObj;
	private final int NUMBER_OF_RECORDS_BY_INTERVAL;
	private final int INTERVAL_GRAPH;
	private boolean isSync;
	private String symbol;


	private ServerSocket serverSocket;
	Socket server;
	OutputStream outToServer;
	DataOutputStream out;


	public managerRealTimeData(String symbol, int intervalGraph, int portSocket)
	{

		makeSocketConnection(portSocket);
		this.symbol = symbol;
		this.isSync = false;
		this.INTERVAL_GRAPH = intervalGraph;
		this.NUMBER_OF_RECORDS_BY_INTERVAL =  intervalGraph*60/5;
		arrFiveSec = new fiveSec[this.NUMBER_OF_RECORDS_BY_INTERVAL];
		countResponseFromTws = 0;

		getBarsByInterval(symbol);
	} 

	public void getBarsByInterval(String symbol)
	{
		client = new EClientSocket (this);// Create a new EClientSocket object
		client.eConnect (null, 7496, 0);// Connect to the TWS or IB Gateway application


		try // Pause here for connection to complete
		{
			// Thread.sleep(1000);//one sec
			while (! (client.isConnected()));
		} catch (Exception e) 
		{
			e.printStackTrace ();
		}

		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = symbol;
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";

		Vector<TagValue> realTimeBarsOptions = new Vector<TagValue>();// Create a TagValue list

		client.reqRealTimeBars(0, contract,5,"TRADES",false,realTimeBarsOptions);// will be returned via the realtimeBar method

		/*
		// Create a new contract
		Contract contract2 = new Contract ();
		contract2.m_symbol = "FB";
		contract2.m_exchange = "SMART";
		contract2.m_secType = "STK";
		contract2.m_currency = "USD";

		Vector<TagValue> realTimeBarsOptions2 = new Vector<TagValue>();// Create a TagValue list

		client.reqRealTimeBars(1, contract2,5,"TRADES",false,realTimeBarsOptions2);// will be returned via the realtimeBar method

		*/
	} 

	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count)
	{
		if (!this.isSync)//sync to system time
		{
			System.out.println("please wait whill sync");
			long mili = time*1000L;
			int secondsStart = (int) (mili / 1000) % 60 ;
			int minutesStart = (int) ((mili / (1000*60)) % 60);

			if ((minutesStart % this.INTERVAL_GRAPH)==0)
			{
				if (secondsStart == 0)
				{
					this.isSync = true;
					System.out.println("is sync!");
					getHistoryByStrategy(mili);
				}
				else
					return;
			}
			else
				return;

		}
		//get history

		fiveSec fiveSecObj;

		try
		{
			System.out.println("realtimeBar: reqId: "+reqId +", time:"+ time + "," + open + "," + high + "," + low + "," + close + ",volume: " +volume + ", counter:"+countResponseFromTws);

			fiveSecObj = new fiveSec(time,open,high,low,close,volume,countResponseFromTws);
			arrFiveSec[countResponseFromTws] = fiveSecObj;
			countResponseFromTws++;

			if (countResponseFromTws == NUMBER_OF_RECORDS_BY_INTERVAL)
			{

				barObj = new barByInterval(arrFiveSec, NUMBER_OF_RECORDS_BY_INTERVAL, time,this.symbol);//make the bar values

				System.err.println("barByInterval: barSize:"+this.INTERVAL_GRAPH+ " symbol:" + this.symbol+" , high:"+ barObj.getHigh() +" , low: "+barObj.getLow()+" ,open: "+barObj.getOpen()+" , close: "+barObj.getClose()+" , vol: "+barObj.getVolume());
				//System.err.println(barObj.convertToJSON());

				countResponseFromTws = 0;

				writeToSocket(barObj.convertToJSON());
				
				//TODO-sent to socket-client
				/*out = new DataOutputStream(outToServer);
				out.writeUTF(barObj.convertToJSON());	*/


			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}
	private void writeToSocket(String st)
	{//the function write to socket 
		
		out = new DataOutputStream(outToServer);
		try {
			out.writeUTF(st);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void getHistoryByStrategy(long timeInMilisec)
	{//the funtion return the history of the graph
		
		//TODO- fix to there var - by input
		int movingAvr = -1;
		int strategy = 1;//1 insideBar; 2 moving Avrag
		
		final int SEC_IN_MIN = 60; 
		
		// Pass date object
		Date date = new Date(timeInMilisec);
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    	
    	String endTime = formatter.format(date);
    	//System.out.println(endTime);
    	
    	String DurationInSec = "";
    	if (strategy == 1)//insideBar strategy
    	{
    		int countOfBar = 3;
    		DurationInSec = Integer.toString(this.INTERVAL_GRAPH*countOfBar*SEC_IN_MIN);
    	}
    	else
    	{//movingAvrage strategy
    		if (movingAvr!=-1)
    		{
        		DurationInSec = Integer.toString(((this.INTERVAL_GRAPH*movingAvr)+this.INTERVAL_GRAPH)*SEC_IN_MIN);//bar in graph * which movingAvrag *60 sec
    		}

    	}
    	
    	String barSize ="";
    	if (this.INTERVAL_GRAPH == 1)
    	{
    		barSize = this.INTERVAL_GRAPH+" min";
    	}
    	else//the difference is in "s"
    	{
    		barSize = this.INTERVAL_GRAPH+" mins";
    	}
    	
    	/*System.out.println("DurationInSec:"+DurationInSec);
    	System.out.println("strategy:"+strategy+" ,timeFream:"+this.INTERVAL_GRAPH+" , movingAv:"+movingAvr);
    	*/
    	
		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = this.symbol;
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";
		// Create a TagValue list for chartOptions
		Vector<TagValue> chartOptions = new Vector<TagValue>();
		// Make a call to start off data historical retrieval
		client.reqHistoricalData(0, contract, 
										 endTime,  // End Date/Time yyyymmdd hh:MM:SS
						                 DurationInSec+" S",                // Duration (S,D)
						                 barSize ,              // Bar size
						                 "TRADES",             // What to show
						                 1,                    // useRTH
						                 1, 
                                         chartOptions);
		
		
		
	}
	
	public void currentTime(long time)
	{	
		long currentDateTime = time*1000L;

		System.out.println("corrent time: "+ currentDateTime);

	}

	private void makeSocketConnection(int port)
	{
		try 
		{
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		
			try
			{
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				server = serverSocket.accept();
				outToServer = server.getOutputStream();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
			}
			catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				//break;
			}catch(IOException e)
			{

				e.printStackTrace();
				//break;
			}
		
	}

	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps)
	{

		
		// Display Historical data
		try 
		{
			if (open!= -1)
			{
				String symbolTemp = this.symbol;
				long timeTemp = convertDataToMilisec(date.toString());
				double openTemp = open;
				double highTemp = high;
				double lowTemp = low;
				double closeTemp = close;
				long volumeTemp = volume;
				
				barByInterval tempBar = new barByInterval(symbolTemp, timeTemp, openTemp, highTemp, lowTemp, closeTemp, volumeTemp);
				
				String outputSt = tempBar.convertToJSON();
				System.err.println(outputSt);
				writeToSocket(outputSt);
			}
				
			/*System.out.println("historicalData: data: " + date + ", open:" + 
					open + ", high:" + high  + ", low:" + low  + ", close:" + close + ", volum:" +
					volume);*/
		} 
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		
	}

	private long convertDataToMilisec(String dataString)
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date = null;
		try {
			date = sdf.parse(dataString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime()/1000;
	}






	//****************
	//unused function
	//****************
	public void bondContractDetails(int reqId, ContractDetails contractDetails)
	{
	}

	public void contractDetails(int reqId, ContractDetails contractDetails)
	{
	}

	public void contractDetailsEnd(int reqId)
	{
	}

	public void fundamentalData(int reqId, String data)
	{
	}

	public void bondContractDetails(ContractDetails contractDetails)
	{
	}

	public void contractDetails(ContractDetails contractDetails)
	{
	}



	public void displayGroupList(int requestId, String contraftInfo)
	{
	}


	public void displayGroupUpdated(int requestId, String contractInfo)
	{
	}

	public void verifyCompleted(boolean completed, String contractInfo)
	{
	}
	public void verifyMessageAPI(String message)
	{
	}

	public void execDetails(int orderId, Contract contract, Execution execution)
	{
	}

	public void execDetailsEnd(int reqId)
	{
	}

	public void managedAccounts(String accountsList)
	{
	}

	public void commissionReport(CommissionReport cr)
	{
	}

	public void position(String account, Contract contract, int pos, double avgCost)
	{
	}

	public void positionEnd()
	{
	}

	public void accountSummary(int reqId, String account, String tag, String value, String currency)
	{
	}

	public void accountSummaryEnd(int reqId)
	{
	}

	public void accountDownloadEnd(String accountName)
	{
	}

	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState)
	{
	}

	public void openOrderEnd()
	{
	}


	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld)
	{
	}

	public void receiveFA(int faDataType, String xml)
	{
	}

	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr)
	{
	}

	public void scannerDataEnd(int reqId)
	{
	}

	public void scannerParameters(String xml)
	{
	}

	public void tickEFP(int symbolId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry)
	{
	}

	public void tickGeneric(int symbolId, int tickType, double value)
	{
	}

	public void tickOptionComputation( int tickerId, int field, 
			double impliedVol, double delta, double optPrice, 
			double pvDividend, double gamma, double vega, 
			double theta, double undPrice)
	{
	}


	public void deltaNeutralValidation(int reqId, UnderComp underComp) 
	{
	}


	public void updateAccountTime(String timeStamp)
	{
	}

	public void updateAccountValue(String key, String value, String currency,
			String accountName)
	{
	}

	public void updateMktDepth(int symbolId, int position, int operation,
			int side, double price, int size)
	{
	}

	public void updateMktDepthL2(int symbolId, int position,
			String marketMaker, int operation, int side, double price, int size)
	{
	}

	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange)
	{
	}

	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName)
	{
	}

	public void marketDataType(int reqId, int marketDataType)
	{
	}

	public void tickSnapshotEnd(int tickerId)
	{
	}

	public void connectionClosed()
	{
	}




	public void error(Exception e)
	{
		// Print out a stack trace for the exception
		e.printStackTrace ();
	}

	public void error(String str)
	{
		// Print out the error message
		System.err.println (str);
	}

	public void error(int id, int errorCode, String errorMsg)
	{
		// Overloaded error event (from IB) with their own error 
		// codes and messages
		System.err.println ("error: " + id + "," + errorCode + "," + errorMsg);
	}

	public void nextValidId (int orderId)
	{
		// Return the next valid OrderID
		nextOrderID = orderId;
	}


	public void tickPrice(int orderId, int field, double price,
			int canAutoExecute)
	{

	}

	public void tickSize (int orderId, int field, int size)
	{
	}

	public void tickString (int orderId, int tickType, String value)
	{
	}



} // end public class 




