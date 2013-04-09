package antsimulation.model;

public class Foodpile {
    private int foodCount;
    public int xLoc, yLoc; //Location
    private Field field;

    public Foodpile(Field f) {
        field = f;
    }
    
    public void decrement() {
    	foodCount--;
    	if(foodCount==0)
    	{
    		field.foodpiles.remove(this);
    	}
    }
}
