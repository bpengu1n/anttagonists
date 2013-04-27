package antsimulation.testing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import antsimulation.ParameterSet;

public class ParameterSetTest {
	
	@Test
	public void testParameterSetup() 
	{
		ParameterSet pSet = new ParameterSet();
		
		for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
		{
			assertTrue(ParameterSet.PARAMETER_NAMES[i] + " was not initialized to default value.\nIt may have a custom value or was not initialized correctly.", (pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == ParameterSet.DEFAULT_VALS[i]));
		}
	}
	
	@Test
	public void testAdjustParameter() 
	{		
		ParameterSet pSet = new ParameterSet();
		
		for(int testNum=0; testNum < 100; testNum++)
			for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			{
				double expectedValue = new Random(new Date().getTime()).nextFloat();
				
				pSet.adjustParameter(ParameterSet.PARAMETER_NAMES[i], expectedValue, true);
				assertTrue(ParameterSet.PARAMETER_NAMES[i] + " was not correctly adjusted",(pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == expectedValue));
			}
	}

	@Test
	public void testCheckParameterAdjustment() 
	{
		ParameterSet pSet = new ParameterSet();
		
		for(int testNum=0; testNum < 100; testNum++)
			for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			{
				double expectedValue = new Random(new Date().getTime()).nextFloat();
				
				pSet.adjustParameter(ParameterSet.PARAMETER_NAMES[i], expectedValue, true);
				assertTrue(ParameterSet.PARAMETER_NAMES[i] + " was not correctly adjusted",(pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == expectedValue));
			}
	}
}
