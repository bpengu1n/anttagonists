package antsimulation.model;

import java.util.*;

public class Field extends java.util.Observable {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    private int width, height;
    public java.util.List<Ant> ants;
    public java.util.List<Foodpile> foodpiles;
    public java.util.List<Colony> colonies;
    private double[][][] pheromones;    //pheromones[faction][x][y]
    public antsimulation.ParameterSet parameters;
    private boolean changed = false;;
    private Random generator;
    
    public Field(antsimulation.ParameterSet thisParams) {
        parameters = thisParams;
        ants = new ArrayList<Ant>();
        foodpiles = new ArrayList<Foodpile>();
        colonies = new ArrayList<Colony>();
        generator = new Random();
        setWidth((int)parameters.checkParameter("xSize"));
        setHeight((int)parameters.checkParameter("ySize"));
        initialize();
        //create our pheromone array
        pheromones= new double[(int)parameters.checkParameter("MaxColonies")][getWidth()][getHeight()];
    }
    
    public void initialize() {
        // Loops to create colonies and ants
        for (int x = 0; x < parameters.checkParameter("MaxColonies"); x++) {
            colonies.add(new Colony(x, generator.nextInt(getWidth()), generator.nextInt(getHeight()), this));
            for (int y = 0; y < parameters.checkParameter("StartAntsPerColony"); y++) {
                ants.add(new Ant(x, generator.nextInt(getWidth()), generator.nextInt(getHeight()), (int)(parameters.checkParameter("AntLifetime")),this));
            }
        }
        
        // Loop to create foodpiles
        for (int x = 0; x < parameters.checkParameter("StartFoodPiles"); x++) {
            foodpiles.add(new Foodpile(generator.nextInt(getWidth()), generator.nextInt(getHeight()), (int)parameters.checkParameter("StartFoodPileSize"), this));
        }
        
        // Assuming predators will be initialized randomly during simulation as well
    }
    
    public void update() {
        setChanged(false);
        //Calling our decayPheromones decays all of the pheromones left in the field (if any)
        decayPheromones();
        //Iterator<Ant> antIter = ants.listIterator();
        //Iterator<Predator> predatorIter = predators.listIterator();
        Iterator<Colony> colonyIter = colonies.listIterator();
        
        // Iterate through all lists, call .update() on each
        for(int i=0; i<ants.size();++i)
        {
        	Ant nextAnt=ants.get(i);
        	nextAnt.update();
                setChanged(true);
        	if(nextAnt.getKillme())
    		{
        		if(i+1 <ants.size())
        		{
        			Ant findNext = ants.get(i+1);
        			ants.remove(nextAnt);
        			i = ants.indexOf(findNext)-1;
        		}
        		else
        			ants.remove(nextAnt);
    		}
        }
        while(colonyIter.hasNext()) {
            Colony nextColony = colonyIter.next();
            nextColony.update();
            setChanged(true);
        }
        
        //if (ants.size() != 0)
        //   ants.remove(0);

        setChanged();
        notifyObservers();  // Sets hasChanged() to false
    }
    

    public double getPheromoneAt(int faction, int x, int y) { return pheromones[faction][x][y]; }
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

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean getChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
}
