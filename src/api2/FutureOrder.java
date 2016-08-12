package api2;

public class FutureOrder {

	public final int ACTIVE = 1;
	public final int WAIT = 2;
	public final int CLOSED = 3;
	public final int CANCEl = 4;
	
	public final int BUY = 1;
	public final int SELL = 2;
	
	public final int MKT = 1;
	public final int STP = 2;
	public final int STP_LIMIT = 3;
	public final int LIMIT = 4;
	
	
	
	private int idServer;
	private int status;//ACTIVE,WAIT,CLOSED,CANCEl
	private int typeOrdr;//LMT, STP, STP LMT, MKT
	
	private double enterPrice;
	private double limitPrice;
	
	private double stopPrice;
	
	private double takeProfitPrice;
	
	
	public FutureOrder(int typeOrdr,double enterPrice,double limitPrice)
	{//only for add enter price
		
		this.status = WAIT;
		
		this.typeOrdr = typeOrdr;//can be STP_LIMIT or STP
		this.enterPrice = enterPrice;
		this.limitPrice = limitPrice;// if not used -1
	}
	
	public FutureOrder(double stopPrice)
	{//only for add stop price
		this.status = WAIT;
		
		this.typeOrdr = STP;
		this.stopPrice = stopPrice;
	}
	public FutureOrder(double takeProfitPrice,int notUse)
	{//only for add take profit price
		
		this.status = WAIT;
		
		this.typeOrdr = LIMIT;
		this.takeProfitPrice = takeProfitPrice;
	}

	
	
	
	
	public int getIdServer() {
		return idServer;
	}

	public void setIdServer(int idServer) {
		this.idServer = idServer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTypeOrdr() {
		return typeOrdr;
	}

	public void setTypeOrdr(int typeOrdr) {
		this.typeOrdr = typeOrdr;
	}

	public double getEnterPrice() {
		return enterPrice;
	}

	public void setEnterPrice(double enterPrice) {
		this.enterPrice = enterPrice;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public double getStopPrice() {
		return stopPrice;
	}

	public void setStopPrice(double stopPrice) {
		this.stopPrice = stopPrice;
	}

	public double getTakeProfitPrice() {
		return takeProfitPrice;
	}

	public void setTakeProfitPrice(double takeProfitPrice) {
		this.takeProfitPrice = takeProfitPrice;
	}
	
	
	
	
}
