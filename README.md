## Knight Shortest Path Exercise


### Requirements/Description

>- Create a Java application that should represent an empty chessboard where the user will be able to enter a starting position and an ending position.
>- The application should then calculate a list of all possible paths that one knight piece in the starting position could take to reach the ending position in 3 moves.
>- Some inputs might not have a solution, in this case the program should display a message that no solution has been found.
>- Otherwise, the shortest path (if that exists) should be returned.
> 

### Approach
We assume that we need to calculate more than 1 paths and actually build a list of all  
possible paths even if those include more steps to reach the target position on the board.  
This hints that a BFS (Breadth First Search) solution is actually redundant here. BFS is ideal  
in situations where we only care about the shortest path, and since BFS fans out in a specific way  
the returned solution is always guaranteed to be the most optimal solution.

Hence, we used DFS (Depth First Search) instead and we've accepeted that the output graph can include  
cycles. We make this assumption as DFS doesn't really make much sense without backtracking in this space.

If a path is not possible (given the 3 moves restriction) then we output that a path was not found.  
If we do find a path or a series of paths we sort the list of paths and print the shortest and then  
all remaining paths (just for reference).

### Implementation Decisions
The application was written mostly around the algorithm which is rather simple. Extra care was taken  
in order to not lock us into a specific algorithm or piece (a Knight's move) or even a board (a chessboard).  
This was achieved using inheritance/composition and relying on interfaces rather on concrete implementations  
where applicable. Additionally, various design patterns were used to make most components pluggable  
and replaceable.

Unit tests are provided for most edge cases and through the use of Parameterized tests which help  
reduce duplication in this scenario.

### User Input
When the app starts it'll prompt the user for the knight and target positions. This might not be  
easy to spot but a log message will propmt you for input and you can just type the positions in order.


### Build/Run/Test
This is a springboot application so you can use the springboot maven runner to build & run the app.
```bash
$ mvn spring-boot:run
```
* if you run into any issues please don't hesitate to reach out. The app is compiled with j11.

Alternative to build and run the tests simply run:
```bash
$ mvn clean install
```
from a terminal session or build & run the project in your IDE.

### Extensions/Improvements
There are various algorithms we could use to improve the DFS search. One approach  
(which I started exploring) is the idea of BiDirectional Search. This problem heavily benefits from  
searching from both directions and stopping when a collision is found. This should improve O to 
roughly O(b^d/2 +b^d/2) where b and d are the start (Knight's position) and end (target's position)  
positions on the board. The way to implement something like this would be with AdjacencyLists.  
`AdjacentKnightPosition`, which is very badly named :), is a very brief effort to achieve this  
and requires the implementation of the actual algorithm and refactoring of various  
interfaces/abstract classes to make the implementation fit around the existing one and be interchangeable  
in a nice way.

Benchmarking is another addition I'd make if time allowed. We can use JMH to write a very simple  
benchmarking suit which will actually give us a standard to compare against while improving the code  
or actually comparing different search implementations.