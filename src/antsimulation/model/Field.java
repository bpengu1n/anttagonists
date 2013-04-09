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
        initialize();
    }
    public void initialize() {
        generator = new Random();
        int xBound = parameters.checkParameter("xSize");
        int yBound = parameters.checkParameter("ySize");
        
        // Loops to create colonies and ants
        for (int x = 0; x < parameters.checkParameter("MaxColonies"); x++) {
            colonies.push(new Colony(x, generator.nextInt(xBound), generator.nextInt(yBound)));
            for (int y = 0; y < parameters.checkParameter("StartAntsPerColony"); y++) {
                ants.push(new Ant(x, generator.nextInt(xBound), generator.nextInt(yBound), parameters.checkParameter("AntLifetime")));
            }
        }
        
        // Loop to create foodpiles
        for (int x = 0; x < parameters.checkParameter("StartFoodPiles"); x++) {
            foodpiles.push(new Foodpile(generator.nextInt(xBound), generator.nextInt(yBound)));
        }
        
        // Loop to create predators
        for int x = 0; x < parameters.checkParameter("StartPredators"); x++) {
            spawnPredator();
        }
        // Assuming predators will be initialized randomly during simulation as well
    }
    public void update() {
        Iterater antIter        = ants.listIterator();
        Iterater predatorIter   = predators.listIterator();
        Iterator colonyIter     = colonies.listIterator();
        
        // Iterate through all lists, call .update() on each object        
        while (antIter.hasNext()) {
            Ant nextAnt = antIter.next();
            nextAnt.update();
        }
        while (predatorIter.hasNext()) {
            Predator nextPredator = predatorIter.next();
            nextPredator.update();
        }
        while(colonyIter.hasNext()) {
            Colony nextColony = colonyIter.next();
            nextColony.update();
        }
        
    }
    public void spawnPredator() {
        predators.push(new Predator(parameters.checkParameters("PredatorHunger"), generator.nextInt(xBound), generator.nextInt(yBound)));
    }
    public double getPheromoneAt(int faction, int x, int y) { return 1; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    public void decayParameters() {}
}
