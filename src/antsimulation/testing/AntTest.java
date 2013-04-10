package antsimulation.testing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

import antsimulation.model.Ant;
import antsimulation.model.Field;
import antsimulation.*;

public class AntTest {
	
	@Test
	public void testAntconstructor(){
		
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		for(int testNum=0; testNum<100;++testNum)
		{
			Random generator = new Random();
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			String failureMessage=("Test number "+testNum+" has FAILED with the following parameters:"+
					"\nFaction = "+faction+
					"\nLifespan = "+antLife+
					"\nX position = "+xVal+
					"\nY position = "+yVal);
			
			assertTrue(failureMessage,faction!=testAnt.faction||xVal!=testAnt.xLoc||yVal!=testAnt.yLoc||antLife!=testAnt.framesToLive);
		}
	}
	
	@Test
	public void testDie()
	{
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		Random generator = new Random();
		int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
		int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
		int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
		int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
		Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
		testAnt.die();
		String errorMessage="Ant did not properly mark itself for death!";
		assertTrue(errorMessage,testAnt.killme);
	}
	
	@Test
	public void testTakeFood()
	{
		
	}
	
	@Test
	public void eatFood()
	{
		
	}
	@Test
	public void giveFood()
	{
		
	}
	@Test
	public void testWander()
	{
		
	}
	
	@Test
	public void testUpdate()
	{

	}
}