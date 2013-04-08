package antsimulation.view;
import java.awt.*;

public class HUD {
    public void update(antsimulation.model.Field field) {
//        System.out.println("Ants: " + field.ants.size());
//        System.out.println("Predators: " + field.predators.size());
//        System.out.println("Foodpiles: " + field.foodpiles.size());
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0,0,60,60);
    }
}
