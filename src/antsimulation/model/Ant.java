package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Ant {
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    public Field.Dir dir;
    public int xLoc, yLoc; //Location
    public int faction;
    private Field field;

    public Ant(Field f) {
        field = f;
    }
    
    
    public Ant(int theFaction, int theX, int theY, int framesofLife){
    	faction=theFaction;
    	xLoc=theX;
    	yLoc=theY;
    	framesToLive=framesofLife;
    	hasFood=false;
    	framesSinceAte=0;
    }
    
    public void update() {
    	if(/*framesSinceAte==field.starvationValue*/ false || framesToLive==0)
    	{
    		die();
    	}
    	else
    	{
    		if(predatorNear())
    		{
    			flee();
    		}
    		else
    		{
    			if(hasFood)
    			{
    				wander();
    				layPheromone();
        			for(int i = 0;i<field.colonies.length; i++) {
        				if(field.colonies[i].xLoc ==xLoc && field.colonies[i].yLoc ==yLoc && field.colonies[i].faction == faction)
        				{
        					giveFood(field.colonies[i]);
        					eatFood(field.colonies[i]);
        					break;
        				}
        			}
    			}
    			else
    			{
    				followPheromones();//This does nothing right now except call wander()
    				for(Iterator<Foodpile> i = field.foodpiles.iterator(); i.hasNext(); ) {
        				Foodpile food = i.next();
        				if(food.xLoc ==xLoc && food.yLoc ==yLoc)
        				{
        					takeFood(food);
        					break;
        				}
        			  
        			}
    			}
    		}
    	}
    	
    }
    public void die() {
    	field.ants.remove(this);
    }
    
    //This will eventually check for nearby predators
    private boolean predatorNear(){
    	return false;
    }
    private void takeFood(Foodpile pile) {
    	hasFood=true;
		pile.decrement();
    }
    private void eatFood(Colony home) {
    	home.foodCount--;
    	framesSinceAte=0;
    }
    private void giveFood(Colony home) {
    	home.foodCount++;
    	hasFood=false;
    }
    private void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(2)-1;
    	int yRand=generator.nextInt(2)-1;
    	xLoc+=xRand;
    	xLoc+=yRand;
    	if(xRand==1)
    	{
    		dir=Field.Dir.RIGHT;
    	}
    	else if(xRand==-1)
    	{
    		dir=Field.Dir.LEFT;
    	}
    	else
    	{
    		if(yRand==1)
    		{
    			dir=Field.Dir.UP;
    		}
    		else
    			dir=Field.Dir.DOWN;
    	}
    }
    private void flee() {
    	System.out.println("error! this should has not yet been implemented!");
    }
    private void layPheromone() {
    	//Does nothing as it has yet to be implemented
    	//field.pheromones[faction][xLoc][yLoc]=100;
    }
    private void followPheromones() {
    	wander();//pheromones not yet fully implemented
    }
}
