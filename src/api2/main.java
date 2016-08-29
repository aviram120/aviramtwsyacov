package api2;

import java.awt.EventQueue;

public class main {

	public static void main(String[] args) {
		startJframeHTTP();
	}
	

	private static void startJframeHTTP(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JframeHTTP frame = new JframeHTTP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
