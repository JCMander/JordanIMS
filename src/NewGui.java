import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
                    System.out.println("We made it");
                }
            }); 
        MenuItem updateProduct = new MenuItem("Update Product");
        updateProduct.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
            	 System.out.println("No seriosuly, we made it");
             }
        }); 
        MenuItem runSim = new MenuItem("Run Simulation");
        runSim.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
            	 System.out.println("Goteem");
             }
        }); 
        MenuItem printStock = new MenuItem("Print Stock Report");
        printStock.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent t) {
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