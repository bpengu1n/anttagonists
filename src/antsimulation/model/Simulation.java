package antsimulation.model;

public class Simulation {
    private int elapsedFrames;
    private antsimulation.ParameterSet parameters;
    private Field field;

    public Simulation (antsimulation.ParameterSet p) {}
    public void update() {}
    public boolean generateOutputFile(String filename) { return true; }
}
