package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Ant {
    private int xLoc, yLoc; //Location
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    private Field.Dir dir;
    private int faction;
    private Field field;
    private boolean killme=false;
    //constructor
    public Ant(int theFaction, int theX, int theY, int framesofLife, Field f){
        setFaction(theFaction);
    	setxLoc(theX);
    	setyLoc(theY);
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
    				headHome();
   				}
   				//instead of heading home they move randomly
   				else
   				{
   					wander();
   				}
   				layPheromone();
       			for(Iterator<Colony> i = field.colonies.iterator(); i.hasNext(); ) {
       				Colony colony = i.next();
       				if(colony.getxLoc() ==getxLoc() && colony.getyLoc() ==getyLoc() && colony.getFaction() == getFaction())
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
   		        	if(nextPile.getxLoc() ==getxLoc() && nextPile.getyLoc() ==getyLoc())
       				{
     					takeFood(nextPile);
       					layPheromone();
       					break;
       				}
   		        	//check to see if the foodpile we are looking at is empty
   		        	//if it is, we go ahead and clean it up
   		        	if(nextPile.isKillme())
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
    	setKillme(true);
    }
    

    public void takeFood(Foodpile pile) {
    	hasFood=true;
		pile.decrement();
    }

    public void eatFood(Colony home) {
    	home.setFoodCount(home.getFoodCount() - 1);
    	framesSinceAte=0;
    }
    
    public void giveFood(Colony home) {
    	home.setFoodCount(home.getFoodCount() + 1);
    	hasFood=false;
    }
    
    
    public void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(3)-1;
    	int yRand=generator.nextInt(3)-1;
    	//This will move the ants to a random position
    	//it will keep ants from leaving the field
    	if(xRand+getxLoc() < field.getWidth() && xRand+getxLoc() >= 0){
    		setxLoc(xRand + getxLoc());
    	}
    	if(yRand+getyLoc() < field.getHeight() && yRand+getyLoc() >= 0){
    		setyLoc(yRand + getyLoc());
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
    	field.setPheromoneAt(getFaction(),getxLoc(),getyLoc(), field.parameters.checkParameter("PheromoneStrength"));
    }
    
  //head home algorithm
    private void headHome(){
    	
		Iterator<Colony> colonyIter = field.colonies.listIterator();
    	Colony myColony= field.colonies.get(0);
    	while(colonyIter.hasNext()) {
            Colony nextColony = colonyIter.next();
            if(nextColony.getFaction()==getFaction())
            {
            	myColony=nextColony;
            	break;
            }
        }
    	if(myColony.getxLoc() > getxLoc()){
    		setxLoc(getxLoc() + 1);
    	}
    	else if(myColony.getxLoc() < getxLoc())
    	{
    		setxLoc(getxLoc() - 1);
    	}
    	if(myColony.getyLoc() > getyLoc())
    	{
    		setyLoc(getyLoc() + 1);
    	}
    	else if(myColony.getyLoc() < getyLoc())
    	{
    		setyLoc(getyLoc() - 1);
    	}
    }
    
    //we would like to follow a trail down the gradient but also follow the strongest trail
    //my code does this by finding the strongest trail that isn't the current strength + pheromone decay strength
    //if there isn't that stronger trail it will check to see if the trail is the downword gradient curr strength - pheromone decay strength
    private void followPheromones() {
    		//no pheromones to follow, or curiosity dictates wander
    		Random curiosity = new Random();
    		if(field.getPheromoneAt(getFaction(),getxLoc(),getyLoc())<=0 /*|| curiosity.nextDouble()>field.parameters.checkParameter("AntCuriosity")*/){
    			wander();
    		}
    		else{
    			//pheromone level at our current position
    			double curPheromone = field.getPheromoneAt(getFaction(),getxLoc(),getyLoc());
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
    	    	if(oldY==getyLoc() && oldX==getxLoc()){
    	    		//This would only happen if the ant found nothing left to follow
    	    		wander();
    	    	}
    		}
    }
    private double pheromoneCheck(int x, int y, double curMax, double downPheromone){
    	if(x>=0 && y>=0 && x<field.getHeight() && y<field.getWidth()){
			if(field.getPheromoneAt(getFaction(),x,y)>curMax)
			{
				setxLoc(x);
				setyLoc(y);
				curMax=field.getPheromoneAt(getFaction(),x,y);
			}
			else if(field.getPheromoneAt(getFaction(),x,y)==downPheromone)
			{
				setxLoc(x);
				setyLoc(y);
			}
		}
    	return curMax;
    }

	public boolean getKillme() {
		return killme;
	}

	public void setKillme(boolean killme) {
		this.killme = killme;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public int getxLoc() {
		return xLoc;
	}

	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
}
