import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable{

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private Label labelA, labelB, labelSwarmSize, labelIterations;

    @FXML
    private Slider sliderSwarmSize, sliderIterations, sliderA, sliderB;


    private Plot plot;

    private void updateParams(double a, double b){
        plot.clear();
        yAxis.setUpperBound(Math.round(a*100));
        yAxis.setTickUnit(Math.round(a * 10));
        plot.plotLine(x -> a * Math.pow(x, 2) + Math.cos(Math.PI * x) - b * Math.sin(2 * Math.PI * x) + Math.cos(3 * Math.PI * x) * Math.sin(Math.PI * x));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plot = new Plot(lineGraph, 10);
        plot.plotLine(x -> 0.1 * Math.pow(x, 2) + Math.cos(Math.PI * x) - 2 * Math.sin(2 * Math.PI * x) + Math.cos(3 * Math.PI * x) * Math.sin(Math.PI * x));

        // Listen for Slider value changes
        sliderSwarmSize.valueProperty().addListener((observable, oldValue, newValue) -> labelSwarmSize.setText(Integer.toString(newValue.intValue())));

        sliderIterations.valueProperty().addListener((observable, oldValue, newValue) -> labelIterations.setText(Integer.toString(newValue.intValue())));

        sliderA.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelA.setText(newValue.toString().substring(0,3));
            updateParams(sliderA.getValue(), sliderB.getValue());
        });

        sliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelB.setText(newValue.toString().substring(0, 3));
            updateParams(sliderA.getValue(), sliderB.getValue());
        });
    }
}
