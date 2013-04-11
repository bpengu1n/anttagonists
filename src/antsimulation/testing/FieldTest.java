/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulation.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import antsimulation.ParameterSet;

/**
 *
 * @author bwyunker
 */
public class FieldTest {
    
    public FieldTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class Field.
     */
    @Test
    public void testInitialize() {
        ParameterSet p = new ParameterSet();
        System.out.println("initialize");
        Field instance = new Field(p);
        instance.initialize();

        if (instance.ants.size() < p.checkParameter("StartAntsPerColony")
                || instance.colonies.size() < p.checkParameter("MaxColonies") 
                || instance.foodpiles.size() < p.checkParameter("StartFoodPiles")
                || instance.predators.size() < p.checkParameter("StartPredators"))
            fail("The Initialization test case failed.");
    }

    /**
     * Test of update method, of class Field.
     */
    @Test
    public void testUpdate() {
        ParameterSet p = new ParameterSet();
        System.out.println("update");
        Field instance = new Field(p);
        for (int x=0; x<500; x++)
            instance.update();
        if(instance.changed != true)
            fail("The Update test case failed.");
    }

    /**
     * Test of spawnPredator method, of class Field.
     */
    @Test
    public void testSpawnPredator() {
        ParameterSet p = new ParameterSet();
        System.out.println("spawnPredator");
        Field instance = new Field(p);
        
        instance.spawnPredator();

        if (instance.predators.size() < p.checkParameter("StartPredators")) 
            fail("The Spawn Predator test case failed.");
    }

}