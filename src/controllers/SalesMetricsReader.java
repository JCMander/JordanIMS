package controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import models.SalesMetricsValue;


//Need a way of splitting the data that it gets (currently seperated by space)
//Once all the data is split (or alternatively find a way to read in line by line), make a new SalesMetricValue for each product on that day

public class SalesMetricsReader {
	private String[] salesMetricValue = new String[6];
	SalesMetricsValueManager salesMetricsValueManager = new SalesMetricsValueManager();

	/**look through each line
	*take each element (separated by ,)
	*each line make a new SalesMetricsValue(productID, lastValue, newValue, productName, totalAmountSold, productPrice)
	**/
	public SalesMetricsReader(){
		ArrayList<String> salesMetricValues = new ArrayList<String>();
		try {
			for (String line : Files.readAllLines(Paths.get("C://Users//jmander//workspace//BlueGardens//SalesMetrics//salesMetrics"))) {
			    for (String part : line.split("\r\n")) {
			        salesMetricValues.add(part);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i =0; i< salesMetricValues.size(); i++){
			salesMetricValue = salesMetricValues.get(i).split(",");
			salesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(Integer.parseInt(salesMetricValue[0]),
					Integer.parseInt(salesMetricValue[3]), Integer.parseInt(salesMetricValue[4]), salesMetricValue[1],
					Double.parseDouble(salesMetricValue[2])));

		}
	}
	
}