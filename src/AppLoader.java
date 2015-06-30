import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTable;


public class AppLoader {

	private static DatabaseConnection db;
	private static FrmTable frm;
	private static PurchaseOrder po;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static String report = "";
    private static File reportFile = new File ("C:\\Users\\Jmander\\workspace\\JordanIMS\\reportfile");
    private static Random rnd;
    private static String stockListMessage = "";
    private String fileDate;
    
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
		productName = db.getProductName();
		productQuantity = db.getProductQuantity();

        frm.setVisible(true);	
        makeTable();
			
		/** Simulation code starts here **/
		/*
		for(int i =0; i<5; i++){
			for(int j=0; j<productID.size();j++){
				db.updateDB(rnd.nextInt(50),rnd.nextInt(50));
				System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
			}
		}
		*/
		/** Simulation code ends here **/
			
	}

	public static void stockListMessage(){
		/** Message to show when quantity is low code starts here**/
		int msgcount =0;
		for(int i=0; i<productID.size(); i++){
			if(productQuantity.get(i)<=50){
				stockListMessage = stockListMessage +  productName.get(i) + "      Quantity: " + productQuantity.get(i) + "\n";
				msgcount++;
			}
		}
		if(msgcount>0){
			JOptionPane.showMessageDialog(null, "The following products are low in quantity:\n\n" + stockListMessage);
		}
		/** Message to show when quantity is low code ends here **/
	}
	
	public void generateStockReport(){
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");    
		Date date = new Date();
		fileDate = dateFormat.format(date);
		System.out.println(dateFormat.format(date));
		/** File saving code begins here **/
		//System.out.println(report);
			report += "\t\t\t" + date + "\r\n";
			report += "\r\nProduct ID\tProduct Name\tProduct Quantity\tReorder? (y/n) \r\n";
		for(int i=0; i<productID.size();i++){
			
			if((productName.get(i)).length()>15){
				if(productQuantity.get(i)>50){
					report += productID.get(i) + "\t\t" + productName.get(i) + "\t" + productQuantity.get(i) + "\t\t\t n \r\n";
					//System.out.println(productID.size());
					}else{
						report += productID.get(i) + "\t\t" + productName.get(i) + "\t" + productQuantity.get(i) + "\t\t\t y \r\n";
						}
			}else{
				if(productQuantity.get(i)>50){
					report += productID.get(i) + "\t\t" + productName.get(i) + "\t\t" + productQuantity.get(i) + "\t\t\t n \r\n";
					//System.out.println(productID.size());
					}else{
						report += productID.get(i) + "\t\t" + productName.get(i) + "\t\t" + productQuantity.get(i) + "\t\t\t y \r\n";
						}
			}
			
			
		}
		//System.out.println(report);
		try {
			FileWriter fw = new FileWriter(reportFile + fileDate + ".txt");
			fw.write(report);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Your stock report has been saved");
		try {
			Runtime.getRuntime().exec("notepad " + reportFile + fileDate + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/** File saving code ends here **/
	}
	
	public void generatePurchaseOrder(){
		
		for(int i=0; i<productID.size(); i++){
			po.addProductToOrder(productID.get(i), productName.get(i), productQuantity.get(i));
		}
		
		po.addTotalPrice();
		po.pack();
        po.setLocationRelativeTo(null);
        po.setVisible(true); 

	}
	
	public static void makeTable(){
		/** Make the table code starts here **/
		for(int i=0; i<productID.size(); i++){
			frm.addProductToTable(productID.get(i), productName.get(i), productQuantity.get(i));
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
	
	public String getProductName(int name1){
		return productName.get(name1-1);
	} 
	
	public int getProductQuantity(int name1){
		return productQuantity.get(name1-1);
		
	} 
	
}
