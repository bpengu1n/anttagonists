/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulation.testing;

import antsimulation.view.View;
import java.awt.event.ActionEvent;
import java.util.Observable;
import javax.swing.event.ChangeEvent;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Courtney
 */
public class ViewTest {
    
    public ViewTest() {
    }

    /**
     * Test of setStatus method, of class View.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        String s = "I am a test";
        antsimulation.Controller c = new antsimulation.Controller();
        View instance = new View(c);
        instance.setStatus(s);
        assertTrue("Status not correctly changed.",instance.getStatus() == s);
    }

    /**
     * Test of update method, of class View.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        antsimulation.ParameterSet p = new antsimulation.ParameterSet();
        antsimulation.model.Field field = new antsimulation.model.Field(p);
        antsimulation.Controller c = new antsimulation.Controller();
        View instance = new View(c);
        field.addObserver(instance);
        instance.setViewUpdated(false);
        field.update();
        assertTrue("View was not updated when field updated.",instance.getViewUpdated()==true);
    }

}