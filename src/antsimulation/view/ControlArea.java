package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class ControlArea extends JPanel {
    public JButton startButton, stopButton, generateOutput, loadScenarioButton, resetParametersButton;
    public JSlider speedSlider;
    
    public ControlArea(java.awt.event.ActionListener listener) {
        add(new JButton("Controls"));
        setPreferredSize(new Dimension(150, 300));
        setBackground(Color.RED);
        
        startButton = new JButton("Start Simulation");
        startButton.addActionListener(listener);
        add(startButton);
        
        JCheckBox cbox = new JCheckBox("Test Update");
        cbox.addItemListener((ItemListener)listener);
        add(cbox);
    }
}
