/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulation.testing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import antsimulation.ParameterSet;
import antsimulation.model.Field;

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
        assertTrue("The Initialization test case failed.",instance.getAntList().size() == p.checkParameter("StartAntsPerColony")
                    && instance.getNumOfColonies() == p.checkParameter("MaxColonies") 
                    && instance.getFoodpileList().size() == p.checkParameter("StartFoodPiles"));
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
        	assertTrue("The Update test case failed.",instance.getChanged() == true);
    }


}