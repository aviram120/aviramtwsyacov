package com.manger.broker;

import org.json.JSONException;
import org.json.JSONObject;

import api2.OrderToExecute;
import api2.updateOrder;
import ayAPI.IbConnector;

public class interfaceToBroker {

	private IbConnector IB_Broker;

	public interfaceToBroker()
	{
		int connectionId = 0;
		this.IB_Broker = new IbConnector(connectionId);

	}
	
	public void disconnectFromBroker()
	{
		IB_Broker.disconnectFromBroker();
		System.out.println("disconnect From Broker");
	}


	public int executeFunction(String message)
	{
		String function ="";
		try
		{
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
				//IB_Broker.getAllPostion();
				IB_Broker.reqAllOpenOrders();
				return 0;
			
			}
		




		}
		catch (JSONException e) {

			e.printStackTrace();
		}
		return -1;//some errer



	}



}
