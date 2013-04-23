package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Ant {
    public int xLoc, yLoc; //Location
    public boolean hasFood;
    public int framesSinceAte, framesToLive;
    public Field.Dir dir;
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
    		//This is not ever going to be called until flee has been implemented
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
        					if(framesSinceAte>20)
        						eatFood(colony);
        					break;
        				}
        			}
    			}
    			else
    			{
    				followPheromones();
    				//so new we check to see if there is any food in our new location
    				for(int i=0; i<field.foodpiles.size();++i)
    		        {
    		        	Foodpile nextPile=field.foodpiles.get(i);
    		        	//if we find a food pile we get food!
    		        	if(nextPile.xLoc ==xLoc && nextPile.yLoc ==yLoc)
        				{
        					takeFood(nextPile);
        					break;
        				}
    		        	//check to see if the foodpile we are looking at is empty
    		        	//if it is, we go ahead and clean it up
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

    public void takeFood(Foodpile pile) {
    	hasFood=true;
		pile.decrement();
    }

    public void eatFood(Colony home) {
    	home.foodCount--;
    	framesSinceAte=0;
    }
    
    public void giveFood(Colony home) {
    	home.foodCount++;
    	hasFood=false;
    }
    
    public void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(3)-1;
    	int yRand=generator.nextInt(3)-1;
    	//This will move the ants to a random position
    	//it will keep ants from leaving the field
    	if(xRand+xLoc <= field.width && xRand+xLoc >= 0){
    		xLoc= (xRand + xLoc)%field.width;
    	}
    	if(yRand+yLoc <= field.height && yRand+yLoc >= 0){
    		yLoc= (yRand + yLoc)%field.height;
    	}
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
    //this doesn't make a ton of sense to me
    //if we are laying pheromone it's going to be the phreromone strength value... all well
    private void layPheromone() {
    	field.setPheromoneAt(faction,xLoc,yLoc, field.parameters.checkParameter("PheromoneStrength"));
    }
    //we would like to follow a trail down the gradient but also follow the strongest trail
    //my code does this by finding the strongest trail that isn't the current strength + pheromone decay strength
    //if there isn't that stronger trail it will check to see if the trail is the downword gradient curr strength - pheromone decay strength
    private void followPheromones() {
    		//no pheromones to follow, wander
    		if(field.pheromones[faction][xLoc][yLoc]<=0){
    			wander();
    		}
    		else{
    			//pheromone level at our current position
    			double curPheromone = field.getPheromoneAt(faction,xLoc,yLoc);
    			//we would like to find something greater than this
    			double curMax=curPheromone+field.parameters.checkParameter("PheromoneDecay");
    			//This is the lower value that is on the downward gradient
    			double downPheromone = curPheromone-field.parameters.checkParameter("PheromoneDecay");
    			
    			//The possible new location
    			int newX=xLoc;
    			int newY=yLoc;
    			//x+1,y
    			if(xLoc+1<=field.width){
    				if(field.getPheromoneAt(faction,xLoc+1,yLoc)>curMax)
    				{
    					newX=xLoc+1;
    					curMax=field.getPheromoneAt(faction,xLoc+1,yLoc);
    				}
    				else if(field.getPheromoneAt(faction,xLoc+1,yLoc)==downPheromone)
    				{
    					newX=xLoc+1;
    				}
    			}
    	    	//x+1,y+1
    			if(xLoc+1<=field.width && yLoc+1<=field.height){
    				if(field.getPheromoneAt(faction,xLoc+1,yLoc+1)>curMax)
    				{
    					newX=xLoc+1;
    					newY=yLoc+1;
    					curMax=field.getPheromoneAt(faction,xLoc+1,yLoc+1);
    				}
    				else if(field.getPheromoneAt(faction,xLoc+1,yLoc+1)==downPheromone)
    				{
    					newX=xLoc+1;
    					newY=yLoc+1;
    				}
    			}
    	    	//x+1,y-1
    			if(xLoc+1<=field.width && yLoc-1>=0){
    				if(field.getPheromoneAt(faction,xLoc+1,yLoc-1)>curMax)
    				{
    					newX=xLoc+1;
    					newY=yLoc-1;
    					curMax=field.getPheromoneAt(faction,xLoc+1,yLoc-1);
    				}
    				else if(field.getPheromoneAt(faction,xLoc+1,yLoc-1)==downPheromone)
    				{
    					newX=xLoc+1;
    					newY=yLoc-1;
    				}
    			}
    	    	//x-1,y
    	    	if(xLoc-1>=0){
    				if(field.getPheromoneAt(faction,xLoc-1,yLoc)>curMax)
    				{
    					newX=xLoc-1;
    					curMax=field.getPheromoneAt(faction,xLoc-1,yLoc);
    				}
    				else if(field.getPheromoneAt(faction,xLoc-1,yLoc)==downPheromone)
    				{
    					newX=xLoc-1;
    				}
    			}
    	    	//x-1,y+1
    	    	if(xLoc-1>=0 && yLoc+1<=field.height){
    				if(field.getPheromoneAt(faction,xLoc-1,yLoc+1)>curMax)
    				{
    					newX=xLoc-1;
    					newY=yLoc+1;
    					curMax=field.getPheromoneAt(faction,xLoc+1,yLoc);
    				}
    				else if(field.getPheromoneAt(faction,xLoc+1,yLoc)==downPheromone)
    				{
    					newX=xLoc-1;
    					newY=yLoc+1;
    				}
    			}
    	    	//x-1,y-1
    	    	if(xLoc-1>=0 && yLoc-1>=0){
    				if(field.getPheromoneAt(faction,xLoc-1,yLoc-1)>curMax)
    				{
    					newX=xLoc-1;
    					newY=yLoc-1;
    					curMax=field.getPheromoneAt(faction,xLoc-1,yLoc-1);
    				}
    				else if(field.getPheromoneAt(faction,xLoc-1,yLoc-1)==downPheromone)
    				{
    					newX=xLoc-1;
    					newY=yLoc-1;
    				}
    			}
    	    	//x,y+1
    	    	if(yLoc+1<=field.height){
    				if(field.getPheromoneAt(faction,xLoc,yLoc+1)>curMax)
    				{
    					newY=yLoc+1;
    					curMax=field.getPheromoneAt(faction,xLoc,yLoc+1);
    				}
    				else if(field.getPheromoneAt(faction,xLoc,yLoc+1)==downPheromone)
    				{
    					newY=yLoc+1;
    				}
    			}
    	    	//x,y-1
    	    	if(yLoc-1>=0){
    				if(field.getPheromoneAt(faction,xLoc,yLoc-1)>curMax)
    				{
    					newY=yLoc-1;
    					curMax=field.getPheromoneAt(faction,xLoc,yLoc-1);
    				}
    				else if(field.getPheromoneAt(faction,xLoc,yLoc-1)==downPheromone)
    				{
    					newY=yLoc-1;
    				}
    			}
    	    	//check to see if there was movement
    	    	if(newY==yLoc&&newX==xLoc){
    	    		//This would only happen if the ant found nothing left to follow
    	    		wander();
    	    	}
    	    	else{
    	    		xLoc=newX;
    	    		yLoc=newY;
    	    	}
    		}
    }
}
