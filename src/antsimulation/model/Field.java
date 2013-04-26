package antsimulation.model;

import java.util.*;

public class Field extends java.util.Observable {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    private antsimulation.ParameterSet parameters;
    private int width, height;
    private java.util.List<Ant> ants;
    private java.util.List<Foodpile> foodpiles;
    private java.util.List<Colony> colonies;
    private double[][][] pheromones;    //pheromones[faction][x][y]
    private boolean changed = false;    //used for JUnit testing
    private Random generator;
    
    public Field(antsimulation.ParameterSet thisParams) {
        parameters = thisParams;
        ants = new ArrayList<Ant>();
        foodpiles = new ArrayList<Foodpile>();
        colonies = new ArrayList<Colony>();
        generator = new Random();
        width = (int)parameters.checkParameter("xSize");
        height = (int)parameters.checkParameter("ySize");
        initialize();
        //create our pheromone array
        pheromones= new double[(int)parameters.checkParameter("MaxColonies")][getWidth()][getHeight()];
    }
    
    public void initialize() {
        // Loops to create colonies and ants
        for (int x = 0; x < parameters.checkParameter("MaxColonies"); x++) {
            colonies.add(new Colony(x, generator.nextInt(width), generator.nextInt(height), this));
            for (int y = 0; y < parameters.checkParameter("StartAntsPerColony"); y++) {
                ants.add(new Ant(x, generator.nextInt(width), generator.nextInt(height), (int)(parameters.checkParameter("AntLifetime")),this));
            }
        }
        
        // Loop to create foodpiles
        for (int x = 0; x < parameters.checkParameter("StartFoodPiles"); x++) {
            foodpiles.add(new Foodpile(generator.nextInt(getWidth()), generator.nextInt(getHeight()), (int)parameters.checkParameter("StartFoodPileSize"), this));
        }
    }
    
    public void update() {
        changed = false;
        //Calling our decayPheromones decays all of the pheromones left in the field (if any)
        decayPheromones();
        // Iterate through all lists, call .update() on each
        for(int i=0; i<ants.size();++i)
        {
        	ants.get(i).update();
                changed = true;
        }
        //update colonies
        for(int i=0; i<colonies.size();++i) {
            colonies.get(i).update();
            changed = true;
        }
        //clean up dead ants and foodpiles
        for (int j=ants.size()-1; j>=0; j--) {
            if (ants.get(j).getKillme())
                ants.remove(j);
        }
        for (int j=foodpiles.size()-1; j>=0; j--) {
            if (foodpiles.get(j).getKillme())
                foodpiles.remove(j);
        }
        //wrapup
        setChanged();   //inherited from Observable
        notifyObservers();  // Sets hasChanged() to false
    }
    
    public double getPheromoneAt(int faction, int x, int y) 
    {
    	try {
    		return pheromones[faction][x][y];
         } catch(ArrayIndexOutOfBoundsException e) {
            return -1;
         }
    	 
	}

    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    
    private void decayPheromones() { 
    	for(int i=0;i<parameters.checkParameter("MaxColonies");++i){
    		for(int j=0;j<getWidth();++j){
    			for(int k=0; k<getHeight();++k){
    				//we do this just to make sure and keep the pheromones from becoming negative
    				if(pheromones[i][j][k]<=0)
    				{
    					pheromones[i][j][k]=0;
    				}
    				else
    				{
    					pheromones[i][j][k]-=parameters.checkParameter("PheromoneDecay");
    					//if the pheromones at this location are now negative or zero, set them to zero
    					if(pheromones[i][j][k]<=0)
        				{
        					pheromones[i][j][k]=0;
        				}
    				}
    			}
    		}
    	}
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
   	return width;
    }
    
    public boolean getChanged() {
	return changed;
    }
    
    public antsimulation.ParameterSet getParameterSet() {
        return parameters;
    }
    
    public List<Ant> getAntList() {
        return ants;
    }
    
    public List<Foodpile> getFoodpileList() {
        return foodpiles;
    }
    
    public int getNumOfColonies() {
        return colonies.size();
    }
    
    public Colony getColony(int faction) {
        if (faction < colonies.size())
            return colonies.get(faction);
        return null;
    }
}
