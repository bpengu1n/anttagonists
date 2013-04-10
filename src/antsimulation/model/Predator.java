package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Predator {
    private double hunger;
    private Field.Dir dir;
    public int xLoc; //Location
	public int yLoc;
    private Field field;
    public boolean killme=false;

    public Predator(int iniHunger, int theX, int theY, Field f) {
        field = f;
    	hunger=iniHunger;
    	xLoc=theX;
    	yLoc=theY;
    	dir=Field.Dir.UP;
    }
    
    //update causes the Predator to wander and then eat all ants within its grid
    public void update() {
    	wander();
    	for(Iterator<Ant> i = field.ants.iterator(); i.hasNext(); ) {
    		if(hunger==0)
			{
				leave();
				break;
			}
    		Ant nextAnt = i.next();
			if(nextAnt.xLoc ==xLoc && nextAnt.yLoc ==yLoc)
			{
				eatAnt(nextAnt);
			}
			
		}
    }
    
    //predator reduces hunger and destroys the ant
    private void eatAnt(Ant ant) {
    	--hunger;
    	ant.die();
    }
    private void leave() {
    	//This will find nearest edge and head that direction
    	//probably will need a leaving boolean implemented
    	//when it has reached the edge it will need the following:
    	killme=true;
    }
    
    //Wander randomly moves by adding a number from -2 to 2 to the location of the predator
    //It also sets the direction first based off of its x movement and then the y
    private void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(5)-2;
    	int yRand=generator.nextInt(5)-2;
    	xLoc+=xRand;
    	yLoc+=yRand;
    	if(xRand>0) {
    		dir=Field.Dir.RIGHT;
    	} else if(xRand<0) {
    		dir=Field.Dir.LEFT;
    	} else {
    		if(yRand>0)
                    dir=Field.Dir.UP;
    		else
                    dir=Field.Dir.DOWN;
    	}
    }
}
