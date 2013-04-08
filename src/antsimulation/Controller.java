package antsimulation;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends Applet {
    private antsimulation.model.Simulation model;
    private antsimulation.view.View view;
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        Controller applet = new Controller();
        applet.init();
        applet.start();
        window.add(applet);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    } 
    
    @Override
    public void init() {
        view = new antsimulation.view.View(this);
        ((FlowLayout)getLayout()).setVgap(0);
        ((FlowLayout)getLayout()).setHgap(0);
        add(view);
    }
    
    public void startSimulation(ParameterSet p) {}
    public void updateSimulation() {}
    public void generateOutputFile(String filename) {}
    public void stopSimulation() {}
}
