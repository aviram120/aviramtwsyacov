package api2;

public class Order {
	public final int ACTIVE = 1;
	public final int WAIT = 2;
	public final int CLOSED = 3;
	public final int CANCEl = 4;
	
	public final int BUY = 1;
	public final int SELL = 2;
	
	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;
	
	
	private int id;
	private int oca;
	private int quantity;
	private int action;//buy - 1; sell - 2;
	private long time;
	private boolean isActive;//if the order is active or unactive
	
	private FutureOrder stop;
	private FutureOrder takeProfit;


	public Order(int id, int quantity, int action, long time,
			int typeOrdrEnter,double enterPrice,double limitPrice,//for enterPrice
			double stopPrice,
			double takeProfitPrice
			)
	{
		//general properties
		this.id = id;
		this.oca = 3;//TODO- make a random number
		this.quantity = quantity;
		this.action = action;
		this.time = time;
		this.isActive = true; 
		
		this.enter = new FutureOrder(typeOrdrEnter, enterPrice, limitPrice);	
		this.stop = new FutureOrder(stopPrice);
		this.takeProfit = new FutureOrder(takeProfitPrice, 0);
	}
	
	
	
	
	
	private FutureOrder enter;
	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public int getOca() {
		return oca;
	}





	public void setOca(int oca) {
		this.oca = oca;
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





	public long getTime() {
		return time;
	}





	public void setTime(long time) {
		this.time = time;
	}





	public FutureOrder getEnter() {
		return enter;
	}





	public void setEnter(FutureOrder enter) {
		this.enter = enter;
	}





	public FutureOrder getStop() {
		return stop;
	}





	public void setStop(FutureOrder stop) {
		this.stop = stop;
	}





	public FutureOrder getTakeProfit() {
		return takeProfit;
	}





	public void setTakeProfit(FutureOrder takeProfit) {
		this.takeProfit = takeProfit;
	}











	public boolean isActive() {
		return isActive;
	}





	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
	
	
	
	
}
