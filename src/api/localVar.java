package api;

import org.json.JSONException;
import org.json.JSONObject;

public class localVar {

	public final int INSIDE_BAR = 1;
	public final int MOVING_AVR = 2;
	public final int LONG = 1;
	public final int SHORT = 2;
	
	private String symbol;
	private int strategy;//inside bar - 1; movingAvr - 2; 
	private int direction;//long - 1; short -2;
	private int movingAvrBar;//whic moving avr needs(1/5/10 mins) if no need -1
	private int interval;
	private int minVolume;
	private double minBarSize;
	private double maxBarSize;
	private double addCentToBreak;//add cents to order (in table num 8)
	private int numBarToCancelDeal;
	private boolean isAggressive;
	private int maxTransactionsPerDay;
	private double riskPerTransactionsDolars;
	private double maxRiskPerTransactionsDolars;
	private double extarPrice;
	private double pe;
	//
	//
	private double barTriger;
	
	
	
	
	
	public localVar(String symbol, int strategy, int direction,int movingAvrBar, int interval,
			int minVolume, double minBarSize, double maxBarSize,
			double addCentToBreak, int numBarToCancelDeal,
			boolean isAggressive, int maxTransactionsPerDay,
			double riskPerTransactionsDolars,
			double maxRiskPerTransactionsDolars, double extarPrice, double pe,
			double barTriger) {
		
		this.symbol = symbol;
		this.strategy = strategy;
		this.direction = direction;
		this.movingAvrBar = movingAvrBar;
		this.interval = interval;
		this.minVolume = minVolume;
		this.minBarSize = minBarSize;
		this.maxBarSize = maxBarSize;
		this.addCentToBreak = addCentToBreak;
		this.numBarToCancelDeal = numBarToCancelDeal;
		this.isAggressive = isAggressive;
		this.maxTransactionsPerDay = maxTransactionsPerDay;
		this.riskPerTransactionsDolars = riskPerTransactionsDolars;
		this.maxRiskPerTransactionsDolars = maxRiskPerTransactionsDolars;
		this.extarPrice = extarPrice;
		this.pe = pe;
		this.barTriger = barTriger;
	}
	public void updateObject(localVar tempLocal)
	{
		this.symbol = tempLocal.symbol;
		this.strategy = tempLocal.strategy;
		this.direction = tempLocal.direction;
		this.movingAvrBar = tempLocal.movingAvrBar;
		this.interval = tempLocal.interval;
		this.minVolume = tempLocal.minVolume;
		this.minBarSize = tempLocal.minBarSize;
		this.maxBarSize = tempLocal.maxBarSize;
		this.addCentToBreak = tempLocal.addCentToBreak;
		this.numBarToCancelDeal = tempLocal.numBarToCancelDeal;
		this.isAggressive = tempLocal.isAggressive;
		this.maxTransactionsPerDay = tempLocal.maxTransactionsPerDay;
		this.riskPerTransactionsDolars = tempLocal.riskPerTransactionsDolars;
		this.maxRiskPerTransactionsDolars = tempLocal.maxRiskPerTransactionsDolars;
		this.extarPrice = tempLocal.extarPrice;
		this.pe = tempLocal.pe;
		this.barTriger = tempLocal.barTriger;
		
	}
	public localVar(String st)
	{
		System.out.println(st);
		try {
			JSONObject obj = new JSONObject(st);
			
			String symbol = obj.getString("symbol");
			int strategy = obj.getInt("strategy");
			int direction = obj.getInt("direction");
			int movingAvrBar = obj.getInt("movingAvrBar");
			int interval  = obj.getInt("interval");
			int minVolume = obj.getInt("minVolume");
			double minBarSize = obj.getDouble("minBarSize");
			double maxBarSize = obj.getDouble("maxBarSize");
			double addCentToBreak = obj.getDouble("addCentToBreak");
			int numBarToCancelDeal = obj.getInt("numBarToCancelDeal");
			boolean isAggressive = obj.getBoolean("isAggressive");
			int maxTransactionsPerDay = obj.getInt("maxTransactionsPerDay");
			double riskPerTransactionsDolars = obj.getDouble("riskPerTransactionsDolars");
			double maxRiskPerTransactionsDolars = obj.getDouble("maxRiskPerTransactionsDolars");
			double extarPrice = obj.getDouble("extarPrice");
			double pe = obj.getDouble("pe");
			double barTriger = obj.getDouble("barTriger");

			
			this.symbol = symbol;
			this.strategy = strategy;
			this.direction = direction;
			this.movingAvrBar = movingAvrBar;
			this.interval = interval;
			this.minVolume = minVolume;
			this.minBarSize = minBarSize;
			this.maxBarSize = maxBarSize;
			this.addCentToBreak = addCentToBreak;
			this.numBarToCancelDeal = numBarToCancelDeal;
			this.isAggressive = isAggressive;
			this.maxTransactionsPerDay = maxTransactionsPerDay;
			this.riskPerTransactionsDolars = riskPerTransactionsDolars;
			this.maxRiskPerTransactionsDolars = maxRiskPerTransactionsDolars;
			this.extarPrice = extarPrice;
			this.pe = pe;
			this.barTriger = barTriger;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String convertObjToJSON()
	{
		JSONObject obj = new JSONObject();
		
	      try {
	    	obj.put("symbol", symbol);
	    	obj.put("strategy", strategy);
	    	obj.put("direction", direction);
	    	obj.put("movingAvrBar", movingAvrBar);
	    	obj.put("interval", interval);
	    	obj.put("minVolume", minVolume);
	    	obj.put("minBarSize", minBarSize);
	    	obj.put("maxBarSize", maxBarSize);
	    	obj.put("addCentToBreak", addCentToBreak);
	    	obj.put("numBarToCancelDeal", numBarToCancelDeal);
	    	obj.put("isAggressive", isAggressive);
	    	obj.put("maxTransactionsPerDay", maxTransactionsPerDay);
	    	obj.put("riskPerTransactionsDolars", riskPerTransactionsDolars);
	    	obj.put("maxRiskPerTransactionsDolars", maxRiskPerTransactionsDolars);
	    	obj.put("extarPrice", extarPrice);
	    	obj.put("pe", pe);
	    	obj.put("barTriger", barTriger);
		    
		} catch (JSONException e) {
			e.printStackTrace();
		}  
	   return obj.toString(); 	
	}
	
	public int getMovingAvrBar() {
		return movingAvrBar;
	}
	public void setMovingAvrBar(int movingAvrBar) {
		this.movingAvrBar = movingAvrBar;
	}
	public double getBarTriger() {
		return barTriger;
	}
	public void setBarTriger(double barTriger) {
		this.barTriger = barTriger;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public double getAddCentToBreak() {
		return addCentToBreak;
	}
	public void setAddCentToBreak(double addCentToBreak) {
		this.addCentToBreak = addCentToBreak;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getStrategy() {
		return strategy;
	}
	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getMinVolume() {
		return minVolume;
	}
	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	public double getMinBarSize() {
		return minBarSize;
	}
	public void setMinBarSize(double minBarSize) {
		this.minBarSize = minBarSize;
	}
	public double getMaxBarSize() {
		return maxBarSize;
	}
	public void setMaxBarSize(double maxBarSize) {
		this.maxBarSize = maxBarSize;
	}
	public int getNumBarToCancelDeal() {
		return numBarToCancelDeal;
	}
	public void setNumBarToCancelDeal(int numBarToCancelDeal) {
		this.numBarToCancelDeal = numBarToCancelDeal;
	}
	public boolean isAggressive() {
		return isAggressive;
	}
	public void setAggressive(boolean isAggressive) {
		this.isAggressive = isAggressive;
	}
	public int getMaxTransactionsPerDay() {
		return maxTransactionsPerDay;
	}
	public void setMaxTransactionsPerDay(int maxTransactionsPerDay) {
		this.maxTransactionsPerDay = maxTransactionsPerDay;
	}
	public double getRiskPerTransactionsDolars() {
		return riskPerTransactionsDolars;
	}
	public void setRiskPerTransactionsDolars(double riskPerTransactionsDolars) {
		this.riskPerTransactionsDolars = riskPerTransactionsDolars;
	}
	
	public double getMaxRiskPerTransactionsDolars() {
		return maxRiskPerTransactionsDolars;
	}
	public void setMaxRiskPerTransactionsDolars(double maxRiskPerTransactionsDolars) {
		this.maxRiskPerTransactionsDolars = maxRiskPerTransactionsDolars;
	}
	public double getExtarPrice() {
		return extarPrice;
	}
	public void setExtarPrice(double extarPrice) {
		this.extarPrice = extarPrice;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	
	
	
	
	
	
	
	
	

	
}
