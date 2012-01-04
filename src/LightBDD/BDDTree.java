package LightBDD;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Data structure for storing a BDD.  Just a wrapper so that BDD only has
 * one object to keep track of rather than two (an ArrayList and HashMap).
 * 
 * Implicitly, the root is the last node in the ArrayList, because the BDD tree
 * is always built from the bottom-up.
 * 
 * @author Eric 'Siggy' Scott
 */
public class BDDTree
{
    /* If you add fields, don't forget to update the copy constructor! */
    private ArrayList<Node> nodes;
    private HashMap<Node, Integer> nodesHash;
    private int numInputs;
    
    /**
     * Empty constructor
     */
    private BDDTree()
    {
        
    }
    
    public BDDTree(int numInputs)
    {
        this.numInputs = numInputs;
        this.nodes = new ArrayList();
        this.nodesHash = new HashMap();
        
        addNode(new Node(false, numInputs), 0); // XXX Should these nodes be made static?
        addNode(new Node(true, numInputs), 1);
    }
    
    /**
     *  Create a BDD for a constant boolean value (i.e. zero inputs).
     */
    public BDDTree(boolean value)
    {
        this.numInputs = 0;
        this.nodes = new ArrayList();
        this.nodesHash = new HashMap();
        
        addNode(new Node(value, 0), 0);
    }
    
    /**
     * Do a deep copy on a pre-existing tree.
     */
    public BDDTree(BDDTree t1)
    {
        int sz = t1.nodes.size();
        this.numInputs = t1.numInputs;
        this.nodes = new ArrayList(sz);
        this.nodesHash = new HashMap(sz);
        for (int i = 0; i < sz; i++)
        {
            Node n = new Node(t1.nodes.get(i));
            this.nodes.add(n);
            this.nodesHash.put(n, i);
        }
    }
    
    /**
     * Deep copy with negation (If this is the BDDTree of f, returns the BDDTree
     * for !f)
     */
    public BDDTree BuildNegation()
    {
        BDDTree negation = new BDDTree();
        negation.numInputs = this.numInputs;
        negation.nodes = new ArrayList(nodes.size());
        negation.nodesHash = new HashMap(nodes.size());
        
        for (int i = 0; i < nodes.size(); i++)
        {
            Node n = new Node(nodes.get(i));
            if (n.low == 0)
                n.low = 1;
            else if (n.low == 1)
                n.low = 0;
            
            if (n.high == 0)
                n.high = 1;
            else if (n.high == 1)
                n.high = 0;
            negation.nodes.add(n);
            negation.nodesHash.put(n, i);
        }
        return negation;
    }
    
    public int getNumInputs()
    {
        return this.numInputs;
    }
    
    /**
     * Increase the number of input variables by adding inputs to the beginning
     * of the input string.  The new inputs will have no effect on the output
     * until the decision tree is modified to make use of them.
     */
    public void preConcatonateInputs(int numInputsToAdd)
    {
        for (int i = 0; i < nodes.size(); i++)
            modifyNodeInputIndex(i, numInputsToAdd);
        this.numInputs += numInputsToAdd;
    }
    
    /**
     * Increase the number of input variables by adding inputs to the end
     * of the input string.  The new inputs will have no effect on the output
     * until the decision tree is modified to make use of them.
     */
    public void postConcatonateInputs(int numInputsToAdd)
    {
        modifyNodeInputIndex(0, numInputsToAdd);
        if (nodes.size() > 1) // In the case of an all-false-output function, the true node will not exist.
            modifyNodeInputIndex(1, numInputsToAdd);
        this.numInputs += numInputsToAdd;
    }
    
    private void modifyNodeInputIndex(int nodeIndex, int difference)
    {
        assert(nodeIndex < nodes.size());
        assert(nodes.size() > 0);
        Node n = nodes.get(nodeIndex);
        nodesHash.remove(n);
        n.inputIndex += difference;
        nodesHash.put(n, nodeIndex);
    }
    
