package LightBDD;

import java.util.ArrayList;
import java.util.Arrays;

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
public class CompositionMap
{
    private ArrayList<Integer>[] maleOutputTargets;
    
    public CompositionMap(int maleNumOutputs)
    {
        this.maleOutputTargets = new ArrayList[maleNumOutputs];
        for (int i = 0; i < maleOutputTargets.length; i++)
            maleOutputTargets[i] = new ArrayList<Integer>();
    }
    
    public void setOutputTargets(int output, ArrayList<Integer> targets)
    {
        this.maleOutputTargets[output] = targets;
    }
    
    public ArrayList<Integer> getMaleOutputTargets(int output)
    {
        return this.maleOutputTargets[output];
    }
    
    public int getMaleNumOutputs()
    {
        return maleOutputTargets.length;
    }
    
    public String toString()
    {
        String output = "";
        for (int i = 0; i < this.getMaleNumOutputs(); i++)
            output += "Output: " + i + " Targets: " + this.getMaleOutputTargets(i).toString() + "\n";
        return output;
    }
    
    @Override
    public boolean equals(Object n)
    {
        if (! (n instanceof CompositionMap))
            return false;
        if (this.getMaleNumOutputs() != ((CompositionMap)n).getMaleNumOutputs())
            return false;
        for (int i = 0; i < this.getMaleNumOutputs(); i++)
        {
            if (! (this.maleOutputTargets[i].equals(((CompositionMap)n).getMaleOutputTargets(i))))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + Arrays.deepHashCode(this.maleOutputTargets);
        return hash;
    }
}
