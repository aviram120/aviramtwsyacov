package notUsed;




import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import api.barByInterval;



import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class exelTest {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write(barByInterval object,String strategy, int index) throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet,object,strategy,index);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		// cv.setAutosize(true);


		// Write a few headers
		addCaption(sheet, 0, 0, "Header 1");
		addCaption(sheet, 1, 0, "This is another header");


	}

	private void createContent(WritableSheet sheet ,barByInterval object,String strategy, int index) throws WriteException,RowsExceededException {
		// Write a few number


		/* 
	  for (int i = 1; i < 10; i++) {
      // First column
      addNumber(sheet, 0, i, i + 10);
      // Second column
      addNumber(sheet, 1, i, i * i);
    }
   // Lets calculate the sum of it
    StringBuffer buf = new StringBuffer();
    buf.append("SUM(A2:A10)");
    Formula f = new Formula(0, 10, buf.toString());
    sheet.addCell(f);
    buf = new StringBuffer();
    buf.append("SUM(B2:B10)");
    f = new Formula(1, 10, buf.toString());
    sheet.addCell(f);*/


		/* // now a bit of text
    for (int i = 12; i < 20; i++) {
      // First column
      addLabel(sheet, 0, i, "Boring text " + i);
      // Second column
      addLabel(sheet, 1, i, "Another text");
    }
		 */


		//only one time
		int i = 0; int j = 0;
		addLabel(sheet, i,j, "id"); i++;
		addLabel(sheet, i,j, "time"); i++;
		addLabel(sheet, i,j, "opne");i++;
		addLabel(sheet, i,j, "high");i++;
		addLabel(sheet, i,j, "low");i++;
		addLabel(sheet, i,j, "close");i++;
		addLabel(sheet, i,j, "vol");i++;
		addLabel(sheet, i,j, "action");i++;
		/*addLabel(sheet, i,j, "stop");i++;
    addLabel(sheet, i,j, "take Profit");i++;*/

		j = 0;
		addNumber(sheet, j, index, index); j++;//id
		addDouble(sheet, j, index, object.getTime()); j++;//time
		addDouble(sheet, j, index, object.getOpen()); j++;//open
		addDouble(sheet, j, index, object.getHigh()); j++;//high
		addDouble(sheet, j, index, object.getLow()); j++;//low
		addDouble(sheet, j, index, object.getClose()); j++;//close
		addDouble(sheet, j, index, object.getVolume()); j++;//volume
		addLabel(sheet, j, index, strategy); j++;//strategy


	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}
	private void addDouble(WritableSheet sheet, int column, int row,
			double doubleNum) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, doubleNum, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	public static void main(String[] args) throws WriteException, IOException {
		/*  exelTest test = new exelTest();
    test.setOutputFile("d:/algo.xls");

    //long time,double open, double high, double low,double close, long volum, int type)
    barByInterval tempObj = new barByInterval(1414, 10.5, 12, 9, 10, 44, 1);

    test.write(tempObj,"bar triger",1);
    System.out
        .println("Please check the result file under d:/algo.xls ");*/


		String timeStop = "16:22";
		System.out.println(getCurrentTime(timeStop));

	}
	public static boolean getCurrentTime(String timeToBreak)
	{//the function return the current time(hh:tt:ss)

		//TODO-replace with arrDate[counter].getTime
		long currTimeLong = 1469796000*1000L;
		Date dateCurr = new Date(currTimeLong);

		//time to break
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date dateToBreak = null;
		try {
			dateToBreak = formatter.parse(timeToBreak);
			dateToBreak.setYear(dateCurr.getYear());
			dateToBreak.setMonth(dateCurr.getMonth());
			dateToBreak.setDate(dateCurr.getDate());
			System.out.println(dateCurr);
			System.out.println(dateToBreak);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (dateCurr.before(dateToBreak))
		{
			return true;
		}
		else
		{
			return false;
		}



	}

} 

