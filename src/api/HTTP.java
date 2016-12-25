package api;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String SERVER_URL = "http://airscort-server.appspot.com";	
	
	public int request(String userName, String password){
		
		try {
			int responseFromServer = sendGET(userName, password);
			if(responseFromServer==1)
			{
				return 1;
			}else{
				if(responseFromServer==0)
				{					
					return 0;
				}
				if(responseFromServer==-1)
				{					
					return -1;
				}
				if(responseFromServer==400)
				{					
					return 400;
				}
			}

		} catch (IOException e) {
			return -1;
		}
		return -1;
	}
	
/*	//http://airscort-server.appspot.com/user?function=userValied&userName=aviram120&password=1234
	public static void main(String[] args) throws IOException {

		int responseFromServer = sendGET("aviram120","1234");
		String stResp="";

		if(responseFromServer==1)
		{
			System.out.println("is OK!");
		}
		else{
			if(responseFromServer==0)
			{
				System.out.println("ERROR: userName or password is in currect. try again...");
				return;
			}
			if(responseFromServer==-1)
			{
				System.out.println("ERROR");
				return;
			}
			if(responseFromServer==400)
			{
				System.out.println("ERROR in coonect to server");
				return;
			}
		}

	}*/

	private static int sendGET(String userName, String password) throws IOException {
		int responseInt;

		String stUrl = SERVER_URL + "/user?function=userValied&userName=" + userName + "&password=" + password;
		URL obj = new URL(stUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if (responseCode>399)
		{
			responseInt = 400;//error connect to server
		}
		//System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();


			if (Integer.parseInt(response.toString()) == 1)
			{
				responseInt = 1;//user is ok
			}
			else
			{
				responseInt = 0;//ERROR -user name or password are not currect
			}
		} else {
			responseInt = -1;
		}

		return responseInt;
	}

}
