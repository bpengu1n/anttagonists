package antsimulation.testing;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import antsimulation.model.Ant;
import antsimulation.model.Colony;
import antsimulation.model.Field;
import antsimulation.model.Foodpile;
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
			//Making sure that all values were properly initialized
			assertTrue(failureMessage,faction==testAnt.faction||xVal==testAnt.xLoc||yVal==testAnt.yLoc||antLife==testAnt.framesToLive);
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
		
		//Death comes to all
		testAnt.die();
		String errorMessage="Ant did not properly mark itself for death!";
		//If ant has marked itself for death we are good
		assertTrue(errorMessage,testAnt.killme);
	}
	
	@Test
	public void testTakeFood()
	{
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		
		for(int testNum=0;testNum<100;++testNum){
			Random generator = new Random();
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			Foodpile testPile= new Foodpile(xVal, yVal, (int)pSet.checkParameter("StartFoodPileSize"),testField);
			
			testAnt.hasFood=false;
			//Checks to make sure foodCount decreases
			int beforeTake=testPile.foodCount;
			testAnt.takeFood(testPile);
			assertTrue("TestPile foodCount did NOT increase after ant took food from it",testPile.foodCount==beforeTake-1);
			//Checks to make sure ant is now carrying food
			assertTrue("Ant is NOT carrying food after taking food from pile",testAnt.hasFood);
			
		}
	}
	
	@Test
	public void eatFood()
	{
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		
		for(int testNum=0;testNum<100;++testNum){
			Random generator = new Random();
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			Colony testColony= new Colony(faction, xVal, yVal, testField);
			
			
			//Checks to make sure the colony food count decreases
			int beforeEat=testColony.foodCount;
			testAnt.eatFood(testColony);
			assertTrue("eatFood has failed to decrease the foodCount of the colony!",beforeEat-1==testColony.foodCount);
			//Checks to make sure the ant has 0 frames since eaten
			assertTrue("Ant has not been satiated from eating?! eatFood has failed!", testAnt.framesSinceAte==0);
		}
	}
	@Test
	public void testGiveFood()
	{
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		
		for(int testNum=0;testNum<100;++testNum){
			Random generator = new Random();
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			Colony testColony= new Colony(faction, xVal, yVal, testField);
			
			//giving ant food
			testAnt.hasFood=true;
			//Checks to make sure the colony food count increases
			int beforeGive=testColony.foodCount;
			testAnt.giveFood(testColony);
			assertTrue("giveFood has failed to increase the foodCount of the colony!",beforeGive+1==testColony.foodCount);
			//Checks to make sure the ant no longer carries food
			assertTrue("Ant still has food and giveFood has failed!", !testAnt.hasFood);
			
			
		}
		
		
	}
	@Test
	public void testWander()
	{
		
		for(int testNum = 0; testNum < 100; ++testNum)
		{
			ParameterSet pSet = new ParameterSet();
			Field testField = new Field(pSet);
			Random generator = new Random();
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			
			for(int wanderIteration = 0; wanderIteration < 100; wanderIteration++)
				testAnt.wander();
			
			//Here we check to see if ant has moved after 100 iterations, this can occasionally fail.
			assertTrue("testAnt.wander did not move the ant on testNum:"+testNum,testAnt.xLoc != xVal || testAnt.yLoc != yVal );
		}
	}
	
	@Test
	public void testUpdate()
	{
		//This will be hairy
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		Random generator = new Random();
		for(int numTests=0;numTests<100;++numTests){
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			
			//update is called
			int beforeFramesAte= testAnt.framesSinceAte;
			int beforeFramesLive= testAnt.framesToLive;
			testAnt.update();
			//Check to make sure frameSinceAte increases
			assertTrue("Ant did not have it's framesSinceAte increase!",testAnt.framesSinceAte==beforeFramesAte+1);
			//Check to make sure framestoLive decreases
			assertTrue("Ant did not have it's framestoLive decrease!",testAnt.framesToLive==beforeFramesLive-1);
			
			testAnt.killme=false;
			testAnt.framesSinceAte=(int)testField.parameters.checkParameter("AntStarvation");
			//Check to see if ant marks itself for death when starving
			testAnt.update();
			assertTrue("Ant did not die of starvation!",testAnt.killme);
			
			testAnt.killme=false;
			testAnt.framesSinceAte=0;
			testAnt.framesToLive=0;
			//check to see if ant marks itself for death when out of frames to live
			testAnt.update();
			assertTrue("Ant did not die of old age!",testAnt.killme);
		}
	}
}