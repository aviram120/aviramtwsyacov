package ayAPI;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			System.out.println(System.currentTimeMillis());
			System.out.println(1466697015);
			
			Timestamp stamp = new Timestamp(1466697015);
			  Date date = new Date(stamp.getTime());
			  System.out.println(date);
		
			String date1 = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (1466697015L));
			System.out.println(date1);
	}
}
