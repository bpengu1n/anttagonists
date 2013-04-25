package antsimulation;
import antsimulation.model.*;
import antsimulation.view.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Controller is an Applet, to allow it to embed in a browser.
//However all display functions are delegated to the View.
public class Controller extends Applet {
    private antsimulation.model.Simulation model;
    private antsimulation.view.View view;

    //creates a new JFrame for the applet, if opened via command line
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        Controller applet = new Controller();
        applet.init();
        applet.start();
        window.add(applet);
        //uncomment this to lock window size:
//        window.setResizable(false);
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

    public void startSimulation(ParameterSet p) {
        model = new Simulation(p, view);
    }
    
    public void updateSimulation() {
        if (model != null)
            model.update();
    }
    
    public void stopSimulation() {
        model = null;
    }
}
