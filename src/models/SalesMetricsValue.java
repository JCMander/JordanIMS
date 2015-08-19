package models;


/** 
 * This class will be used like a Product in JavaEE, where we make a new SalesMetricValue for each product each day
 * This will then be added to an ArrayList of SalesMetricValues, to then be printed in the pdf
 * **/
public class SalesMetricsValue {

	private int productID;
	private int lastValue;
	private int newValue;
	private String productName;
	private int totalAmountSold;
	//so we can determine the total value of the amount of the products sold, productPrice * totalAmountSold
	private double productPrice;
	
	public SalesMetricsValue(int productID, int lastValue, int newValue,
			String productName, double productPrice) {
		this.productID = productID;
		this.lastValue = lastValue;
		this.newValue = newValue;
		this.productName = productName;
		this.totalAmountSold = lastValue-newValue;
		this.productPrice = productPrice;
	}
	
	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getLastValue() {
		return lastValue;
	}
	public void setLastValue(int lastValue) {
		this.lastValue = lastValue;
	}
	public int getNewValue() {
		return newValue;
	}
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getTotalAmountSold() {
		return totalAmountSold;
	}
	public void setTotalAmountSold(int totalAmountSold) {
		this.totalAmountSold = totalAmountSold;
	}
	
	
	
}
