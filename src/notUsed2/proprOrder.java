package notUsed2;

public class proprOrder {

	private int orderId;
	private String status;
	
	private int tkOrderId;
	private int stOrderId;
	
	public proprOrder(int orderId, String status, int tkOrderId, int stOrderId) {
		this.orderId = orderId;
		this.status = status;
		this.tkOrderId = tkOrderId;
		this.stOrderId = stOrderId;
	} 
	
	public void updateStatus(String newStatus)
	{
		this.status = newStatus;
	}
	
	

	
}
