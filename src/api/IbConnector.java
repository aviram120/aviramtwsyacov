package api;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import notUsed.updateOrder;


import chat.serverClient.interfaceToBroker;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.ExecutionFilter;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;
import com.ib.controller.AccountSummaryTag;
import com.ib.controller.Types.MktDataType;

public class IbConnector implements EWrapper{

	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;

	private int nextOrderID = 0;// Keep track of the next ID
	private EClientSocket client = null;// The IB API Client Socket object

	private double totalCashValue;//of use in updateAccountValue 
	private double realizedPnL;//of use in updateAccountValue 
	private static Logger Logger;
	
	
	public IbConnector(int connectionID)
	{	
		Logger = LoggerFactory.getLogger(IbConnector.class);
		//orderList = new LinkedList<proprOrder>();
		client = new EClientSocket (this);
		client.eConnect (null, 7496, connectionID);

		try // Pause here for connection to complete
		{
			Thread.sleep(100);//wait for connect
			while (! (client.isConnected()));
		} catch (Exception e) 
		{
			e.printStackTrace ();
		}
		//get the new valid nextOrderID - call automatic!
	}

	public void disconnectFromBroker()
	{
		this.client.eDisconnect();
		Logger.info("disconnectFromBroker");
	}

	public int placeNewOrder(OrderToExecute objOrder)
	{//the function make the reqest to IB format

		//action = BUY, SELL
		//orderType = LMT, STP, STP LMT, MKT

		Contract contract = new Contract();
		Order order = new Order();

		contract.m_symbol = objOrder.getSymbol();     // For combo order use �USD� as the symbol value all the time
		contract.m_secType = "STK";   // BAG is the security type for COMBO order
		contract.m_exchange = "SMART";
		contract.m_currency = "USD";

		//set action
		if (objOrder.getAction() == objOrder.BUY)
		{order.m_action = "buy";}
		if (objOrder.getAction() == objOrder.SELL)
		{order.m_action = "SELL";}

		order.m_totalQuantity = objOrder.getQuantity();

		if (objOrder.getOca()!= -1)
		{
			order.m_ocaGroup = String.valueOf(objOrder.getOca());
		}



		//set order type
		switch(objOrder.getTypeOrder()){
		case MKT:
			order.m_orderType = "MKT";
			break;

		case STP:
			order.m_orderType = "STP";
			break;

		case STP_LIMIT:
			order.m_orderType = "STP LMT";
			order.m_lmtPrice = objOrder.getLimitPrice();//only for stop limit order
			break;

		case LIMIT:
			order.m_orderType = "LMT";
			order.m_lmtPrice = objOrder.getPrice();//only for take profit
			break;
		}
		order.m_auxPrice = objOrder.getPrice();//for STP orders only
		Logger.info("nextOrderID****:"+nextOrderID);
		Logger.info("placeOrder- symbol: " + objOrder.getSymbol() + ",action: " + objOrder.getAction() + ",Quantity: " + objOrder.getQuantity());
		this.client.placeOrder(nextOrderID,contract,order);
				
		int IdOrder = nextOrderID;
		nextOrderID++;

		Logger.info("IdOrder from broker:"+ IdOrder);

		return IdOrder;
	}
	public void updateOrder(updateOrder orderToUpdate)
	{
		Contract contract = new Contract();
		Order order = new Order();

		contract.m_symbol = orderToUpdate.getSymbol();     // For combo order use �USD� as the symbol value all the time
		contract.m_secType = "STK";   // BAG is the security type for COMBO order
		contract.m_exchange = "SMART";
		contract.m_currency = "USD";

		
		//set action
		if (orderToUpdate.getAction() == orderToUpdate.BUY)
		{order.m_action = "buy";}
		if (orderToUpdate.getAction() == orderToUpdate.SELL)
		{order.m_action = "SELL";}

		order.m_totalQuantity = orderToUpdate.getQuantity();
		
		//set order type
		switch(orderToUpdate.getTypeOrder()){
		
		case STP:
			order.m_orderType = "STP";
			break;

		case STP_LIMIT:
			order.m_orderType = "STP LMT";
			order.m_lmtPrice = orderToUpdate.getLimitPrice();//only for stop limit order
			break;

		case LIMIT:
			order.m_orderType = "LMT";
			order.m_lmtPrice = orderToUpdate.getOrderPriceUpdate();//only for take profit
			break;
		}
		order.m_auxPrice = orderToUpdate.getOrderPriceUpdate();//for STP orders only


		this.client.placeOrder(orderToUpdate.getIdSrver(),contract,order);

	}

	public void cancelOrder(int idServer)
	{
		client.cancelOrder(idServer);
		Logger.info("cancelOrder id:"+ idServer);
	}

