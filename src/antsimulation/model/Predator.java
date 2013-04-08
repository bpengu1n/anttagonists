package antsimulation.model;

public class Predator {
    private double hunger;
    private Field.Dir dir;
    private int xLoc, yLoc; //Location
    private Field field;

    public Predator(Field f) {
        field = f;
    }
    
    public void update() {}
    private void eatAnt(Ant ant) {}
    private void leave() {}
    private void wander() {}
}
