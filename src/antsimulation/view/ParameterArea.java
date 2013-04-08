package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class ParameterArea extends JPanel {
    private boolean editable;
    //private ParameterSet activeParameterSet;
        //we will probably use a GUI rather than the OBJECT ParameterSet.  We will see.
    
    public ParameterArea(ActionListener listener) {
        add(new JButton("Parameters"));
        setPreferredSize(new Dimension(150, 300));
        setBackground(Color.BLUE);
        
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL);
        speedSlider.addChangeListener((ChangeListener)listener);
        add(speedSlider);
    }

    public void resetParameters(antsimulation.ParameterSet s) {}
    public void adjustParameter(String name, double val, boolean edit) {}
    public antsimulation.ParameterSet getParameterSet() { return new antsimulation.ParameterSet(); }
    public void lock() { editable = false; }
    public void unlock() { editable = true; }
}
