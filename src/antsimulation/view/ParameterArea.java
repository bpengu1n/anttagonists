package antsimulation.view;
import antsimulation.ParameterSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;

public class ParameterArea extends JPanel {
    private final int LABELWIDTH = 115;
    private final int CONTROLWIDTH = 60;
    private final int PREFERREDHEIGHT = 500;
    private View master;
    //our value-storing elements
    private JSlider[] sliders;
    private boolean[] slidersEditable;
    private JTextField[] boxes;
    private boolean[] boxesEditable;

    private final String[] SLIDERNAMES = {
	"AntCuriosity", "ReproductionRate"
    };
    private final String[] BOXNAMES = {
        "Size", "MaxColonies", "StartAntsPerColony", "StartFoodPiles",
        "StartFoodPileSize", "PheromoneStrength", "PheromoneDecay", "AntStarvation",
        "AntHunger", "AntLifetime", "ReproductionCost"
    };
    
    public ParameterArea(View mstr) {
        master = mstr;
        setPreferredSize(new Dimension(LABELWIDTH+CONTROLWIDTH+15, PREFERREDHEIGHT));
        setBackground(Color.GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        
        sliders = new JSlider[SLIDERNAMES.length];
        slidersEditable = new boolean[SLIDERNAMES.length];
        boxes = new JTextField[BOXNAMES.length];
        boxesEditable = new boolean[BOXNAMES.length];

        JLabel label = new JLabel("Parameters");
        label.setPreferredSize(new Dimension(LABELWIDTH+CONTROLWIDTH,25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN,20));
        add(label);
        
        for (int i=0; i<SLIDERNAMES.length; i++) {
            label = new JLabel(SLIDERNAMES[i]);
            label.setPreferredSize(new Dimension(LABELWIDTH,16));
            sliders[i] = new JSlider(JSlider.HORIZONTAL);
            sliders[i].setPreferredSize(new Dimension(CONTROLWIDTH,20));
            add(label);
            add(sliders[i]);
            slidersEditable[i] = true;
        }
        for (int i=0; i<BOXNAMES.length; i++) {
            label = new JLabel(BOXNAMES[i]);
            label.setPreferredSize(new Dimension(LABELWIDTH,16));
            boxes[i] = new JTextField();
            boxes[i].setPreferredSize(new Dimension(CONTROLWIDTH,20));
            add(label);
            add(boxes[i]);
            boxesEditable[i] = true;
        }
    }

    public void resetParameters(ParameterSet s) {
        for (int i=0; i<SLIDERNAMES.length; i++) {
            int parameterIndex;
            if (SLIDERNAMES[i]=="Size")   //this is an exception
                parameterIndex = ParameterSet.indexOf("xSize");
            else
                parameterIndex = ParameterSet.indexOf(SLIDERNAMES[i]);
            double min = ParameterSet.MIN_VALS[parameterIndex];
            double max = ParameterSet.MAX_VALS[parameterIndex];
            double val = s.checkParameter(ParameterSet.PARAMETER_NAMES[parameterIndex]);
            double frac = (val-min)/(max-min);
            sliders[i].setValue((int)(100*frac));
            slidersEditable[i] = s.checkForEditable(ParameterSet.PARAMETER_NAMES[parameterIndex]);
        }
        for (int i=0; i<BOXNAMES.length; i++) {
            int parameterIndex;
            if (BOXNAMES[i]=="Size")   //this is an exception
                parameterIndex = ParameterSet.indexOf("xSize");
            else
                parameterIndex = ParameterSet.indexOf(BOXNAMES[i]);
            boolean mustBeInt = ParameterSet.MUSTBEINT[parameterIndex];
            double val = s.checkParameter(ParameterSet.PARAMETER_NAMES[parameterIndex]);
            if (mustBeInt)
                boxes[i].setText(""+(int)val);
            else
                boxes[i].setText(""+val);
            boxesEditable[i] = s.checkForEditable(ParameterSet.PARAMETER_NAMES[parameterIndex]);
        }
        unlock();
    }

    public ParameterSet getParameterSet() {
        ParameterSet p = new ParameterSet();
        for (int i=0; i<SLIDERNAMES.length; i++) {
            int parameterIndex;
            if (SLIDERNAMES[i]=="Size")   //this is an exception
                parameterIndex = ParameterSet.indexOf("xSize");
            else
                parameterIndex = ParameterSet.indexOf(SLIDERNAMES[i]);
            boolean mustBeInt = ParameterSet.MUSTBEINT[parameterIndex];
            double min = ParameterSet.MIN_VALS[parameterIndex];
            double max = ParameterSet.MAX_VALS[parameterIndex];
            double frac = sliders[i].getValue()/100.0;
            double val = frac*(max-min) + min;
            if (mustBeInt)
                val = (int)val;
            p.adjustParameter(ParameterSet.PARAMETER_NAMES[parameterIndex], val, false);
            if (SLIDERNAMES[i]=="Size") //set ySize, too
                p.adjustParameter("ySize", val, false);
        }
        for (int i=0; i<BOXNAMES.length; i++) {
            int parameterIndex;
            if (BOXNAMES[i]=="Size")   //this is an exception
                parameterIndex = ParameterSet.indexOf("xSize");
            else
                parameterIndex = ParameterSet.indexOf(BOXNAMES[i]);
            boolean mustBeInt = ParameterSet.MUSTBEINT[parameterIndex];
            double min = ParameterSet.MIN_VALS[parameterIndex];
            double max = ParameterSet.MAX_VALS[parameterIndex];
            double val;
            try {
                if (mustBeInt)
                    val = Integer.parseInt(boxes[i].getText());
                else
                    val = Double.parseDouble(boxes[i].getText());
            } catch (NumberFormatException e) {
                master.setStatus("Bad Format: "+BOXNAMES[i]+" must be a"+ (mustBeInt ? "n integer." :" number."));
                return null;
            }
            if (val < min || val > max) {
                master.setStatus("Bad Value: "+BOXNAMES[i]+" must be"+(mustBeInt?" an integer":"")+" between "+min+" and "+max+".");
                return null;
            }
            p.adjustParameter(ParameterSet.PARAMETER_NAMES[parameterIndex], val, false);
            if (BOXNAMES[i]=="Size") //set ySize, too
                p.adjustParameter("ySize", val, false);
        }
        return p;
    }

    public void lock() {
        for (int i=0; i<SLIDERNAMES.length; i++)
            sliders[i].setEnabled(false);
        for (int i=0; i<BOXNAMES.length; i++)
            boxes[i].setEnabled(false);
    }

    public void unlock() {
        for (int i=0; i<SLIDERNAMES.length; i++)
            sliders[i].setEnabled(slidersEditable[i]);
        for (int i=0; i<BOXNAMES.length; i++)
            boxes[i].setEnabled(boxesEditable[i]);
    }
}
