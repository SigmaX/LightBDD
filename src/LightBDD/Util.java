package LightBDD;

import java.util.ArrayList;
import static org.junit.Assert.*;

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
    
    
    /** Deep array comparison for booleans.
     *  
     *  JUnit doesn't have an assertArrayEquals for booleans.  So we
     *  provide our own.
     */
    public static void assertArrayEquals(boolean[] a, boolean[] b)
    {
        assertTrue(a.length == b.length);
        for (int i = 0; i < a.length; i++)
            assertEquals(a[i], b[i]);
    }
    
    /** Deep array comparison for two-dimensional booleans.
     *  
     */
    /*public static void assertArrayEquals(boolean[][] a, boolean[][] b)
    {
        assertTrue(a.length == b.length);
        for (int i = 0; i < a.length; i++)
            assertArrayEquals(a[i], b[i]);
    }*/
}
