package antsimulation.view;
import java.awt.*;

import antsimulation.model.Colony;
import antsimulation.model.Field;

public class HUD {
    public void paint(Graphics g, java.util.Observable o) {
        antsimulation.model.Field field = (antsimulation.model.Field)o;
        
        FontMetrics fm = g.getFontMetrics();
        int fontHeight = fm.getHeight(); 
        
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, 210, (int)((field.parameters.checkParameter("MaxColonies")+1)*fontHeight)+5, 7, 7);
        
        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        g.setFont(new Font("serif", Font.BOLD, 14));
        

        int foodTotal=0;
        int y = 0;
        int x = 3;
        
        //Draw Food Pile Count
        for(int i = 0; i < field.foodpiles.size(); i++)
            foodTotal+=field.foodpiles.get(i).getFoodCount();
        String pileCountStr = "Food Remaining: " + foodTotal;
 
        y+=fontHeight;
        g.drawString(pileCountStr, x, y);
        
        //Draw Colony food storage and ant count
        String colonyStr;
        Colony colony;
        for(int col = 0; col < field.parameters.checkParameter("MaxColonies"); col++)
    	{
        	
        	colony = field.colonies.get(col);
        	
            switch(colony.getFaction())
            {
            case 0:
            		colonyStr = "Blue Colony: ";
                    break;
            case 1: 
            		colonyStr = "Red Colony: ";
                    break;
            case 2:
            		colonyStr = "Orange Colony: ";
                    break;
            case 3:
            		colonyStr = "Pink Colony: ";
                    break;
            default:
            		colonyStr = "Magenta Colony: ";
                    break;
            }
        	
            //append food count
        	colonyStr += colony.getFoodCount() + " food, ";
        	
        	//count ants of colony col
        	int colonyAnts = 0;
        	for(int i = 0; i < field.ants.size(); i++)
        	{
        		if(field.ants.get(i).getFaction() == col)
        			colonyAnts++;
        	}
        	//append ant count
        	colonyStr += colonyAnts + " ants";
            
        	y+=fontHeight;
        	g.drawString(colonyStr, x, y);
    	}
        
        g.drawRoundRect(0, 0, 210, y+5, 7, 7);
        
        g.setFont(oldFont);
    }
}
