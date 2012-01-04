package LightBDD;

import java.util.ArrayList;

/**
 * A mapping of outputs to inputs, used to compose functions with arbitrary
 * numbers of inputs and outputs.
 * 
 * More specifically, if f1 (female) is being composed with f2 (male), the
 * CompositionMapping.getOutputTargets(i) is a list of inputs of f1 that will
 * receive their signal from the ith output of f2.  If -1 appears in the list,
 * the ith output of f2 also becomes a new output of the composition.
 * 
 * @author Eric 'Siggy' Scott
 */
public class CompositionMapping
{
    private ArrayList<Integer>[] outputTargets;
    
    public CompositionMapping(int numOutputs)
    {
        this.outputTargets = new ArrayList[numOutputs];
        for (int i = 0; i < outputTargets.length; i++)
            outputTargets[i] = new ArrayList<Integer>();
    }
    
    public void setOutputTargets(int output, ArrayList<Integer> targets)
    {
        this.outputTargets[output] = targets;
    }
    
    public ArrayList<Integer> getOutputTargets(int output)
    {
        return this.outputTargets[output];
    }
    
    public int getMaleNumOutputs()
    {
        return outputTargets.length;
    }
}
