package ayAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class barByInterval {

	
	
	private double open;
	private double high;
	private double low;
	private double close;
	private long volume;
	private int type;//equal-0,long-1, short-2
	
	private long time;
	
	
	public barByInterval(long time,double open, double high, double low,double close, long volum, int type)
	{
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volum;
		this.type = type;
	}
	public barByInterval(String st)
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

			this.time = time;
			this.open = open;
			this.high = high;
			this.low = low;
			this.close = close;
			this.volume = volume;
			this.type = type;
			
			//barByInterval barVal =  new barByInterval(time, open,  high,  low, close,  volume,  type);
			//return barVal;
			//System.out.println("form client- high: "+barVal.getHigh());


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;

	}
	
	public String convertToJSON()
	{
		JSONObject obj = new JSONObject();

	      try {
			obj.put("time", this.time);
			obj.put("open", this.open);	
			obj.put("high", this.high);	
			obj.put("low", this.low);	
			obj.put("close", this.close);	
			obj.put("volume", this.volume);	
			obj.put("type", this.type);	
		    
		} catch (JSONException e) {
			e.printStackTrace();
		}
	      
	   return obj.toString(); 
	      
	}
	
	public barByInterval(fiveSec[] arrFiveSec,int interval,long time)
	{		
		double open = arrFiveSec[0].getOpen();
		double high = arrFiveSec[0].getHigh();
		double low = arrFiveSec[0].getLow();
		double close = arrFiveSec[interval-1].getClose();
		long volumeSum = arrFiveSec[0].getVolume();
		

		for (int i=1;i<interval; i++)
		{
			volumeSum = volumeSum +arrFiveSec[i].getVolume();
			
			if (arrFiveSec[i].getHigh()>high)//get the high value from date
			{
				high = arrFiveSec[i].getHigh();
			}
			if (arrFiveSec[i].getLow()<low)//get the low value from date
			{
				low = arrFiveSec[i].getLow();
			}
		}
		
		
		int type;
		if (open>close)
		{
			type = 1;//long
		}
		else if (open<close)
		{
			type = 2;//short
		}
		else
		{
			type = 0;//equal
		}
		
		this.time = time;
		
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volumeSum*100;	
		this.type = type;
	
	}

	

	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public double getOpen() {
		return open;
	}


	public void setOpen(double open) {
		this.open = open;
	}


	public double getHigh() {
		return high;
	}


	public void setHigh(double high) {
		this.high = high;
	}


	public double getLow() {
		return low;
	}


	public void setLow(double low) {
		this.low = low;
	}


	public double getClose() {
		return close;
	}


	public void setClose(double close) {
		this.close = close;
	}


	public long getVolume() {
		return volume;
	}


	public void setVolume(long volume) {
		this.volume = volume;
	}
}
