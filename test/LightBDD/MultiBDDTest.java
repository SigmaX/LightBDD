/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LightBDD;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 *
 * @author eric
 */
public class MultiBDDTest
{
    
    public MultiBDDTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of compose constructor, of class MultiBDD.
     */
    @Test
    public void testCompose()
    {
        System.out.println("compose");
        MultiBDD f1 = new MultiBDD(BDD.Function.NAND);
        MultiBDD f2 = new MultiBDD(BDD.Function.XOR);
        ArrayList<Integer>[] inputMapping = new ArrayList[] { new ArrayList() };
        inputMapping[0].add(-1);
        MultiBDD c = new MultiBDD(f1, f2, inputMapping);
        ArrayList<boolean[]> input4 = Util.generateInputs(4);
        boolean[] outputXOR = {false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false };
        boolean[] outputNAND = {false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true};
        for (int i = 0; i < input4.size(); i++)
        {
            boolean[] result = c.execute(input4.get(i));
            assertEquals(outputXOR[i], result[0]);
            assertEquals(outputNAND[i], result[1]);
        }
        
        MultiBDD f3 = new MultiBDD(BDD.Function.MIMIC);
        inputMapping = new ArrayList[] { new ArrayList() };
        inputMapping[0].add(0);
        inputMapping[0].add(2);
        c = new MultiBDD(c, f3, inputMapping);
        ArrayList<boolean[]> input3 = Util.generateInputs(3);
        outputXOR = new boolean[] { false, false, true, true, true, true, false, false };
        outputNAND = new boolean[] { false, true, false, true, true, true, true, true };
        /*
        for (boolean[] in : input3)
        {
            for (boolean v : in)
            {
                System.out.print((v ? "T " : "F "));
            }
            System.out.print("  " + (c.execute(in)[0] ? "T  " : "F  "));
            System.out.print("  " + (c.execute(in)[1] ? "T\n" : "F\n"));
        }*/
        for (int i = 0; i < input3.size(); i++)
        {
            boolean[] result = c.execute(input3.get(i));
            assertEquals(outputXOR[i], result[0]);
            assertEquals(outputNAND[i], result[1]);
        }
    }
}
