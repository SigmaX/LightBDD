package LightBDD;

import java.util.ArrayList;

/**
 * A unified interface for BDDs and MultiBDDs -- useful mostly for extension
 * by the client (ex. to define objective functions in an evolutionary algorithm).
 * 
 * @author Eric 'Siggy' Scott
 */
public abstract class Executable
{
    protected int numInputs;
    protected int numOutputs;
    
    public abstract boolean[] execute(boolean[] input); 
   
    public int getNumInputs()
    {
        return numInputs;
    }
    
    public int getNumOutputs()
    {
        return numOutputs;
    }
    
    public String printTruthTable()
    {
        String output = "";
        ArrayList<boolean[]> inputs = Util.generateInputs(this.getNumInputs());
        for (boolean[] in : inputs)
        {
            for (boolean v : in)
            {
                output += (v ? "T " : "F ");
            }
            output += ":";
            boolean[] outs = this.execute(in);
            for (boolean out : outs)
                output += "  " + (out ? "T  " : "F  ");
            output += "\n";
        }
        return output;
    }
}
