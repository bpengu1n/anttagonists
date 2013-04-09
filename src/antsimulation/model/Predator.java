package antsimulation.model;

import java.util.Iterator;
import java.util.Random;

public class Predator {
    private double hunger;
    private Field.Dir dir;
    public int xLoc; //Location
	public int yLoc;
    private Field field;

    public Predator(int iniHunger, int theX, int theY, Field f) {
        field = f;
    	hunger=iniHunger;
    	xLoc=theX;
    	yLoc=theY;
    	dir=Field.Dir.UP;
    }
    
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
    private void eatAnt(Ant ant) {
    	--hunger;
    	ant.die();
    }
    private void leave() {
    	//This will find nearest edge and head that direction
    	//probably will need a leaving boolean implemented
    }
    private void wander() {
    	Random generator = new Random();
    	int xRand=generator.nextInt(2)-1;
    	int yRand=generator.nextInt(2)-1;
    	xLoc+=xRand;
    	yLoc+=yRand;
    	if(xRand==1) {
    		dir=Field.Dir.RIGHT;
    	} else if(xRand==-1) {
    		dir=Field.Dir.LEFT;
    	} else {
    		if(yRand==1)
                    dir=Field.Dir.UP;
    		else
                    dir=Field.Dir.DOWN;
    	}
    }
}
