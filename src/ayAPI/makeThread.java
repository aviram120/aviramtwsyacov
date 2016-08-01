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
		//for t1-connect with tws
		
		this.threadName = threadName;
		this.threadID = threadID;
		
		this.inteval = interval;
		this.portSocket = portSocekt;
		this.symbol = symbol;
	}
	public makeThread(String threadName, int threadID,int interval, int portSocekt){
		//for t2- get date from broker
		
		this.threadName = threadName;
		this.threadID = threadID;
		
		this.inteval = interval;
		this.portSocket = portSocekt;
	}


	private void task_RealTimeDate()
	{//frunction for t1
		managerRealTimeData instance = new managerRealTimeData("spy",inteval,portSocket);// Create an instance	
	}

	private void task_OrderManger()
	{//frunction for t2
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