import javafx.scene.chart.XYChart;

import java.util.Vector;
import java.util.function.Function;

class Plot {
    private final XYChart<Double, Double> graph;
    private final double range;

    Plot(final XYChart<Double, Double> graph, final double range) {
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

    public void plotSwarm(Vector<Particle> swarm, final Function<Double, Double> function) {
        final XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        for (Particle p : swarm) {
            plotPoint(p.getLocation().getLocations()[0], function.apply(p.getLocation().getLocations()[0]), series1);
            p.getLocation().getLocations();
        }
        graph.getData().add(series1);
    }

    public void clear() {
        graph.getData().clear();
    }
}
