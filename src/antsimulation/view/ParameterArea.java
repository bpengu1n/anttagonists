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

        JLabel curiosity = new JLabel("Ant Curiosity");
        add(curiosity);
        JSlider AntCuriosity = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.addChangeListener((ChangeListener)listener);
        add(AntCuriosity);
        
        
        JLabel starvation = new JLabel("Ant Starvation");
        add(starvation);
        JSlider AntStarvation = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.addChangeListener((ChangeListener)listener);
        add(AntStarvation);
        
        
        JLabel hunger = new JLabel("Ant Hunger");
        add(hunger);
        JSlider AntHunger = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.addChangeListener((ChangeListener)listener);
        add(AntHunger);
        
        
        JLabel lifetime = new JLabel("Ant Lifetime");
        add(lifetime);
        JSlider AntLifetime = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.addChangeListener((ChangeListener)listener);
        add(AntLifetime);
        
        
        JLabel reproduction = new JLabel("Reproduction Rate");
        add(reproduction);
        JSlider ReproductionRate = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.addChangeListener((ChangeListener)listener);
        add(ReproductionRate);
        
    }

    public void resetParameters(antsimulation.ParameterSet s)
    {
        
    }
    public void adjustParameter(String name, double val, boolean edit) {}
    public antsimulation.ParameterSet getParameterSet()
    {
        return new antsimulation.ParameterSet();
    }
    public void lock() { editable = false; }
    public void unlock() { editable = true; }
}
