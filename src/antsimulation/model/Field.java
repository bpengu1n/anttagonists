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
        // Initialize ants, predators, foodpiles, and colonies, randomized by width, height
        // Set parameters, e.g. ants time to die,
    }
    public void spawnPredator() {}
    public double getPheromoneAt(int faction, int x, int y) { return 1; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    public void decayParameters() {}
}
