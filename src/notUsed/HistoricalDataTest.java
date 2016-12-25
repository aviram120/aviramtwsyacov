package notUsed;

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

// RealTimeData Class is an implementation of the 
// IB API EWrapper class
public class HistoricalDataTest implements EWrapper
{
	// Keep track of the next ID
	private int nextOrderID = 0;
	// The IB API Client Socket object
	private EClientSocket client = null;
	
	long currentTime;
	int movingAvr = -1;
	int timefream = 1;
	int strategy = 1;//1 insideBar; 5/10 moving Avrag

	public HistoricalDataTest ()
	{
		// Create a new EClientSocket object
      		client = new EClientSocket (this);
		// Connect to the TWS or IB Gateway application
		// Leave null for localhost
		// Port Number (should match TWS/IB Gateway configuration)
		client.eConnect (null, 7496, 14);
		// Pause here for connection to complete
		try 
		{
			while (! (client.isConnected()));
			// May also consider checking for nextValidID call here
			// such as:   while (client.NextOrderId <= 0);
		} 
		catch (Exception e) 
		{
		}

		
		client.reqCurrentTime();
		
/*		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = "SPY";
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";
		// Create a TagValue list for chartOptions
		Vector<TagValue> chartOptions = new Vector<TagValue>();
		// Make a call to start off data historical retrieval
		client.reqHistoricalData(0, contract, 
										 "20160803 22:00:00",  // End Date/Time yyyymmdd hh:MM:SS
						                 "1000 S",                // Duration (S,D)
						                 +this.timefream+" mins",              // Bar size
						                 "TRADES",             // What to show
						                 1,                    // useRTH
						                 1, 
                                         chartOptions);*/
		// You may need to leave off the chartOptions parameter depending on your API version
		// At this point our call is done and any market data events
		// will be returned via the historicalPrice method

	} // end HistoricalData


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

    public void currentTime(long time)
    {
    	this.currentTime = time*1000L;
    	
    	Date date = new Date(this.currentTime);
    	
    	date.setMinutes(date.getMinutes()-(date.getMinutes()% this.timefream));
    	date.setSeconds(00);
    	
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    	// Pass date object
    	String endTime = formatter.format(date);
    	System.out.println(endTime);
    	
    	String DurationInSec = "";
    	if (this.strategy == 1)//insideBar strategy
    	{
    		int countOfBar = 3;
    		DurationInSec = Integer.toString(this.timefream*countOfBar*60);
    	}
    	else
    	{//movingAvrage strategy
    		if (this.movingAvr!=-1)
    		{
        		DurationInSec = Integer.toString(((this.timefream*this.movingAvr)+this.timefream)*60);//bar in graph * which movingAvrag *60 sec
    		}

    	}
    	
    	System.out.println("DurationInSec:"+DurationInSec);
    	System.out.println("strategy:"+this.strategy+" ,timeFream:"+this.timefream+" , movingAv:"+this.movingAvr);
    	
		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = "SPY";
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";
		// Create a TagValue list for chartOptions
		Vector<TagValue> chartOptions = new Vector<TagValue>();
		// Make a call to start off data historical retrieval
		client.reqHistoricalData(0, contract, 
										 endTime,  // End Date/Time yyyymmdd hh:MM:SS
						                 DurationInSec+" S",                // Duration (S,D)
						                 +this.timefream+" mins",              // Bar size
						                 "TRADES",             // What to show
						                 1,                    // useRTH
						                 1, 
                                         chartOptions);
		
    	
    	
    	
 
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

    public void historicalData(int reqId, String date, double open,
            double high, double low, double close, int volume, int count,
            double WAP, boolean hasGaps)
    {
	// Display Historical data
	try 
	{
		System.out.println("historicalData: " + date + "," + 
                                    open + "," + high  + "," + low  + "," + close + "," +
                                    volume);
	} 
	catch (Exception e)
        {
		e.printStackTrace ();
        }
	
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

    public void realtimeBar (int reqId, long time, double open, double high,
            double low, double close, long volume, double wap, int count)
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


    public static void main (String args[])
    {
        try
        {
		// Create an instance
		// At this time a connection will be made
		// and the request for market data will happen
		HistoricalDataTest myData = new HistoricalDataTest();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    } // end main

} // e