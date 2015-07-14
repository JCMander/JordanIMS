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
    private static ArrayList<Integer> productThreshold;
    private static DatabaseConnection db;
	private Random rnd;
	private int count = 0;
	private int[] quantityArray;
	private int[] thresholdArray;
	private String[] nameArray;
	private int quantitySwap;
	private int thresholdSwap;
	private String nameSwap;
	XYChart.Series series1 = new XYChart.Series();
	XYChart.Series series2 = new XYChart.Series();
	XYChart.Series series3 = new XYChart.Series();
	XYChart.Series series4 = new XYChart.Series();
	XYChart.Series series5 = new XYChart.Series();
	XYChart.Series series6 = new XYChart.Series();
	XYChart.Series series7 = new XYChart.Series();
	XYChart.Series series8 = new XYChart.Series();
	XYChart.Series series9 = new XYChart.Series();
	XYChart.Series series10 = new XYChart.Series();

	@Override public void start(Stage stage) {
		db = new DatabaseConnection();
		
		db.accessDB();
		db.readDB();
		
		productID = db.getProductID();
		productName = db.getProductName();
		productQuantity = db.getProductQuantity();
		productThreshold = db.getProductThreshold();
		
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Day");
         yAxis.setLabel("Stock");
        final LineChart<Number, Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Simulation");
        
        rnd = new Random();
        initialiseData();
        sortData();
        addData();
        
        Scene scene  = new Scene(lineChart, 1000,750);       
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addData();
            }
        });
        
        lineChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);
        stage.setScene(scene);
        stage.show();
        
    }
	

	
	public void initialiseData(){
		quantityArray = new int[productID.size()];
		thresholdArray = new int[productID.size()];
		nameArray = new String[productID.size()];
		for(int i =0; i<productID.size(); i++){
			quantityArray[i] = productQuantity.get(i);
			thresholdArray[i] = productThreshold.get(i);
			nameArray[i] = productName.get(i);
		}		
	}
	
	public void sortData(){
		for (int i=0; i<productID.size()-1; i++){
			for (int j=0; j<productID.size()-1; j++){
				if(quantityArray[j] > quantityArray[j+1]){
					quantitySwap = quantityArray[j];
					nameSwap = nameArray[j];
					thresholdSwap = thresholdArray[j];
					quantityArray[j] = quantityArray[j+1];
					nameArray[j] = nameArray[j+1];
					thresholdArray[j] = thresholdArray[j+1];
					quantityArray[j+1] = quantitySwap;
					nameArray[j+1] = nameSwap;
					thresholdArray[j+1] = thresholdSwap;
				}
			}
		}
		
	}
	
	public void addData(){       
		
        series1.setName(nameArray[0]);  
        series2.setName(nameArray[1]);  
        series3.setName(nameArray[2]);  
        series4.setName(nameArray[3]);  
        series5.setName(nameArray[4]);  
        series6.setName(nameArray[5]);  
        series7.setName(nameArray[6]);  
        series8.setName(nameArray[7]);  
        series9.setName(nameArray[8]);  
        series10.setName(nameArray[9]);  
        
    	for(int i=0;i<10;i++){
	        switch(i){
	        case 0: 
		        series1.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 1: 
		        series2.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 2: 
		        series3.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 3: 
		        series4.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 4: 
		        series5.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;       
	        case 5: 
		        series6.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 6: 
		        series7.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 7: 
		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 8: 
		        series9.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;
	        case 9: 
		        series10.getData().add(new XYChart.Data(count,quantityArray[i]));
		        break;  
	        }
        	quantityArray[i]-=rnd.nextInt(200);
    		if(quantityArray[i]<=thresholdArray[i]){
    	        quantityArray[i]=2000;
    	        switch(i){
    	        case 0: 
    		        series1.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 1: 
    		        series2.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 2: 
    		        series3.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 3: 
    		        series4.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 4: 
    		        series5.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;  
    	        case 5: 
    		        series6.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 6: 
    		        series7.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 7: 
    		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 8: 
    		        series9.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;
    	        case 9: 
    		        series10.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(200);
    		        break;  
    	        }
    		}
    	}

       count++; 		
        
	}
	
}