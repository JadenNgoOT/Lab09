package Labs.Lab09;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class Lab09 extends Application {
    private static String name1 = "GOOG";
    private static String name2 = "AAPL";

    private static String fileName1 = "C:\\Users\\jaden\\IdeaProjects\\CSCI2020U\\src\\main\\java\\Labs\\Lab09\\"+name1+"StockData.csv";
    private static String fileName2 = "C:\\Users\\jaden\\IdeaProjects\\CSCI2020U\\src\\main\\java\\Labs\\Lab09\\"+name2+"StockData.csv";

    public static void main(String[] args) {
        downloadStockPrices(name1);
        downloadStockPrices(name2);

        launch(args);
    }

    public static void downloadStockPrices(String name){
        String url = "https://query1.finance.yahoo.com/v7/finance/download/" + name +
                "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
        String file = "C:\\Users\\jaden\\IdeaProjects\\CSCI2020U\\src\\main\\java\\Labs\\Lab09\\"+name+"StockData.csv";
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Stock Comparison 2010-2015");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(name1);

        BufferedReader br = new BufferedReader(new FileReader(fileName1));
        br.readLine();
        String line = null;

        while ((line = br.readLine()) != null) {
            String[] stock = line.split(",");
            series1.getData().add(new XYChart.Data(stock[0], Double.valueOf(stock[4])));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName(name2);

        br = new BufferedReader(new FileReader(fileName2));
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] stock = line.split(",");
            series2.getData().add(new XYChart.Data(stock[0], Double.valueOf(stock[4])));
        }

        Scene scene  = new Scene(lineChart,1900,800);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }

}
