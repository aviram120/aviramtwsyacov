package ayAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class mainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String inputString = "20160809 18:54:00";

        Date date;
		try {
			date = sdf.parse( inputString);
			 System.out.println("in milliseconds: " + date.getTime()/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
	}

}
