package antsimulation.view;
import antsimulation.ParameterSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class View extends JPanel implements java.util.Observer, ActionListener, ItemListener, ChangeListener {
    private boolean simulationRunning;
    private antsimulation.ParameterSet scenario;
    private ParameterArea parameterArea;
    private ControlArea controlArea;
    private SimulationDisplay displayArea;
    private javax.swing.Timer timer;
    private antsimulation.Controller controller;
    private JLabel statusLabel;
    private JMenuItem saveMI, loadMI, exitMI;

    public View(antsimulation.Controller c, boolean isInBrowser) {
        controller = c;
        controlArea = new ControlArea(this);
        parameterArea = new ParameterArea(this);
        displayArea = new SimulationDisplay();
        timer = new javax.swing.Timer(1000, this);
        scenario = new ParameterSet();
        statusLabel = new JLabel("status");
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
        add(statusLabel, BorderLayout.SOUTH);
        //finalize
        parameterArea.resetParameters(scenario);
        if (isInBrowser)
            exitMI.setEnabled(false);
    }

    public void setStatus(String s) {
        statusLabel.setText(s);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitMI)
            System.exit(0);
        if (e.getSource() == controlArea.startButton) {
            ParameterSet p = parameterArea.getParameterSet();
            controller.startSimulation(p);
            parameterArea.lock();
            timer.start();
        }
        if (e.getSource() == controlArea.stopButton) {
            timer.stop();
            controller.stopSimulation();
            displayArea.clearImage();
            parameterArea.unlock();
        }
        if (e.getSource() == controlArea.pauseButton) {
            timer.stop();
        }
        if (e.getSource() == controlArea.resumeButton) {
            timer.start();
        }
        if (e.getSource() == controlArea.generateOutput) {
            controller.generateOutputFile("Output.txt");
            System.out.println("Output");
        }
        if (e.getSource() == controlArea.loadScenarioButton || e.getSource() == loadMI) {
            //ask which file to load, and load it
            System.out.println("Load");
        }
        if (e.getSource() == controlArea.resetParametersButton) {
            parameterArea.resetParameters(scenario);
            System.out.println("Restart");
        }
        if (e.getSource() == timer) {
            controller.updateSimulation();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        System.out.println("Item State Changed");
        controller.updateSimulation();
    }
    
    public void stateChanged(ChangeEvent e) {
        System.out.println("Slider Moved.");
        //we will make speed changes here
    }
    
    public void update(java.util.Observable o, Object arg) {
        displayArea.update(o);
    }
    
    private void loadScenario(String filename) {}
    private void resetParameterArea() {}
    private void adjustRate(double speed) {}
    private void pauseSimulation() {}
    private void resumeSimulation() {}    
}
