package antsimulation.model;

import java.util.*;

public class Field extends java.util.Observable {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    public int width, height;
    public java.util.List<Ant> ants;
    public java.util.List<Predator> predators;
    public java.util.List<Foodpile> foodpiles;
    public java.util.List<Colony> colonies;
    public double[][][] pheromones;    //pheromones[faction][x][y]
    public antsimulation.ParameterSet parameters;
    public boolean changed = false;;
    private Random generator;
    
    public Field(antsimulation.ParameterSet thisParams) {
        parameters = thisParams;
        ants = new ArrayList<Ant>();
        predators = new ArrayList<Predator>();
        foodpiles = new ArrayList<Foodpile>();
        colonies = new ArrayList<Colony>();
        generator = new Random();
        width = (int)parameters.checkParameter("xSize");
        height = (int)parameters.checkParameter("ySize");
        initialize();
        //create our pheromone array
        pheromones= new double[(int)parameters.checkParameter("MaxColonies")][width+1][height+1];
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
            foodpiles.add(new Foodpile(generator.nextInt(width), generator.nextInt(height), (int)parameters.checkParameter("StartFoodPileSize"), this));
        }
        
        // Loop to create predators
        for (int x = 0; x < (int)parameters.checkParameter("StartPredators"); x++) {
            spawnPredator();
        }
        // Assuming predators will be initialized randomly during simulation as well
    }
    
    public void update() {
        changed = false;
        //Calling our decayPheromones decays all of the pheromones left in the field (if any)
        decayPheromones();
        //Iterator<Ant> antIter = ants.listIterator();
        //Iterator<Predator> predatorIter = predators.listIterator();
        Iterator<Colony> colonyIter = colonies.listIterator();
        
        // Iterate through all lists, call .update() on each object
        /*if(antIter.hasNext()){
        	Ant nextAnt = antIter.next();
        	while (antIter.hasNext()) {
        		nextAnt.update();
        		if(nextAnt.killme)
        		{
        			Ant deadAnt=nextAnt;
        			nextAnt = antIter.next();
        			ants.remove(deadAnt);
        			
        		}
        		else
        			nextAnt = antIter.next();
        	}
    	}*/
        for(int i=0; i<ants.size();++i)
        {
        	Ant nextAnt=ants.get(i);
        	nextAnt.update();
                changed = true;
        	if(nextAnt.killme)
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
        for(int i=0; i<predators.size();++i)
        {
        	Predator nextPred=predators.get(i);
        	nextPred.update();
                changed = true;
        	if(nextPred.killme)
    		{
        		if(i+1 <predators.size())
        		{
        			Predator findNext = predators.get(i+1);
        			predators.remove(nextPred);
        			i = predators.indexOf(findNext)-1;
        		}
        		else
        			predators.remove(nextPred);
    		}
        }
        while(colonyIter.hasNext()) {
            Colony nextColony = colonyIter.next();
            nextColony.update();
            changed = true;
        }
        
        //if (ants.size() != 0)
        //   ants.remove(0);

        setChanged();
        notifyObservers();  // Sets hasChanged() to false
    }
    
    public void spawnPredator() {
        predators.add(new Predator((int)parameters.checkParameter("PredatorHunger"), generator.nextInt(width), generator.nextInt(height), this));
    }

    public double getPheromoneAt(int faction, int x, int y) { return pheromones[faction][x][y]; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    private void decayPheromones() { 
    	for(int i=0;i<parameters.checkParameter("MaxColonies");++i){
    		for(int j=0;j<width;++j){
    			for(int k=0; k<height;++k){
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
}
