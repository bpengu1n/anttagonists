package antsimulation.model;

public class Foodpile {
    private int xLoc, yLoc; //Location
    private int foodCount;
    private Field field;
    private boolean killme=false;
    
    public Foodpile(int theX, int theY, int food, Field f) {
        field = f;
        xLoc = theX;
        yLoc = theY;
        foodCount = food;
    }
    
    public void decrement() {
    	foodCount--;
    	if(getFoodCount()==0)
            killme = true;
    }

    public int getxLoc() {
            return xLoc;
    }

    public int getyLoc() {
            return yLoc;
    }

    public boolean getKillme() {
            return killme;
    }

    public int getFoodCount() {
            return foodCount;
    }

        
    //for JUnit testing
    public Field getField() {
            return field;
    }
}
