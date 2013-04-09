package antsimulation.model;

public class Field {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    public int width, height;
    public java.util.List<Ant> ants;
    public java.util.List<Predator> predators;
    public java.util.List<Foodpile> foodpiles;
    public Colony[] colonies;
    public double[][][] pheromones;    //pheromones[faction][x][y]
    public antsimulation.ParameterSet parameters;
    
    public void initialize() {
        // Initialize colonies, ants, predators, foodpiles, randomized by width, height
        // TODO - create skeleton of constructor for each object (ant, colony, etc.)
        // Set parameters, e.g. ants time to die,
    }
    public void update() {
        // Iterate through all lists, call .update() on each object
    }
    public void spawnPredator() {}
    public double getPheromoneAt(int faction, int x, int y) { return 1; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    public void decayParameters() {}
}
