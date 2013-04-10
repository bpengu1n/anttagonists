package antsimulation.view;
import java.awt.*;

import antsimulation.model.Field;

public class HUD {
    public void update(antsimulation.model.Field field) {
        System.out.println("Ants: " + field.ants.size());
        System.out.println("Predators: " + field.predators.size());
        System.out.println("Foodpiles: " + field.foodpiles.size());
    }
    
    public void paint(Graphics g, antsimulation.model.Field field) {
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
        	foodTotal+=field.foodpiles.get(i).foodCount;
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