    /**
     *  Test whether this is equal to the reference BDD.  Runs a DFS-based
     *  rooted directed acyclic graph isomorphism algorithm, which
     *  is linear in the number of nodes.
     */
    private int nextLabel;
    private HashMap<Integer, Integer> visitedNodeLabels;
    private HashMap<Integer, Integer> refVisitedNodeLabels;
    public boolean equals(BDDTree referenceTree)
    {
        visitedNodeLabels = new HashMap();
        refVisitedNodeLabels = new HashMap();
        visitedNodeLabels.put(0, 0);
        visitedNodeLabels.put(1, 1);
        refVisitedNodeLabels.put(0, 0);
        refVisitedNodeLabels.put(1, 1);
        nextLabel = 2;
        return equalsDFS(referenceTree, this.getRootIndex(), referenceTree.getRootIndex());
    }
    
    private boolean equalsDFS(BDDTree referenceTree, int thisNodeIndex, int refNodeIndex)
    {   
        // Special case at terminals
        if ((thisNodeIndex == 0 || thisNodeIndex == 1) && (refNodeIndex == 0 || refNodeIndex == 1))
            return (this.getNode(thisNodeIndex).terminalValue == referenceTree.getNode(refNodeIndex).terminalValue);
        // Isomorphic nodes are visited together, so if their visted status is mismatched, they are not isomorphic. 
        if (visitedNodeLabels.containsKey(thisNodeIndex) != refVisitedNodeLabels.containsKey(refNodeIndex))
            return false;
        // If the local and refernce nodes have both been visited, are they isomorphic?
        if (visitedNodeLabels.containsKey(thisNodeIndex))
        {
            if (visitedNodeLabels.get(thisNodeIndex) == refVisitedNodeLabels.get(refNodeIndex))
                return true;
            else
                return false;
        }
              
        
        // If we haven't yet visited these nodes, see if the subgraphs are isomorphic.
        Node thisNode = this.getNode(thisNodeIndex);
        Node refNode = referenceTree.getNode(refNodeIndex);
        
        if (thisNode.inputIndex != refNode.inputIndex)
            return false;
        
        boolean lowResult = equalsDFS(referenceTree, thisNode.low, refNode.low);
        if (!lowResult)
            return false;
        
        boolean highResult = equalsDFS(referenceTree, thisNode.high, refNode.high);
        if (!highResult)
            return false;
        
        // Both children are isomorphic, so the parent is too!
        visitedNodeLabels.put(thisNodeIndex, nextLabel);
        refVisitedNodeLabels.put(refNodeIndex, nextLabel);
        nextLabel++;
        return true;
        
    }
    
    /**
     * Obliterate an input variable.  Only use this if there are no nodes for
     * this variable, i.e. if the function doesn't depend on it!
     */
    public void collapseInput(int var)
    {
        // FIXME Should *really* check to make sure there are no nodes for this input.  This has been a problem before.
        this.numInputs--;
        modifyNodeInputIndex(0, -1);
        if (nodes.size() > 1) // In the case of an all-false-output function, the true node will not exist.
            modifyNodeInputIndex(1, -1);
        for (int i = 2; i < nodes.size(); i++)
        {
            if (nodes.get(i).inputIndex > var)
                modifyNodeInputIndex(i, -1);
        }
    }
    
    public Node getNode(int index)
    {
        return nodes.get(index);
    }
    
    public int getNodeIndex(Node n)
    {
        return nodesHash.get(n);
    }
    
    public Node getRootNode()
    {
        return nodes.get(getRootIndex());
    }
    
    public int getRootIndex()
    {
        return nodes.size() - 1;
    }
    
    public void setRootIndex(int r)
    {
        int oldRoot = this.getRootIndex();
        while (r < oldRoot)
        {
            this.removeNode(oldRoot);
            oldRoot = this.getRootIndex();
        }
    }
    
    /**
     *  Careful!  addNode(Node) makes assumptions about the indices. 
     */
    private void addNode(Node n, int index)
    {
        nodes.add(index, n);
        nodesHash.put(n, index);
    }
    
    public int addNode(Node n)
    {
        nodes.add(n);
        int index = nodes.size() - 1;
        nodesHash.put(n, index);
        return index;
    }
    
    /**
     * Use carefully!
     */
    public void removeNode(int index)
    {
        nodesHash.remove(nodes.get(index));
        nodes.remove(index);
    }
    
    public boolean contains(Node n)
    {
        return nodesHash.containsKey(n);
    }
}
