package antsimulation.view;
import java.awt.event.*;
import javax.swing.*;

public class View extends JPanel implements ActionListener, ItemListener {
    private boolean simulationRunning;
    private antsimulation.ParameterSet scenario;
    private ParameterArea parameterArea;
    private ControlArea controlArea;
    private SimulationDisplay displayArea;
    private javax.swing.Timer timer;
    
    
    public void updateSimulationDisplay(antsimulation.model.Simulation s) {}
    
    public void actionPerformed(ActionEvent e) {}
    public void itemStateChanged(ItemEvent e) {}
    
    private void loadScenario(String filename) {}
    private void resetParameterArea() {}
    private void timerFired() {}
    private void adjustRate(double speed) {}
    private void pauseSimulation() {}
    private void resumeSimulation() {}    
}
