package ayAPI;

public class mainIbConnector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		IbConnector IB_Broker;

		System.out.println("is in");

		int connectionId = 2;
		IB_Broker = new IbConnector(connectionId);

		IB_Broker.getAllPostion();

	}

}
