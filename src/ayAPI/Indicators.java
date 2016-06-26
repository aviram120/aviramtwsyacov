package ayAPI;

public abstract class Indicators  {
	
	public double calculateMovingAverage(fiveSec[] arrFiveSec,int numBar,int indexStart)
	{//the function get array of data, number of bars to calculate, and in which index in array to start the calculation
		
		
		
		if (arrFiveSec[indexStart-numBar]!=null)//check if there is enough data in array 
		{
			double priceSum = 0;
			
			for (int i=indexStart; i>indexStart-numBar; i--)
			{
				priceSum = priceSum +arrFiveSec[i].getClose();			
			}
			
			return priceSum/numBar;	
		}
		
		return -1;
	}
	
	

}
