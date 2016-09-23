package api2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesClass {



	public static String fileName = "config.properties";

	public static String getProperties(String proName)
	{//the function return properties from the 'config.properties' file

		//System.out.println(System.getProperty("user.dir"));//get the path of the server reads
		
		Properties properties = new Properties();
		FileInputStream fis = null;
		try 
		{ 
			fis = new FileInputStream(fileName);//read from C:\Program Files (x86)\IBM\WebSphere\AppServer\profiles\AppSrv01 
			properties.loadFromXML(fis);
			fis.close();

		}catch (Exception e) {
			
			try {
				fis.close();
			} catch (IOException e1) {
				
			}
			
			
		}



		return  properties.getProperty(proName);
	}

	
}
