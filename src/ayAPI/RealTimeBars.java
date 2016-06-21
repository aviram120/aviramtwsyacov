package ayAPI;

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

public class RealTimeBars implements EWrapper
{
	
	private int nextOrderID = 0;// Keep track of the next ID
	private EClientSocket client = null;// The IB API Client Socket object
	
	private fiveSec[] arrFiveSec;
	private int countClass;
	private oneMin oneMinObj;
	private final int NUMBER_OF_RECORDS;

	
	public RealTimeBars(String symbol, int intervalGraph)
	{
		this.NUMBER_OF_RECORDS =  intervalGraph*60/5;
		arrFiveSec = new fiveSec[this.NUMBER_OF_RECORDS];
		countClass = 0;
		
		getBarsByInterval(symbol,intervalGraph);
	} 

	public void getBarsByInterval(String symbol, int intervalGraph)
	{
		client = new EClientSocket (this);// Create a new EClientSocket object
		client.eConnect (null, 7496, 0);// Connect to the TWS or IB Gateway application

		
		try // Pause here for connection to complete
		{
			// Thread.sleep (1000);
			while (! (client.isConnected()));
		} catch (Exception e) 
		{
			e.printStackTrace ();
		};
		
		waitForMin();//sync timer
		
		// Create a new contract
		Contract contract = new Contract ();
		contract.m_symbol = symbol;
		contract.m_exchange = "SMART";
		contract.m_secType = "STK";
		contract.m_currency = "USD";
			
		Vector<TagValue> realTimeBarsOptions = new Vector<TagValue>();// Create a TagValue list
		
		client.reqRealTimeBars(0, contract,5,"TRADES",false,realTimeBarsOptions);// will be returned via the realtimeBar method
	} 

	public void waitForMin()
	{
		long currentDateTime = System.currentTimeMillis();
		System.out.println(currentDateTime);
		
		int seconds = (int) (currentDateTime / 1000) % 60 ;
		int minutesStart = (int) ((currentDateTime / (1000*60)) % 60);
		System.out.println("sec:"+seconds);
		System.out.println("min:"+minutesStart);
		
		while(true)
		{
			currentDateTime = System.currentTimeMillis();
			int minutesUpdate = (int) ((currentDateTime / (1000*60)) % 60);
			if (minutesUpdate!=minutesStart)
			{
				System.err.println("one min more:"+minutesUpdate);
				break;
			}	
		}		
		
	}
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count)
	{
		fiveSec fiveSecObj;

		try
		{
			System.out.println("realtimeBar:" + time + "," + open + "," + high + "," + low + "," + close + ",volume: " +volume + ", counter:"+countClass);
			
			fiveSecObj = new fiveSec(time,open,high,low,close,volume,countClass);
			arrFiveSec[countClass] = fiveSecObj;
			countClass++;
			if (countClass == NUMBER_OF_RECORDS)
			{
				oneMinObj = getOneMin();
				
				System.err.println("oneMin: high:"+ oneMinObj.getHigh() +" , low: "+oneMinObj.getLow()+" ,open: "+oneMinObj.getOpen()+" , close: "+oneMinObj.getClose()+" , vol: "+oneMinObj.getVolume());
				countClass = 0;	
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}
	private oneMin getOneMin()
	{
		oneMin tempOne;

		
		double open = arrFiveSec[0].getOpen();
		double high = arrFiveSec[0].getHigh();
		double low = arrFiveSec[0].getLow();
		double close = arrFiveSec[11].getClose();
		long volumeSum = arrFiveSec[0].getVolume();
		

		for (int i=1;i<NUMBER_OF_RECORDS; i++)
		{
			volumeSum = volumeSum +arrFiveSec[i].getVolume();
			
			if (arrFiveSec[i].getHigh()>high)
			{
				high = arrFiveSec[i].getHigh();
			}
			if (arrFiveSec[i].getLow()<low)
			{
				low = arrFiveSec[i].getLow();
			}
		}

		tempOne = new oneMin(1466450000,open,high,low,close,volumeSum*100);
		return tempOne;

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

	public void currentTime(long time)
	{
		System.out.println(time);
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



} // end public class RealTimeBars
