package chat.serverClient;

import notUsed.updateOrder;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.IbConnector;
import api.OrderToExecute;

public class interfaceToBroker {

	private IbConnector IB_Broker;
	private static Logger Logger;

	public interfaceToBroker()
	{
		int connectionId = 0;
		this.IB_Broker = new IbConnector(connectionId);
		Logger = LoggerFactory.getLogger(interfaceToBroker.class);
	}
	
	public void disconnectFromBroker()
	{
		IB_Broker.disconnectFromBroker();
		Logger.info("***************disconnect From Broker***************");
	}


	public int executeFunction(String message)
	{
		
/*		
		{"price":50,"typeOrder":4,"symbol":"GE","threadId":0,"action":2,"limitPrice":-1,"quantity":10,"oca":49484,"function":"placeNewOrder"}

		{"orderIdServer":105,"function":"cancelOrder"}

		{"function":"checkStatus"}*/

		
		String function ="";
		try
		{
			Logger.info("msg to exect: " + message);
			JSONObject obj = new JSONObject(message);

			function = obj.getString("function");
			
			if (("placeNewOrder").equalsIgnoreCase(function))
			{
				OrderToExecute orderToExext = new OrderToExecute(message);
				
				int orderId = IB_Broker.placeNewOrder(orderToExext);
				
				return orderId;
			}
			if (("cancelOrder").equalsIgnoreCase(function))
			{
				int orderToCancel = obj.getInt("orderIdServer");
				IB_Broker.cancelOrder(orderToCancel);
				return 1;//is ok
			}
			if (("riskIsOK").equalsIgnoreCase(function))
			{
				double maxRisk = obj.getDouble("maxRisk");
				
				int answer = IB_Broker.reqAccountUpdates(maxRisk);

				return answer;
			}
			if (("updateOrder").equalsIgnoreCase(function))
			{

				updateOrder orderToUpdate = new updateOrder(message);
				IB_Broker.updateOrder(orderToUpdate);
			}
			if (("checkStatus").equalsIgnoreCase(function))
			{
				IB_Broker.getAllPostion();
				//IB_Broker.reqAllOpenOrders();
				return 0;
			
			}
		




		}
		catch (JSONException e) {

			e.printStackTrace();
		}
		return -1;//some errer



	}



}
