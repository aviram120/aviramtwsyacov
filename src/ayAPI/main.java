package ayAPI;


public class main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int port = 6066;
		int inteval = 1;//in mintues
		managerRealTimeData instance = new managerRealTimeData("spy",inteval,port);// Create an instance	
		
	}
}
