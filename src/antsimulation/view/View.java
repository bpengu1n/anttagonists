package antsimulation.view;
import antsimulation.ParameterSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class View extends JPanel implements java.util.Observer, ActionListener, ChangeListener {
    private int MINMSPERTICK = 10;      //the smaller this is, the better the max speed
    private int MAXMSPERTICK = 1200;    //the bigger this is, the slower the min speed
    
    private boolean simulationRunning, paused;
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
        timer = new javax.swing.Timer(150, this);
        adjustRate(50);
        scenario = new ParameterSet();
        statusLabel = new JLabel("Ready");
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
        if (isInBrowser)
            exitMI.setEnabled(false);
        simulationRunning = false;
        paused = false;
        controlArea.updateEnabling(simulationRunning, paused);
        parameterArea.resetParameters(scenario);
    }

    public void setStatus(String s) {
        statusLabel.setText(s);
    }

    //this is to handle button clicks and timer firing
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitMI)
            System.exit(0);
        if (e.getSource() == controlArea.startStopButton) {
            if (simulationRunning)
                stopSimulation();
            else
                startSimulation();
        }
        if (e.getSource() == controlArea.pauseResumeButton) {
            if (paused)
                resumeSimulation();
            else
                pauseSimulation();
        }
        if (e.getSource() == controlArea.generateOutput) {
            controller.generateOutputFile("Output.txt");
            System.out.println("Output");
        }
        if (e.getSource() == controlArea.loadScenarioButton || e.getSource() == loadMI) {
            //ask which file to load, and load it
            loadScenario("TestFileName.xml");
        }
        if (e.getSource() == controlArea.freeplayButton) {
            //ask which file to load, and load it
            scenario = new ParameterSet();
            parameterArea.resetParameters(scenario);
            setStatus("Loaded \"Freeplay\" scenario.");
        }
        if (e.getSource() == controlArea.resetParametersButton)
            resetParameterArea();
        if (e.getSource() == timer)
            controller.updateSimulation();
        //finish up
        controlArea.updateEnabling(simulationRunning, paused);
    }

    //this is for handling sliders
    public void stateChanged(ChangeEvent e) {
        if (e.getSource()==controlArea.speedSlider)
            adjustRate(controlArea.speedSlider.getValue());
    }
    
    //this is for handling notifications (from an Observable)
    public void update(java.util.Observable o, Object arg) {
        displayArea.update(o);
    }
    
    private void loadScenario(String filename) {
        ////popup window here
        ParameterSet newScenario = ParameterSet.generateFromFile(filename);
        if (newScenario != null) {
            scenario = newScenario;
            parameterArea.resetParameters(scenario);
            setStatus("Loaded scenario \""+filename+"\" from file.");
        } else {
            setStatus("Failed to load scenario file \""+filename+"\".");
        }
    }

    private void resetParameterArea() {
        parameterArea.resetParameters(scenario);
        setStatus("Parameters reset.");
    }

    private void adjustRate(double percent) {
        double minFrequency = 1.0/MAXMSPERTICK;
        double maxFrequency = 1.0/MINMSPERTICK;
        double frequency = (percent/100)*(maxFrequency-minFrequency) + minFrequency;
        timer.setDelay((int)(1/frequency));
    }

    public void startSimulation() {
        ParameterSet p = parameterArea.getParameterSet();
        if (p != null) {
            parameterArea.lock();
            controller.startSimulation(p);
            simulationRunning = true;
            paused = false;
            setStatus("Simulation running");
            timer.start();
        }
    }
    public void stopSimulation() {
        timer.stop();
        controller.stopSimulation();
        displayArea.clearImage();
        simulationRunning = false;
        paused = false;
        parameterArea.unlock();
        setStatus("Simulation stopped.");
    }
    private void pauseSimulation() {
        paused = true;
        setStatus("Simulation paused");
        timer.stop();
    }
    private void resumeSimulation() {
        paused = false;
        setStatus("Simulation running");
        timer.start();
    }
}
