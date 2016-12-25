package api;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


public class barByInterval {

	
	private String symbol;
	private double open;
	private double high;
	private double low;
	private double close;
	private long volume;
	private int type;//equal-0,long-1, short-2
	
	private long time;
	
	
	public barByInterval(barByInterval tempBar)
	{
		this.symbol = tempBar.getSymbol();
		this.time = tempBar.getTime();
		this.open = tempBar.getOpen();
		this.high = tempBar.getHigh();
		this.low = tempBar.getLow();
		this.close = tempBar.getClose();
		this.volume = tempBar.getVolume();
		this.type = tempBar.getType();
		
	}
	public barByInterval(String symbol,long time,double open, double high, double low,double close, long volum, int type)
	{
		this.symbol = symbol;
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volum;
		this.type = type;
	}
	
	public barByInterval(String symbol,long time,double open, double high, double low,double close, long volum)
	{
		this.symbol = symbol;
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volum*100;
		
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
		
		this.type = type;
	}
	
	public String getTimeInNY()
	{
		Date date = new Date(this.time*1000L);
		SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
		
		date.setHours(date.getHours()-7);
		String time = formatter.format(date);
		
		return time;
	}
	
	public barByInterval(String st)
	{
		try {
			JSONObject obj = new JSONObject(st);

			String symbol = obj.getString("symbol");
			double open = obj.getDouble("open");
			double high = obj.getDouble("high");
			double low = obj.getDouble("low");
			double close = obj.getDouble("close");
			long volume = obj.getLong("volume");
			int type = obj.getInt("type");//equal-0,long-1, short-2
			long time = obj.getLong("time");

			
			this.symbol = symbol;
			this.time = time;
			this.open = open;
			this.high = high;
			this.low = low;
			this.close = close;
			this.volume = volume;
			this.type = type;
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;

	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String convertToJSON()
	{
		JSONObject obj = new JSONObject();

	      try {
	    	  
	    	obj.put("symbol", this.symbol);  
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
	
	public barByInterval(fiveSec[] arrFiveSec,int interval,long time,String symbol)
	//public barByInterval(fiveSec[] arrFiveSec,int interval,long time)
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
		
		this.symbol = symbol;
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
