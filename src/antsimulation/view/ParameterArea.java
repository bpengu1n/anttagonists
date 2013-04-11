package antsimulation.view;
import antsimulation.ParameterSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;

public class ParameterArea extends JPanel {
    private boolean editable;
    private JSlider AntCuriosity, AntStarvation, AntHunger, AntLifetime, ReproductionRate;
    //Note: ParameterSet activeParameterSet is represented by JSliders, rather than an actual object
    
    public ParameterArea(ActionListener listener) {
        setPreferredSize(new Dimension(150, 300));
        setBackground(Color.GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED));

        JLabel curiosity = new JLabel("Ant Curiosity");
        add(curiosity);
        AntCuriosity = new JSlider(JSlider.HORIZONTAL);
        AntCuriosity.setPreferredSize(new Dimension(140,20));
        add(AntCuriosity);
        
        
        JLabel starvation = new JLabel("Ant Starvation");
        add(starvation);
        AntStarvation = new JSlider(JSlider.HORIZONTAL);
        AntStarvation.setPreferredSize(new Dimension(140,20));
        add(AntStarvation);
        
        
        JLabel hunger = new JLabel("Ant Hunger");
        add(hunger);
        AntHunger = new JSlider(JSlider.HORIZONTAL);
        AntHunger.setPreferredSize(new Dimension(140,20));
        add(AntHunger);
        
        
        JLabel lifetime = new JLabel("Ant Lifetime");
        add(lifetime);
        AntLifetime = new JSlider(JSlider.HORIZONTAL);
        AntLifetime.setPreferredSize(new Dimension(140,20));
        add(AntLifetime);
        
        
        JLabel reproduction = new JLabel("Reproduction Rate");
        add(reproduction);
        ReproductionRate = new JSlider(JSlider.HORIZONTAL);
        ReproductionRate.setPreferredSize(new Dimension(140,20));
        add(ReproductionRate);
        
    }

    public void resetParameters(ParameterSet s)
    {
        AntCuriosity.setValue((int)(s.checkParameter("AntCuriosity")*100/20));
        AntStarvation.setValue((int)(s.checkParameter("AntStarvation")*100/20));
        AntHunger.setValue((int)(s.checkParameter("AntHunger")*100/20));
        AntLifetime.setValue((int)(s.checkParameter("AntLifetime")*100/20));
        ReproductionRate.setValue((int)(s.checkParameter("ReproductionRate")*100/20));
        ////handle disabling of parameters here
        AntCuriosity.setEnabled(s.checkForEditable("AntCuriosity"));
        AntStarvation.setEnabled(s.checkForEditable("AntStarvation"));
        AntHunger.setEnabled(s.checkForEditable("AntHunger"));
        AntLifetime.setEnabled(s.checkForEditable("AntLifetime"));
        ReproductionRate.setEnabled(s.checkForEditable("ReproductionRate"));
    }

    public ParameterSet getParameterSet() {
        ParameterSet p = new ParameterSet();
        p.adjustParameter("AntCuriosity", AntCuriosity.getValue()*20/100, false);
        p.adjustParameter("AntStarvation", AntStarvation.getValue()*20/100, false);
        p.adjustParameter("AntHunger", AntHunger.getValue()*20/100, false);
        p.adjustParameter("AntLifetime", AntLifetime.getValue()*20/100, false);
        p.adjustParameter("ReproductionRate", ReproductionRate.getValue()*20/100, false);
        return p;
    }

    public void lock() { editable = false; }
    public void unlock() { editable = true; }
}
