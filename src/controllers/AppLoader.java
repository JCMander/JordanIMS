package controllers;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import models.DatabaseConnection;
import views.FrmTable;
import views.PurchaseOrder;

/** Main class **/

public class AppLoader {

	private static DatabaseConnection db;
	private static FrmTable frm;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static ArrayList<Integer> productThreshold;
    private static ArrayList<Integer> productWeight;
    private static ArrayList<Integer> productPrice;
    private static String stockListMessage = "";
    private static String[] options = new String[2];
    private static int viewReceipt;
    private static int[] maxStock;
    
    /** 
     * Main class, this is used to run the application, from here FrmTable and Database Connection are called
     * **/
    
	public static void main(String[] args) {
		

		
		/*smw = new SalesMetricsWriter();
		
		smw.setSalesMetricsValue(new SalesMetricsValue(1, 15, 14, "Item1", 2.00));
		smw.setSalesMetricsValue(new SalesMetricsValue(2, 24, 23, "Item2", 1.00));
		smw.setSalesMetricsValue(new SalesMetricsValue(3, 36, 1, "Item3", 5.00));
		smw.setSalesMetricsValue(new SalesMetricsValue(4, 46, 34, "Item4", 7.00));
		smw.setSalesMetricsValue(new SalesMetricsValue(5, 44, 24, "Item5", 9.99));

		smw.writeReport();*/
		
		
		
		db = new DatabaseConnection();
		frm = new FrmTable();
		frm.pack();
        frm.setLocationRelativeTo(null);
		db.accessDB();
		db.readDB();

		setProductID(db.getProductID());
		setProductWeight(db.getProductWeight());
		setProductQuantity(db.getProductQuantity());
		setProductName(db.getProductName());
		setProductThreshold(db.getProductThreshold());
		setProductPrice(db.getProductPrice());
		
		
		setMaxStock(new int[getProductID().size()]);
		for(int i =0; i<getProductID().size(); i++){
			getMaxStock()[i] = (getProductThreshold().get(i)*4);
		}
		
        frm.setVisible(true);	
		new PurchaseOrder();		
		makeTable();
	}
	
	public static void stockListMessage(){
	    options[0] = new String("OK");
	    options[1] = new String("View Receipt");
		/** Message to show when quantity is low code starts here**/
		int msgcount =0;
		for(int i=0; i<getProductID().size(); i++){
			if(getProductQuantity().get(i)<=getProductThreshold().get(i)){
				stockListMessage = stockListMessage +  getProductName().get(i) + "      Quantity: " + getProductQuantity().get(i) + "\n";
				msgcount++;
				db.updateDB(getProductID().get(i), getMaxStock()[i]);
			}
		}
		if(msgcount>0){
		    viewReceipt = JOptionPane.showOptionDialog(null, "The following products are low in quantity and so have been re-ordered:\n\n" + stockListMessage,"Low Stock Levels", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		}
		
		if(viewReceipt == 1){
				try {
					 
					if ((new File(PurchaseOrder.getFILE())).exists()) {
			 
						Process p = Runtime
						   .getRuntime()
						   .exec("rundll32 url.dll,FileProtocolHandler " + PurchaseOrder.getFILE());
						p.waitFor();
			 
					} else {
			 
					    JOptionPane.showMessageDialog(null, "This file does not exist");
			 
					}			 
			  	  } catch (Exception ex) {
					ex.printStackTrace();
					}
         
		}
		/** Message to show when quantity is low code ends here **/
	}
	
	public static void makeTable(){
		/** Make the table code starts here **/
		for(int i=0; i<getProductID().size(); i++){
			frm.addProductToTable(getProductID().get(i), getProductName().get(i), getProductQuantity().get(i), getProductThreshold().get(i));
		}
		stockListMessage();
		/** Make the table code ends here **/
	}
	

	public void addProduct(int newid, String newname){
		db.createEntry(newid, newname);
	}

	public void updateProduct(int id, int quantity){
		db.updateDB(id, quantity);
	}
	
	public void updateThreshold(int id, int threshold){
		db.updateDBThreshold(id, threshold);
	}
	
	public String getProductName(int name1){
		return productName.get(name1-1);
	} 
	
	public int getProductQuantity(int name1){
		return productQuantity.get(name1-1);
	}

	public static ArrayList<Integer> getProductID() {
		return productID;
	}


	public static void setProductID(ArrayList<Integer> productID) {
		AppLoader.productID = productID;
	}


	public static ArrayList<String> getProductName() {
		return productName;
	}


	public static void setProductName(ArrayList<String> productName) {
		AppLoader.productName = productName;
	}


	public static ArrayList<Integer> getProductQuantity() {
		return productQuantity;
	}


	public static void setProductQuantity(ArrayList<Integer> productQuantity) {
		AppLoader.productQuantity = productQuantity;
	}


	public static ArrayList<Integer> getProductPrice() {
		return productPrice;
	}

	public static void setProductPrice(ArrayList<Integer> productPrice) {
		AppLoader.productPrice = productPrice;
	}

	public static ArrayList<Integer> getProductThreshold() {
		return productThreshold;
	}


	public static void setProductThreshold(ArrayList<Integer> productThreshold) {
		AppLoader.productThreshold = productThreshold;
	}


	public static ArrayList<Integer> getProductWeight() {
		return productWeight;
	}


	public static void setProductWeight(ArrayList<Integer> productWeight) {
		AppLoader.productWeight = productWeight;
	}

	public static int[] getMaxStock() {
		return maxStock;
	}

	public static void setMaxStock(int[] maxStock) {
		AppLoader.maxStock = maxStock;
	}
}
