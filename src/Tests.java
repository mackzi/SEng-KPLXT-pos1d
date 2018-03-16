import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Tests {

    @Test
    public void testSwarmNotNull() {
        ParticleSwarmOptimization pos = new ParticleSwarmOptimization();
        assertNotNull(pos.execute(50, 10, 0.5, 0.5));
    }

    @Test
    public void testSwarmReturningSize() {
        ParticleSwarmOptimization pos = new ParticleSwarmOptimization();
        assertEquals(50, pos.execute(50, 10, 0.5, 0.5).size());
    }

    @Test
    public void testProblemSetEvaluation() {
        ProblemSet prob = new ProblemSet(2.6, 1.4);
        double[] loc = new double[2];
        loc[0] = 1;
        loc[1] = 4;
        Location location = new Location(loc);
        assertEquals(1.60, prob.evaluate(location), 0.000000001);
    }

    @Test
    public void testUtilities() {
        double[] test = new double[5];
        test[0] = 19.133;
        test[1] = 2.23;
        test[2] = 5.008;
        test[3] = 13.029;
        test[4] = 8.9623;
        assertEquals(1, Utilities.getMinimumPosition(test));
    }
}
