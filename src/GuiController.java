import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.function.Function;

public class GuiController implements Initializable{

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private TextArea textArea;

    @FXML
    private Label labelA, labelB, labelSwarmSize, labelIterations;

    @FXML
    private Slider sliderSwarmSize, sliderIterations, sliderA, sliderB;

    private ParticleSwarmOptimization pos;
    private Plot plot;

    private Function<Double, Double> function() {
        return x -> sliderA.getValue() * Math.pow(x, 2) + Math.cos(Math.PI * x) - sliderB.getValue() * Math.sin(2 * Math.PI * x) + Math.cos(3 * Math.PI * x) * Math.sin(Math.PI * x);
    }

    private void clearTextArea() {
        textArea.clear();
        textArea.appendText("Best global minimum:");
    }

    private boolean updatePlot() {
        plot.clear();
        yAxis.setUpperBound(Math.round(sliderA.getValue() * 100));
        yAxis.setTickUnit(Math.round(sliderA.getValue() * 10));
        plot.plotLine(function());
        return true;
    }

    private void execute() {
        plot.clear();
        textArea.clear();
        yAxis.setUpperBound(Math.round(sliderA.getValue() * 100));
        yAxis.setTickUnit(Math.round(sliderA.getValue() * 10));

        plot.plotLine(function());

        pos = new ParticleSwarmOptimization();
        Vector<Particle> swarm = pos.execute((int) sliderSwarmSize.getValue(), (int) sliderIterations.getValue(), sliderA.getValue(), sliderB.getValue());
        plot.plotSwarm(swarm, x -> sliderA.getValue() * Math.pow(x, 2) + Math.cos(Math.PI * x) - sliderB.getValue() * Math.sin(2 * Math.PI * x) + Math.cos(3 * Math.PI * x) * Math.sin(Math.PI * x));

        textArea.appendText("Best global minimum:\n\n" +
                "x    = " + Configuration.instance.decimalFormat.format(pos.getGlobalBestLocation().getLocations()[0]) +
                "\nf(x) = " + Configuration.instance.decimalFormat.format(function().apply(pos.getGlobalBestLocation().getLocations()[0])));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plot = new Plot(lineGraph, 10);

        plot.plotLine(function());

        // Listen for Slider value changes
        sliderSwarmSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSwarmSize.setText(Integer.toString(newValue.intValue()));
            if (sliderIterations.getValue() != 0) execute();
        });

        sliderIterations.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelIterations.setText(Integer.toString(newValue.intValue()));
            if (newValue.intValue() != 0) execute();
        });

        sliderA.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelA.setText(newValue.toString().substring(0,3));
            sliderIterations.setValue(0);
            updatePlot();
            clearTextArea();
        });

        sliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelB.setText(newValue.toString().substring(0, 3));
            sliderIterations.setValue(0);
            updatePlot();
            clearTextArea();
        });
    }
}
