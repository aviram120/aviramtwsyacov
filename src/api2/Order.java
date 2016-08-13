package api2;

import java.util.Random;

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
	private int counterArr;//the index of the bar in the arrGraph

	private FutureOrder enter;
	private FutureOrder stop;
	private FutureOrder takeProfit;


	public Order(int id, int quantity, int action, long time,int counterArr,
			int typeOrdrEnter,double enterPrice,double limitPrice,//for enterPrice
			double stopPrice,
			double takeProfitPrice
			)
	{

		//general properties
		this.id = id;

		//make random num for ocaGroup
		Random rand = new Random();
		int  ocaGroupNum = rand.nextInt(99999) + 999;
		this.oca = ocaGroupNum;
		
		this.quantity = quantity;
		this.action = action;
		this.time = time;
		this.counterArr = counterArr;
		this.isActive = true; 

		this.enter = new FutureOrder(typeOrdrEnter, enterPrice, limitPrice);	
		this.stop = new FutureOrder(stopPrice);
		this.takeProfit = new FutureOrder(takeProfitPrice, 0);
	}

	public void updateOrder(long time,int counterArr,double enterPrice,double limitOrder,double stopPrice,double takeProfitPrice)
	{//the function update the order price
		
		this.time = time;
		this.counterArr = counterArr;
		
		this.enter.updateEnterOrder(enterPrice, limitOrder);
		this.stop.updateStopOrder(stopPrice);
		this.takeProfit.updateTakeProfitOrder(takeProfitPrice);
		
		
	}
	public void cencelOrder()
	{//this function cancel the order
		
		this.isActive = false;
		this.enter.setStatus(CANCEl);
		this.stop.setStatus(CANCEl);
		this.takeProfit.setStatus(CANCEl);
		
	}




	
	public int getCounterArr() {
		return counterArr;
	}






	public void setCounterArr(int counterArr) {
		this.counterArr = counterArr;
	}

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
