import java.util.ArrayList;
import java.util.Scanner;


public class HelloWorld {

	private static DatabaseConnection db;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
			
			for(int i = 0; i < productID.size(); i++){
				System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
			}
			
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
			db.readDB();
			System.out.println("You entered " + usertest2);
			for(int i=0; i<productID.size(); i++){
				System.out.println(productID.get(i) + ", " + productName.get(i) + ", " + productQuantity.get(i) + ".");
			}
			db.closeDB();
			
			/**Update quantity code ends here**/
			
	}

}
