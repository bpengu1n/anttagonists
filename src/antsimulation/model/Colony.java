package antsimulation.model;

public class Colony {
    private int framesSinceAntBorn;
    public int faction;
    public int xLoc, yLoc; //Location
    public int foodCount;
    private Field field;

    //initializes the location and faction of the colony
    public Colony(int theFaction, int theX, int theY, Field f)
    {
        field = f;
    	faction=theFaction;
    	xLoc=theX;
    	yLoc=theY;
    	framesSinceAntBorn=10;
    	foodCount=100;
    }
    //update checks to see if conditions are filled for ant birthing
    //it also adjusts the framesSinceAntBorn appropriately
    public void update() {
    	if(framesSinceAntBorn==10 && foodCount >10)
    	{
    		foodCount-=10;
    		birthAnt();
    		framesSinceAntBorn=0;//This maybe should be tied to a parameter
    	}
    	else
    	{
    		++framesSinceAntBorn;
    	}
    	
    	
    }
    private void birthAnt() {
    	field.ants.add(new Ant (faction, xLoc, yLoc, (int) field.parameters.checkParameter("AntLifetime")));
    }
}
