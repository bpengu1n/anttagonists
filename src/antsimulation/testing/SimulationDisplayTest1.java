/* I have NO idea what I'm doing here */

package antsimulation.testing;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;
import java.util.Random;
import antsimulation.model.*;
import antsimulation.view.SimulationDisplay;

public class SimulationDisplayTest1 {

	/*
	@BeforeClass
	public static void testSetup() 
	{
		// Code executed before test cases
	}
	*/
	/*
	@AfterClass
	public static void testCleanup() 
	{
		// Code Executed after test cases
	}
	*/

	@Test
	public void testParameter() {
            SimulationDisplay disp = new SimulationDisplay();
            disp.update(new Field());
//            if (disp.dispImage == null)
//                fail(disp.dispImage + " was not correctly initialized");
	}
}
