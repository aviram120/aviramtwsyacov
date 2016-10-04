package api2;


import ayAPI.globalVar;
import ayAPI.localVar;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JframeVaribalseLocGlob extends JFrame {
	private HTTP httpRequest = new HTTP();
	private static Logger Logger;

	SpinnerDateModel spinMod1;	
	SpinnerDateModel spinMod2;
	SpinnerModel modelTheradNumber;
	//gui element
	JPanel contentPane;
	JPanel panel;
	JLabel lblLoacal;
	JLabel lblNewLabel;
	JLabel lblOrdertype;
	JLabel lblCenttogiveup;
	JLabel lblDefinenextstop;
	JLabel lblStoptype;
	JLabel lblMaxrisk;
	JLabel lblTimestopaddorder;
	JLabel lblTimecloseallorder;
	JLabel lblSymbol;
	JTextField textFieldSymbol;
	JLabel lblStrategy;
	JLabel lblDirection;
	JLabel lblMovingAvr;
	JLabel lblInterval;
	JLabel lblMinvolume;
	JLabel lblMinbarsize;
	JLabel lblMaxbarsize;
	JLabel lblIsaggressive;
	JLabel lblAddcenttobreak;
	JLabel lblNumbartocanceldeal;
	JLabel lblMaxtransactionsperday;
	JLabel lblRiskpertransactionsdolars;
	JLabel lblMaxriskpertransactionsdolars;
	JLabel lblExtarprice;
	JLabel lblPe;
	JLabel lblBartriger;
	JSpinner spinnerCentToGiveup;
	JSpinner spinnerDefineNextStop;
	JSpinner spinnerMaxRisk;
	JSpinner spinnerMinVolume;
	JSpinner spinnerMinBarSize;
	JSpinner spinnerMaxBarSize;
	JSpinner spinnerAddCentToBreak;
	JSpinner spinnerNumBarToCancelDeal;
	JSpinner spinnerMaxTransactionsPerDay;
	JSpinner spinnerRiskPerTransactionsDolars;
	JSpinner spinnerMaxRiskPerTransactionsDolars;
	JSpinner spinnerExtarPrice;
	JSpinner spinnerPe;
	JSpinner spinnerBarTriger;
	JButton btnNewButton;
	JButton btnStop;
	JLabel lblNewLabel_1;
	JComboBox comboBoxOrderType;	

	String[] stringsOrderType = { "Market", "Stop limit" };//1,2
	Map<String, Integer> mapOrderType = new HashMap<String, Integer>();

	private JComboBox comboBoxStopType;
	String[] stringsStopType = { "Low", "Close" };
	Map<String, Integer> mapStopType = new HashMap<String, Integer>();
	JSpinner spinnerTimeStopaddOrder;
	private JSpinner spinnerTimeCloseAllOrder;
	private JComboBox comboBoxStrategy;
	String[] stringsStrategy = { "InsideBar", "MovingAvr" };
	Map<String, Integer> mapStrategy = new HashMap<String, Integer>();
	JComboBox comboBoxDirection;
	String[] stringsDirection = { "Long", "Short" };
	Map<String, Integer> mapDirection = new HashMap<String, Integer>();
	JComboBox comboBoxMovingAvr;
	String[] stringsMovingAvr = { "MoivngAvr1", "MoivngAvr5", "MoivngAvr10", "MoivngAvr15", "MoivngAvr30", "MoivngAvr60", "NotUset"};
	Map<String, Integer> mapMovingAvr = new HashMap<String, Integer>();
	private JComboBox comboBoxInterval;
	String[] stringsInterval = { "M1", "M2", "M5", "M10", "M15", "M30", "M60"};
	Map<String, Integer> mapInterval = new HashMap<String, Integer>();

	JComboBox comboBoxIsAggressive;
	String[] stringsIsAggressive = { "No", "Yes"};
	Map<String, Boolean> mapIsAggressive = new HashMap<String, Boolean>();
	JSpinner spinnerThreadId;
	//algo varibale
	globalVar tempGlobal= null;
	localVar tempLocal = null;
	ManagerRealTimeData newObj;
	int threadId;
	int portNumberToserverChat = 1500;

	public JframeVaribalseLocGlob() {
		if(authentication())
			initialize();
	}

	public void initialize(){	
		mapOrderType.put("Market", new Integer(1));
		mapOrderType.put("Stop limit", new Integer(2));

		mapStopType.put("Low", new Integer(1));
		mapStopType.put("Close", new Integer(2));

		mapStrategy.put("InsideBar", new Integer(1));
		mapStrategy.put("MovingAvr", new Integer(2));

		mapDirection.put("Long", new Integer(1));
		mapDirection.put("Short", new Integer(2));

		mapMovingAvr.put("MoivngAvr1", new Integer(1));
		mapMovingAvr.put("MoivngAvr5", new Integer(5));
		mapMovingAvr.put("MoivngAvr10", new Integer(10));
		mapMovingAvr.put("MoivngAvr15", new Integer(15));
		mapMovingAvr.put("MoivngAvr30", new Integer(30));
		mapMovingAvr.put("MoivngAvr60", new Integer(60));
		mapMovingAvr.put("NotUset", new Integer(-1));


		mapInterval.put("M1", new Integer(1));
		mapInterval.put("M2", new Integer(2));
		mapInterval.put("M5", new Integer(5));
		mapInterval.put("M10", new Integer(10));
		mapInterval.put("M15", new Integer(15));
		mapInterval.put("M30", new Integer(30));
		mapInterval.put("M60", new Integer(60));

		mapIsAggressive.put("No", false);
		mapIsAggressive.put("Yes", true);

		spinMod1 = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
		spinMod2 = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
		modelTheradNumber = new SpinnerNumberModel(1, 1, 20, 1); 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();		
		contentPane.add(panel, BorderLayout.CENTER);

		lblLoacal = new JLabel("global");
		lblLoacal.setBounds(37, 11, 38, 16);
		lblLoacal.setFont(new Font("Tahoma", Font.BOLD, 13));

		lblNewLabel = new JLabel("local");
		lblNewLabel.setBounds(303, 11, 29, 16);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

		lblOrdertype = new JLabel("orderType");
		lblOrdertype.setBounds(106, 42, 145, 16);
		lblOrdertype.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblCenttogiveup = new JLabel("centToGiveup");
		lblCenttogiveup.setBounds(106, 68, 85, 16);
		lblCenttogiveup.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblDefinenextstop = new JLabel("defineNextStop");
		lblDefinenextstop.setBounds(106, 94, 91, 16);
		lblDefinenextstop.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblStoptype = new JLabel("stopType");
		lblStoptype.setBounds(106, 120, 91, 16);
		lblStoptype.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMaxrisk = new JLabel("maxRisk");
		lblMaxrisk.setBounds(106, 146, 91, 16);
		lblMaxrisk.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblTimestopaddorder = new JLabel("timeStopAddOrder");
		lblTimestopaddorder.setBounds(106, 172, 118, 16);
		lblTimestopaddorder.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblTimecloseallorder = new JLabel("timeCloseAllOrder");
		lblTimecloseallorder.setBounds(106, 198, 118, 16);
		lblTimecloseallorder.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblSymbol = new JLabel("symbol");
		lblSymbol.setBounds(369, 42, 118, 16);
		lblSymbol.setFont(new Font("Tahoma", Font.PLAIN, 13));

		textFieldSymbol = new JTextField();
		textFieldSymbol.setBounds(273, 40, 86, 20);
		textFieldSymbol.setColumns(10);

		lblStrategy = new JLabel("strategy");
		lblStrategy.setBounds(369, 68, 118, 16);
		lblStrategy.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblDirection = new JLabel("direction");
		lblDirection.setBounds(369, 94, 118, 16);
		lblDirection.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMovingAvr = new JLabel("movingAvr");
		lblMovingAvr.setBounds(369, 120, 118, 16);
		lblMovingAvr.setFont(new Font("Tahoma", Font.PLAIN, 13));			

		lblInterval = new JLabel("interval");
		lblInterval.setBounds(369, 146, 118, 16);
		lblInterval.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMinvolume = new JLabel("minVolume");
		lblMinvolume.setBounds(369, 172, 118, 16);
		lblMinvolume.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMinbarsize = new JLabel("minBarSize");
		lblMinbarsize.setBounds(369, 198, 118, 16);
		lblMinbarsize.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMaxbarsize = new JLabel("maxBarSize");
		lblMaxbarsize.setBounds(369, 224, 118, 16);
		lblMaxbarsize.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblIsaggressive = new JLabel("isAggressive");
		lblIsaggressive.setBounds(369, 302, 118, 16);
		lblIsaggressive.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblAddcenttobreak = new JLabel("addCentToBreak");
		lblAddcenttobreak.setBounds(369, 250, 118, 16);
		lblAddcenttobreak.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblNumbartocanceldeal = new JLabel("numBarToCancelDeal");
		lblNumbartocanceldeal.setBounds(369, 276, 136, 16);
		lblNumbartocanceldeal.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMaxtransactionsperday = new JLabel("maxTransactionsPerDay");
		lblMaxtransactionsperday.setBounds(369, 328, 153, 16);
		lblMaxtransactionsperday.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblRiskpertransactionsdolars = new JLabel("riskPerTransactionsDolars");
		lblRiskpertransactionsdolars.setBounds(369, 354, 153, 16);
		lblRiskpertransactionsdolars.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblMaxriskpertransactionsdolars = new JLabel("maxRiskPerTransactionsDolars");
		lblMaxriskpertransactionsdolars.setBounds(369, 380, 188, 16);
		lblMaxriskpertransactionsdolars.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblExtarprice = new JLabel("extarPrice");
		lblExtarprice.setBounds(369, 406, 118, 16);
		lblExtarprice.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblPe = new JLabel("pe");
		lblPe.setBounds(369, 432, 118, 16);
		lblPe.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblBartriger = new JLabel("barTriger");
		lblBartriger.setBounds(369, 458, 118, 16);
		lblBartriger.setFont(new Font("Tahoma", Font.PLAIN, 13));

		spinnerCentToGiveup = new JSpinner();
		spinnerCentToGiveup.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerCentToGiveup.setBounds(10, 66, 86, 20);

		spinnerDefineNextStop = new JSpinner();
		spinnerDefineNextStop.setBounds(10, 92, 86, 20);

		spinnerMaxRisk = new JSpinner();
		spinnerMaxRisk.setBounds(10, 144, 86, 20);

		spinnerMinVolume = new JSpinner();
		spinnerMinVolume.setBounds(273, 170, 86, 20);

		spinnerMinBarSize = new JSpinner();
		spinnerMinBarSize.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerMinBarSize.setBounds(273, 196, 86, 20);

		spinnerMaxBarSize = new JSpinner();
		spinnerMaxBarSize.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerMaxBarSize.setBounds(273, 222, 86, 20);

		spinnerAddCentToBreak = new JSpinner();
		spinnerAddCentToBreak.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerAddCentToBreak.setBounds(273, 248, 86, 20);

		spinnerNumBarToCancelDeal = new JSpinner();
		spinnerNumBarToCancelDeal.setBounds(273, 274, 86, 20);

		spinnerMaxTransactionsPerDay = new JSpinner();
		spinnerMaxTransactionsPerDay.setBounds(273, 326, 86, 20);

		spinnerRiskPerTransactionsDolars = new JSpinner();
		spinnerRiskPerTransactionsDolars.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerRiskPerTransactionsDolars.setBounds(273, 352, 86, 20);

		spinnerMaxRiskPerTransactionsDolars = new JSpinner();
		spinnerMaxRiskPerTransactionsDolars.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerMaxRiskPerTransactionsDolars.setBounds(273, 378, 86, 20);

		spinnerExtarPrice = new JSpinner();
		spinnerExtarPrice.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerExtarPrice.setBounds(273, 404, 86, 20);

		spinnerPe = new JSpinner();
		spinnerPe.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerPe.setBounds(273, 430, 86, 20);

		spinnerBarTriger = new JSpinner();
		spinnerBarTriger.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerBarTriger.setBounds(273, 456, 86, 20);

		btnNewButton = new JButton("start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startAlgo();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(295, 543, 126, 31);

		btnStop = new JButton("stop");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStop.setBounds(431, 543, 126, 31);

		lblNewLabel_1 = new JLabel("thread id:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 553, 65, 14);

		comboBoxOrderType = new JComboBox(stringsOrderType);		
		comboBoxOrderType.setBounds(10, 40, 86, 20);

		comboBoxStopType = new JComboBox(stringsStopType);
		comboBoxStopType.setBounds(10, 118, 86, 20);

		spinnerTimeStopaddOrder = new JSpinner(spinMod1);
		spinnerTimeStopaddOrder.setEditor(new JSpinner.DateEditor(spinnerTimeStopaddOrder,"HH:mm"));
		spinnerTimeStopaddOrder.setBounds(10, 170, 86, 20);

		spinnerTimeCloseAllOrder = new JSpinner(spinMod2);
		spinnerTimeCloseAllOrder.setEditor(new JSpinner.DateEditor(spinnerTimeCloseAllOrder,"HH:mm"));
		spinnerTimeCloseAllOrder.setBounds(10, 196, 86, 20);

		comboBoxStrategy = new JComboBox(stringsStrategy);
		comboBoxStrategy.setBounds(273, 66, 86, 20);

		comboBoxDirection = new JComboBox(stringsDirection);
		comboBoxDirection.setBounds(273, 92, 86, 20);

		comboBoxMovingAvr = new JComboBox(stringsMovingAvr);
		comboBoxMovingAvr.setBounds(273, 118, 85, 20);

		comboBoxInterval = new JComboBox(stringsInterval);
		comboBoxInterval.setBounds(273, 144, 86, 20);

		comboBoxIsAggressive = new JComboBox(stringsIsAggressive);
		comboBoxIsAggressive.setBounds(273, 300, 86, 20);

		spinnerThreadId = new JSpinner(modelTheradNumber);		
		spinnerThreadId.setBounds(71, 550, 86, 20);

		panel.setLayout(null);
		panel.add(lblTimestopaddorder);
		panel.add(lblTimecloseallorder);
		panel.add(lblCenttogiveup);
		panel.add(lblLoacal);
		panel.add(lblOrdertype);
		panel.add(lblDefinenextstop);
		panel.add(lblStoptype);
		panel.add(lblMaxrisk);
		panel.add(spinnerMaxRisk);
		panel.add(spinnerDefineNextStop);
		panel.add(spinnerCentToGiveup);
		panel.add(lblNewLabel);
		panel.add(lblMaxriskpertransactionsdolars);
		panel.add(lblExtarprice);
		panel.add(lblPe);
		panel.add(lblBartriger);
		panel.add(textFieldSymbol);
		panel.add(spinnerMaxBarSize);
		panel.add(spinnerAddCentToBreak);
		panel.add(lblNumbartocanceldeal);
		panel.add(lblIsaggressive);
		panel.add(lblAddcenttobreak);
		panel.add(lblMaxbarsize);
		panel.add(lblSymbol);
		panel.add(lblStrategy);
		panel.add(lblDirection);
		panel.add(lblMovingAvr);
		panel.add(lblInterval);
		panel.add(lblMinvolume);
		panel.add(lblMinbarsize);
		panel.add(lblMaxtransactionsperday);
		panel.add(lblRiskpertransactionsdolars);
		panel.add(spinnerMinBarSize);
		panel.add(spinnerMinVolume);						
		panel.add(spinnerNumBarToCancelDeal);			
		panel.add(spinnerMaxTransactionsPerDay);			
		panel.add(spinnerRiskPerTransactionsDolars);			
		panel.add(spinnerMaxRiskPerTransactionsDolars);		
		panel.add(spinnerExtarPrice);			
		panel.add(spinnerPe);		
		panel.add(spinnerBarTriger);
		panel.add(btnNewButton);			
		panel.add(btnStop);			
		panel.add(lblNewLabel_1);
		panel.add(comboBoxOrderType);			
		panel.add(comboBoxStopType);		
		panel.add(spinnerTimeStopaddOrder);			
		panel.add(spinnerTimeCloseAllOrder);
		panel.add(comboBoxStrategy);				
		panel.add(comboBoxDirection);		
		panel.add(comboBoxMovingAvr);				
		panel.add(comboBoxInterval);		
		panel.add(comboBoxIsAggressive);
		panel.add(spinnerThreadId);


	}
	private void readFileToGUI(String fileName)
	{//the function get a file path(file that include 'global' and 'local' vars) and set the var into GUI 
		
		String partGlobal = "";
		String partLocal = "";
		try {
			String stFile = readFile(fileName);//read the file
			String[] parts = stFile.split("\n");
			partGlobal = parts[0]; 
			partLocal = parts[1]; 

		} catch (IOException e) {
			e.printStackTrace();
		}



		globalVar tempGlobal = new globalVar(partGlobal);//from JSON

		spinnerCentToGiveup.setValue(tempGlobal.getCentToGiveup());
		comboBoxOrderType.setSelectedItem(getKeyFromValue(mapOrderType,tempGlobal.getOrderType()));
		spinnerDefineNextStop.setValue(tempGlobal.getDefineNextStop());
		comboBoxStopType.setSelectedItem(getKeyFromValue(mapStopType,tempGlobal.getStopType()));
		spinnerMaxRisk.setValue(tempGlobal.getMaxRisk());

		//set time value
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		try {
			Date dateTimeStopaddOrder = simpleDateFormat.parse(tempGlobal.getTimeStopAddOrder());
			spinnerTimeStopaddOrder.setValue(dateTimeStopaddOrder);	

			Date dateTimeCloseAllOrder = simpleDateFormat.parse(tempGlobal.getTimeCloseAllOrder());
			spinnerTimeCloseAllOrder.setValue(dateTimeCloseAllOrder);	

		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		localVar tempLocal = new localVar(partLocal);//from JSON
		comboBoxStrategy.setSelectedItem(getKeyFromValue(mapStrategy,tempLocal.getStrategy()));
		comboBoxDirection.setSelectedItem(getKeyFromValue(mapDirection,tempLocal.getDirection()));
		comboBoxMovingAvr.setSelectedItem(getKeyFromValue(mapMovingAvr,tempLocal.getMovingAvrBar()));
		comboBoxInterval.setSelectedItem(getKeyFromValue(mapInterval,tempLocal.getInterval()));
		spinnerMinVolume.setValue(tempLocal.getMinVolume());
		spinnerMinBarSize.setValue(tempLocal.getMinBarSize());
		spinnerMaxBarSize.setValue(tempLocal.getMaxBarSize());
		spinnerAddCentToBreak.setValue(tempLocal.getAddCentToBreak());
		spinnerNumBarToCancelDeal.setValue(tempLocal.getNumBarToCancelDeal());
		comboBoxIsAggressive.setSelectedItem(getKeyFromValue(mapIsAggressive,tempLocal.isAggressive()));
		spinnerMaxTransactionsPerDay.setValue(tempLocal.getMaxTransactionsPerDay());
		spinnerRiskPerTransactionsDolars.setValue(tempLocal.getRiskPerTransactionsDolars());
		spinnerMaxRiskPerTransactionsDolars.setValue(tempLocal.getMaxRiskPerTransactionsDolars());
		spinnerExtarPrice.setValue(tempLocal.getExtarPrice());
		spinnerPe.setValue(tempLocal.getPe());
		spinnerBarTriger.setValue(tempLocal.getBarTriger());
	}

	private static Object getKeyFromValue(Map hm, Object value) 
	{//the function searce in map a value
		
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	private void startAlgo(){
		//get globalVar
		int orderType = mapOrderType.get(stringsOrderType[comboBoxOrderType.getSelectedIndex()]);
		double centToGiveup = (double) spinnerCentToGiveup.getValue();
		int defineNextStop = (int) spinnerDefineNextStop.getValue();
		int stopType = mapStopType.get(stringsStopType[comboBoxStopType.getSelectedIndex()]);
		double maxRisk = (double) spinnerMaxRisk.getValue();		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime((Date) spinnerTimeStopaddOrder.getValue()); 								
		String timeStopAddOrder = "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
		calendar.setTime((Date) spinnerTimeCloseAllOrder.getValue());		
		String timeCloseAllOrder = "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
		tempGlobal = new globalVar(orderType,centToGiveup,defineNextStop,stopType,maxRisk,timeStopAddOrder, timeCloseAllOrder);

		//get localVar
		String symbol = textFieldSymbol.getText();
		int strategy = mapStrategy.get(stringsStrategy[comboBoxStrategy.getSelectedIndex()]);
		int direction = mapDirection.get(stringsDirection[comboBoxDirection.getSelectedIndex()]);		
		int movingAvr = mapMovingAvr.get(stringsMovingAvr[comboBoxMovingAvr.getSelectedIndex()]);
		int interval = mapInterval.get(stringsInterval[comboBoxInterval.getSelectedIndex()]);
		int minVolume = (int) spinnerMinVolume.getValue();
		double minBarSize = (double) spinnerMinBarSize.getValue();
		double maxBarSize = (double) spinnerMaxBarSize.getValue();
		double addCentToBreak = (double) spinnerAddCentToBreak.getValue();
		int numBarToCancelDeal = (int) spinnerNumBarToCancelDeal.getValue();
		boolean isAggressive = mapIsAggressive.get(stringsIsAggressive[comboBoxIsAggressive.getSelectedIndex()]);
		int maxTransactionsPerDay = (int) spinnerMaxTransactionsPerDay.getValue();
		double riskPerTransactionsDolars = (double) spinnerRiskPerTransactionsDolars.getValue();
		double maxRiskPerTransactionsDolars = (double) spinnerMaxRiskPerTransactionsDolars.getValue();
		double extarPrice = (double) spinnerExtarPrice.getValue();
		double pe = (double) spinnerPe.getValue();
		double barTriger = (double) spinnerBarTriger.getValue();
		tempLocal = new localVar(symbol, strategy, direction, movingAvr,interval, minVolume, minBarSize,maxBarSize, addCentToBreak, numBarToCancelDeal, 
				isAggressive, maxTransactionsPerDay,riskPerTransactionsDolars, maxRiskPerTransactionsDolars, extarPrice, pe, barTriger );

		//threadId
		threadId = (int) spinnerThreadId.getValue();

		System.setProperty("symbol", tempLocal.getSymbol());

		Logger = LoggerFactory.getLogger(mainJar.class);
		Logger.info("Hi, in mainGui {}", tempLocal.getSymbol());
		Logger.info("print local var {}", tempLocal.convertObjToJSON());
		Logger.info("print global var {}", tempGlobal.convertObjToJSON());

		newObj = new ManagerRealTimeData(threadId, tempGlobal, tempLocal, portNumberToserverChat);
	}

	private void saveGuiToFile(globalVar global, localVar local,String fileName)
	{// the function seve the GUI in to file
		
		String stGlobal = global.convertObjToJSON();
		String stLocal = local.convertObjToJSON();

		writeStringToFile(stGlobal + "\n" + stLocal, fileName);
	}
	private void writeStringToFile(String st,String fileName)
	{//the fucntion write to file a string
		BufferedWriter writer = null;
		try
		{
			
			writer = new BufferedWriter( new FileWriter(fileName));
			writer.write( st);

		}
		catch ( IOException e)
		{
		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
		}


	}



	private void stopAlgo(){
		if(newObj != null)
			newObj.disconnect();
	}

	private int getIndexOfArray(String[] array, String value){
		for (int i = 0; i < array.length; i++) {
			if(array[i].equals(value)){
				return i+1;
			}
		}
		return -1;
	}

	private boolean authentication(){
		int ansswer = httpRequest.request("haggailis", "AB123456");
		if(ansswer == 1){
			return true;									
		}
		return false;
	}

	private static String readFile(String fileName) throws IOException
	{//the fucntion return string from file
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}