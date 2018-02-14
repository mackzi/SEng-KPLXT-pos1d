public class Particle {
    private Velocity velocity;
    private Location location;
    private ProblemSet function;

    public Particle(ProblemSet function) {
        super();
        this.function = function;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getFitnessValue() {
        return function.evaluate(location);
    }
}