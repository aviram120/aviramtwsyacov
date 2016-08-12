package ayAPI;

public class mangerThread   {

	public mangerThread()
	{

		int interval = 1; //in minets
		int portSocekt =  6613;
		String symbol = "SPY";
		
		//get data ftom TWS
		makeThread R1 = new makeThread("Thread-1",1,interval,portSocekt,symbol);
		R1.start();

		/*//get date from broker and start the alog
		makeThread R2 = new makeThread("Thread-2",2,interval,portSocekt);
		R2.start();
*/

	}
}


