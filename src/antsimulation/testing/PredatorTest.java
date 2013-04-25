package antsimulation.testing;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import antsimulation.ParameterSet;
import antsimulation.model.*;

public class PredatorTest {
  
  private static int RUN_COUNT = 100;

	@Test
	public void constructorTest() 
	{
		Random r = new Random();
		Predator predatorTest;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			int arg1 = r.nextInt();
			int arg2 = r.nextInt();
			int arg3 = r.nextInt();
			Field arg4 = new Field(new ParameterSet());
			
			predatorTest = new Predator(arg1, arg2, arg3, arg4);

			if(predatorTest.hunger!=arg1)
				fail("Predator.foodCount is not properly instanciated.");
			if(predatorTest.xLoc!=arg2)
				fail("Predator.xLoc is not properly instanciated.");
			if(predatorTest.yLoc!=arg3)
				fail("Predator.yLoc is not properly instanciated.");
			if(!predatorTest.field.equals(arg4))
				fail("Predator.field is not properly instanciated.");
		}		
	}
	
	@Test
	public void updateTest()
	{
		Predator predatorTest;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			predatorTest = new Predator(0, 0, 0, new Field(new ParameterSet()));
			predatorTest.update();
			if(!predatorTest.killme)
				fail("Predator.update() did not change killme to true when hunger = 0.");
			
		}
		
	}
	
	@Test
	public void eatAntTest()
	{
		Predator predatorTest;
		Ant antTest;
		Random r = new Random();
		
		int x,y,hunger;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			x = r.nextInt(100);
			y = r.nextInt(100);
			hunger = r.nextInt(10);
			
			predatorTest = new Predator(hunger, x, y, null);
			antTest = new Ant(0, x, y, 1, null);
			
			predatorTest.eatAnt(antTest);
			
			if(!antTest.getKillme())
				fail("Predator.eatAnt does not set killme flag.");
			if(predatorTest.hunger != hunger-1)
				fail("Predator.eatAnt does not decrement hunger.");
		}
	}
	
	@Test
	public void leaveTest()
	{
		Predator predatorTest;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			predatorTest = new Predator(1, 1, 1, null);
			predatorTest.killme = false;
			
			predatorTest.leave();
			
			if(predatorTest.killme == false)
				fail("PredatorTest.killme does not set the killme flag.");
		}
	}
	
	@Test
	public void wanderTest()
	{
		Predator predatorTest;
		Random r = new Random();
		int xInit, yInit;
		
		for(int testRun = 0; testRun < RUN_COUNT; testRun++)
		{
			xInit = r.nextInt(1000);
			yInit = r.nextInt(1000);
			
			predatorTest = new Predator(1, xInit, yInit, null);
			
			for(int wanderIteration = 0; wanderIteration < 100; wanderIteration++)
				predatorTest.wander();
			
			//After 100 wander calls, the predator should have moved from its initial location
			//MAY fail occasionally, That is expected.
			if(predatorTest.xLoc == xInit && predatorTest.yLoc == yInit )
				fail("PredatorTest.wander does not move predator.");
		}
	}


}
