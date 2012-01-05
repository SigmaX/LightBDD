/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LightBDD;

/**
 * Graph objects can return dot code, which can be used by Graphviz to generate
 * images of them.
 * 
 * @author Eric 'Siggy' Scott
 */
public interface Graph
{
    /**
     * Generate a dot graph from this object.
     */
    public String toDot(String name);
    /**
     * Generate a dot cluster from this object.
     */
    public String toSubDot(String id);
}
