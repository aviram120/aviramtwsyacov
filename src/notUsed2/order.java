package notUsed2;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class order {
	
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
	
	
	private int id;
	private int idServer;
	private int typeOrdr;
	private double enterPrice;
	private double limitOrder;
	private double stop;
	private double takeProfit;
	private long volume;
	private int quantity;
	private int action;//buy - 1; sell - 2;
	private int counterBar;
	private long time;
	private int status;//active - 1; wait - 2; closed -3; cancel - 4;

	
	
	
	public order(int id ,int typeOrder, double enterPrice, double limitOrder, double stop, double takeProfit, long volume, int quantity, int action, int counterBar,long time,int status ) 
	{//make new orde to place in BROKER
		//action = BUY, SELL
		//orderType = LMT, STP, STP LMT, MKT
		
		this.id = id;
		this.idServer = -1;
		this.typeOrdr = typeOrder;
		this.enterPrice = enterPrice;
		this.limitOrder = limitOrder;
		this.stop = stop;
		this.takeProfit = takeProfit;
		this.volume = volume;
		this.quantity = quantity;
		this.action = action;
		this.counterBar = counterBar;
		this.time = time;
		this.status = status;	
	}
	
	

	public void writeStringToFile(String path, String stToWrite) throws IOException {
	  PrintWriter out = null;
	    BufferedWriter bufWriter;

	    try{
	        bufWriter = Files.newBufferedWriter(
								                Paths.get(path),
								                Charset.forName("UTF8"),
								                StandardOpenOption.WRITE, 
								                StandardOpenOption.APPEND,
								                StandardOpenOption.CREATE);
	        
	        out = new PrintWriter(bufWriter, true);
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	    out.println(stToWrite);
	    out.close();
	}
	
	
	public double getLimitOrder() {
		return limitOrder;
	}

	public void setLimitOrder(double limitOrder) {
		this.limitOrder = limitOrder;
	}
	public int getTypeOrdr() {
		return typeOrdr;
	}

	public void setTypeOrdr(int typeOrdr) {
		this.typeOrdr = typeOrdr;
	}

	
	public int getCounterBar() {
		return counterBar;
	}

	public void setCounterBar(int counterBar) {
		this.counterBar = counterBar;
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdServer() {
		return idServer;
	}


	public void setIdServer(int idServer) {
		this.idServer = idServer;
	}

	public double getEnterPrice() {
		return enterPrice;
	}

	public void setEnterPrice(double enterPrice) {
		this.enterPrice = enterPrice;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getStop() {
		return stop;
	}


	public void setStop(double stop) {
		this.stop = stop;
	}


	public double getTakeProfit() {
		return takeProfit;
	}


	public void setTakeProfit(double takeProfit) {
		this.takeProfit = takeProfit;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getAction() {
		return action;
	}


	public void setAction(int action) {
		this.action = action;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	
	
}
