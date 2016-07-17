package ayAPI;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class managerClient {

	private int portSocket;
	private int interval;
	
	private barByInterval[] arrData;
	private int counter;
	
	private String serverName ="localhost";
	private Indicators indicatorsClass;
	
	public managerClient(int interval,int portSocket)
	{
		this.interval = interval;
		this.portSocket = portSocket;
		
		arrData = new barByInterval[390/interval];
		counter = 0;
		
		indicatorsClass = new Indicators();
	}
	
	public void getData()
	{
		try
		{
			System.out.println("Connecting to " + serverName +" on port " + portSocket);
			Socket client = new Socket(serverName, portSocket);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			
			DataInputStream in;
			while (true)
			{				
				in = new DataInputStream(client.getInputStream());
				
				arrData[counter] = new barByInterval(in.readUTF());
				//arrData[counter] = convertStringToJSON(in.readUTF());				
				//System.out.println("form client- open: "+arrData[counter].getOpen());
				System.err.println("barByInterval from client: high:"+ arrData[counter].getHigh() +" , low: "+arrData[counter].getLow()+" ,open: "+arrData[counter].getOpen()+" , close: "+arrData[counter].getClose()+" , vol: "+arrData[counter].getVolume());


				indicatorsClass.calculateMovingAverage(arrData, 5 , counter);
				indicatorsClass.calculateVWAP(arrData, counter);
				
				counter++;
				
			}
        // client.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	


}
