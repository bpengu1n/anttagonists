package antsimulation.view;
import javax.swing.*;
import java.awt.*;

public class SimulationDisplay extends JPanel {
    private boolean hudVisible, numbersVisible;
    private HUD hud;
    
    public SimulationDisplay() {
        add(new JButton("Display"));
        setPreferredSize(new Dimension(250, 300));
        setBackground(Color.GREEN);
    }
    
    public void update(antsimulation.model.Simulation s) {}
}
