package ayAPI;


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
	
	private int id;
	private int idServer;
	private double orderPrice;
	private double stop;
	private double takeProfit;
	private double quantity;
	private int action;//long - 1; short - 2;
	private int counterBar;
	private long time;
	private int status;//active - 1; wait - 2; closed -3; cancel - 4;
	
	
	
	public order(int id ,int idServer, double orderPrice, double stop, double takeProfit, double quantity, int action, long time, int status) 
	{//make new orde to place in BROKER
		
		this.id = id;
		this.idServer = idServer;
		this.orderPrice = orderPrice;	
		this.stop = stop;
		this.takeProfit = takeProfit;
		this.quantity = quantity;
		this.action = action;
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


	public double getOrderPrice() {
		return orderPrice;
	}


	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
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


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
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
