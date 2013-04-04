package antsimulation.view;

public class ParameterArea {
    private boolean editable;
    //private ParameterSet activeParameterSet;
        //we will probably use a GUI rather than the OBJECT ParameterSet.  We will see.
    
    public void resetParameters(antsimulation.ParameterSet s) {}
    public void adjustParameter(String name, double val, boolean edit) {}
    public antsimulation.ParameterSet getParameterSet() { return new antsimulation.ParameterSet(); }
    public void lock() { editable = false; }
    public void unlock() { editable = true; }
}
