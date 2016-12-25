package notUsed2;

import java.util.LinkedList;
import java.util.List;

public class symbolIndex {

	public final int ACTIVE = 1;
	public final int WAIT = 2;
	public final int CLOSED = 3;
	public final int CANCEl = 4;
	
	
	private String symbol;
	private List<order> listOrders;
	
	private int numOfActiveOrders;//ACTIVE
	private int numOfWaitOrders;//WAIT
	private int numOfClosedOrder;//CLOSED - Reached to 'stop' or 'take-profit' 
	private int numOfCancelOrder;//CANCEl - cancle by the robot
	
	public symbolIndex(String symbol,order newOrder)
	{
		this.symbol = symbol;
		
		listOrders = new LinkedList<order>();
		listOrders.add(newOrder);
		
		this.numOfActiveOrders = 0;
		this.numOfWaitOrders = 0;
		this.numOfClosedOrder = 0;
		this.numOfCancelOrder = 0;
		
	}
	
	public void addNewOrderToSymbol(order newOrder)
	{
		this.listOrders.add(newOrder);
	}

	public boolean searchIfHaveOpenOrder()
	{//the function cheak in all the order of the symbol if there active/wait order
		for (int i = 0; i<this.listOrders.size(); i++)
		{
			int orderStatus = this.listOrders.get(i).getStatus();
			if ((orderStatus == ACTIVE) || (orderStatus == WAIT))
			{
				return true;			
			}		
		}
		
		return false;
		
	}

	
	public int getNumOfActiveOrCloseOrders()
	{
		return this.numOfActiveOrders + this.numOfClosedOrder;
	}
	public boolean isHaveFuterOrders()
	{
		if (this.numOfWaitOrders != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	
	
	public int getNumOfActiveOrders() {
		return numOfActiveOrders;
	}

	public void setNumOfActiveOrders(int numOfActiveOrders) {
		this.numOfActiveOrders = numOfActiveOrders;
	}

	public int getNumOfWaitOrders() {
		return numOfWaitOrders;
	}

	public void setNumOfWaitOrders(int numOfWaitOrders) {
		this.numOfWaitOrders = numOfWaitOrders;
	}

	public int getNumOfClosedOrder() {
		return numOfClosedOrder;
	}

	public void setNumOfClosedOrder(int numOfClosedOrder) {
		this.numOfClosedOrder = numOfClosedOrder;
	}
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<order> listOrders) {
		this.listOrders = listOrders;
	}
	
}
