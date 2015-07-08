import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class SimulationGUI extends Application {
	private Random rnd;
	private int x1;
	private int x2;
	private int x3;
	private int x4;
	private int x5;

	@Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Day");
        final LineChart<Number, Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Simulation");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 2");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Portfolio 3");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Portfolio 4");
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Portfolio 5");
        rnd = new Random();
        
        x1=rnd.nextInt(250);
        x2=rnd.nextInt(250);
        x3=rnd.nextInt(250);
        x4=rnd.nextInt(250);
        x5=rnd.nextInt(250);
        
        for(int i=0; i<11; i++){        
	        series1.getData().add(new XYChart.Data(i,x1));
	        series2.getData().add(new XYChart.Data(i,x2));
	        series3.getData().add(new XYChart.Data(i,x3));
	        series4.getData().add(new XYChart.Data(i,x4));
	        series5.getData().add(new XYChart.Data(i,x5));
        	x1-=rnd.nextInt(25);
        	x2-=rnd.nextInt(25);
        	x3-=rnd.nextInt(25);
        	x4-=rnd.nextInt(25);
        	x5-=rnd.nextInt(25);
        	
        		if(x1<=50){
        	        x1=250;
        	        series1.getData().add(new XYChart.Data(i,x1));
        		}
        		if(x2<=50){
        	        x2=250;
        	        series2.getData().add(new XYChart.Data(i,x2));
        		}
        		if(x3<=50){
        	        x3=250;
        	        series3.getData().add(new XYChart.Data(i,x3));
        		}
        		if(x4<=50){
        	        x4=250;
        	        series4.getData().add(new XYChart.Data(i,x4));
        		}
        		if(x5<=50){
        	        x5=250;
        	        series5.getData().add(new XYChart.Data(i,x5));
        		}
        }
        
        Scene scene  = new Scene(lineChart,400,300);       
        lineChart.getData().addAll(series1, series2, series3, series4, series5);
        stage.setScene(scene);
        stage.show();
    }
}