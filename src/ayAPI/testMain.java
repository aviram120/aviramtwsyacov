package ayAPI;

import org.json.JSONException;
import org.json.JSONObject;


public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONObject obj = new JSONObject();

	      try {
			obj.put("name", "foo");
			obj.put("num", new Integer(100));
		    obj.put("balance", new Double(1000.21));
		    obj.put("is_vip", new Boolean(true));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     

	      System.out.print(obj);
		
		
	}
}
