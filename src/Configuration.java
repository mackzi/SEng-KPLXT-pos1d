import java.text.DecimalFormat;

public enum Configuration {
    instance;

    double xLocationMinimum = -10;
    double xLocationMaximum = 10;
    double velocityMinimum = -1;
    double velocityMaximum = 1;

    int dimensionOfProblem = 1;
    double c1 = 2.0;
    double c2 = 2.0;
    double wUpperBound = 1.0;
    double wLowerBound = 0.0;

    MersenneTwister randomGenerator = new MersenneTwister(System.currentTimeMillis());
    DecimalFormat decimalFormat = new DecimalFormat("0.00000000");
}