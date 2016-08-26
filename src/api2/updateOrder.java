package api2;

import org.json.JSONException;
import org.json.JSONObject;

public class updateOrder {

	public final int BUY = 1;
	public final int SELL = 2;

	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;

	public final int ENTER = 1;
	public final int STOP = 2;
	public final int TAKEPROFIT = 3;

	private int idSrver;
	private String symbol;
	private int quantity;
	private int action;//buy - 1; sell - 2;
	private int typeOrder;//LMT, STP, STP LMT, MKT

	private double orderPriceUpdate;//use for enter/stop/takeProfit price 
	private double limitPrice;//use for only in stp_lmt else put -1

	private int whichOrder;//indicated on which order

	public updateOrder(String messge)
	{
		try {
			JSONObject obj = new JSONObject(messge);


			String symbol = obj.getString("symbol");
			int quantity = obj.getInt("quantity");
			int action = obj.getInt("action");
			int idSrver = obj.getInt("idSrver");
			
			int typeOrder = obj.getInt("typeOrder");
			double orderPriceUpdate = obj.getDouble("orderPriceUpdate");
			double limitPrice = obj.getDouble("limitPrice");
			
			this.symbol = symbol;
			this.quantity = quantity;
			this.action = action;
			this.idSrver = idSrver;
			
			this.typeOrder = typeOrder;
			this.orderPriceUpdate = orderPriceUpdate;
			this.limitPrice = limitPrice;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public updateOrder(String symbol, Order orderObj, int orderToUpdate)
	{
		this.symbol = symbol;
		this.quantity = orderObj.getQuantity();
		this.whichOrder = orderToUpdate;

		if (orderToUpdate == ENTER)
		{
			this.idSrver = orderObj.getEnter().getIdServer();
			this.action = orderObj.getAction();
			this.typeOrder = orderObj.getEnter().getTypeOrdr();

			this.orderPriceUpdate = orderObj.getEnter().getEnterPrice();
			this.limitPrice = orderObj.getEnter().getLimitPrice();
		}

		int newAction = 0;//for stop/take profit order
		if (orderObj.getAction() == BUY)
		{ newAction = SELL;}
		if (orderObj.getAction() == SELL)
		{ newAction = BUY;}


		if (orderToUpdate == STOP)
		{
			this.idSrver = orderObj.getStop().getIdServer();
			this.action = newAction;
			this.typeOrder = orderObj.getStop().getTypeOrdr();

			this.orderPriceUpdate = orderObj.getStop().getStopPrice();
			this.limitPrice = -1;
		}
		if (orderToUpdate == TAKEPROFIT)
		{
			this.idSrver = orderObj.getTakeProfit().getIdServer();
			this.action = newAction;
			this.typeOrder = orderObj.getTakeProfit().getTypeOrdr();

			this.orderPriceUpdate = orderObj.getTakeProfit().getTakeProfitPrice();
			this.limitPrice = -1;
		}
	}

	public String convertToJSON(String function,int threadId)
	{
		JSONObject obj = new JSONObject();

		try {
			obj.put("threadId", threadId);
			obj.put("function", function);

			obj.put("symbol", this.symbol);  
			obj.put("idSrver", this.idSrver);
			obj.put("quantity", this.quantity);
			obj.put("action", this.action);
			obj.put("typeOrder", this.typeOrder);
			obj.put("orderPriceUpdate", this.orderPriceUpdate);			
			obj.put("limitPrice", this.limitPrice);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString(); 
	}
	
	public int getIdSrver() {
		return idSrver;
	}

	public void setIdSrver(int idSrver) {
		this.idSrver = idSrver;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(int typeOrder) {
		this.typeOrder = typeOrder;
	}

	public double getOrderPriceUpdate() {
		return orderPriceUpdate;
	}

	public void setOrderPriceUpdate(double orderPriceUpdate) {
		this.orderPriceUpdate = orderPriceUpdate;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public int getWhichOrder() {
		return whichOrder;
	}

	public void setWhichOrder(int whichOrder) {
		this.whichOrder = whichOrder;
	}
}
