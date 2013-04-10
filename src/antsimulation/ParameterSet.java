/**
 * PARAMETER LIST
PARAMETER:			DESCRIPTION
Ant curiosity:		Indicates the tendency of ants to leave the pheromone trails and wander the field at random.
Ant starvation:		Indicates the time a ant can live without food.
Ant hunger:			Indicates the time a ant will go before trying to eat food, if possible.
Ant lifetime:		Indicates the maximum time that a ant can live.
Reproduction rate:	Indicates the tendency of the colony to produce new ants.
Reproduction cost:	Indicates the amount of food required to produce a new ant.
Pheromone strength:	Indicates the initial strength of pheromones.
Pheromone decay:	Indicates how quickly pheromones decay.
Predator hunger:	Indicates how much a predator will eat before leaving the field.
Predator skill:		Indicates the probability of a predator successfully eating a ant.
Predator frequency:	Indicates how likely it is for a predator to appear on the field.
xSize, ySize:           Gives the size of the field
MaxColonies:            Gives the number of colonies on the field
StartAntsPerColony:     Gives the initial number of ants per colony initially on the field
StartFoodPiles:         Gives the initial number of food piles
StartFoodPileSize:      Gives the initial number of food units per food pile
StartPredators:         Gives the initial number of predators
 */

package antsimulation;
import java.util.Arrays;

public class ParameterSet {
	public static Boolean EDITABLE = true;	
	
	public static float PARAMETER_DEFAULT_VALUE = 4.5f;
	public static int SIMULATION_WIDTH = 250;
	public static int SIMULATION_HEIGHT = 300;
	
	public static float START_FOODPILE_SIZE = 10;
	public static float MAX_COLONIES = 4;
	public static float ANT_LIFETIME = 30;
	public static float ANT_STARVATION = 10;
	
	public static String[] PARAMETER_NAMES = {
		"AntCuriosity",
		"AntStarvation",
		"AntHunger",
		"AntLifetime",
		"ReproductionRate",
		"ReproductionCost",
		"PheromoneStrength",
		"PheromoneDecay",
		"PredatorHunger",
		"PredatorSkill",
		"PredatorFrequency",
		"xSize",
		"ySize",
		"MaxColonies",
		"StartAntsPerColony",
            "StartFoodPiles",
            "StartFoodPileSize",
            "StartPredators"
	};
	
	public Parameter[] parameters;
	
	public ParameterSet() {
		parameters = new Parameter[ParameterSet.PARAMETER_NAMES.length];
		
		//Set all params to default
		for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			parameters[i] = new Parameter(
					ParameterSet.PARAMETER_NAMES[i],
					ParameterSet.PARAMETER_DEFAULT_VALUE,
					ParameterSet.EDITABLE);
		
		//Set Custom Values
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("AntStarvation")].setValue(ANT_STARVATION);
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("xSize")].setValue(SIMULATION_WIDTH);
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("ySize")].setValue(SIMULATION_HEIGHT);
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("StartFoodPileSize")].setValue(START_FOODPILE_SIZE);
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("MaxColonies")].setValue(MAX_COLONIES);
		this.parameters[Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf("AntLifetime")].setValue(ANT_LIFETIME);
		
	}
	
    public void adjustParameter(String name, double val, boolean editable) 
    {
    	if(this.parameters==null) {
    		System.err.println("Parameter " + name + " was adjusted without ParameterList instantiation!");
    		return;
    	}
    	
    	int parameterIndex = Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
    	
    	if(editable)
    		this.parameters[parameterIndex].setValue(val);
    	else
    		System.err.println("Parameter " + name + " is not editable.");
    }
    
    public double checkParameter(String name) {
    	int parameterIndex = Arrays.asList(ParameterSet.PARAMETER_NAMES).indexOf(name);
    	return parameters[parameterIndex].getValue();
    }

	public void print()  {
		for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			System.out.println(parameters[i].toString());
		
	}
}
