package api2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ayAPI.globalVar;
import ayAPI.localVar;

public class mainJar {

	public final static int MARKET = 1;
	public final static int STOP_LIMIT = 2;
	public final static int INSIDE_BAR = 1;
	public final static int MOVING_AVR = 2;
	public final static int LONG = 1;
	public final static int SHORT = 2;
	
	private static Logger Logger;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		globalVar tempGlobal= null;
		localVar tempLocal = null;
		ManagerRealTimeData newObj;
		int threadId;
		int portNumberToserverChat = 1500;

/*
		//init - globalVar
		int orderType = STOP_LIMIT;
		double centToGiveup = 0.5;
		int defineNextStop = 6;
		int stopType = 1;
		int maxRisk = 10;
		String timeStopAddOrder = "18:40";
		String timeCloseAllOrder = "22:50";
//		tempGlobal = new globalVar(orderType,centToGiveup,defineNextStop,stopType,maxRisk,timeStopAddOrder, timeCloseAllOrder);

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

		
		System.out.println(tempGlobal.convertObjToJSON());
		System.out.println(tempLocal.convertObjToJSON());*/
		
		
		 Scanner in = new Scanner(System.in);
		 System.out.println("Enter thread id");
		 threadId = Integer.parseInt(in.nextLine());
		 
	    System.out.println("Enter global file");
	    String fileGlobal = in.nextLine();
	   // System.out.println("print global:"+fileGlobal);
	    
	    System.out.println("Enter local file");
	    String filelocal = in.nextLine();
	    //System.out.println("print local:"+filelocal);

		try {
			tempGlobal = new globalVar(readFile(fileGlobal));
			tempLocal = new localVar(readFile(filelocal));
			System.out.println(tempLocal.getSymbol());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.setProperty("symbol", tempLocal.getSymbol());
    	
    	Logger = LoggerFactory.getLogger(mainJar.class);
    	Logger.info("Hi, in main {}", tempLocal.getSymbol());

		newObj = new ManagerRealTimeData(threadId, tempGlobal, tempLocal,portNumberToserverChat);
			

	}
	
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

}
