# Strands Solver

This is a simple solver for the NYT strands crossword.

A trie is used to generate all possible word combinations in the grid.
DLX (Dancing Links AlgorithmX) is then used to find all exact coverings of the grid using the identified words.

This is intented as an exercise to demonstrate how to take a poorly structured codebase and refactor it.

