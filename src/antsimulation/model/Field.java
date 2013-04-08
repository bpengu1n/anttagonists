package antsimulation.model;

public class Field {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    private int width, height;
    private java.util.List<Ant> ants;
    private java.util.List<Predator> predators;
    private java.util.List<Foodpile> foodpiles;
    private Colony[] colonies;
    private double[][][] pheromones;    //pheromones[faction][x][y]
    
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
