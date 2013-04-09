package antsimulation.model;

public class Foodpile {
    public int xLoc, yLoc; //Location
    private int foodCount;
    private Field field;

    public Foodpile(int theX, int theY, Field f) {
        field = f;
        xLoc = theX;
        yLoc = theY;
    }
    
    public void decrement() {
    	foodCount--;
    	if(foodCount==0) {
    		field.foodpiles.remove(this);
    	}
    }
}
