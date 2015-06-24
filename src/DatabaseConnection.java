import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/** Class used to access, CRUD and close the database **/


public class DatabaseConnection {
       
       static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
       static final String DB_URL = "jdbc:mysql://localhost/mydb";
       static final String USER = "JM";
       static final String PASS = "root";
       private Random rnd = new Random();
       
       String[][] ProductArray = new String [33][3];
       private String[] gnomeArray = {"Hippy", "King", "Queen", "Nuclear", "Biohazard", "Obama", "Redneck", "Business", "Chav", "Beiber", "Potter", "Wolverine", "Iron Man", "Voldemort", "Jedi", "Sith", "Picard", "Angel", "Gun", "Big Gun",  "Bazooka", "Tank", "Police", "French", "Australian", "Insane", "Demon", "Samurai", "Time Lord", "Chewbacca", "Roman", "Greek", "Other"};
       
       private Statement stmt;
       private Connection conn;
       
       private ArrayList<Integer> productID;
       private ArrayList<String> productName;
       private ArrayList<Integer> productQuantity;
       
       public DatabaseConnection(){
    	   
    	   productID = new ArrayList<Integer>();
    	   productName = new ArrayList<String>();
    	   productQuantity = new ArrayList<Integer>();
    	   
       }
   
       public ArrayList<Integer> getProductID(){
    	   return productID;
       }
       public ArrayList<String> getProductName(){
    	   return productName;
       }
       public ArrayList<Integer> getProductQuantity(){
    	   return productQuantity;
       }
       
       public void accessDB(){ // Establish the connection to the database
              conn = null;
              stmt = null;
              
              try{
                     Class.forName(JDBC_DRIVER);
                     System.out.println("Connecting to db");
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);
              }catch(Exception e){
                     System.out.println(e);
              }
       }

       public void createEntry(){ // Add a row to the database
    	   for(int i=0;i<33;i++){
              System.out.println("Inserting records into table");
              try {
                     stmt = conn.createStatement();
                     String sql = "INSERT INTO Product VALUES (" + (i+1) + ", '" + gnomeArray[i] + " Gnome', "+rnd.nextInt(250)+")";
                     stmt.executeUpdate(sql);
                     System.out.println("Inserted into tables");
              } catch (SQLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
    	   }
       }
       
       public void readDB(){ // Read the database row by row
    	   //int dbcounter = 0 ;
    	   System.out.println("Creating statement...");
    	   try {
    		   stmt = conn.createStatement();
    		   String sql2 = "SELECT ProductID, ProductName, ProductQuantity FROM Product";
    		   ResultSet rs = stmt.executeQuery(sql2);
    		   while (rs.next()){
    			   //int id = rs.getInt("ProductID");
    			   //String name = rs.getString("ProductName");
    			   //int quantity = rs.getInt("ProductQuantity");
    			   productID.add(rs.getInt("ProductID"));
    			   productName.add(rs.getString("ProductName"));
    			   productQuantity.add(rs.getInt("ProductQuantity"));
    			   //System.out.println("ID: " + id + ", name: " + name + ", quantity: " + quantity + ", dbcounter: " + dbcounter);
    			   //System.out.println(ProductArray[dbcounter][0] + " " + ProductArray[dbcounter][1] + " " + ProductArray[dbcounter][2] + " ");
    			   //dbcounter++;
    		   }
    		   //dbcounter=0;
    		   rs.close();
    	   }catch (SQLException e){
    		// TODO Auto-generated catch block
               e.printStackTrace();
    	   }
       }
       
       public void updateDB(){ // Update an entry in a particular row in the database
    	   System.out.println("Creating statement...");
    	   try {
    	   stmt = conn.createStatement();
    	   String sql3 = "UPDATE Product SET ProductQuantity = 23 WHERE ProductID < 7";
    	   stmt.executeUpdate(sql3);
    	   }catch (SQLException e){
    		// TODO Auto-generated catch block
               e.printStackTrace();
    	   }
       }
       
       public void deleteRow(){ // Delete a row in the database
    	   System.out.println("Creating statement...");
    	   try {
    	   stmt = conn.createStatement();
    	   String sql4 = "DELETE FROM Product WHERE ProductID = 9" ;
    	   stmt.executeUpdate(sql4);
    	   }catch (SQLException e){
    		// TODO Auto-generated catch block
               e.printStackTrace();
    	   }
       }
       
       public void closeDB(){ // Close the connection to the database
       try{
    	   if (stmt !=null)
    		   conn.close();
       } catch (SQLException se){}
    	   try{
    		   if (conn !=null)
    			   conn.close();
    	   }catch (SQLException se){
    		   se.printStackTrace();
    	   }
    	   System.out.println("Goodbye!");
       }
       
       
       }
       
       
       
