package controllers;

import java.util.ArrayList;

import models.SalesMetricsValue;

public class SalesMetricsValueManager {
	
	private ArrayList<SalesMetricsValue> salesMetricsValues = new ArrayList<SalesMetricsValue>();
	private static ArrayList<SalesMetricsValue> newSalesMetricsValues = new ArrayList<SalesMetricsValue>();
	
	public ArrayList<SalesMetricsValue> getSalesMetricsValues() {
		return salesMetricsValues;
	}

	public void setSalesMetricsValue(SalesMetricsValue salesMetricValue) {
		setNewSalesMetricsValue(salesMetricValue);
		salesMetricsValues.add(salesMetricValue);
	}

	public void setSalesMetricsValues(ArrayList<SalesMetricsValue> salesMetricsValues) {
		setNewSalesMetricsValues(salesMetricsValues);
		this.salesMetricsValues.addAll(salesMetricsValues);
	}
	
	public static ArrayList<SalesMetricsValue> getNewSalesMetricsValues() {
		return newSalesMetricsValues;
	}

	public static void setNewSalesMetricsValue(SalesMetricsValue salesMetricValue) {
		newSalesMetricsValues.add(salesMetricValue);
	}

	public static void setNewSalesMetricsValues(ArrayList<SalesMetricsValue> salesMetricsValues) {
		newSalesMetricsValues.addAll(salesMetricsValues);
	}

}
