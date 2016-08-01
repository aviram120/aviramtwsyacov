package ayAPI;


public class globalVar {
	public final int MARKET = 1;
	public final int STOP_LIMIT = 2;
	
	private int orderType;//market - 1; stop limit - ;
	private double centToGiveup;
	private double barTriger;
	private int defineNextStop;
	private int stopType;
	private boolean b1;//*****************
	private boolean maxRisk;
	private String timeStopAddOrder;//clear all Future provisions(in format- HH:mm)
	private String timeCloseAllOrder;
	private boolean stopRobot;




	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
	public double getCentToGiveup() {
		return centToGiveup;
	}
	public void setCentToGiveup(double centToGiveup) {
		this.centToGiveup = centToGiveup;
	}
	public double getBarTriger() {
		return barTriger;
	}
	public void setBarTriger(double barTriger) {
		this.barTriger = barTriger;
	}
	public int getDefineNextStop() {
		return defineNextStop;
	}
	public void setDefineNextStop(int defineNextStop) {
		this.defineNextStop = defineNextStop;
	}
	public int getStopType() {
		return stopType;
	}
	public void setStopType(int stopType) {
		this.stopType = stopType;
	}
	public boolean getB1() {
		return b1;
	}
	public void setB1(boolean b1) {
		this.b1 = b1;
	}
	public boolean getMaxRisk() {
		return maxRisk;
	}
	public void setMaxRisk(boolean maxRisk) {
		this.maxRisk = maxRisk;
	}
	public String getTimeStopAddOrder() {
		return timeStopAddOrder;
	}
	public void setTimeStopAddOrder(String timeStopAddOrder) {
		this.timeStopAddOrder = timeStopAddOrder;
	}
	public String getTimeCloseAllOrder() {
		return timeCloseAllOrder;
	}
	public void setTimeCloseAllOrder(String timeCloseAllOrder) {
		this.timeCloseAllOrder = timeCloseAllOrder;
	}
	public boolean getStopRobot() {
		return stopRobot;
	}
	public void setStopRobot(boolean stopRobot) {
		this.stopRobot = stopRobot;
	}




}
