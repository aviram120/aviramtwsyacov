package ayAPI;

public class Indicators  {
	
	private double sumAverPriceMultVol;
	private long sumVol;
	
	public Indicators()
	{
		this.sumAverPriceMultVol = 0;
		this.sumVol = 0;
	}
	
	public double calculateMovingAverage(barByInterval[] arrData,int period, int arrLength)
	{//the function get array of data, number of bars to calculate, and in which index in array to start the calculation
		
		if (1+arrLength-period >=  0 )//check if there is enough data in array 
		{
			double priceSum = 0;
			
			for (int i=arrLength; i>arrLength-period; i--)
			{
				priceSum = priceSum +arrData[i].getClose();			
			}
			
			System.out.println("MovingAverage: " + priceSum/period);
			return priceSum/period;	
		}
		
		return -1;
	}
	public double calculateVWAP(barByInterval[] arrData, int indexInArr)
	{/*
		calculate VWAP; 
		for more info: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:vwap_intraday
	 */	
		
		
		//calculate average price (h+l+c)/3		
		double avrPrice = (arrData[indexInArr].getHigh() + arrData[indexInArr].getLow() + arrData[indexInArr].getClose())/3;
		long vol = arrData[indexInArr].getVolume();
		
		//save the sum for next value
		this.sumAverPriceMultVol = this.sumAverPriceMultVol + avrPrice * vol;
		this.sumVol = this.sumVol + vol;
		
		System.out.println("vwap: "+this.sumAverPriceMultVol/this.sumVol);
		return this.sumAverPriceMultVol/this.sumVol;
	}
	
	
	

}