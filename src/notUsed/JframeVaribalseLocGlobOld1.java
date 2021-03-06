package notUsed;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import api.ManagerRealTimeData;
import api.globalVar;
import api.localVar;

public class JframeVaribalseLocGlobOld1 extends JFrame {
	//gui elements
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
	JTextField textFieldTimeStop;
	JLabel lblTimecloseallorder;
	JTextField textFieldTimeCloseAllOrder;
	JLabel lblSymbol;
	JTextField textFieldSymbol;
	JLabel lblStrategy;
	JLabel lblDirection;
	JLabel lblMovingavr;
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
	JSpinner spinnerOrderType;
	JSpinner spinnerCentToGiveup;
	JSpinner spinnerDefineNextStop;
	JSpinner spinnerStopType;
	JSpinner spinnerMaxRisk;
	JSpinner spinnerStrategy;
	JSpinner spinnerDirection;
	JSpinner spinnerMovingAvr;
	JSpinner spinnerInterval;
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
	JCheckBox chckbxIsAggressive;
	JButton btnStart;
	JButton btnStop;
	JLabel lblNewLabel_1;
	JTextField textFieldThreadID;
	//algo varibale
	globalVar tempGlobal= null;
	localVar tempLocal = null;
	ManagerRealTimeData newObj;
	int threadId;
	int portNumberToserverChat = 1500;

	public JframeVaribalseLocGlobOld1() {
		initialize();
	}

