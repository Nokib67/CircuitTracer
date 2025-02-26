****************
* CircuitTracer
* CS 221 
* 23 April 2024
* Nokib Hossain
**************** 

OVERVIEW:

 CircuitTracer is a Java program designed to find the shortest path 
 for connecting a pair of components on a circuit board, as defined in an input file. 
 The program reads the board's layout and computes optimal routing paths
 using a brute-force search algorithm, where the pathfinding can utilize either a stack or a queue 
 to manage the search states.


INCLUDED FILES:

* CircuitBoard.java - Parses the input file and initializes the board layout.
* CircuitTracer.java - Main class that handles command-line arguments, initiates the board setup, and finds the shortest path.
* Storage.java - Implements both stack and queue data structures to store path states during the search.
* TraceState.java - Represents a single state in the pathfinding process.
* InvalidFileFormatException.java - Custom exception for handling input file errors.
* OccupiedPositionException.java - Custom exception for handling errors when trying to place a trace on an occupied spot.
* README.md - Documentation file (this file).


COMPILING AND RUNNING:

"Usage: java CircuitTracer -s|-q -c|-g filename"

 To compile the program:

  $ javac CircuitTracer.java

 To run the program:

  $ java CircuitTracer -s -c filename

Replace `-s` with `-q` to use a queue instead of a stack for storage,
replace `-c` with `-g` for GUI mode (not implemented in this version ), and filename with your specific input file.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 CircuitTracer employs a brute-force search algorithm to explore all possible paths 
 from a start component ('1') to an end component ('2') on a circuit board. 
 The choice of data structure (stack or queue) for storing the search states influences 
 the pathfinding behavior which can be either a stack or queue.The core algorithm is designed to be efficient and adaptable, supporting easy switching between stack and queue to compare performance implications.

TESTING:

 Testing involved automated unit tests to validate the functionality of each component
 under various scenarios, including valid and invalid input files. 
 The testing strategy ensured that the program could handle incorrect formats, 
 missing data, and operational edge cases without crashing.


DISCUSSION:
 
 Developing CircuitTracer provided a deep dive into pathfinding algorithms 
 and data structure efficiency. Challenges included Debugging the search process and 
 ensuring robust file parsing. 
 Debugging required meticulous attention to how paths were constructed and traced back.
 
 
EXTRA CREDIT:

<No Extra credit work is done for this project>

ANAlYSIS:

 1.How does the choice of Storage configuration (stack vs queue) affect the 
 sequence in which paths are explored in the search algorithm? 
 (This requires more than a "stacks are LIFOs and queues are FIFOs" answer.)
 Ans :
 The choice between a stack (LIFO) and a queue (FIFO) 
 fundamentally changes the order in which paths are explored:

 Stack (LIFO): Using a stack causes the algorithm to explore paths in a depth-first search manner.
  This means the algorithm will continuously extend the most recently discovered path until
  it hits a dead end or finds a solution. 
  This approach dives deep into one path direction before backtracking and exploring other possibilities.
 Queue (FIFO): Using a queue implements a breadth-first search approach. The algorithm will explore 
  all paths one step at a time, expanding all paths discovered at each depth before moving to paths 
  that are one step longer. This ensures a level-by-level exploration of the search space.


  2.Is the total number of search states (possible paths) affected by the choice of stack or queue?
  Ans :
  No. Since the algorithm is brute-force,regardless which storing data structure has been used,the total
  number of search states will be the same but the order will be different.

  3.Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?
  Ans:
  Stack is more likely to find the solution in fewer steps because it works on one solution at a time while 
  the queue works on multiple solutions simultaneously. 

  4.Does using either of the storage structures guarantee that the first solution found will be a shortest path?
  Ans :
  Queues guarantee that the first solution will be the shortest path because it looks at all the solutions at 
  the same time and lets us know what the shortest path will be. 

  5.How is memory use (the maximum number of states in Storage at one time) affected by the choice of
   underlying structure?
   Ans:
   Queues use the more amount of memory than the stack because it works on all paths or solutions at 
   the same time simultaneously.

  6.What is the Big-Oh runtime order for the search algorithm?
   What does the order reflect? (Maximum size of Storage? Number of board positions? 
   Number of paths explored? Maximum path length? Something else?)
   What is 'n', the single primary input factor that increases the difficulty of the task?
   Ans:
   The BigO runtime order for the brute-force search algorithm is O(n^k), where n is the average number of moves
   possible from any given position, and k is the depth of the longest path necessary to solve the problem.It reflects
   the number of board positions and the number of paths explored. 
   The available number of board positions is the primary input factor that increases the difficulty of the task. 
 

