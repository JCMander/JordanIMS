package views;

import java.util.ArrayList;
import java.util.Random;

import models.SalesMetricsValue;
import controllers.AppLoader;
import controllers.NewSalesMetricsValueManager;
import controllers.SalesMetricsReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
/** Class used to create a simulation of product sales over a given time **/

public class SimulationGUI extends Application {
	//Add the variable
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static ArrayList<Integer> productThreshold;
    private static ArrayList<Integer> productWeight;
    private static ArrayList<Integer> productPrice;
    private Random rnd;
	private int count = 0;
	private int[] priceArray;
	private int[] quantityArray;
	private int[] thresholdArray;
	private String[] nameArray;
	private int priceSwap;
	private int quantitySwap;
	private int thresholdSwap;
	private String nameSwap;
	private int[] weightArray;
	private int weightSwap;
	private int[] incrementArray;
	private int[] idArray;
	private int idSwap;
	private int maxStockSwap;
	private int[] maxStockArray = new int[AppLoader.getMaxStock().length];
	private static NewSalesMetricsValueManager newSalesMetricsValueManager;
	private int[] lastValue;
	
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

		new SalesMetricsReader();
		rnd = new Random();
		
		newSalesMetricsValueManager = new NewSalesMetricsValueManager();

		//May need to also get the maximum stock and put it into an ArrayList
		productID = AppLoader.getProductID();
		productName = AppLoader.getProductName();
		productQuantity = AppLoader.getProductQuantity();
		productThreshold = AppLoader.getProductThreshold();
		productWeight = AppLoader.getProductWeight();
		maxStockArray = AppLoader.getMaxStock();
		productPrice = AppLoader.getProductPrice();
		
		//Trying to loop through all the products to add them all to an array list
		// using productID.size() as it should have the maximum size of the array
		
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Week");
         yAxis.setLabel("Stock Level");
        final LineChart<Number, Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Simulation");
        
        rnd = new Random();
        initialiseData();
        sortData();
        calculateIncrement();
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
        addData();
        
        Scene scene  = new Scene(lineChart, 1000,750);       
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addData();
                new SalesMetricsWriter();
            }
        });
        
        //Adds all of the series (products) to the graph. Only has 8 unique colours.
        lineChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);
        stage.setScene(scene);
        stage.show();        
    }
	

	
	public void initialiseData(){
		//The max stock would then need to be initialised into a standard array for it to be bubble sorted
		quantityArray = new int[productID.size()];
		thresholdArray = new int[productID.size()];
		nameArray = new String[productID.size()];
		weightArray = new int[productID.size()];
		idArray = new int[productID.size()];
		priceArray = new int[productID.size()];
		lastValue = new int[productID.size()];
		//Then set each element to its respectful position
		for(int i =0; i<productID.size(); i++){
			quantityArray[i] = productQuantity.get(i);
			thresholdArray[i] = productThreshold.get(i);
			nameArray[i] = productName.get(i);
			weightArray[i] = productWeight.get(i);
			idArray[i] = productID.get(i);
			priceArray[i] = productPrice.get(i);
		}
	}
	
	public void sortData(){
		//The bubble sort would then attach it to its respected product
		for (int i=0; i<productID.size()-1; i++){
			for (int j=0; j<productID.size()-1; j++){
				if(weightArray[j] < weightArray[j+1]){
					
					quantitySwap = quantityArray[j];
					nameSwap = nameArray[j];
					thresholdSwap = thresholdArray[j];
					weightSwap = weightArray[j];
					idSwap = idArray[j];
					maxStockSwap = maxStockArray[j];
					priceSwap = priceArray[j];
					
					quantityArray[j] = quantityArray[j+1];
					nameArray[j] = nameArray[j+1];
					thresholdArray[j] = thresholdArray[j+1];
					weightArray[j] = weightArray[j+1];
					idArray[j] = idArray[j+1];
					maxStockArray[j] = maxStockArray[j+1];
					priceArray[j] = priceArray[j+1];
					
					quantityArray[j+1] = quantitySwap;
					nameArray[j+1] = nameSwap;
					thresholdArray[j+1] = thresholdSwap;
					weightArray[j+1] = weightSwap;
					idArray[j+1] = idSwap;
					maxStockArray[j+1] = maxStockSwap;
					priceArray[j+1] = priceSwap;
					
				}
			}
		}		
	}
	
	public void calculateIncrement(){
		incrementArray = new int[productID.size()];
		for(int i =0; i<productID.size(); i++){
			incrementArray[i] = 10 + ((weightArray[i] * weightArray[i]) * (rnd.nextInt(10) + 5)) ;
		}
	}
	
	public void addData(){       
		//during this for loop, a new arraylist will need to be written to, to then be passed to SalesMetricsPDF
		//not fully sure what would be contained within this array
		for(int i=0;i<20;i++){
	        switch(i){
	        case 0: 
		        series1.getData().add(new XYChart.Data(count,quantityArray[i]));
		        /**new SalesMetricsValue(productID, lastValue, newValue, productName, productPrice)**/
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 1: 
		        series2.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 2: 
		        series3.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 3: 
		        series4.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 4: 
		        series5.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;       
	        case 5: 
		        series6.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 6: 
		        series7.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break;
	        case 7: 
		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
		        break; 
	        case 8:
		        series9.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
	        	break;
	        case 9:
		        series10.getData().add(new XYChart.Data(count,quantityArray[i]));
		        if(count!=0){
		        newSalesMetricsValueManager.setSalesMetricsValue(new SalesMetricsValue(idArray[i], lastValue[i], quantityArray[i], nameArray[i], priceArray[i]));
		        }
	        	break;
	        }
	        //Once all series have been updated, the pdf will have to be written to
	        
	        lastValue[i] = quantityArray[i];
	        quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		if(quantityArray[i]<=thresholdArray[i]){
    			//Check that getMaxStock gets the right maximum threshold for each specific product. Unlike quantity, weight, threshold and name,
    			//it does not get sorted within the SimulationGUI class
    	        quantityArray[i]=AppLoader.getMaxStock()[i];
		        lastValue[i] = quantityArray[i];
    	        switch(i){
    	        case 0: 
    		        series1.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 1: 
    		        series2.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 2: 
    		        series3.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 3: 
    		        series4.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 4: 
    		        series5.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;  
    	        case 5: 
    		        series6.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 6: 
    		        series7.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 7: 
    		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 8: 
    		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        case 9: 
    		        series8.getData().add(new XYChart.Data(count,quantityArray[i]));
    	        	quantityArray[i]-=rnd.nextInt(incrementArray[i]);
    		        break;
    	        }
    		}
    	}
       count++; 		
   	
	}	
	

	
	
}