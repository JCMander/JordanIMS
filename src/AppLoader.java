import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/** Main class **/

public class AppLoader {

	private static DatabaseConnection db;
	private static FrmTable frm;
	private static PurchaseOrder po;
	private static Random rnd;
	static ArrayList<Integer> productID;
    static ArrayList<String> productName;
    static ArrayList<Integer> productQuantity;
    static ArrayList<Integer> productThreshold;
    static ArrayList<Integer> productWeight;
    private static String stockListMessage = "";
    private static String[] options = new String[2];
    private static int viewReceipt;
    private static int weightedQuantity;
    private static int weightEquation;
    static int maxStock = 2000;

    
	public static void main(String[] args) {
		
		rnd = new Random();
		db = new DatabaseConnection();
		frm = new FrmTable();
		po = new PurchaseOrder();
		frm.pack();
        frm.setLocationRelativeTo(null);
		db.accessDB();
		db.readDB();

		productID = db.getProductID();
		productWeight = db.getProductWeight();
		productQuantity = db.getProductQuantity();
		productName = db.getProductName();
		productThreshold = db.getProductThreshold();
		
        frm.setVisible(true);	
		//po = new PurchaseOrder();		
		makeTable();
		for(int i =0; i<productID.size(); i++){
			weightEquation = (10 + ((productWeight.get(i) * productWeight.get(i)) * (rnd.nextInt(10) + 5)));
			if((productQuantity.get(i) - weightEquation) < 0){
			weightedQuantity = 0 ;
			}else if((productQuantity.get(i) - weightEquation) >= 0){
				weightedQuantity = productQuantity.get(i) - weightEquation ;
			}
		if(productQuantity.get(i) <= productThreshold.get(i)){
			db.updateDB(productID.get(i), maxStock);
		}else{
			db.updateDB(productID.get(i), weightedQuantity);
		}
	}			
	}

	
	public static void stockListMessage(){
	    options[0] = new String("OK");
	    options[1] = new String("View Receipt");
		/** Message to show when quantity is low code starts here**/
		int msgcount =0;
		for(int i=0; i<productID.size(); i++){
			if(productQuantity.get(i)<=productThreshold.get(i)){
				stockListMessage = stockListMessage +  productName.get(i) + "      Quantity: " + productQuantity.get(i) + "\n";
				msgcount++;
				db.updateDB(productID.get(i), maxStock);
			}
		}
		if(msgcount>0){
		    viewReceipt = JOptionPane.showOptionDialog(null, "The following products are low in quantity and so have been re-ordered:\n\n" + stockListMessage,"Low Stock Levels", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		}
		
		/*if(viewReceipt == 1){
				try {
					 
					if ((new File(StockReport.FILE)).exists()) {
			 
						Process p = Runtime
						   .getRuntime()
						   .exec("rundll32 url.dll,FileProtocolHandler " + PurchaseOrder.FILE);
						p.waitFor();
			 
					} else {
			 
					    JOptionPane.showMessageDialog(null, "This file does not exist");
			 
					}			 
			  	  } catch (Exception ex) {
					ex.printStackTrace();
				  }
         
		}		*/
		/** Message to show when quantity is low code ends here **/
	}
	
	public static void makeTable(){
		/** Make the table code starts here **/
		for(int i=0; i<productID.size(); i++){
			frm.addProductToTable(productID.get(i), productName.get(i), productQuantity.get(i), productThreshold.get(i));
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
	
}
