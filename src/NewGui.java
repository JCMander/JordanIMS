import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class NewGui extends Application {

    private TableView table = new TableView();
    private static DatabaseConnection db;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    final static ObservableList<Product> data = FXCollections.observableArrayList();
    private int userinput1;
    private int userinput2;
    private String newProductName;
    private int confirmProductName;
    private static String report = "";
    private static File reportFile = new File ("../reportfile");
    private String fileDate;
	private static int count = 0;
    
    public static void main(String[] args) {
    	
    	db = new DatabaseConnection();
		db.accessDB();
		db.readDB();
			
		productID = db.getProductID();
		productName = db.getProductName();
		productQuantity = db.getProductQuantity();
		
		for(int i=0; i<productID.size(); i++){
			makeTable(productID.get(i), productName.get(i), productQuantity.get(i));
		}
		System.out.println(productID);
		System.out.println(productName);
		System.out.println(productQuantity);
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Inventory Management System");
        stage.setWidth(470);
        stage.setHeight(660);
       
        
        table.setEditable(true);
 
        TableColumn ID = new TableColumn("Product ID");
        ID.setMinWidth(100);
        ID.setCellValueFactory(
                new PropertyValueFactory<Product, Integer>("productID"));
        TableColumn Name = new TableColumn("Product Name");
        Name.setMinWidth(200);
        Name.setCellValueFactory(
                new PropertyValueFactory<Product, String>("productName"));
        TableColumn Quantity = new TableColumn("Product Quantity");
        Quantity.setMinWidth(150);
        Quantity.setCellValueFactory(
                new PropertyValueFactory<Product, Integer>("productQuantity"));
        
        table.setItems(data);
        table.getColumns().addAll(ID, Name, Quantity);
        table.setMinHeight(600);        
        
        MenuBar menuBar = new MenuBar();
        
        // --- Menu File
        Menu menuFile = new Menu("File");
 
        menuBar.getMenus().addAll(menuFile);
        MenuItem addProduct = new MenuItem("Add Product");
            addProduct.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
            		newProductName = (String)JOptionPane.showInputDialog("Please enter the name of the new product");
            		confirmProductName = JOptionPane.showConfirmDialog(null,"Would you like to add the product " + newProductName + "?","Confirm", JOptionPane.YES_NO_OPTION);
    				if(confirmProductName == JOptionPane.YES_OPTION){
    	        		db.createEntry(productID.size()+1, newProductName);
    	        		data.add(new Product(productID.size()+1, newProductName, 0));
    	        		productID.add(productID.size()+1); productName.add(newProductName); productQuantity.add(0); 
    				}else{
    					JOptionPane.showMessageDialog(null, "Request cancelled");
    				}
                }
            }); 
        MenuItem updateProduct = new MenuItem("Update Product");
        updateProduct.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
                 System.out.println(data.size());
            	 System.out.println("Real talk, we made it");
             	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the id of the product you wish to change"));
             	System.out.println(userinput1);
             	userinput2 = Integer.parseInt((String)JOptionPane.showInputDialog("Product ID: " + userinput1 + "   "
             			+ "Product Name: " + productName.get(userinput1-1) + "   Product Quantity: "
             			+ "" + productQuantity.get(userinput1-1) + "\n\nPlease enter the new quantity"));
        		data.set(userinput1-1, (new Product(userinput1, productName.get(userinput1-1), userinput2 )));
             	db.updateDB(userinput1, userinput2 );
             	productQuantity.set(userinput1-1, userinput2);

             }
        }); 

        MenuItem printStock = new MenuItem("Print Stock Report");
        printStock.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
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
            	 System.out.println("Du yes");
             }
        }); 
        MenuItem makeOrder = new MenuItem("Generate Purchase Order");
        makeOrder.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
            	 System.out.println("Fantastic");
             }
        }); 
        
        final VBox vbox = new VBox();
        
        MenuItem runSim = new MenuItem("Run Simulation");
        runSim.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
             }
        }); 
        
        menuFile.getItems().addAll(addProduct, updateProduct, runSim, printStock, makeOrder);
        
        vbox.getChildren().addAll(menuBar, table);
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
    
    public static void makeTable(int productID, String productName, int productQuantity){
        data.add(new Product(productID, productName, productQuantity ));
    }
    
    
}