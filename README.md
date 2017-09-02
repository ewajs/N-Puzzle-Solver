# N-Puzzle-Solver
A solution to the traditional game 8-Puzzle for N by N boards. 
The program uses the A* search algorithm to find the optimum (least moves made) solution.
This is an excercise in Coursera's Princeton Algorithms Part I Course.

## Compiling
To compile its necessary to have package edu.princeton.cs.algs4 installed.

```
javac Board.java
javac Solver.java
```

## Running

```
java Solver path/to/input_file.txt
```

## Input Files

Input files should be formatted as following:
1st line: grid length (n)
2nd line and the rest: The numbers of the board separated by spaces and a 0 in place of the empty tile.

Sample input files are provided in the 8puzzle folder, these are the ones provided by the Course to check correctness.

## Output

The program outputs the number of moves required to solve the puzzle (if it's solvable) and then each move that's needed to solve it in minimum amount of moves.

