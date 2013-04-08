package antsimulation.model;

public class Field {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    private int width, height;
    public java.util.List<Ant> ants;
    public java.util.List<Predator> predators;
    public java.util.List<Foodpile> foodpiles;
    public Colony[] colonies;
    private double[][][] pheromones;    //pheromones[faction][x][y]
    
    public void initialize() {}
    public void spawnPredator() {}
    public double getPheromoneAt(int faction, int x, int y) { return 1; }
    public void setPheromoneAt(int faction, int x, int y, double value) { pheromones[faction][x][y] = value; }
    public void decayParameters() {}
}
