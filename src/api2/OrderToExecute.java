package api2;

public class OrderToExecute {
	public final int BUY = 1;
	public final int SELL = 2;

	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;
	
	
	private String symbol;
	private int action;//buy - 1; sell - 2;
	private int quantity;
	private int oca;
	private int typeOrder;
	
	private double price;//can use for enter/stop/limit
	
	private double limitPrice;//only for use in stp_limit(if not use -1)
	
	public OrderToExecute(String symbol,int action,int quantity,int oca,int typeOrder, double price,double limitPrice)
	{
		this.symbol = symbol;
		this.action = action;//buy - 1; sell - 2;
		this.quantity = quantity;
		this.oca = oca;
		this.typeOrder = typeOrder;
		
		this.price = price;//can use for enter/stop/limit
		
		this.limitPrice = limitPrice;//only for use in stp_limit(if not use -1)
		
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOca() {
		return oca;
	}

	public void setOca(int oca) {
		this.oca = oca;
	}

	public int getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(int typeOrder) {
		this.typeOrder = typeOrder;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}
	
	
	
}
