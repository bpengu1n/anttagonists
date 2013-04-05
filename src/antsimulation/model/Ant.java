package antsimulation.model;

public class Ant {
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    private Field.Dir dir;
    private int xLoc, yLoc; //Location
    private int faction;
    
    public void update() {}
    public void die() {}
    private void takeFood(Foodpile pile) {}
    private void eatFood(Colony home) {}
    private void giveFood(Colony home) {}
    private void wander() {}
    private void flee() {}
    private void layPheromone() {}
    private void followPheromones() {}
}
