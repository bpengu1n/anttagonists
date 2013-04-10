package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import javax.swing.border.BevelBorder;

public class ControlArea extends JPanel {
    public JButton startButton, stopButton, generateOutput, loadScenarioButton, resetParametersButton;
    public JSlider speedSlider;
    
    public ControlArea(java.awt.event.ActionListener listener) {
        add(new JButton("Controls"));
        setPreferredSize(new Dimension(150, 300));
        setBackground(Color.GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED)); 
        
        startButton = new JButton("Start Simulation");
        startButton.addActionListener(listener);
        add(startButton);
        
        stopButton = new JButton("Stop Simulation");
        stopButton.addActionListener(listener);
        add(stopButton);
        
        generateOutput = new JButton("Generate Output Data");
        generateOutput.addActionListener(listener);
        add(generateOutput);
        
        loadScenarioButton = new JButton("Load Scenario");
        loadScenarioButton.addActionListener(listener);
        add(loadScenarioButton);
        
        resetParametersButton = new JButton("Reset Parameters");
        resetParametersButton.addActionListener(listener);
        add(resetParametersButton);
        
        JCheckBox cbox = new JCheckBox("Test Update");
        cbox.addItemListener((ItemListener)listener);
        add(cbox);
    }
}
