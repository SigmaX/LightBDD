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
        CompositionMap inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(0).add(-1);
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
        // Test multi-execute function
        boolean[][] output = {    {false, false},
                                {false, true},
                                {false, true},
                                {false, true},
                                {true, false},
                                {true, true},
                                {true, true},
                                {true, true},
                                {true, false },
                                {true, true},
                                {true, true},
                                {true, true},
                                {false, false},
                                {false, true},
                                {false, true},
                                {false, true}};
        assertArrayEquals(output, c.execute(input4));
        
        MultiBDD f3 = new MultiBDD(BDD.Function.SHUNT);
        inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(0).add(0);
        inputMapping.getMaleOutputTargets(0).add(2);
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
        MultiBDD mimic = new MultiBDD(BDD.Function.SHUNT);
        inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(0).add(0);
        inputMapping.getMaleOutputTargets(0).add(-1);
        MultiBDD n12 = new MultiBDD(n2, n1, inputMapping);
        inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(0).add(1);
        inputMapping.getMaleOutputTargets(0).add(2);
        // FIXME This operation causes an unintend input variable reordering (swaps 0 and 1).
        MultiBDD n12m = new MultiBDD(n12, mimic, inputMapping);
        //System.out.println(n12m.bdds.get(0).toDot("G"));
        //System.out.println(n12m.bdds.get(1).toDot("G"));
        inputMapping = new CompositionMap(2);
        inputMapping.getMaleOutputTargets(0).add(0);
        inputMapping.getMaleOutputTargets(1).add(1);
        MultiBDD n123m = new MultiBDD(n3, n12m, inputMapping);
        MultiBDD outputmbdd = new MultiBDD(BDD.Function.TEST1);
        ArrayList<boolean[]> input2 = Util.generateInputs(2);
        for (int i = 0; i < input2.size(); i++)
        {
            boolean[] result = n123m.execute(input2.get(i));
            assertEquals(outputmbdd.execute(input2.get(i))[0], result[0]);
        }
        
        // XOR and MIMIC
        MultiBDD xor = new MultiBDD(BDD.Function.XOR);
        MultiBDD mim = new MultiBDD(BDD.Function.SHUNT);
        inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(0).add(1);
        inputMapping.getMaleOutputTargets(0).add(0);
        MultiBDD xormim = new MultiBDD(xor, mim, inputMapping);
        
        // Another instance that caused trouble.  Take him out behind the barn and shoot 'im, I say.
        MultiBDD doubleMimic = new MultiBDD(BDD.Function.SHUNT);
        doubleMimic.bdds.add(new BDD(BDD.Function.SHUNT));
        doubleMimic.bdds.get(0).postConcatonateInputs(1);
        doubleMimic.bdds.get(1).postConcatonateInputs(1);
        MultiBDD notNand = new MultiBDD(BDD.Function.NOT);
        notNand.bdds.get(0).postConcatonateInputs(2);
        notNand.bdds.add(new BDD(BDD.Function.NAND));
        notNand.bdds.get(1).preConcatonateInputs(1);
        inputMapping = new CompositionMap(2);
        inputMapping.getMaleOutputTargets(0).add(-1);
        inputMapping.getMaleOutputTargets(1).add(0);
        inputMapping.getMaleOutputTargets(1).add(1);
        MultiBDD dmnnd = new MultiBDD(doubleMimic, notNand, inputMapping);
        
        // And another.  This one had a wack number of outputs.
        MultiBDD mimicNot = new MultiBDD(BDD.Function.SHUNT);
        mimicNot.bdds.add(new BDD(BDD.Function.NOT));
        mimicNot.bdds.get(0).preConcatonateInputs(1);
        mimicNot.bdds.get(1).preConcatonateInputs(1);
        MultiBDD and = new MultiBDD(BDD.Function.AND);
        inputMapping = new CompositionMap(1);
        inputMapping.getMaleOutputTargets(1).add(-1);
        MultiBDD mna = new MultiBDD(mimicNot, and, inputMapping);
        
        // Yet another trouble-maker.  Yay for boundary conditions O_o
        BDDTree m0Tree = new BDDTree(3);
        m0Tree.addNode(new Node(0, 1, 0));
        BDDTree m1Tree = new BDDTree(3);
        int m1_1 = m1Tree.addNode(new Node(1, 0, 2));
        m1Tree.addNode(new Node(1, m1_1, 1));
        ArrayList<BDD> maleBDDs = new ArrayList(2);
        maleBDDs.add(new BDD(m0Tree));
        maleBDDs.add(new BDD(m1Tree));
        MultiBDD male = new MultiBDD(maleBDDs);
        
        BDDTree fTree = new BDDTree(3);
        int f_2 = fTree.addNode(new Node(1, 0, 2));
        int f_3 = fTree.addNode(new Node(0, 1, 2));
        fTree.addNode(new Node(f_2, f_3, 0));
        ArrayList<BDD> femaleBDDs = new ArrayList(1);
        femaleBDDs.add(new BDD(fTree));
        MultiBDD female = new MultiBDD(femaleBDDs);
        
        BDDTree rTree = new BDDTree(3);
        int r_2 = rTree.addNode(new Node(0, 1, 2));
        int r_3 = rTree.addNode(new Node(0, r_2, 1));
        int r_4 = rTree.addNode(new Node(1, 0, 2));
        int r_5 = rTree.addNode(new Node(1, r_4, 1));
        rTree.addNode(new Node(r_3, r_5, 0));
        ArrayList<BDD> resultBDDs = new ArrayList(1);
        resultBDDs.add(new BDD(rTree));
        MultiBDD expectedResult = new MultiBDD(resultBDDs);
        
        inputMapping = new CompositionMap(2);
        inputMapping.getMaleOutputTargets(0).add(1);
        inputMapping.getMaleOutputTargets(0).add(2);
        inputMapping.getMaleOutputTargets(1).add(0);
        
        MultiBDD result = new MultiBDD(female, male, inputMapping);
        assertArrayEquals(expectedResult.execute(input3), result.execute(input3)); // Truth tables match
        assertTrue(result.equals(expectedResult)); // BDDs are isomorphic
    }
}
