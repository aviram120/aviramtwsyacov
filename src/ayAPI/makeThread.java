package ayAPI;

import com.ib.client.EClientSocket;

class makeThread implements Runnable {
	private Thread t;
	private int threadID;
	private String threadName;
	
	private int inteval;
	private int portSocket;
	private String symbol;
	
	

	public makeThread(String threadName, int threadID,int interval, int portSocekt, String symbol){
		this.threadName = threadName;
		this.threadID = threadID;
		
		this.inteval = interval;
		this.portSocket = portSocekt;
		this.symbol = symbol;
	}
	public makeThread(String threadName, int threadID,int interval, int portSocekt){
		this.threadName = threadName;
		this.threadID = threadID;
		
		this.inteval = interval;
		this.portSocket = portSocekt;
	}


	private void task_RealTimeDate()
	{
		
		/*int portSocket = 6066;
		int inteval = 1;//in mintues
*/		managerRealTimeData instance = new managerRealTimeData("spy",inteval,portSocket);// Create an instance	
	}

	private void task_OrderManger()
	{
		/*int portSocket = 6066;
		int inteval = 1;//in mintues		
*/		
		managerClient client = new managerClient(inteval, portSocket);
		client.getData();
	}

	public void run() {

		if (this.threadID == 1)
		{
			this.task_RealTimeDate();
		}


		if (this.threadID == 2)
		{
			
			try {
				this.t.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.task_OrderManger();
		}

	}

	public void start ()
	{
		//  System.out.println("Starting " +  threadName );
		if (t == null)
		{
			t = new Thread (this, threadName);
			t.start ();
		}
	}

}