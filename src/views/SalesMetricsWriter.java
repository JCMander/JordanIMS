package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controllers.NewSalesMetricsValueManager;
import controllers.SalesMetricsValueManager;
import models.SalesMetricsValue;

//Will hold the arraylist of salesmetric values
public class SalesMetricsWriter {
    private static String report = "";
    private static File reportFile = new File ("../salesMetrics");
    SalesMetricsValueManager salesMetricsValueManager;

	//Define all of the variables that we'll need to print to the pdf
	//The arraylist allows the pdf to be updated but not rewritten every time the file is written to, if it was an array, it would overwrite every time
	
	
	private static ArrayList<SalesMetricsValue> salesMetricsValues = new ArrayList<SalesMetricsValue>();
	
	//won't need any of the other variables, will just need to getProductID() etc. each entry of the table
	//private static int numberOfDaysPassed;
	//this will be used to determine the total value of all the products sold
	private static int totalPrice;
	
	/** 
	 * May or may not need a date, but the date will be the date of first creation
	 * Could be updated every time as long as the file name is fixed
	 * Ideally could have it update to the recent date hen the report was last pulled by a user
	 * **/


	
	//Constructor
	/**
	 * @author jake
	 */
	public SalesMetricsWriter() {
		
		salesMetricsValues = new ArrayList<SalesMetricsValue>();
		
		for(int i =0; i<SalesMetricsValueManager.getNewSalesMetricsValues().size(); i++){
			salesMetricsValues.add(SalesMetricsValueManager.getNewSalesMetricsValues().get(i));
		}
		
		for(int i=0; i<NewSalesMetricsValueManager.getNewSalesMetricsValues().size(); i++){
			salesMetricsValues.add(NewSalesMetricsValueManager.getNewSalesMetricsValues().get(i));
		}
		
		//salesMetricsValues = salesMetricsValueManager.getSalesMetricsValues();
		// productID = right productID
		// newValue = lastValue (order crucial)
		// lastValue = Simulation New ArrayList
		
		writeReport();
			
		
	}
	
	public void writeReport(){
		System.out.println(salesMetricsValues + " : " + salesMetricsValues.size());
		report="";
		/** File saving code begins here **/
		for(int i=0; i<salesMetricsValues.size(); i++){
			report+=(salesMetricsValues.get(i).getProductID() + "," + salesMetricsValues.get(i).getProductName()
					+ ","+ salesMetricsValues.get(i).getProductPrice() + "," + salesMetricsValues.get(i).getLastValue()
					+ "," + salesMetricsValues.get(i).getNewValue() + "," + salesMetricsValues.get(i).getTotalAmountSold() + "\r\n");
		}
		
		//Look through salesMetricValues and add each element, separated by a comma
		//report+=ele1,ele2,ele3
try {
				FileWriter fw = new FileWriter(reportFile + ".txt");
				fw.write(report);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			/*JOptionPane.showMessageDialog(null, "Your stock report has been saved");
			try {
				Runtime.getRuntime().exec("notepad " + reportFile + ".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			/** File saving code ends here **/
	}
	
		
	
}
