import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
       
       static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
       static final String DB_URL = "jdbc:mysql://localhost/mydb";
       static final String USER = "JM";
       static final String PASS = "root";
       
       private Statement stmt;
       private Connection conn;
       
       public DatabaseConnection(){

       }
       
       public void accessDB(){
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

       public void createEntry(){
    	   for(int i=1;i<10;i++){
              System.out.println("Inserting records into table");
              try {
                     stmt = conn.createStatement();
                     String sql = "INSERT INTO Product VALUES (" + i + ", 'Gnome" + i +"', "+(i*23/2*3)+")";
                     stmt.executeUpdate(sql);
                     System.out.println("Inserted into tables");
              } catch (SQLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
    	   }
       }
       
       public void readDB(){
    	   System.out.println("Creating statement...");
    	   try {
    		   stmt = conn.createStatement();
    		   String sql2 = "SELECT ProductID, ProductName, ProductQuantity FROM Product";
    		   ResultSet rs = stmt.executeQuery(sql2);
    		   while (rs.next()){
    			   int id = rs.getInt("ProductID");
    			   String name = rs.getString("ProductName");
    			   int quantity = rs.getInt("ProductQuantity");
    			   System.out.println("ID: " + id + ", name: " + name + ", quantity: " + quantity);
    		   }
    		   rs.close();
    	   }catch (SQLException e){
    		// TODO Auto-generated catch block
               e.printStackTrace();
    	   }
       }
       
       public void updateDB(){
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
       
       public void deleteRow(){
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
       
}
       
       
       
