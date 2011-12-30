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
    
    /** Run execute(boolean[] input) on an array of inputs
     * 
     */
    public boolean[][] execute(ArrayList<boolean[]> inputs)
    {
        boolean[][] outputs = new boolean[inputs.size()][inputs.get(0).length];
        for (int i = 0; i < inputs.size(); i++)
        {
            boolean[] in = inputs.get(i);
            assert(in.length == inputs.get(0).length); // Ragged input is invalid
            outputs[i] = this.execute(in);
        }
        return outputs;
    }
   
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
