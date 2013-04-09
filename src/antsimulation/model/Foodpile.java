package antsimulation.model;

public class Foodpile {
    private int foodCount;
    public int xLoc, yLoc; //Location

    public Foodpile(int theX, int theY) {
        xLoc = theX;
        yLoc = theY;
    }
    
    public void decrement() {
    	foodCount--;
    	if(foodCount==0)
    	{
    		field.foodpiles.remove(this);
    	}
    }
}
