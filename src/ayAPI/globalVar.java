package ayAPI;

import org.json.JSONException;
import org.json.JSONObject;


public class globalVar {
	public final int STP = 1;
	public final int STOP_LIMIT = 2;
	
	public final int STOP_LOW_OR_HIGHT = 1;
	public final int STOP_IN_CLOSED = 2;
	
	private int orderType;//market - 1; stop limit - ;
	private double centToGiveup;
	private int defineNextStop;
	private int stopType;
	private boolean b1;//*****************
	private double maxRisk;
	private String timeStopAddOrder;//clear all Future provisions(in format- HH:mm)
	private String timeCloseAllOrder;//(in format- HH:mm)
	private boolean stopRobot;




	public globalVar(int orderType, double centToGiveup, int defineNextStop,
			int stopType, double maxRisk, String timeStopAddOrder,
			String timeCloseAllOrder) {
		
		this.orderType = orderType;
		this.centToGiveup = centToGiveup;
		this.defineNextStop = defineNextStop;
		this.stopType = stopType;
		this.maxRisk = maxRisk;
		this.timeStopAddOrder = timeStopAddOrder;
		this.timeCloseAllOrder = timeCloseAllOrder;
		//this.stopRobot = stopRobot;
	}
	public globalVar(String st)
	{
		
		try {
			JSONObject obj = new JSONObject(st);
			
			int orderType = obj.getInt("orderType");
			double centToGiveup = obj.getDouble("centToGiveup");
			int defineNextStop = obj.getInt("defineNextStop");
			int stopType = obj.getInt("stopType");
			int maxRisk = obj.getInt("maxRisk");
			String timeStopAddOrder = obj.getString("timeStopAddOrder");
			String timeCloseAllOrder = obj.getString("timeCloseAllOrder");
			
			
			this.orderType = orderType;
			this.centToGiveup = centToGiveup;
			this.defineNextStop = defineNextStop;
			this.stopType = stopType;
			this.maxRisk = maxRisk;
			this.timeStopAddOrder = timeStopAddOrder;
			this.timeCloseAllOrder = timeCloseAllOrder;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String convertObjToJSON()
	{
		JSONObject obj = new JSONObject();

	      try {
	    	obj.put("orderType", orderType);
	    	obj.put("centToGiveup", centToGiveup);
	    	obj.put("defineNextStop", defineNextStop);
	    	obj.put("stopType", stopType);
	    	obj.put("maxRisk", maxRisk);
	    	obj.put("timeStopAddOrder", timeStopAddOrder);
	    	obj.put("timeCloseAllOrder", timeCloseAllOrder);
		    
		} catch (JSONException e) {
			e.printStackTrace();
		}  
	   return obj.toString(); 	
	}
	
	
	public int getOrderType() {//can by STP or STP_LIMIT
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
	public double getMaxRisk() {
		return maxRisk;
	}
	public void setMaxRisk(int maxRisk) {
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
