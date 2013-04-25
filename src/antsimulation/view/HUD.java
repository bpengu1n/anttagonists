package antsimulation.view;
import java.awt.*;

import antsimulation.model.Field;

public class HUD {
    public void paint(Graphics g, java.util.Observable o) {
        antsimulation.model.Field field = (antsimulation.model.Field)o;
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, 95, 60, 7, 7);
        
        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        g.setFont(new Font("serif", Font.BOLD, 14));
        
        FontMetrics fm = g.getFontMetrics();
        int fontHeight = fm.getHeight();
        int foodTotal=0;
        int y = 0;
        int x = 3;
        
        String antCountStr = "Ants: " + field.ants.size();
        for(int i = 0; i < field.foodpiles.size(); i++)
        	foodTotal+=field.foodpiles.get(i).getFoodCount();
        String foodCountStr = "Total Food: " + foodTotal;
        String pileCountStr = "Food Piles: " + field.foodpiles.size();
        
        y+=fontHeight;
        g.drawString(antCountStr, x, y);
        y+=fontHeight;
        g.drawString(foodCountStr, x, y);
        y+=fontHeight;
        g.drawString(pileCountStr, x, y);
        

        //g.drawRect(0,0,90,60);
        
        g.drawRoundRect(0, 0, 95, 60, 7, 7);
        
        g.setFont(oldFont);
    }
}
