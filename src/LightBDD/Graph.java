/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LightBDD;

/**
 * Graph objects can return dot code, which can be used by Graphiz to generate
 * images of them.
 * 
 * @author Eric 'Siggy' Scott
 */
public interface Graph
{
    public String toDot();
}
