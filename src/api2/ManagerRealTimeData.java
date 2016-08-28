package api2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ayAPI.barByInterval;
import ayAPI.fiveSec;
import ayAPI.globalVar;
import ayAPI.localVar;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.TagValue;
import com.ib.client.UnderComp;

public class ManagerRealTimeData implements EWrapper {
	//the class get real time data from broker , sent it to managerClient
	
	private static Logger Logger;
	private globalVar objGlobal;
	private localVar objLocal;

	private EClientSocket client;// The IB API Client Socket object
	private int threadId;//the id of thread

	private fiveSec[] arrFiveSec;//array to hold all 5-sec values.
	private int countResponseFromTws;
	private barByInterval barObj;
	
	private final int NUMBER_OF_RECORDS_BY_INTERVAL;
	private final int INTERVAL_GRAPH;
	private boolean isSync;
	private String symbol;
	
	private ManagerClient mngClient;
	



	public ManagerRealTimeData(int threadId, globalVar tempGlobal, localVar tempLocal,int portNumberToserverChat)
	{
		Logger = LoggerFactory.getLogger(ManagerRealTimeData.class);
		
		this.threadId = threadId;
		this.objGlobal = tempGlobal;
		this.objLocal = tempLocal;

		this.symbol = tempLocal.getSymbol();
		this.isSync = false;
		this.INTERVAL_GRAPH = tempLocal.getInterval();
		this.NUMBER_OF_RECORDS_BY_INTERVAL =  tempLocal.getInterval()*60/5;
		arrFiveSec = new fiveSec[this.NUMBER_OF_RECORDS_BY_INTERVAL];
		countResponseFromTws = 0;
		
		mngClient = new ManagerClient(threadId, tempGlobal, tempLocal,portNumberToserverChat);
		
		getBarsByInterval();
	}
	public void disconnect()
	{
		this.client.cancelRealTimeBars(0);
		this.client.eDisconnect();
	}


	public void getBarsByInterval()
	{//the function make request from broker 
		
		client = new EClientSocket (this);// Create a new EClientSocket object
		client.eConnect (null, 7496, this.threadId);// Connect to the TWS or IB Gateway application


		try // Pause here for connection to complete
		{
			// Thread.sleep(1000);//one sec
			while (! (client.isConnected()));
		} catch (Exception e) 
		{
			e.printStackTrace ();
		}
		
		Logger.info("make request for RealTimeBars for symbol:{}",this.symbol);
		
		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = this.symbol;
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";

		Vector<TagValue> realTimeBarsOptions = new Vector<TagValue>();// Create a TagValue list

		client.reqRealTimeBars(0, contract,5,"TRADES",false,realTimeBarsOptions);// will be returned via the realtimeBar method

	} 
	public void sentDataToClient(barByInterval tempBar)
	{//the function "sent" bar to managerClient
		
		this.mngClient.addBarToGraph(tempBar);
	}
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count)
	{//the data from broker will returned via this method

		if (!this.isSync)//sync to system time
		{
			//System.out.println("please wait whill sync");
			Logger.info("please wait whill sync");
			long mili = time*1000L;
			int secondsStart = (int) (mili / 1000) % 60 ;
			int minutesStart = (int) ((mili / (1000*60)) % 60);

			if ((minutesStart % this.INTERVAL_GRAPH)==0)
			{
				if (secondsStart == 0)
				{
					this.isSync = true;
					//System.out.println("is sync!");
					Logger.info("is sync!");
					getHistoryByStrategy(mili);//get history
				}
				else
					return;
			}
			else
				return;

		}
		

		fiveSec fiveSecObj;

		try
		{
			String stDebug = "realtimeBar: reqId: "+reqId +", time:"+ time + "," + open + "," + high + "," + low + "," + close + ",volume: " +volume + ", counter:"+countResponseFromTws;
			//System.out.println(stDebug);
			//Logger.info(stDebug);
			fiveSecObj = new fiveSec(time,open,high,low,close,volume,countResponseFromTws);
			arrFiveSec[countResponseFromTws] = fiveSecObj;
			countResponseFromTws++;

			if (countResponseFromTws == NUMBER_OF_RECORDS_BY_INTERVAL)
			{

				barObj = new barByInterval(arrFiveSec, NUMBER_OF_RECORDS_BY_INTERVAL, time,this.symbol);//make the bar values

				String stBar = "barByInterval: barSize:"+this.INTERVAL_GRAPH+ " symbol:" + this.symbol+" , high:"+ barObj.getHigh() +" , low: "+barObj.getLow()+" ,open: "+barObj.getOpen()+" , close: "+barObj.getClose()+" , vol: "+barObj.getVolume();
				System.err.println(stBar);
				//Logger.info(stBar);
				countResponseFromTws = 0;

				//System.out.println(barObj.convertToJSON());
				
				sentDataToClient(barObj);//send data
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

	}
	private void getHistoryByStrategy(long timeInMilisec)
	{//the funtion return the history of the graph- by the strategy

		int movingAvr = this.objLocal.getMovingAvrBar();//whic moving avr needs(1/5/10 mins) if no need -1
		int strategy = this.objLocal.getStrategy();//1 insideBar; 2 moving Avrag

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


		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = this.symbol;
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";

		Vector<TagValue> chartOptions = new Vector<TagValue>();
		client.reqHistoricalData(0, contract, 
				endTime,  // End Date/Time yyyymmdd hh:MM:SS
				DurationInSec+" S",                // Duration (S,D)
				barSize ,              // Bar size
				"TRADES",             // What to show
				1,                    // useRTH
				1, 
				chartOptions);



	}
	public void historicalData(int reqId, String date, double open,double high, double low, double close, int volume, int count,double WAP, boolean hasGaps) 
	{// Display Historical data

		try 
		{
			if (open!= -1)//if isn't and for data
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
				//Logger.info(outputSt);
				sentDataToClient(tempBar);//send data
			}
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























	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountTime(String timeStamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accountDownloadEnd(String accountName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextValidId(int orderId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contractDetailsEnd(int reqId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execDetailsEnd(int reqId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		// TODO Auto-generated method stub

	}

	@Override
	public void managedAccounts(String accountsList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveFA(int faDataType, String xml) {
		// TODO Auto-generated method stub

	}



	@Override
	public void scannerParameters(String xml) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scannerDataEnd(int reqId) {
		// TODO Auto-generated method stub

	}




	@Override
	public void currentTime(long time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fundamentalData(int reqId, String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickSnapshotEnd(int reqId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		// TODO Auto-generated method stub

	}

	@Override
	public void position(String account, Contract contract, int pos,
			double avgCost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void accountSummary(int reqId, String account, String tag,
			String value, String currency) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accountSummaryEnd(int reqId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verifyMessageAPI(String apiData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verifyCompleted(boolean isSuccessful, String errorText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayGroupList(int reqId, String groups) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayGroupUpdated(int reqId, String contractInfo) {
		// TODO Auto-generated method stub

	}



}
