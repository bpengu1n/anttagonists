package antsimulation.testing;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import antsimulation.ParameterSet;
import antsimulation.model.*;

public class FoodpileTest {
  
	private static int RUN_COUNT = 100;

	@Test
	public void ConstructorTest() 
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
			
			if(foodpileTest.xLoc!=arg1)
				fail("FoodPile.xLoc is not properly instanciated.");
			if(foodpileTest.yLoc!=arg2)
				fail("FoodPile.yLoc is not properly instanciated.");
			if(foodpileTest.foodCount!=arg3)
				fail("FoodPile.foodCount is not properly instanciated.");
			if(!foodpileTest.field.equals(arg4))
				fail("FoodPile.field is not properly instanciated.");
		}		
	}
	
	@Test
	public void DecrementTest()
	{
		Random r = new Random();
		Foodpile foodpileTest;
		int foodCount;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			
			foodCount = r.nextInt();
			
			foodpileTest = new Foodpile(0,0,foodCount,null);
			foodpileTest.decrement();
			
			if(foodpileTest.foodCount != (foodCount-1))
				fail("FoodPile.foodCount is not properly decremented.");
		}
		
	}

}
