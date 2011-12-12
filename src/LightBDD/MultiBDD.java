package LightBDD;

import java.util.ArrayList;

/**
 * BDD objects are multi-input, single-output.  MultiBDDs use multiple BDD's to
 * provide a multi-input to multi-output function.
 * 
 * @author Eric 'Siggy' Scott
 */
public class MultiBDD
{
    public ArrayList<BDD> bdds;
    
    /**
     * Initializes a one-output function (i.e., one BDD).  
     */
    public MultiBDD(BDD.Function function)
    {
        bdds = new ArrayList<BDD>(1);
        bdds.add(new BDD(function));
    }
    
    /**
     *  Construct a MultiBDD by composing two existing ones.  Note that the 
     *  early inputs of the composition correspond to inputs of f2, while the
     *  later ones correspond to the inputs of f1.
     * 
     *  @param f1 "Female" function.  It's outputs are outputs of the composition
     *  @param f2 "Male" function.  It's outputs are inputs to f1, and/or outputs of the composition, or ignored.
     *  @param inputMapping Defines where the outputs of f2 get directed.  inputMapping[i] is a list of
     *          inputs of f1 that will receive their signal from the ith output of f2.  If -1 appears in
     *          the list, the ith output of f2 also becomes a new output of the composition.
     */
    public MultiBDD(MultiBDD f1, MultiBDD f2, ArrayList<Integer>[] inputMapping)
    {
        bdds = new ArrayList<BDD>(10);
        ArrayList<BDD> newOutputs = new ArrayList(5);
        ArrayList<BDD> oldOutputs = new ArrayList(5);
        // We have to modify each one of f1's BDDs to be our new outputs
        for (int i = 0; i < f1.getNumOutputs(); i++)
        {
            BDD f1NewOutput = compose(f1.bdds.get(i), f2, inputMapping, newOutputs);
            oldOutputs.add(f1NewOutput);
        }
        for (BDD b : newOutputs)
            this.bdds.add(b);
        for (BDD b : oldOutputs)
            this.bdds.add(b);
    }
    
    /**
     * Create one output of a composed function and add it to bdds.
     */
    private BDD compose(BDD f1Output, MultiBDD f2, ArrayList<Integer>[] inputMapping, ArrayList<BDD> newOutputs)
    {
        BDD f1NewOutput = new BDD(f1Output); // Deep copy to avoid side effects
        f1NewOutput.preConcatonateInputs(f2.getNumInputs());
        ArrayList<Integer> inputsNoLongerUsed = new ArrayList(10);
        // Each one of f2's BDDs (outputs) may have an output feeding into f1
        assert(inputMapping.length == f2.getNumOutputs());
        for (int j = 0; j < inputMapping.length; j++)
        {
            ArrayList<Integer> f2OutputTargets = inputMapping[j];
            BDD f2Output = new BDD(f2.bdds.get(j));
            f2Output.postConcatonateInputs(f1Output.tree.getNumInputs());
            for (int k = 0; k < f2OutputTargets.size(); k++)
            {
                int target = f2OutputTargets.get(k);
                if (target == -1)
                {
                    newOutputs.add(f2Output);
                }
                else
                {
                    target += f2.getNumInputs(); // The preConcatonateInputs() at the top of this function moves the target
                    f1NewOutput = new BDD(target, f1NewOutput, f2Output, false); // Compose this output's contribution into f1Output
                    inputsNoLongerUsed.add(target);
                }
            }
        } // f1Output has now received all the inputs it will from f2
        // Remove the inputs that are no longer used
        for (int i = 0; i < inputsNoLongerUsed.size(); i++)
        {
            f1NewOutput.tree.collapseInput(inputsNoLongerUsed.get(i) - i);
        }
        return f1NewOutput;
    }
    
    /**
     * Execute the boolean function represented by this MultiBDD. 
     */
    public boolean[] execute(boolean[] input)
    {
        assert(input.length == getNumInputs());
        boolean[] output = new boolean[bdds.size()];
        for (int i = 0; i < bdds.size(); i++)
            output[i] = bdds.get(i).execute(input);
        return output;
    }
    
    public int getNumInputs()
    {
        return bdds.get(0).tree.getNumInputs();
    }
    
    public int getNumOutputs()
    {
        return bdds.size();
    }
}
