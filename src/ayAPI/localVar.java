package ayAPI;

public class localVar {

	public final int INSIDE_BAR = 1;
	public final int MOVING_AVR = 2;
	public final int LONG = 1;
	public final int SHORT = 2;
	
	private String symbol;
	private int strategy;//inside bar - 1; movingAvr - 2; 
	private int direction;//long - 1; short -2;
	private int timefream;
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
	public int getTimefream() {
		return timefream;
	}
	public void setTimefream(int timefream) {
		this.timefream = timefream;
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
