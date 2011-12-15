# LightBDD: Binary Decision Diagrams in Java

LightBDD is a bare-bones library for building and combining Reduced Ordered Binary Decision Diagrams, which are memory-efficient data structures for storing the truth tables of large boolean functions.

# BDDs
BDDs are used extensively in logic circuit design software and select computer science problems.  They can be seen as a binary analog to [Directed Acyclic Word Graphs](http://en.wikipedia.org/wiki/Directed_acyclic_word_graph) (DAWGs), which use the same graph-based mechanism to reduced the memory footprint of, for instance, spelling dictionaries.  For more on BDDs, see Henrik Reif Anderson's excellent [Introduction to Binary Decision Diagrams][Anderson]

# Features

LightBDD's aim is to be easy to use for basic tasks, and has no pretense of competing with industrial-strength BDD libraries in terms of efficiency or completeness.  It has the following capabilities:

* Apply, Restrict, and Compose operations (See [Bryant])
* Automatic generation of `dot` code for graph visualization by [Graphiz](http://www.graphviz.org/)
* Multiple-output boolean function support (the MultiBDD class)

[Anderson]: http://www.cs.unb.ca/~gdueck/courses/cs4835/bdd97.pdf "Henrik Reif Anderson, 'An Introduction to Binary Decision Diagrams,' 1997."
[Bryan]: http://www.dtic.mil/cgi-bin/GetTRDoc?AD=ADA470446&Location=U2&doc=GetTRDoc.pdf "Randall Bryant, 'Graph-Based Algorithms for Boolean Function Manipulation,' _IEEE Transactions on Computers_, August 1986.
