package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.BevelBorder;

public class ControlArea extends JPanel {
    private final int BUTTONWIDTH = 140;
    private final int PREFERREDHEIGHT = 300;
    
    public JButton startStopButton, pauseResumeButton, generateOutput, loadScenarioButton, freeplayButton, resetParametersButton;
    public JSlider speedSlider;
    public JCheckBox showHudCB, showGridCB;
    
    public ControlArea(java.awt.event.ActionListener listener) {
        setPreferredSize(new Dimension(BUTTONWIDTH+10, PREFERREDHEIGHT));
        setBackground(Color.GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED)); 
    
        JLabel label = new JLabel("Controls");
        label.setPreferredSize(new Dimension(BUTTONWIDTH,25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN,20));
        add(label);
        
        startStopButton = new JButton("Start Simulation");
        startStopButton.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        startStopButton.addActionListener(listener);
        add(startStopButton);
        
        pauseResumeButton = new JButton("Pause Simulation");
        pauseResumeButton.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        pauseResumeButton.addActionListener(listener);
        add(pauseResumeButton);
                
        generateOutput = new JButton("Generate Output Data");
        generateOutput.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        generateOutput.addActionListener(listener);
        add(generateOutput);
        
        loadScenarioButton = new JButton("Load Scenario");
        loadScenarioButton.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        loadScenarioButton.addActionListener(listener);
        add(loadScenarioButton);

        
        freeplayButton = new JButton("Freeplay");
        freeplayButton.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        freeplayButton.addActionListener(listener);
        add(freeplayButton);
        
        resetParametersButton = new JButton("Reset Parameters");
        resetParametersButton.setPreferredSize(new Dimension(BUTTONWIDTH, 25));
        resetParametersButton.addActionListener(listener);
        add(resetParametersButton);

        add(new JLabel());
        
        JLabel speedLabel = new JLabel("Speed");
        add(speedLabel);
        speedSlider = new JSlider(JSlider.HORIZONTAL);
        speedSlider.setPreferredSize(new Dimension(140,20));
        speedSlider.addChangeListener((ChangeListener)listener);
        add(speedSlider);
        
        add(new JLabel());
        
        showHudCB = new JCheckBox("Show HUD");
        showHudCB.setSelected(true);
        showHudCB.addActionListener(listener);
        add(showHudCB);
        
        showGridCB = new JCheckBox("Show grid");
        showGridCB.setSelected(false);
        showGridCB.addActionListener(listener);
        add(showGridCB);
    }

    public void updateEnabling(boolean simRunning, boolean paused) {
        startStopButton.setText(simRunning ? "Stop Simulation" : "Start Simulation");
        pauseResumeButton.setText(paused ? "Resume" : "Pause");
        pauseResumeButton.setEnabled(simRunning);
        generateOutput.setEnabled(simRunning && paused);
        loadScenarioButton.setEnabled(!simRunning);
        freeplayButton.setEnabled(!simRunning);
        resetParametersButton.setEnabled(!simRunning);
    }
}
