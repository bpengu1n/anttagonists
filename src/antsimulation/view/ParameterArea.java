package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;

public class ParameterArea extends JPanel {
    private boolean editable;
    //Note: ParameterSet activeParameterSet is represented by JSliders, rather than an actual object
    
    public ParameterArea(ActionListener listener) {
        setPreferredSize(new Dimension(150, 300));
        setBackground(Color.GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        
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
