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
 * @author Eric 'Siggy' Scott
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
        for (int i = 0; i < input3.size(); i++)
        {
            boolean[] result = c.execute(input3.get(i));
            assertEquals(outputXOR[i], result[0]);
            assertEquals(outputNAND[i], result[1]);
        }
        
        // TEST1
        MultiBDD n1 = new MultiBDD(BDD.Function.NAND);
        MultiBDD n2 = new MultiBDD(BDD.Function.NAND);
        MultiBDD n3 = new MultiBDD(BDD.Function.NAND);
        MultiBDD mimic = new MultiBDD(BDD.Function.MIMIC);
        inputMapping = new ArrayList[] { new ArrayList(2) };
        inputMapping[0].add(0);
        inputMapping[0].add(-1);
        MultiBDD n12 = new MultiBDD(n2, n1, inputMapping);
        inputMapping = new ArrayList[] { new ArrayList(2)};
        inputMapping[0].add(1);
        inputMapping[0].add(2);
        // FIXME This operation causes an unintend input variable reordering (swaps 0 and 1).
        MultiBDD n12m = new MultiBDD(n12, mimic, inputMapping);
        System.out.println(n12m.bdds.get(0).toDot("A"));
        System.out.println(n12m.bdds.get(1).toDot("B"));
        inputMapping = new ArrayList[] { new ArrayList(1), new ArrayList(1) };
        inputMapping[0].add(0);
        inputMapping[1].add(1);
        MultiBDD n123m = new MultiBDD(n3, n12m, inputMapping);
        MultiBDD output = new MultiBDD(BDD.Function.TEST1);
        ArrayList<boolean[]> input2 = Util.generateInputs(2);
        for (int i = 0; i < input2.size(); i++)
        {
            boolean[] result = n123m.execute(input2.get(i));
            assertEquals(output.execute(input2.get(i))[0], result[0]);
        }
        
    }
}
