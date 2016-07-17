package ayAPI;


public class testTread {

	public static void main(String args[]) {
		  int x=5;
	
	     RunnableDemo R1 = new RunnableDemo( "Thread-1",x);
	      R1.start();
	      
	      
	      RunnableDemo R2 = new RunnableDemo( "Thread-2",x);
	      R2.start();
	   } 
	public static void mainFunction1()
	{
		 try {
	         for(int i = 4; i > 0; i--) {
 
	        	 System.out.println("mainFunction1");
	            // Let the thread sleep for a while.
	            Thread.sleep(50);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Thread  interrupted.");
	     }
	     System.out.println("Thread  exiting.");
	     
		
	}
	public static void mainFunction2()
	{
		
		 try {
	         for(int i = 4; i > 0; i--) {
 
	        	 System.out.println("mainFunction2");
	            // Let the thread sleep for a while.
	            Thread.sleep(50);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Thread  interrupted.");
	     }
	     System.out.println("Thread  exiting.");
	}
}

class RunnableDemo implements Runnable {
	   private Thread t;
	   private String threadName;
	   private int x;
	   
	   RunnableDemo( String name,int x){
		   this.x = x;
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   private void t1()
	   {
		   x = x+1;
		   System.out.println("1run from: "+this.threadName +" x:"+x);
	   }
	   
	   private void t2()
	   {
		   x = x+2;
		   System.out.println("1run from: "+this.threadName +" x:"+x);
	   }
	   
	   public void run() {
		   

      	 
	    System.out.println("Running " +  threadName );
	      try {
	         for(int i = 4; i > 0; i--) {
	        	 
	        	 
	        	 if (this.threadName.equals("Thread-1"))
	          		 this.t1();
	          	 
	          	 if (this.threadName.equals("Thread-2"))
	          		 this.t2();
	          	 
	            // Let the thread sleep for a while.
	            Thread.sleep(50);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	     }
	     System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start ()
	   {
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

	}