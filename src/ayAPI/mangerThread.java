package ayAPI;

public class mangerThread   {

	public mangerThread()
	{

		int interval = 1; //in minets
		int portSocekt =  6608;
		String symbol = "spy";
		
		//get data ftom TWS
		makeThread R1 = new makeThread("Thread-1",1,interval,portSocekt,symbol);
		//makeThread R1 = new makeThread("Thread-1",1);

		R1.start();


		makeThread R2 = new makeThread("Thread-2",2,interval,portSocekt);
		//makeThread R2 = new makeThread("Thread-2",2);
		R2.start();


	}
}