	public void initialize(){
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

		textFieldTimeStop = new JTextField();
		textFieldTimeStop.setBounds(10, 170, 86, 20);
		textFieldTimeStop.setColumns(10);

		lblTimecloseallorder = new JLabel("timeCloseAllOrder");
		lblTimecloseallorder.setBounds(106, 198, 118, 16);
		lblTimecloseallorder.setFont(new Font("Tahoma", Font.PLAIN, 13));

		textFieldTimeCloseAllOrder = new JTextField();
		textFieldTimeCloseAllOrder.setBounds(10, 196, 86, 20);
		textFieldTimeCloseAllOrder.setColumns(10);		

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

		lblMovingavr = new JLabel("movingAvr");
		lblMovingavr.setBounds(369, 120, 118, 16);
		lblMovingavr.setFont(new Font("Tahoma", Font.PLAIN, 13));			

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

		spinnerOrderType = new JSpinner();
		spinnerOrderType.setBounds(10, 40, 86, 20);
		spinnerOrderType.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));

		spinnerCentToGiveup = new JSpinner();
		spinnerCentToGiveup.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerCentToGiveup.setBounds(10, 66, 86, 20);

		spinnerDefineNextStop = new JSpinner();
		spinnerDefineNextStop.setBounds(10, 92, 86, 20);

		spinnerStopType = new JSpinner();
		spinnerStopType.setBounds(10, 118, 86, 20);

		spinnerMaxRisk = new JSpinner();
		spinnerMaxRisk.setBounds(10, 144, 86, 20);

		spinnerStrategy = new JSpinner();
		spinnerStrategy.setBounds(273, 66, 86, 20);

		spinnerDirection = new JSpinner();
		spinnerDirection.setBounds(273, 92, 86, 20);

		spinnerMovingAvr = new JSpinner();
		spinnerMovingAvr.setBounds(273, 118, 86, 20);

		spinnerInterval = new JSpinner();
		spinnerInterval.setBounds(273, 144, 86, 20);

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

		chckbxIsAggressive = new JCheckBox("");
		chckbxIsAggressive.setBounds(340, 300, 21, 23);

		btnStart = new JButton("start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startAlgo();
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.setBounds(295, 543, 126, 31);

		btnStop = new JButton("stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopAlgo();
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStop.setBounds(431, 543, 126, 31);

		lblNewLabel_1 = new JLabel("thread id:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 553, 65, 14);

		textFieldThreadID = new JTextField();
		textFieldThreadID.setBounds(83, 550, 126, 20);
		textFieldThreadID.setColumns(10);

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
		panel.add(spinnerStopType);
		panel.add(spinnerDefineNextStop);
		panel.add(spinnerCentToGiveup);
		panel.add(spinnerOrderType);
		panel.add(textFieldTimeStop);
		panel.add(textFieldTimeCloseAllOrder);
		panel.add(lblNewLabel);
		panel.add(lblMaxriskpertransactionsdolars);
		panel.add(lblExtarprice);
		panel.add(lblPe);
		panel.add(lblBartriger);
		panel.add(textFieldSymbol);
		panel.add(spinnerStrategy);
		panel.add(spinnerDirection);
		panel.add(spinnerMovingAvr);
		panel.add(spinnerInterval);
		panel.add(spinnerMaxBarSize);
		panel.add(spinnerAddCentToBreak);
		panel.add(lblNumbartocanceldeal);
		panel.add(lblIsaggressive);
		panel.add(lblAddcenttobreak);
		panel.add(lblMaxbarsize);
		panel.add(lblSymbol);
		panel.add(lblStrategy);
		panel.add(lblDirection);
		panel.add(lblMovingavr);
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
		panel.add(chckbxIsAggressive);				
		panel.add(btnStart);			
		panel.add(btnStop);			
		panel.add(lblNewLabel_1);			
		panel.add(textFieldThreadID);
	}
	private void startAlgo(){
		//get globalVar
		int orderType = (int) spinnerOrderType.getValue();
		double centToGiveup = (double) spinnerCentToGiveup.getValue();
		int defineNextStop = (int) spinnerDefineNextStop.getValue();
		int stopType = (int) spinnerStopType.getValue();
		int maxRisk = (int) spinnerMaxRisk.getValue();
		String timeStopAddOrder = textFieldTimeStop.getText();
		String timeCloseAllOrder = textFieldTimeCloseAllOrder.getText();
		tempGlobal = new globalVar(orderType,centToGiveup,defineNextStop,stopType,maxRisk,timeStopAddOrder, timeCloseAllOrder);

		//get localVar
		String symbol = textFieldSymbol.getText();
		int strategy = (int) spinnerStrategy.getValue();
		int direction = (int) spinnerDirection.getValue();// = LONG;
		int movingAvr = (int) spinnerMovingAvr.getValue();
		int interval = (int) spinnerInterval.getValue();
		int minVolume = (int) spinnerMinVolume.getValue();
		double minBarSize = (double) spinnerMinBarSize.getValue();
		double maxBarSize = (double) spinnerMaxBarSize.getValue();
		double addCentToBreak = (double) spinnerAddCentToBreak.getValue();
		int numBarToCancelDeal = (int) spinnerNumBarToCancelDeal.getValue();
		boolean isAggressive = chckbxIsAggressive.isSelected();
		int maxTransactionsPerDay = (int) spinnerMaxTransactionsPerDay.getValue();
		double riskPerTransactionsDolars = (double) spinnerRiskPerTransactionsDolars.getValue();
		double maxRiskPerTransactionsDolars = (double) spinnerMaxRiskPerTransactionsDolars.getValue();
		double extarPrice = (double) spinnerExtarPrice.getValue();
		double pe = (double) spinnerPe.getValue();
		double barTriger = (double) spinnerBarTriger.getValue();
		tempLocal = new localVar(symbol, strategy, direction, movingAvr,interval, minVolume, minBarSize,maxBarSize, addCentToBreak, numBarToCancelDeal, 
				isAggressive, maxTransactionsPerDay,riskPerTransactionsDolars, maxRiskPerTransactionsDolars, extarPrice, pe, barTriger );
		
		//threadId
		threadId = Integer.parseInt(textFieldThreadID.getText());
		
		newObj = new ManagerRealTimeData(threadId, tempGlobal, tempLocal, portNumberToserverChat);
	}
	
	private void stopAlgo(){
		if(newObj != null)
		newObj.disconnect();
	}
}
