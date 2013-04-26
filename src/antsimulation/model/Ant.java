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
        faction = theFaction;
    	xLoc = theX;
    	yLoc = theY;
    	framesToLive = framesofLife;
    	hasFood = false;
    	framesSinceAte = 0;
    	field = f;
    }
    
    //update does a lot
    //1. it figures out if it's time to die of natural causes
    //2. checks to see if has food and then sees if it can place it in its colony. It will also lay pheromone
    //3. wanders and sees if it can pick up food
    //needs to implement some follow pheromone logic
    //
    public void update() {
    	if(framesSinceAte==(int)field.getParameterSet().checkParameter("AntStarvation") || framesToLive==0)
    	{
    		die();
    	}
    	else
    	{
            //interact with colony, whether or not the ant has food
            Colony colony = field.getColony(faction);
            if(colony!=null && colony.getxLoc() == xLoc&& colony.getyLoc() == yLoc)
            {
                if (hasFood)
                    giveFood(colony);
                if (getFramesSinceAte()>field.getParameterSet().checkParameter("AntHunger"))
                	eatFood(colony);
            }
            //move.  Motion depends on whether we have food
            if(hasFood)
            { 
                Random curiosity = new Random();
                //check to see if the ant will move randomly or towards the nest
                if(curiosity.nextDouble()>field.getParameterSet().checkParameter("AntCuriosity"))
                {
                    headHome();
                }
                //instead of heading home they move randomly
                else
                {
                    wander();
                }
                layPheromone();
            }
            else
            {
                followPheromones();
                //so new we check to see if there is any food in our new location
                java.util.List<Foodpile> foodpiles = field.getFoodpileList();
                for(int i=0; i<foodpiles.size();++i)
                {
                    Foodpile nextPile=foodpiles.get(i);
                    //if we find a food pile we get food!
                    if(nextPile.getxLoc() ==getxLoc() && nextPile.getyLoc() ==getyLoc() && nextPile.getFoodCount()>0)
                    {
                            takeFood(nextPile);
                            layPheromone();
                            break;
                    }
                }
            }
            
        }
        framesSinceAte++;
        framesToLive--;
    }

    //this is public for future expansion (ie, predators)
    public void die() {
    	killme = true;
    }
    

    private void takeFood(Foodpile pile) {
    	hasFood = true;
	pile.decrement();
    }

    private void eatFood(Colony home) {
    	if(home.getFoodCount()>0){
    		home.setFoodCount(home.getFoodCount() - 1);
    		framesSinceAte = 0;
    	}
    }
    
    private void giveFood(Colony home) {
    	home.setFoodCount(home.getFoodCount() + 1);
    	hasFood = false;
    }
    
    
    private void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(3)-1;
    	int yRand=generator.nextInt(3)-1;
    	//This will move the ants to a random position
    	//it will keep ants from leaving the field
    	if(xRand+getxLoc() < field.getWidth() && xRand+getxLoc() >= 0){
    		xLoc += xRand;
    	}
    	if(yRand+getyLoc() < field.getHeight() && yRand+getyLoc() >= 0){
    		yLoc += yRand;
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
    	field.setPheromoneAt(getFaction(),getxLoc(),getyLoc(), field.getParameterSet().checkParameter("PheromoneStrength"));
    }
    
  //head home algorithm
    private void headHome(){
    	Colony myColony= field.getColony(faction);
        if (myColony == null)
            return;
    	if(myColony.getxLoc() > getxLoc())
    		xLoc++;
    	else if(myColony.getxLoc() < getxLoc())
    		xLoc--;
    	if(myColony.getyLoc() > getyLoc())
    		yLoc++;
    	else if(myColony.getyLoc() < getyLoc())
    		yLoc--;
    }
    
    //we would like to follow a trail down the gradient but also follow the strongest trail
    //my code does this by finding the strongest trail that isn't the current strength + pheromone decay strength
    //if there isn't that stronger trail it will check to see if the trail is the downword gradient curr strength - pheromone decay strength
    private void followPheromones() {
    		//no pheromones to follow, or curiosity dictates wander
    		Random curiosity = new Random();
    		if(field.getPheromoneAt(getFaction(),getxLoc(),getyLoc())<=0 ){
    			wander();
    		}
    		else if(curiosity.nextDouble()<field.getParameterSet().checkParameter("AntCuriosity"))
    		{
    			wander();
    		}
    		else{
    			//pheromone level at our current position
    			double curPheromone = field.getPheromoneAt(getFaction(),getxLoc(),getyLoc());
    			//we would like to find something greater than this
    			double curMax=curPheromone+field.getParameterSet().checkParameter("PheromoneDecay");
    			//This is the lower value that is on the downward gradient
    			double downPheromone = curPheromone-field.getParameterSet().checkParameter("PheromoneDecay");
    			
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
				xLoc = x;
				yLoc = y;
				curMax=field.getPheromoneAt(getFaction(),x,y);
			}
			else if(field.getPheromoneAt(getFaction(),x,y)==downPheromone)
			{
				xLoc = x;
				yLoc = y;
			}
		}
    	return curMax;
    }

	public boolean getKillme() {
		return killme;
	}

        public int getFaction() {
		return faction;
	}
        
        public int getxLoc() {
		return xLoc;
	}

        public int getyLoc() {
		return yLoc;
	}

        //for JUnit testing
	public void setKillme(boolean killme) {
		this.killme = killme;
	}

        //for JUnit testing
	public boolean getHasFood() {
		return hasFood;
	}

        //for JUnit testing
	public void setHasFood(boolean hasFood) {
		this.hasFood = hasFood;
	}

        //for JUnit testing
	public int getFramesSinceAte() {
		return framesSinceAte;
	}

        //for JUnit testing
	public void setFramesSinceAte(int framesSinceAte) {
		this.framesSinceAte = framesSinceAte;
	}

        //for JUnit testing
	public int getFramesToLive() {
		return framesToLive;
	}

        //for JUnit testing
	public void setFramesToLive(int framesToLive) {
		this.framesToLive = framesToLive;
	}
        
        //wrappers for private functions, used for JUnit testing
        public void jUnitTesttakeFood(Foodpile p) { takeFood(p); }
        public void jUnitTesteatFood(Colony c) { eatFood(c); }
        public void jUnitTestgiveFood(Colony c) { giveFood(c); }
        public void jUnitTestwander() { wander(); }
}
