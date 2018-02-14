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

    //todo writing tests
}
