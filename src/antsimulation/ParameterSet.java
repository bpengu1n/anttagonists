/*
 * PARAMETER LIST
PARAMETER:			DESCRIPTION
AntCuriosity:		Indicates the tendency of ants to leave the pheromone trails and wander the field at random.
AntStarvation:		Indicates the time a ant can live without food.
AntHunger:		Indicates the time a ant will go before trying to eat food, if possible.
AntLifetime:		Indicates the maximum time that a ant can live.
ReproductionRate:	Indicates the tendency of the colony to produce new ants.
ReproductionCost:	Indicates the amount of food required to produce a new ant.
PheromoneStrength:	Indicates the initial strength of pheromones.
PheromoneDecay: 	Indicates how quickly pheromones decay.
xSize, ySize:           Gives the size of the field
MaxColonies:            Gives the number of colonies on the field
StartAntsPerColony:     Gives the initial number of ants per colony initially on the field
StartFoodPiles:         Gives the initial number of food piles
StartFoodPileSize:      Gives the initial number of food units per food pile
StartPredators:         Gives the initial number of predators
 */

package antsimulation;
import java.util.*;
import java.io.*;

public class ParameterSet {
    //static contsants
    public static String[] PARAMETER_NAMES = {
	"AntCuriosity", "AntStarvation", "AntHunger", "AntLifetime", "ReproductionRate",
	"ReproductionCost", "PheromoneStrength", "PheromoneDecay", "xSize", "ySize",
        "MaxColonies", "StartAntsPerColony", "StartFoodPiles", "StartFoodPileSize", "StartPredators"
    };
    public static boolean[] MUSTBEINT = {   //restricts the possible values for a parameter
        false, true, true, true, true,
        true, false, false, true, true,
        true, true, true, true, true
    };
    public static float[] MIN_VALS = {  //restricts the possible values for a parameter
	0, 1, 0, 1, 0,
        0, 0, 0, 1, 1,
        1, 1, 0, 1, 0
    };
    public static float[] MAX_VALS = {  //restricts the possible values for a parameter
	1, 1000, 1000, 1000, 100,
        50, 700, 100, 125, 125,
        4, 20, 50, 100, 5
    };
    public static float[] DEFAULT_VALS = {
        .2f, 400, 100, 700, 50,
        4, 250, 5, 30, 30,
        3, 6, 5, 10, 1
    };
    
    private static char COMMENTCHARACTER = '#'; //this marks lines to ignore in the Scenario file

    private Parameter[] parameters;

    public static int indexOf(String name) {
        return Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
    }
    
    public ParameterSet() {
        parameters = new Parameter[ParameterSet.PARAMETER_NAMES.length];
        //Set all params to default
        for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
            parameters[i] = new Parameter(
                ParameterSet.PARAMETER_NAMES[i],
                ParameterSet.DEFAULT_VALS[i],
                true);
    }
    
    public static ParameterSet generateFromFile(File file) {
        try {
            ParameterSet p = new ParameterSet();
            Scanner in = new Scanner(new FileReader(file));
            String line;
            String name;
            double val;
            boolean canEdit;
            while (in.hasNextLine()) {
                line = in.nextLine();
                if (line.charAt(0) != COMMENTCHARACTER) {
                    String[] words = line.split("\\s+");
                    if (words.length < 5)
                        return null;
                    name = words[0];
                    val = Double.parseDouble(words[2]);
                    canEdit = Boolean.parseBoolean(words[4]);
                    p.adjustParameter(name, val, canEdit);
                }
            }
            in.close();
            return p;
        } catch (FileNotFoundException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //returns true on cuccess
    public boolean adjustParameter(String name, double val, boolean editable) {
    	if(this.parameters==null) {
            System.err.println("Parameter " + name + " was adjusted without ParameterList instantiation!");
            return false;
    	}
    	int parameterIndex = Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
        if (parameterIndex == -1) {
            System.err.println("\"" + name + "\" is not a valid parameter.");
            return false;
        }
    	this.parameters[parameterIndex].setValue(val);
    	this.parameters[parameterIndex].setEditable(editable);
        return true;
    }
    
    public double checkParameter(String name) {
    	int parameterIndex = Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
    	return parameters[parameterIndex].getValue();
    }
    
    public boolean checkForEditable(String name) {
        int parameterIndex = Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
    	return parameters[parameterIndex].isEditable();
    }

    //for debugging
    public void print()  {
        for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
            System.out.println(parameters[i].toString());
    }
}
