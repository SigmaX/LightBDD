package LightBDD;

/**
 * Interface for the anonymous boolean functions you might want to pass into
 * BDD.apply(), since lovely Java doesn't support lambda expressions :-/.
 * 
 * @author Eric 'Siggy' Scott
 */
public interface BooleanOperator
{
    public boolean operate(boolean x, boolean y);
}
