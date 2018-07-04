package LightBDD;

public class Example
{
    public static void main(final String[] args)
    {
	final BooleanFunction myFunction = new BooleanFunction("myFunc", 3, 1) {
	    public boolean[] execute(boolean[] input)
	    {
		assert(input.length == 3);
		return new boolean[] { input[0] ^ input[1] && input[2] };
	    }
	};

	System.out.println("Custom function " + myFunction.getName() + " has the following truth table:");
	System.out.println(myFunction.printTruthTable());

	System.out.println("Building BDD...");
	final BDD myBDD = new BDD(myFunction, 0);

	System.out.println("The BDD has the following truth table (should be identical):");
	System.out.println(myBDD.printTruthTable());

	System.out.println("And the BDD represents the function with the folowing graph:");
	System.out.println(myBDD.toDot("My_BDD"));
    }
}
