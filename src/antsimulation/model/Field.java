package antsimulation.model;

import java.util.*;

public class Field {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    public int width, height;
    public java.util.List<Ant> ants;
    public java.util.List<Predator> predators;
    public java.util.List<Foodpile> foodpiles;
    public java.util.List<Colony> colonies;
    public double[][][] pheromones;    //pheromones[faction][x][y]
    public antsimulation.ParameterSet parameters;
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
        	if(nextPred.killme)
    		{
        		if(i+1 <predators.size())
        		{
        			Predator findNext = predators.get(i+1);
        			predators.remove(nextPred);
        			i = predators.indexOf(findNext)-1;
        		}
        		else
        			ants.remove(nextPred);
    		}
        }
        while(colonyIter.hasNext()) {
            Colony nextColony = colonyIter.next();
            nextColony.update();
        }
        
        //if (ants.size() != 0)
        //   ants.remove(0);
    }
    
    public void spawnPredator() {
        predators.add(new Predator((int)parameters.checkParameter("PredatorHunger"), generator.nextInt(width), generator.nextInt(height), this));
    }
    
    public double getPheromoneAt(int faction, int x, int y) { return 1; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    public void decayParameters() {}
}
