package ayAPI;

import api2.OrderToExecute;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;
import com.ib.controller.Types.MktDataType;

public class IbConnector implements EWrapper{

	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;

	private int nextOrderID = 0;// Keep track of the next ID
	private EClientSocket client = null;// The IB API Client Socket object

	public IbConnector(int connectionID)
	{	
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
		
	}

	public int placeNewOrder(OrderToExecute objOrder)
	{//the function make the reqest to IB format
		
		//action = BUY, SELL
		//orderType = LMT, STP, STP LMT, MKT

		Contract contract = new Contract();
		Order order = new Order();

		contract.m_symbol = objOrder.getSymbol();     // For combo order use “USD” as the symbol value all the time
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

		this.client.placeOrder(nextOrderID,contract,order);
		//this.client.eDisconnect();
		
		int IdOrder = nextOrderID;
		nextOrderID++;
		
		return IdOrder;
	}
	
	public void cancelOrder(int idServer)
	{
		client.cancelOrder(idServer);
		
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
		this.nextOrderID = orderId;
		System.out.println("from nextValidId:"+orderId);
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
