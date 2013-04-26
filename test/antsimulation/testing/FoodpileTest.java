package antsimulation.testing;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import antsimulation.ParameterSet;
import antsimulation.model.*;

public class FoodpileTest {
  
	private static int RUN_COUNT = 100;

	@Test
	public void testConstructor() 
	{
		Random r = new Random();
		Foodpile foodpileTest;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			int arg1 = r.nextInt();
			int arg2 = r.nextInt();
			int arg3 = r.nextInt();
			Field arg4 = new Field(new ParameterSet());
			
			foodpileTest = new Foodpile(arg1, arg2, arg3, arg4);
			
			assertTrue("FoodPile is not properly instanciated.",foodpileTest.getxLoc()==arg1 && foodpileTest.getyLoc()==arg2 && foodpileTest.getFoodCount()==arg3 && foodpileTest.getField().equals(arg4));
		}		
	}
	
	@Test
	public void testDecrement()
	{
		Random r = new Random();
		Foodpile foodpileTest;
		int foodCount;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			
			foodCount = r.nextInt();
			
			foodpileTest = new Foodpile(0,0,foodCount,null);
			foodpileTest.decrement();
			
			assertTrue("FoodPile.foodCount is not properly decremented.",foodpileTest.getFoodCount() == (foodCount-1));
		}
		
	}

}
