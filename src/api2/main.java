package api2;

import ayAPI.globalVar;
import ayAPI.localVar;

public class main {

	public final static int MARKET = 1;
	public final static int STOP_LIMIT = 2;
	public final static int INSIDE_BAR = 1;
	public final static int MOVING_AVR = 2;
	public final static int LONG = 1;
	public final static int SHORT = 2;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		globalVar tempGlobal;
		localVar tempLocal;
		ManagerRealTimeData newObj;
		int threadId = 0;


		//init - globalVar
		int orderType = STOP_LIMIT;
		double centToGiveup = 0.5;
		int defineNextStop = 6;
		int stopType = 1;
		int maxRisk = 10;
		String timeStopAddOrder = "18:40";
		String timeCloseAllOrder = "22:50";
		tempGlobal = new globalVar(orderType,centToGiveup,defineNextStop,stopType,maxRisk,timeStopAddOrder, timeCloseAllOrder);

		//init - localVar
		String symbol = "SPY";
		int strategy = INSIDE_BAR;
		int direction = LONG;
		int movingAvr = -1;
		int interval = 1;
		int minVolume = 50000;
		double minBarSize = 0.2;
		double maxBarSize = 0.7;
		double addCentToBreak = 0.03;
		int numBarToCancelDeal = 6;
		boolean isAggressive = false;
		int maxTransactionsPerDay = 2;
		double riskPerTransactionsDolars = 150;
		double maxRiskPerTransactionsDolars = 250;
		double extarPrice = 0.07;
		double pe = 3;
		double barTriger = 3;
		tempLocal = new localVar(symbol, strategy, direction, movingAvr,interval, minVolume, minBarSize,maxBarSize, addCentToBreak, numBarToCancelDeal, 
				isAggressive, maxTransactionsPerDay,riskPerTransactionsDolars, maxRiskPerTransactionsDolars, extarPrice, pe, barTriger );

		
		newObj = new ManagerRealTimeData(threadId, tempGlobal, tempLocal);
		


	}

}
