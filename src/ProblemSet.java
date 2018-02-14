// find an x and a y that minimize the function below:
// f(x, y) = (2.8125 - x + x * y^4)^2 + (2.25 - x + x * y^2)^2 + (1.5 - x + x*y)^2
// where 1 <= x <= 4, and -1 <= y <= 1

public class ProblemSet {


    double a, b;

    public ProblemSet(double a, double b) {
        this.a = a;
        this.b = b;
    }


    public double evaluate(Location location) {
        double result;
        double x = location.getLocations()[0];

        result = a * Math.pow(x, 2) + Math.cos(Math.PI * x) - b * Math.sin(2 * Math.PI * x) + Math.cos(3 * Math.PI * x) * Math.sin(Math.PI * x);

        return result;
    }
}