	public void getCashInAccount() {
		// TODO Auto-generated method stub

		// build CSV string of attributes to request
		StringBuilder attrStr = new StringBuilder();
		for (AccountSummaryTag tag : AccountSummaryTag.values()) {
			if (attrStr.length() > 0) { attrStr.append(','); }
			attrStr.append(tag);
		}





		// request account summary
		client.reqAccountSummary(0, "All", "TotalCashValue".toString());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//wait for response
	}


	public void getAllPostion() {
		// TODO Auto-generated method stub
		client.reqPositions();//return from function "position"
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//wait for response
	}
	public int reqAccountUpdates(double maxRisk)
	{
		//ExecutionFilter tt = new ExecutionFilter();
		//System.out.println(tt.m_acctCode);
		int flagAns = 0;//false
		
		
		//TODO- fix DU205140
		client.reqAccountUpdates(true, "DU205140");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//wait for response
		System.out.println("form reqAccountUpdates:"+this.totalCashValue);
		System.out.println("form reqAccountUpdates:"+this.realizedPnL);
		
		if (this.totalCashValue/maxRisk>this.realizedPnL)
		{
			System.out.println("true");
			flagAns = 1;
		}
		
		return flagAns;
	}
	
	public void reqAllOpenOrders()
	{
		this.client.reqAllOpenOrders();//return from function "orderStatus"
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//wait for response
	}




	public void position(String account, Contract contract, int pos, double avgCost) {
		String msg = " ---- Position begin ----\n"
				+ "account = " + account + "\n"
				+ "conid = " + contract.m_conId + "\n"
				+ "symbol = " + contract.m_symbol + "\n"
				+ "secType = " + contract.m_secType + "\n"
				+ "expiry = " + contract.m_expiry + "\n"
				+ "strike = " + contract.m_strike + "\n"
				+ "right = " + contract.m_right + "\n"
				+ "multiplier = " + contract.m_multiplier + "\n"
				+ "exchange = " + contract.m_exchange + "\n"
				+ "primaryExch = " + contract.m_primaryExch + "\n"
				+ "currency = " + contract.m_currency + "\n"
				+ "localSymbol = " + contract.m_localSymbol + "\n"
				+ "tradingClass = " + contract.m_tradingClass + "\n"
				+ "position = " + pos + "\n"
				+ "averageCost = " + avgCost + "\n"
				+ " ---- Position end ----\n";
		System.out.println(msg);
	}
	public void updateAccountValue(String key, String value, String currency, String accountName) {

		if ("TotalCashValue".equalsIgnoreCase(key))
		{

			this.totalCashValue = Double.parseDouble(value);
			System.out.println(this.totalCashValue);
		}
		if ((("RealizedPnL".equalsIgnoreCase(key)&&("USD").equalsIgnoreCase(currency))))
		{
			this.realizedPnL = Double.parseDouble(value);
			System.out.println(this.realizedPnL);
		}

		/*if  ( (("TotalCashValue".equalsIgnoreCase(key)))||
				(("RealizedPnL".equalsIgnoreCase(key)&&("USD").equalsIgnoreCase(currency))) )
		{
			String msg = "  account = "  + accountName
					+ "  tag = "      + key
					+ "  value = "    + value
					+ "  currency = " + currency;

			System.out.println("TWSClientInterface:updateAccountValue -- "+msg);
		}
		 */
	}

	@Override
	public void accountSummary(int reqId, String account, String tag, String value, String currency) {
		String msg = "  reqId = "    + reqId
				+ "  account = "  + account
				+ "  tag = "      + tag
				+ "  value = "    + value
				+ "  currency = " + currency;

		System.out.println(msg);
		System.out.println(value);
	}



	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		
		String msg = "orderId = " + orderId + "\n"
				+  "status = " + status + "\n"
				+  "filled = " + filled + "\n"
				+  "remaining = " + remaining + "\n"
				+  "avgFillPrice = " + avgFillPrice + "\n"
				+  "permId = " + permId + "\n"
				+  "parentId = " + parentId + "\n"
				+  "lastFillPrice = " + lastFillPrice + "\n"
				+ "clientId = " + clientId + "\n"
				+ "whyHeld = " + whyHeld + "\n";

		System.out.println(msg);
		

	}




	@Override
	public void accountSummaryEnd(int reqId) {
		//System.out.println("TWSClientInterface:accountSummaryEnd(reqId)  was called");
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
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openOrderEnd() {
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
		this.nextOrderID = orderId;
		Logger.info("from nextValidId:"+orderId);
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
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
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
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {
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


	public void positionEnd() {
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
