package antsimulation.model;

public class Colony {
    private int framesSinceAntBorn;
    private int faction;
    private int xLoc, yLoc; //Location
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
    	if(framesSinceAntBorn==10 && getFoodCount() >10)
    	{
    		setFoodCount(getFoodCount() - 10);
    		birthAnt();
    		framesSinceAntBorn=0;//This maybe should be tied to a parameter
    	}
    	else
    	{
    		++framesSinceAntBorn;
    	}
    	
    	
    }
    private void birthAnt() {
    	field.ants.add(new Ant (getFaction(), getxLoc(), getyLoc(), (int) field.parameters.checkParameter("AntLifetime"), field));
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
	public int getFoodCount() {
		return foodCount;
	}
	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
}
