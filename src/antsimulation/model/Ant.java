package antsimulation.model;

public class Ant {
    public int xLoc, yLoc; //Location
    private boolean hasFood;
    private int framesSinceAte, framesToLive;
    private Field.Dir dir;
    public int faction;
    
    public Ant(int theFaction, int theX, int theY, int framesofLife){
        faction=theFaction;
    	xLoc=theX;
    	yLoc=theY;
    	framesToLive=framesofLife;
    	hasFood=false;
    	framesSinceAte=0;
    }
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
