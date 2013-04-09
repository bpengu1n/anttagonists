package antsimulation.view;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class SimulationDisplay extends JPanel {
    private boolean hudVisible, numbersVisible;
    private HUD hud;
    private BufferedImage dispImage;

    public SimulationDisplay() {
        setPreferredSize(new Dimension(250, 300));
        setBackground(Color.GREEN);
        
        hud = new HUD();
    }
    
    public void update(antsimulation.model.Field field) {
        if (dispImage == null)
            dispImage = new BufferedImage(250,250, BufferedImage.TYPE_INT_RGB);
        Graphics g = dispImage.getGraphics();

        
        //start Cameron area
        g.setColor(Color.white);
        g.fillRect(0,0, dispImage.getWidth(), dispImage.getHeight());
        g.setColor(Color.ORANGE);
        g.drawLine(0,0,100,150);
        //end Cameron area
        
        hud.update(field);
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dispImage != null) {
            g.drawImage(dispImage, 0,0, 150,150, this);
            hud.paint(g);
        }
    }
}
