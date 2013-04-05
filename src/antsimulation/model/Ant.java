package antsimulation.model;

public class Ant {
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    private Field.Dir dir;
    private int xLoc, yLoc; //Location
    private int faction;
    
    public void takeFood(Foodpile pile) {}
    public void eatFood(Colony home) {}
    public void giveFood(Colony home) {}
    public void wander() {}
    public void die() {}
    public void flee() {}
    public void layPheromone() {}
    public void followPheromone(Pheromone p) {}
}
