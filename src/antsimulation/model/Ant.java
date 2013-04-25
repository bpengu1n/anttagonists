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
   			if(hasFood)
   			{ 
   				Random curiosity = new Random();
   				//check to see if the ant will move randomly or towards the nest
   				if(curiosity.nextDouble()>field.parameters.checkParameter("AntCuriosity"))
   				{
    				//head home algorithm
    				//this could be put into another function
    				//I just don't want to go through the work of making 10 more diagrams
    				Iterator<Colony> colonyIter = field.colonies.listIterator();
    		    	Colony myColony= field.colonies.get(0);
    		    	while(colonyIter.hasNext()) {
    		            Colony nextColony = colonyIter.next();
    		            if(nextColony.faction==faction)
    		            {
    		            	myColony=nextColony;
    		            	break;
    		            }
    		        }
    		    	if(myColony.xLoc > xLoc){
    		    		xLoc+=1;
    		    	}
    		    	else if(myColony.xLoc < xLoc)
    		    	{
    		    		xLoc-=1;
    		    	}
    		    	if(myColony.yLoc > yLoc)
    		    	{
    		    		yLoc+=1;
    		    	}
    		    	else if(myColony.yLoc < yLoc)
    		    	{
    		    		yLoc-=1;
    		    	}
    		    	///end head home algorithm
   				}
   				//instead of heading home they move randomly
   				else
   				{
   					wander();
   				}
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
       					layPheromone();
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
    	++framesSinceAte;
    	--framesToLive;
    	
    }

    public void die() {
    	//field.ants.remove(this);
    	killme=true;
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
    	if(xRand+xLoc < field.getWidth() && xRand+xLoc >= 0){
    		xLoc= xRand + xLoc;
    	}
    	if(yRand+yLoc < field.getHeight() && yRand+yLoc >= 0){
    		yLoc= yRand + yLoc;
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
    
    //this doesn't make a ton of sense to me
    //if we are laying pheromone it's going to be the phreromone strength value... all well
    private void layPheromone() {
    	field.setPheromoneAt(faction,xLoc,yLoc, field.parameters.checkParameter("PheromoneStrength"));
    }
    
    
    
    //we would like to follow a trail down the gradient but also follow the strongest trail
    //my code does this by finding the strongest trail that isn't the current strength + pheromone decay strength
    //if there isn't that stronger trail it will check to see if the trail is the downword gradient curr strength - pheromone decay strength
    private void followPheromones() {
    		//no pheromones to follow, or curiosity dictates wander
    		Random curiosity = new Random();
    		if(field.getPheromoneAt(faction,xLoc,yLoc)<=0 /*|| curiosity.nextDouble()>field.parameters.checkParameter("AntCuriosity")*/){
    			wander();
    		}
    		else{
    			//pheromone level at our current position
    			double curPheromone = field.getPheromoneAt(faction,xLoc,yLoc);
    			//we would like to find something greater than this
    			double curMax=curPheromone+field.parameters.checkParameter("PheromoneDecay");
    			//This is the lower value that is on the downward gradient
    			double downPheromone = curPheromone-field.parameters.checkParameter("PheromoneDecay");
    			
    			//used to check for movement
    			int oldX=xLoc;
    			int oldY=yLoc;
    			for(int i=oldX-1;i<oldX+2;++i){
    				for(int j=oldY-1;j<oldY+2;++j){
    					curMax=pheromoneCheck(i,j,curMax,downPheromone);
    				}
    			}
    	    	//check to see if there was movement
    	    	if(oldY==yLoc && oldX==xLoc){
    	    		//This would only happen if the ant found nothing left to follow
    	    		wander();
    	    	}
    		}
    }
    private double pheromoneCheck(int x, int y, double curMax, double downPheromone){
    	if(x>=0 && y>=0 && x<field.getHeight() && y<field.getWidth()){
			if(field.getPheromoneAt(faction,x,y)>curMax)
			{
				xLoc=x;
				yLoc=y;
				curMax=field.getPheromoneAt(faction,x,y);
			}
			else if(field.getPheromoneAt(faction,x,y)==downPheromone)
			{
				xLoc=x;
				yLoc=x;
			}
		}
    	return curMax;
    }
}
