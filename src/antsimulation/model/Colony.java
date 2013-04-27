package antsimulation.model;

public class Colony {
    private int faction;
    private int xLoc, yLoc; //Location
    private int framesSinceAntBorn;
    private int foodCount;
    private Field field;

    //initializes the location and faction of the colony
    public Colony(int theFaction, int theX, int theY, Field f)
    {
        field = f;
    	setFaction(theFaction);
    	setxLoc(theX);
    	setyLoc(theY);
    	framesSinceAntBorn=0;
    	setFoodCount(100);
    }

    //update checks to see if conditions are filled for ant birthing
    //it also adjusts the framesSinceAntBorn appropriately
    public void update() {
    	if(framesSinceAntBorn==field.getParameterSet().checkParameter("ReproductionRate") && foodCount > field.getParameterSet().checkParameter("ReproductionCost"))
    	{
    		foodCount -= field.getParameterSet().checkParameter("ReproductionCost");
    		birthAnt();
    		framesSinceAntBorn=0;//This maybe should be tied to a parameter
    	}
    	else
    	{
    		++framesSinceAntBorn;
    	}
    	
    	
    }

    private void birthAnt() {
    	field.getAntList().add(new Ant (getFaction(), getxLoc(), getyLoc(), (int) field.getParameterSet().checkParameter("AntLifetime"), field));
    }

    public int getxLoc() {
            return xLoc;
    }

    public int getyLoc() {
            return yLoc;
    }

    public int getFoodCount() {
            return foodCount;
    }
    
    public int getFaction() {
            return faction;
    }


    //JUnit testing functions below here
    
    public void setFoodCount(int foodCount) {
            this.foodCount = foodCount;
    }    
    public void setFaction(int faction) {
        this.faction = faction;
    }
    public void setxLoc(int xLoc) {
            this.xLoc = xLoc;
    }
    public void setyLoc(int yLoc) {
            this.yLoc = yLoc;
    }
}
