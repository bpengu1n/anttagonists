/**
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
PredatorHunger:         Indicates how much a predator will eat before leaving the field.
PredatorSkill:		Indicates the probability of a predator successfully eating a ant.
PredatorFrequency:	Indicates how likely it is for a predator to appear on the field.
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
	"ReproductionCost", "PheromoneStrength", "PheromoneDecay", "PredatorHunger", "PredatorSkill",
	"PredatorFrequency", "xSize", "ySize", "MaxColonies", "StartAntsPerColony",
        "StartFoodPiles", "StartFoodPileSize", "StartPredators"
    };
    public static float[] MIN_VALS = {
	0, 1, 0, 1, 0,
        0, 0, 0, 0, 0,
        0, 1, 1, 1, 1,
        0, 1, 0
    };
    public static float[] MAX_VALS = {
	1, 1000, 1000, 1000, 100,
        100, 700, 100, 100, 1,
        1000, 125, 125, 4, 20,
        50, 100, 5
    };
    public static float[] DEFAULT_VALS = {
        .5f, 400, 500, 700, 50,
        50, 250, 5, 30, .5f,
        200, 30, 30, 3, 6,
        5, 10, 1
    };
    public static boolean[] MUSTBEINT = {
        false, true, true, true, true,
        true, false, false, true, false,
        true, true, true, true, true,
        true, true, true
    };
    
    private static char COMMENTCHARACTER = '#';

    public Parameter[] parameters;

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
