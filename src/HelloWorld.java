import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class HelloWorld {

	private static DatabaseConnection db;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static String report = "";
    private static File reportFile = new File ("reportfile.txt");
    
     
	
	public static void main(String[] args) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");    
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	    
			int usertest1;
			int usertest2;
			
			System.out.println("Hello World");
			db = new DatabaseConnection();
			db.accessDB();
			//db.createEntry();
			//db.readDB();
			//db.updateDB();
			//db.deleteRow();
			db.readDB();
			//db.closeDB();
			//SwingAppGUI sD = new SwingAppGUI();
			//sD.showEvent();
			
			
			productID = db.getProductID();
			productName = db.getProductName();
			productQuantity = db.getProductQuantity();
			
			/*for(int i = 0; i < productID.size(); i++){
				System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
			}*/
			
			/**Update quantity code starts here**/
			
			Scanner usermsg1 = new Scanner(System.in);
			System.out.println("Enter a product ID");
			usertest1 = usermsg1.nextInt();
			for(int i = 0; i < productID.size(); i++){
				if(usertest1==productID.get(i)){
					System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
					i=productID.size()-1;
				}else if(usertest1!=productID.get(i) && productID.get(i)>(productID.size()-1)){
					System.out.println("The product ID entered is invalid, please try again");
				}	
			}

			System.out.println("Enter a quantity");
			usertest2 = usermsg1.nextInt();
			db.updateDB(usertest1, usertest2);
			productQuantity.set(usertest1-1, usertest2);
			System.out.println("You entered " + usertest2);
			for(int i=0; i<productID.size(); i++){
				//System.out.println(productID.size());
				System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
			}
			db.closeDB();
			
			/**Update quantity code ends here**/
			
			/** File saving code begins here **/
			//System.out.println(report);
				report += date + "\r\n";
			for(int i=0; i<productID.size();i++){
				report += productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + "\r\n";
				//System.out.println(productID.size());
			}
			//System.out.println(report);
			try {
				FileWriter fw = new FileWriter(reportFile);
				fw.write(report);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/** File saving code ends here **/
			
	}

}
