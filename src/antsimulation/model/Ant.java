package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Ant {
    public int xLoc, yLoc; //Location
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    private Field.Dir dir;
    public int faction;
    private Field field;
    public boolean killme=false;
    //constructor
    public Ant(int theFaction, int theX, int theY, int framesofLife, Field f){
        faction=theFaction;
    	xLoc=theX;
    	yLoc=theY;
    	framesToLive=framesofLife;
    	hasFood=false;
    	framesSinceAte=0;
    	field = f;
    }
    
    //update does a lot
    //1. it figures out if it's time to die of natural causes (non predator murder)
    //2. figures out whether or not to flee
    //3. checks to see if has food and then sees if it can place it in its colony. It will also lay pheromone
    //4. wanders and sees if it can pick up food
    //needs to implement some follow pheromone logic
    //
    public void update() {
    	if(framesSinceAte==(int)field.parameters.checkParameter("AntStarvation") || framesToLive==0)
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
        			for(Iterator<Colony> i = field.colonies.iterator(); i.hasNext(); ) {
        				Colony colony = i.next();
        				if(colony.xLoc ==xLoc && colony.yLoc ==yLoc && colony.faction == faction)
        				{
        					giveFood(colony);
        					eatFood(colony);
        					break;
        				}
        			}
    			}
    			else
    			{
    				followPheromones();//This does nothing right now except call wander()
    				for(int i=0; i<field.foodpiles.size();++i)
    		        {
    		        	Foodpile nextPile=field.foodpiles.get(i);
    		        	if(nextPile.xLoc ==xLoc && nextPile.yLoc ==yLoc)
        				{
        					takeFood(nextPile);
        					break;
        				}
    		        	if(nextPile.killme)
    		    		{
    		        		if(i+1 <field.foodpiles.size())
    		        		{
    		        			Foodpile findNext = field.foodpiles.get(i+1);
    		        			field.foodpiles.remove(nextPile);
    		        			i = field.foodpiles.indexOf(findNext)-1;
    		        		}
    		        		else
    		        			field.foodpiles.remove(nextPile);
    		    		}
    		        }
    			}
    		}
    	}
    	++framesSinceAte;
    	--framesToLive;
    	
    }
    public void die() {
    	//field.ants.remove(this);
    	killme=true;
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
    	int xRand=generator.nextInt(3)-1;
    	int yRand=generator.nextInt(3)-1;
    	xLoc+=xRand;
    	yLoc+=yRand;
    	if(xRand>0)
    	{
    		dir=Field.Dir.RIGHT;
    	}
    	else if(xRand<0)
    	{
    		dir=Field.Dir.LEFT;
    	}
    	else
    	{
    		if(yRand>0)
    		{
    			dir=Field.Dir.UP;
    		}
    		else
    			dir=Field.Dir.DOWN;
    	}
    }
    private void flee() {
    	System.out.println("error! this has not yet been implemented!");
    }
    private void layPheromone() {
    	//Does nothing as it has yet to be implemented
    	//field.pheromones[faction][xLoc][yLoc]=100;
    }
    private void followPheromones() {
    	wander();//pheromones not yet fully implemented
    }
}

