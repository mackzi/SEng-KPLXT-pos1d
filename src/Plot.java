import javafx.scene.chart.XYChart;
import java.util.function.Function;

public class Plot {
    private XYChart<Double, Double> graph;
    private double range;
    public Plot(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }
    public void plotLine(final Function<Double, Double> function) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (double x = -range; x <= range; x = x + 0.1) {
            plotPoint(x, function.apply(x), series);
        }
        graph.getData().add(series);
    }
    private void plotPoint(final double x, final double y, final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }
    public void clear() {
        graph.getData().clear();
    }
}
