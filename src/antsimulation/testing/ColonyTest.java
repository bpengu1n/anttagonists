/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulation.testing;

import antsimulation.ParameterSet;
import antsimulation.model.Colony;
import antsimulation.model.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steve
 */
public class ColonyTest {
    
    public ColonyTest() {
    }
    
    /**
     *
     */
    @Test
    public void testColonyConstructor()
    {
        int faction = 1;
        int xLoc=50;
    	int yLoc=50;
    	int framesSinceAntBorn=0;
    	int foodCount=100;
        
        ParameterSet pSet = new ParameterSet();
        Field testField = new Field(pSet);
        Colony testColony = new Colony(faction, xLoc, yLoc, testField);
        
        String fail = "Colony Constructor failed";
        
        assertTrue(fail, faction != testColony.faction || xLoc != testColony.xLoc || yLoc!=testColony.yLoc);
        
    }
}
