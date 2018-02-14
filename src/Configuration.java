import java.text.DecimalFormat;

public enum Configuration {
    instance;

    //Params from gui
    int swarmSize = 30;
    int maximumNumberOfIterations = 100;

    double xLocationMinimum = -10;
    double xLocationMaximum = 10;
    double yLocationMinimum = -5;
    double yLocationMaximum = 300;
    double velocityMinimum = -1;
    double velocityMaximum = 1;
    double maximumErrorTolerance = 1E-20;


    int dimensionOfProblem = 1;
    double c1 = 2.0;
    double c2 = 2.0;
    double wUpperBound = 1.0;
    double wLowerBound = 0.0;

    MersenneTwister randomGenerator = new MersenneTwister(System.currentTimeMillis());
    DecimalFormat decimalFormat = new DecimalFormat("0.0000000000");
}