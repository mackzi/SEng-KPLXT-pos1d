import java.util.Vector;

public class ParticleSwarmOptimization {
    private Vector<Particle> swarm = new Vector<>();
    private double[] bestParticles;
    private Vector<Location> bestParticleLocations = new Vector<>();
    private double globalBestParticle;
    private Location globalBestLocation;
    private double[] fitnessValueList;

    public Vector<Particle> execute(int swarmSize, int iterations, double a, double b) {
        ProblemSet function = new ProblemSet(a, b);
        bestParticles = new double[swarmSize];
        fitnessValueList = new double[swarmSize];

        initializeSwarm(swarmSize, function);
        updateFitnessList(swarmSize);


        for (int i = 0; i < swarmSize; i++) {
            bestParticles[i] = fitnessValueList[i];
            bestParticleLocations.add(swarm.get(i).getLocation());
        }

        int n = 0;
        double w;
        double error = 9999;

        while (n < iterations && error > Configuration.instance.maximumErrorTolerance) {
            // step 1 - update bestParticles
            for (int i = 0; i < swarmSize; i++)
                if (fitnessValueList[i] < bestParticles[i]) {
                    bestParticles[i] = fitnessValueList[i];
                    bestParticleLocations.set(i, swarm.get(i).getLocation());
                }

            // step 2 - update globalBestParticle
            int bestParticleIndex = Utilities.getMinimumPosition(fitnessValueList);
            if (n == 0 || fitnessValueList[bestParticleIndex] < globalBestParticle) {
                globalBestParticle = fitnessValueList[bestParticleIndex];
                globalBestLocation = swarm.get(bestParticleIndex).getLocation();
            }

            w = Configuration.instance.wUpperBound - (((double) n) / Configuration.instance.maximumNumberOfIterations) * (Configuration.instance.wUpperBound - Configuration.instance.wLowerBound);

            for (int i = 0; i < swarmSize; i++) {
                double r1 = Configuration.instance.randomGenerator.nextDouble();
                double r2 = Configuration.instance.randomGenerator.nextDouble();

                Particle particle = swarm.get(i);

                // step 3 - update velocity
                double[] newVelocity = new double[Configuration.instance.dimensionOfProblem];
                newVelocity[0] = (w * particle.getVelocity().getPosition()[0]) +
                        (r1 * Configuration.instance.c1) * (bestParticleLocations.get(i).getLocations()[0] - particle.getLocation().getLocations()[0]) +
                        (r2 * Configuration.instance.c2) * (globalBestLocation.getLocations()[0] - particle.getLocation().getLocations()[0]);
                //newVelocity[1] = (w * particle.getVelocity().getPosition()[1]) +
                //                 (r1 * Configuration.instance.c1) * (bestParticleLocations.get(i).getLocations()[1] - particle.getLocation().getLocations()[1]) +
                //                 (r2 * Configuration.instance.c2) * (globalBestLocation.getLocations()[1] - particle.getLocation().getLocations()[1]);
                Velocity velocity = new Velocity(newVelocity);
                particle.setVelocity(velocity);

                // step 4 - update location
                double[] newLocation = new double[Configuration.instance.dimensionOfProblem];
                newLocation[0] = particle.getLocation().getLocations()[0] + newVelocity[0];
                //newLocation[1] = particle.getLocation().getLocations()[1] + newVelocity[1];
                Location location = new Location(newLocation);
                particle.setLocation(location);
            }

            //evaluate error
            //error = Configuration.instance.evaluate(globalBestLocation) - 0;
            /*
            if (n < 10)
                System.out.println("0" + n + " - x : " + Configuration.instance.decimalFormat.format(globalBestLocation.getLocations()[0])  + " - " + Configuration.instance.decimalFormat.format(function.evaluate(globalBestLocation)));
            else
                System.out.println(n + " - x : " + Configuration.instance.decimalFormat.format(globalBestLocation.getLocations()[0])  + " - " + Configuration.instance.decimalFormat.format(function.evaluate(globalBestLocation)));
*/
            n++;
            updateFitnessList(swarmSize);
        }

        System.out.println("\nsolution found at iteration " + (n - 1) + ", the solutions is :");
        System.out.println("     globalBestLocation(x) : " + Configuration.instance.decimalFormat.format(globalBestLocation.getLocations()[0]));
        //System.out.println("     globalBestLocation(y) : " + Configuration.instance.decimalFormat.format(globalBestLocation.getLocations()[1]));


        return swarm;
    }

    public void initializeSwarm(int swarmSize, ProblemSet function) {
        Particle particle;
        for (int i = 0; i < swarmSize; i++) {
            particle = new Particle(function);

            // randomize location inside a space defined in problem set
            double[] newLocation = new double[Configuration.instance.dimensionOfProblem];
            newLocation[0] = Configuration.instance.xLocationMinimum + Configuration.instance.randomGenerator.nextDouble() * (Configuration.instance.xLocationMaximum - Configuration.instance.xLocationMinimum);
            //newLocation[1] = ProblemSet.instance.yLocationMinimum + Configuration.instance.randomGenerator.nextDouble() * (ProblemSet.instance.yLocationMaximum - ProblemSet.instance.yLocationMinimum);
            Location location = new Location(newLocation);

            // randomize velocity in the range defined in problem set
            double[] newVelocity = new double[Configuration.instance.dimensionOfProblem];
            newVelocity[0] = Configuration.instance.velocityMinimum + Configuration.instance.randomGenerator.nextDouble() * (Configuration.instance.velocityMaximum - Configuration.instance.velocityMinimum);
            //newVelocity[1] = ProblemSet.instance.velocityMinimum + Configuration.instance.randomGenerator.nextDouble() * (ProblemSet.instance.velocityMaximum - ProblemSet.instance.velocityMinimum);
            Velocity velocity = new Velocity(newVelocity);

            particle.setLocation(location);
            particle.setVelocity(velocity);
            swarm.add(particle);
        }
    }

    public void updateFitnessList(int swarmSize) {
        for (int i = 0; i < swarmSize; i++)
            fitnessValueList[i] = swarm.get(i).getFitnessValue();
    }
}