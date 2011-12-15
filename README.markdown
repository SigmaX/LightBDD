# LightBDD: Binary Decision Diagrams in Java

LightBDD is a bare-bones library for building and combining Reduced Ordered Binary Decision Diagrams, which are memory-efficient data structures for storing the truth tables of large boolean functions.

BDDs are used extensively in logic circuit design software and select computer science problems.  They can be seen as a binary analog to Directed Acyclic Word Graphs (DAWGs), which use the same graph-based mechanism to reduced the memory footprint of, for instance, spelling dictionaries.  For more on BDDs, see Henrik Reif Anderson's excellent [Introduction to Binary Decision Diagrams][1]

LightBDD's aim is to be easy to use for basic tasks, and has no pretense of competing with industrial-strength BDD libraries in terms of efficiency.

## References

[1]: http://www.cs.unb.ca/~gdueck/courses/cs4835/bdd97.pdf "Henrik Reif Anderson, 'An Introduction to Binary Decision Diagrams,' 1997."
