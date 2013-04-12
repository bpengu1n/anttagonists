package antsimulation.view;

import antsimulation.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class SimulationDisplay extends JPanel {
    private static int PREFERREDSIZE = 600;
    
    private boolean hudVisible, numbersVisible;
    private HUD hud;
    private BufferedImage dispImage;
    protected antsimulation.model.Field field;

    public SimulationDisplay() {
        setPreferredSize(new Dimension(PREFERREDSIZE, PREFERREDSIZE));
        setBackground(Color.GREEN);
        
        hud = new HUD();
        hudVisible = true;
    }
    
    public void clearImage() {
        dispImage = null;
        repaint();
    }
    
    public void update(java.util.Observable o) {
        field = (antsimulation.model.Field)o;
        if (dispImage == null)
            dispImage = new BufferedImage(PREFERREDSIZE,PREFERREDSIZE, BufferedImage.TYPE_INT_RGB);
        Graphics g = dispImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0, dispImage.getWidth(), dispImage.getHeight());
        //begin drawing
        int unitWidth = dispImage.getWidth() / field.width;
        int unitHeight = dispImage.getHeight() / field.width;
        int unitSize = (unitWidth<unitHeight) ? unitWidth : unitHeight;
        drawColonies(g, unitSize);
        drawAnts(g, unitSize);
        drawFoodpiles(g, unitSize);
        drawPredators(g, unitSize);
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dispImage != null) {
            g.drawImage(dispImage, 0,0, PREFERREDSIZE,PREFERREDSIZE, this);
            if(hudVisible)
            	hud.paint(g, field);
        }
    }

    private void drawColonies(Graphics g, int unitSize) {
        int x, y, colonySize = 15;
        Colony colony;
        for(int i = 0; i < field.colonies.size(); i++)
        {
                colony = field.colonies.get(i);

                x = colony.xLoc * unitSize;;
                y = colony.yLoc * unitSize;;
                g.setColor(Color.orange);

                g.fillArc(x-(colonySize/2), y-(colonySize), colonySize, colonySize*2, 0, 180);
        }
    }

    private void drawAnts(Graphics g, int unitSize) {
        int x, y, antSize=6;
        for(int i = 0; i < field.ants.size(); i++)
        {
                x = field.ants.get(i).xLoc * unitSize;
                y = field.ants.get(i).yLoc * unitSize;

                switch(field.ants.get(i).faction)
                {
                case 1:
                        g.setColor(Color.blue);
                        break;
                case 2: 
                        g.setColor(Color.red);
                        break;
                case 3:
                        g.setColor(Color.yellow);
                        break;
                case 4:
                        g.setColor(Color.pink);
                        break;
                default:
                        g.setColor(Color.magenta);
                        break;
                }
                g.fillRect(x-(antSize/2), y-(antSize), antSize, antSize);
        }
    }

    private void drawFoodpiles(Graphics g, int unitSize) {
        int x, y, pileSize;
        Foodpile foodPile;
        for(int i = 0; i < field.foodpiles.size(); i++)
        {
                foodPile = field.foodpiles.get(i);

                x = foodPile.xLoc * unitSize;;
                y = foodPile.yLoc * unitSize;;
                g.setColor(Color.green);
                pileSize = foodPile.foodCount;

                g.fillOval(x-(pileSize/2), y-(pileSize), pileSize, pileSize);
        }
    }

    private void drawPredators(Graphics g, int unitSize) {
        int x, y, predatorSize = 8;
        Predator predator;
        for(int i = 0; i < field.predators.size(); i++)
        {
                predator = field.predators.get(i);

                x = predator.xLoc * unitSize;;
                y = predator.yLoc * unitSize;;
                g.setColor(Color.black);

                g.fillRect(x-(predatorSize/2), y-(predatorSize), predatorSize, predatorSize);
        }
    }
}
