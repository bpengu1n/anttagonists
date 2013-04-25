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
			assertTrue(failureMessage,faction==testAnt.getFaction()||xVal==testAnt.getxLoc()||yVal==testAnt.getyLoc()||antLife==testAnt.getFramesToLive());
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
		assertTrue(errorMessage,testAnt.getKillme());
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
			
			testAnt.setHasFood(false);
			testAnt.takeFood(testPile);
			//Checks to make sure ant is now carrying food
			assertTrue("Ant is NOT carrying food after taking food from pile",testAnt.getHasFood());
			
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
			int beforeEat=testColony.getFoodCount();
			testAnt.eatFood(testColony);
			assertTrue("eatFood has failed to decrease the foodCount of the colony!",beforeEat-1==testColony.getFoodCount());
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
			testAnt.setHasFood(true);
			testAnt.giveFood(testColony);
			//Checks to make sure the ant no longer carries food
			assertTrue("Ant still has food and giveFood has failed!", !testAnt.getHasFood());
			
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
			assertTrue("testAnt.wander did not move the ant on testNum:"+testNum,testAnt.getxLoc() != xVal || testAnt.getyLoc() != yVal );
		}
	}
	
	@Test
	public void testUpdateframeSinceAte()
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
			int beforeFramesAte= testAnt.getFramesSinceAte();
			testAnt.update();
			//Check to make sure frameSinceAte increases
			assertTrue("Ant did not have it's framesSinceAte increase!",testAnt.getFramesSinceAte()==beforeFramesAte+1);
		}
	}
	
	@Test
	public void testUpdateframestoLive(){
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
			int beforeFramesLive= testAnt.getFramesToLive();
			testAnt.update();
			//Check to make sure framestoLive decreases
			assertTrue("Ant did not have it's framestoLive decrease!",testAnt.getFramesToLive()==beforeFramesLive-1);
		}
	}
	
	@Test
	public void testUpdateStarvation(){
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		Random generator = new Random();
		for(int numTests=0;numTests<100;++numTests){
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			
			testAnt.setKillme(false);
			testAnt.setFramesSinceAte((int)testField.parameters.checkParameter("AntStarvation"));
			//Check to see if ant marks itself for death when starving
			testAnt.update();
			assertTrue("Ant did not die of starvation!",testAnt.getKillme());
		}
	}
	@Test
	public void testUpdateOldAgeDeath(){
		ParameterSet pSet = new ParameterSet();
		Field testField = new Field(pSet);
		Random generator = new Random();
		for(int numTests=0;numTests<100;++numTests){
			int faction=generator.nextInt((int)pSet.checkParameter("MaxColonies"));
			int xVal=generator.nextInt((int)pSet.checkParameter("xSize"));
			int yVal=generator.nextInt((int)pSet.checkParameter("ySize"));
			int antLife=generator.nextInt((int)pSet.checkParameter("AntLifetime"));
			Ant testAnt=new Ant(faction,xVal,yVal,antLife, testField);
			
			testAnt.setKillme(false);
			testAnt.setFramesSinceAte(0);
			testAnt.setFramesToLive(0);
			//check to see if ant marks itself for death when out of frames to live
			testAnt.update();
			assertTrue("Ant did not die of old age!",testAnt.getKillme());
		}
	}
}