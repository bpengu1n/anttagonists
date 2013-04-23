package antsimulation.testing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import antsimulation.ParameterSet;

public class ParameterSetTest {
	
	/*
	@BeforeClass
	public static void testSetup() 
	{
		// Code executed before test cases
	}
	*/
	/*
	@AfterClass
	public static void testCleanup() 
	{
		// Code Executed after test cases
	}
	*/

	public float expectedValue = 4.5f;
	
	@Test
	public void testParameter() 
	{
		ParameterSet pSet = new ParameterSet();
		
		for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
		{
			if( !(pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == expectedValue) )
				fail(ParameterSet.PARAMETER_NAMES[i] + " was not initialized to default value.\nIt may have a custom value or was not initialized correctly.");
		}
	}
	
	@Test
	public void testAdjustParameter() 
	{		
		ParameterSet pSet = new ParameterSet();
		
		for(int testNum=0; testNum < 100; testNum++)
			for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			{
				expectedValue = new Random(new Date().getTime()).nextFloat();
				
				pSet.adjustParameter(ParameterSet.PARAMETER_NAMES[i], expectedValue, true);
				if( !(pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == expectedValue) )
					fail(ParameterSet.PARAMETER_NAMES[i] + " was not correctly adjusted");
			}
	}

	@Test
	public void testCheckParameter() 
	{
		ParameterSet pSet = new ParameterSet();
		
		for(int testNum=0; testNum < 100; testNum++)
			for(int i = 0; i < ParameterSet.PARAMETER_NAMES.length; i++)
			{
				expectedValue = new Random(new Date().getTime()).nextFloat();
				
				pSet.adjustParameter(ParameterSet.PARAMETER_NAMES[i], expectedValue, true);
				if( !(pSet.checkParameter(ParameterSet.PARAMETER_NAMES[i]) == expectedValue) )
					fail(ParameterSet.PARAMETER_NAMES[i] + " was not correctly adjusted");
			}
	}

}
