package antsimulation.view;

import antsimulation.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class SimulationDisplay extends JPanel {
    private static int PREFERREDSIZE = 500;
    
    private HUD hud;
    private BufferedImage dispImage;
    private antsimulation.model.Field field;
    private boolean hudVisible, gridVisible;

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
        int unitWidth = PREFERREDSIZE / field.getWidth();
        int unitHeight = PREFERREDSIZE / field.getWidth();
        int unitSize = (unitWidth<unitHeight) ? unitWidth : unitHeight;
        if (dispImage == null)
            dispImage = new BufferedImage(unitSize*field.getWidth(),unitSize*field.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = dispImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0, dispImage.getWidth(), dispImage.getHeight());
        //begin drawing
        drawFoodpiles(g, unitSize);
        drawColonies(g, unitSize);
        drawAnts(g, unitSize);
        drawPheremones(g, unitSize);
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (dispImage != null) {
            int offsetX = (PREFERREDSIZE-dispImage.getWidth())/2;
            int offsetY = (PREFERREDSIZE-dispImage.getHeight())/2;
            g.drawImage(dispImage, offsetX,offsetY, dispImage.getWidth(),dispImage.getHeight(), this);
            if(gridVisible)
                paintGrid(g, field, offsetX,offsetY);
            if(hudVisible)
                hud.paint(g, field);
        }
    }
    
    private void paintGrid(Graphics g, Field field, int offsetX, int offsetY) 
    {
        int unitWidth = dispImage.getWidth() / field.getWidth();
        int unitHeight = dispImage.getHeight() / field.getWidth();
        int unitSize = (unitWidth<unitHeight) ? unitWidth : unitHeight;
        for(int i = 0; i <= field.getHeight() ; i++)
        {
            g.drawLine(
                    offsetX + unitSize*i,
                    offsetY, 
                    offsetX + unitSize*i,
                    offsetY + field.getHeight()*(unitSize)
                    );
            g.drawLine(
                    offsetX, 
                    offsetY + unitSize*i, 
                    offsetX + field.getHeight()*(unitSize), 
                    offsetY + unitSize*i
                    );
        }
    }

    private void drawColonies(Graphics g, int unitSize) {
        int x, y;
        Colony colony;
        for(int i = 0; i < field.getNumOfColonies(); i++)
        {
            colony = field.getColony(i);
            x = colony.getxLoc() * unitSize;
            y = colony.getyLoc() * unitSize;
            switch(colony.getFaction())
            {
            case 0:
                    g.setColor(Color.blue);
                    break;
            case 1: 
                    g.setColor(Color.red);
                    break;
            case 2:
                    g.setColor(Color.ORANGE);
                    break;
            case 3:
                    g.setColor(Color.pink);
                    break;
            default:
                    g.setColor(Color.magenta);
                    break;
            }

            g.fillArc(x, y, unitSize, unitSize*2, 0, 180);
        }
    }

    private void drawAnts(Graphics g, int unitSize) {
        int x, y;
        for(int i = 0; i < field.getAntList().size(); i++)
        {
                x = field.getAntList().get(i).getxLoc() * unitSize;
                y = field.getAntList().get(i).getyLoc() * unitSize;

                switch(field.getAntList().get(i).getFaction())
                {
                case 0:
                        g.setColor(Color.blue);
                        break;
                case 1: 
                        g.setColor(Color.red);
                        break;
                case 2:
                        g.setColor(Color.ORANGE);
                        break;
                case 3:
                        g.setColor(Color.pink);
                        break;
                default:
                        g.setColor(Color.magenta);
                        break;
                }
                g.fillRect(x+(unitSize/4), y+(unitSize/4), unitSize/2, unitSize/2);
        }
    }

    private void drawFoodpiles(Graphics g, int unitSize) {
        int x, y, pileSize, diff;
        float foodScale=.13f;
        Foodpile foodPile;
                       
        for(int i = 0; i < field.getFoodpileList().size(); i++)
        {
                foodPile = field.getFoodpileList().get(i);
                pileSize = (int)(unitSize*foodScale*foodPile.getFoodCount());

                x = foodPile.getxLoc() * unitSize;
                y = foodPile.getyLoc() * unitSize;
                g.setColor(Color.green);
                
                diff = unitSize-pileSize;
                g.fillOval(x+diff/2, y+diff/2, pileSize, pileSize);
                
        }
    }
    
    private void drawPheremones(Graphics g, int unitSize) 
    {
        double pheromone, percentStrength;
        int faction = 0, size, diff;
        
        for(int x = 0 ; x < field.getWidth(); x++)
        {
            for(int y = 0 ; y < field.getWidth(); y++)
            {
        	while((field.getPheromoneAt(faction, x, y)) != -1)
    		{   //we loop through each faction, here
                    switch(faction)
                    {
                        case 0:
                            g.setColor(Color.blue);
                            break;
                        case 1: 
                                g.setColor(Color.red);
                                break;
                        case 2:
                                g.setColor(Color.ORANGE);
                                break;
                        case 3:
                                g.setColor(Color.pink);
                                break;
                        default:
                                g.setColor(Color.magenta);
                                break;
                    }
                    pheromone = field.getPheromoneAt(faction, x, y);
                    percentStrength = pheromone / field.getParameterSet().checkParameter("PheromoneStrength");
                    size = (int)(unitSize* percentStrength);
                    diff = unitSize - size;
                    if(pheromone!=0)
                        g.drawOval((x*unitSize)+diff/2, (y*unitSize)+diff/2, size, size);
                                faction++;
                }
    		faction=0;
            }
    	} 
    }

    public void setHUDVisible(boolean vis) {
        hudVisible = vis;
        repaint();
    }
    
    public void setGridVisible(boolean vis) {
        gridVisible = vis;
        repaint();
    }
}
