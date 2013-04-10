package antsimulation.model;

public class Simulation {
    private int elapsedFrames;
    private antsimulation.view.View view;
    private Field field;

    public Simulation (antsimulation.ParameterSet p, antsimulation.view.View v) {
        field = new Field(p);
        view = v;
        field.addObserver(view);
        view.update(field,null);
    }

    public void update() {
        field.update();
    }

    public boolean generateOutputFile(String filename) {
        return true;
    }
}
