package antsimulation.model;

public class Field {
    public enum Dir {UP, DOWN, LEFT, RIGHT};
    private int width, height;
    private java.util.List<Ant> ants;
    private java.util.List<Predator> predators;
    private GridUnit locations[][];
    
    public void initialize() {}
    public void spawnPredator() {}
}
