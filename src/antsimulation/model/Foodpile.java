package antsimulation.model;

public class Foodpile {
    public int xLoc, yLoc; //Location
    public int foodCount;
    public Field field;
    public boolean killme=false;
    
    public Foodpile(int theX, int theY, int food, Field f) {
        field = f;
        xLoc = theX;
        yLoc = theY;
        foodCount = food;
    }
    
    public void decrement() {
    	foodCount--;
    	if(foodCount==0) {
    		killme=true;
    	}
    }
}
