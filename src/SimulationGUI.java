import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class SimulationGUI extends Application {
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static DatabaseConnection db;
	private Random rnd;
	private int x1;
	private int x2;
	private int x3;
	private int x4;
	private int x5;
	private int count = 0;
	private int holdQuantity;
	XYChart.Series series1 = new XYChart.Series();
	XYChart.Series series2 = new XYChart.Series();
	XYChart.Series series3 = new XYChart.Series();
	XYChart.Series series4 = new XYChart.Series();
	XYChart.Series series5 = new XYChart.Series();

	@Override public void start(Stage stage) {
		db = new DatabaseConnection();
		
		db.accessDB();
		db.readDB();
		
		productID = db.getProductID();
		productName = db.getProductName();
		productQuantity = db.getProductQuantity();
		
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Day");
         yAxis.setLabel("Stock");
        final LineChart<Number, Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Simulation");
        
        series1.setName(productName.get(4));  
        series2.setName(productName.get(6));  
        series3.setName(productName.get(3));  
        series4.setName(productName.get(7));  
        series5.setName(productName.get(0));  
        rnd = new Random();
        
        x1=productQuantity.get(4);
        x2=productQuantity.get(6);
        x3=productQuantity.get(3);
        x4=productQuantity.get(7);
        x5=productQuantity.get(0);
        
        addData();
        
        
        
        Scene scene  = new Scene(lineChart, 1000,750);       
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addData();
            }
        });
        

        
        lineChart.getData().addAll(series1, series2, series3, series4, series5);
        stage.setScene(scene);
        stage.show();
        
    }
	
	public void addData(){       
		
		System.out.println(productID + " : " + productName + " : " + productQuantity);
		
		for (int i =0; i<productID.size(); i++){
			for (int j =0; j<productID.size()-1; j++){
				if(productQuantity.get(j) < productQuantity.get(j+1)){
					holdQuantity = productQuantity.get(j);
					productQuantity.set(j, productQuantity.get(j+1));
					productQuantity.set(j, holdQuantity);				}
			}
		}
		
		System.out.println(productID + " : " + productName + " : " + productQuantity);

	        series1.getData().add(new XYChart.Data(count,x1));
	        series2.getData().add(new XYChart.Data(count,x2));
	        series3.getData().add(new XYChart.Data(count,x3));
	        series4.getData().add(new XYChart.Data(count,x4));
	        series5.getData().add(new XYChart.Data(count,x5));
        	x1-=rnd.nextInt(400);
        	x2-=rnd.nextInt(400);
        	x3-=rnd.nextInt(400);
        	x4-=rnd.nextInt(400);
        	x5-=rnd.nextInt(400);
        	
        		if(x1<=50){
        	        x1=2500;
        	        series1.getData().add(new XYChart.Data(count,x1));
        		}
        		if(x2<=50){
        	        x2=2500;
        	        series2.getData().add(new XYChart.Data(count,x2));
        		}
        		if(x3<=50){
        	        x3=2500;
        	        series3.getData().add(new XYChart.Data(count,x3));
        		}
        		if(x4<=50){
        	        x4=2500;
        	        series4.getData().add(new XYChart.Data(count,x4));
        		}
        		if(x5<=50){
        	        x5=2500;
        	        series5.getData().add(new XYChart.Data(count,x5));
        		}
        	
       count++; 		
        
	}
	
}