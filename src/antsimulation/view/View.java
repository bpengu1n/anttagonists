package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JPanel implements ActionListener, ItemListener {
    private boolean simulationRunning;
    private antsimulation.ParameterSet scenario;
    private ParameterArea parameterArea;
    private ControlArea controlArea;
    private SimulationDisplay displayArea;
    private javax.swing.Timer timer;
    private antsimulation.Controller controller;
    private JLabel status;
    private JMenuItem saveMI, loadMI, exitMI;
    
    public View(antsimulation.Controller c) {
        controller = c;
        controlArea = new ControlArea(this);
        parameterArea = new ParameterArea(this);
        displayArea = new SimulationDisplay();
        status = new JLabel("status");
        loadMI = new JMenuItem("Load Scenario");
        loadMI.addActionListener(this);
        saveMI = new JMenuItem("Save Scenario");
        saveMI.addActionListener(this);
        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(this);
        //layout management
        setLayout(new BorderLayout(2,0));
        JMenuBar mbar = new JMenuBar();
        JMenu fileM = new JMenu("File");        
        fileM.add(loadMI);
        fileM.add(saveMI);
        fileM.add(exitMI);
        mbar.add(fileM);
        add(mbar, BorderLayout.NORTH);
        add(controlArea, BorderLayout.WEST);
        add(displayArea, BorderLayout.CENTER);
        add(parameterArea, BorderLayout.EAST);
        add(status, BorderLayout.SOUTH);
        //temporary
        loadMI.setEnabled(false);
        saveMI.setEnabled(false);
    }
    
    public void updateSimulationDisplay(antsimulation.model.Simulation s) {}
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitMI)
            System.exit(0);
        if (e.getSource() == controlArea.startButton)
            System.out.println("Start");
        if (e.getSource() == controlArea.stopButton)
            System.out.println("Stop");
        if (e.getSource() == controlArea.generateOutput)
            System.out.println("Output");
        if (e.getSource() == controlArea.loadScenarioButton || e.getSource() == loadMI)
            System.out.println("Load");
        if (e.getSource() == controlArea.resetParametersButton)
            System.out.println("Restart");
    }
    public void itemStateChanged(ItemEvent e) {}
    
    private void loadScenario(String filename) {}
    private void resetParameterArea() {}
    private void timerFired() {}
    private void adjustRate(double speed) {}
    private void pauseSimulation() {}
    private void resumeSimulation() {}    
}
