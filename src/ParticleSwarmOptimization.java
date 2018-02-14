import java.util.Vector;

public class ParticleSwarmOptimization {

    private Vector<Particle> swarm = new Vector<>();
    private Vector<Location> bestParticleLocations = new Vector<>();
    private double globalBestParticle;

    public Location getGlobalBestLocation() {
        return globalBestLocation;
    }

    private Location globalBestLocation;
    private double[] fitnessValueList;

    public Vector<Particle> execute(int swarmSize, int iterations, double a, double b) {
        ProblemSet function = new ProblemSet(a, b);
        double[] bestParticles = new double[swarmSize];
        fitnessValueList = new double[swarmSize];

        initializeSwarm(swarmSize, function);
        updateFitnessList(swarmSize);

        for (int i = 0; i < swarmSize; i++) {
            bestParticles[i] = fitnessValueList[i];
            bestParticleLocations.add(swarm.get(i).getLocation());
        }

        double w;

        for (int n = 0; n < iterations; n++) {
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

            w = Configuration.instance.wUpperBound -
                    (((double) n) / iterations) * (Configuration.instance.wUpperBound - Configuration.instance.wLowerBound);

            for (int i = 0; i < swarmSize; i++) {
                double r1 = Configuration.instance.randomGenerator.nextDouble();
                double r2 = Configuration.instance.randomGenerator.nextDouble();

                Particle particle = swarm.get(i);

                // step 3 - update velocity
                double[] newVelocity = new double[Configuration.instance.dimensionOfProblem];
                newVelocity[0] = (w * particle.getVelocity().getPosition()[0]) +
                        (r1 * Configuration.instance.c1) * (bestParticleLocations.get(i).getLocations()[0] - particle.getLocation().getLocations()[0]) +
                        (r2 * Configuration.instance.c2) * (globalBestLocation.getLocations()[0] - particle.getLocation().getLocations()[0]);

                Velocity velocity = new Velocity(newVelocity);
                particle.setVelocity(velocity);

                // step 4 - update location
                double[] newLocation = new double[Configuration.instance.dimensionOfProblem];
                newLocation[0] = particle.getLocation().getLocations()[0] + newVelocity[0];
                Location location = new Location(newLocation);
                particle.setLocation(location);
            }
            updateFitnessList(swarmSize);
        }

        return swarm;
    }

    private void initializeSwarm(int swarmSize, ProblemSet function) {
        Particle particle;
        for (int i = 0; i < swarmSize; i++) {
            particle = new Particle(function);

            // randomize location inside a space defined in problem set
            double[] newLocation = new double[Configuration.instance.dimensionOfProblem];
            newLocation[0] = Configuration.instance.xLocationMinimum +
                    Configuration.instance.randomGenerator.nextDouble() * (Configuration.instance.xLocationMaximum - Configuration.instance.xLocationMinimum);
            Location location = new Location(newLocation);

            // randomize velocity in the range defined in problem set
            double[] newVelocity = new double[Configuration.instance.dimensionOfProblem];
            newVelocity[0] = Configuration.instance.velocityMinimum +
                    Configuration.instance.randomGenerator.nextDouble() * (Configuration.instance.velocityMaximum - Configuration.instance.velocityMinimum);
            Velocity velocity = new Velocity(newVelocity);

            particle.setLocation(location);
            particle.setVelocity(velocity);
            swarm.add(particle);
        }
    }

    private void updateFitnessList(int swarmSize) {
        for (int i = 0; i < swarmSize; i++)
            fitnessValueList[i] = swarm.get(i).getFitnessValue();
    }
}