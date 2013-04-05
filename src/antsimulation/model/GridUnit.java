package antsimulation.model;

public class GridUnit {
    private Pheromone p[];  //one for each faction
    private Colony colony;  //may be null
    private Foodpile foodpile;  //may be null
    
    public void initialize() {}
    public void spawnPredator() {}
}
