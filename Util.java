package LightBDD;

import java.util.ArrayList;

/**
 *
 * @author Eric 'Siggy' Scott
 */
public class Util
{
    public static ArrayList<boolean[]> generateInputs(int numInputs)
    {
        ArrayList<boolean[]> output = new ArrayList((int)Math.pow(numInputs, 2));
        boolean[] b = new boolean[numInputs];

        generateInputs(numInputs, b, output, 0);
        return output;
    }
    
    private static void generateInputs(int numInputs, boolean[] stringUnderConstruction, ArrayList<boolean[]> collection, int index)
    {
        if (index == numInputs)
        {
            collection.add(stringUnderConstruction.clone());
        }
        else
        {
            stringUnderConstruction[index] = true;
            generateInputs(numInputs, stringUnderConstruction, collection, index+1);
            stringUnderConstruction[index] = false;
            generateInputs(numInputs, stringUnderConstruction, collection, index+1);
        }
    }
    
}
