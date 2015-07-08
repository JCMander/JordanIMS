import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/** Class used to access, CRUD and close the database
 * Also used to convert the database into a number of ArrayLists **/


public class DatabaseConnection {
       
       static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
       static final String DB_URL = "jdbc:mysql://10.50.15.9:3306/mydb";
       static final String USER = "JM";
       static final String PASS = "root"; 
       private Random rnd;
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
                     //System.out.println("Connecting to db");
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);
              }catch(Exception e){
                     System.out.println(e);
              }
       }

       public void createEntry(int sql7, String sql8){ // Add a row to the database
              System.out.println("Inserting records into table");
              try {
                     stmt = conn.createStatement();
                     String sql = "INSERT INTO Product VALUES (" + sql7 + ", '" + sql8 + "', " + 0 + ")";
                     stmt.executeUpdate(sql);
                     System.out.println("Inserted into tables");
              } catch (SQLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
       }
       
       public void readDB(){ // Read the database row by row
    	   //System.out.println("Creating statement...");
    	   try {
    		   stmt = conn.createStatement();
    		   String sql2 = "SELECT ProductID, ProductName, ProductQuantity FROM Product";
    		   ResultSet rs = stmt.executeQuery(sql2);
    		   while (rs.next()){
    			   productID.add(rs.getInt("ProductID"));
    			   productName.add(rs.getString("ProductName"));
    			   productQuantity.add(rs.getInt("ProductQuantity"));
    		   }
    		   rs.close();
    	   }catch (SQLException e){
    		// TODO Auto-generated catch block
               e.printStackTrace();
    	   }
       }
       
       public void updateDB(int sql4, int sql5){ // Update an entry in a particular row in the database
    	   System.out.println("Creating statement...");
    	   try {
    	   stmt = conn.createStatement();
    	   String sql3 = "UPDATE Product SET ProductQuantity = " + sql5 + " WHERE ProductID = " + sql4 + "";
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
    	   String sql6 = "DELETE FROM Product WHERE ProductID = 9" ;
    	   stmt.executeUpdate(sql6);
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
       
       
       
