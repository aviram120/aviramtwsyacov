package api2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class mainTest {
	public static void main(String[] args) {
		
		Date date = new Date(1471880340*1000L);
		SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
		
		date.setHours(date.getHours()-7);
		String endTime = formatter.format(date);
		System.out.println(endTime);
	}
}
