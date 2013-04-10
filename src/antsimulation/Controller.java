package antsimulation;
import antsimulation.model.*;
import antsimulation.view.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends Applet {
    private antsimulation.model.Simulation model;
    private antsimulation.view.View view;
    private boolean isInBrowser = true; //assumed true
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        Controller applet = new Controller();
        applet.isInBrowser = false;
        applet.init();
        applet.start();
        window.add(applet);
//        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
    
    @Override
    public void init() {
        view = new antsimulation.view.View(this, isInBrowser);
        ((FlowLayout)getLayout()).setVgap(0);
        ((FlowLayout)getLayout()).setHgap(0);
        add(view);
    }

    public void startSimulation(ParameterSet p) {
        model = new Simulation(p, view);
    }
    
    public void updateSimulation() {
        if (model != null)
            model.update();
    }
    
    public void generateOutputFile(String filename) {
        if (model != null)
            model.generateOutputFile(filename);
    }
    
    public void stopSimulation() {
        model = null;
    }
}
