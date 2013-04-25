package antsimulation.model;

public class Foodpile {
    private int xLoc, yLoc; //Location
    private int foodCount;
    private Field field;
    private boolean killme=false;
    
    public Foodpile(int theX, int theY, int food, Field f) {
        field = f;
        setxLoc(theX);
        setyLoc(theY);
        setFoodCount(food);
    }
    
    public void decrement() {
    	setFoodCount(getFoodCount() - 1);
    	if(getFoodCount()==0) {
    		setKillme(true);
    	}
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

	public boolean isKillme() {
		return killme;
	}

	public void setKillme(boolean killme) {
		this.killme = killme;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
}
