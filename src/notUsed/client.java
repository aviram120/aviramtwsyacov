package notUsed;

import java.net.*;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class client
{
	public static void main(String [] args)
	{

		String serverName ="localhost";
		int port = 6066;
		try
		{
			System.out.println("Connecting to " + serverName +" on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out;// = new DataOutputStream(outToServer);
			DataInputStream in;
			while (true)
			{				
				in = new DataInputStream(client.getInputStream());
				System.out.println("server said: "+in.readUTF());
				convertStringToJSON(in.readUTF());
			}

			/*  InputStream inFromServer = client.getInputStream();
         DataInputStream in =new DataInputStream(inFromServer);
         System.out.println("Server says " + in.readUTF());
         client.close();*/
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void convertStringToJSON(String st)
	{
		try {
			JSONObject obj = new JSONObject(st);

			
			double open = obj.getDouble("open");
			double high = obj.getDouble("high");
			double low = obj.getDouble("low");
			double close = obj.getDouble("close");
			long volume = obj.getLong("volume");
			int type = obj.getInt("type");//equal-0,long-1, short-2
			long time = obj.getLong("time");
			

			//barByInterval barVal =  new barByInterval(time, open,  high,  low, close,  volume,  type);
			//System.out.println("form client- high: "+barVal.getHigh());


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}