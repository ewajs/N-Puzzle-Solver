import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    
    private Node lastNode, lastNodeTwin;
    
    private class Node implements Comparable<Node>{ // Nodes for list
        
        Board board;
        Node prev;
        int moves;
        int priority;
        
        public Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }
        
        public int compareTo(Node that) {
            return (this.priority - that.priority);
        }
    }
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        lastNode = new Node(initial, null, 0);
        
        if (initial.isGoal()){
            return;
        }
       
        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> pqTwin = new MinPQ<Node>();
        
        
        //pq.insert(new Node(initial, null, 0)); // Start the Queue with inital board
     
        lastNodeTwin = new Node(initial.twin(), null, 0);
        for (Board b : lastNode.board.neighbors()) {
            pq.insert(new Node(b, lastNode, lastNode.moves + 1));
        }
        for (Board bTwin : lastNodeTwin.board.neighbors()) {
            pqTwin.insert(new Node(bTwin, lastNodeTwin, lastNodeTwin.moves + 1));
        }
        lastNode = pq.delMin();
        lastNodeTwin = pqTwin.delMin();
        while (!(lastNode.board.isGoal() || lastNodeTwin.board.isGoal())) {
            for (Board b : lastNode.board.neighbors()) {
                if (!b.equals(lastNode.prev.board)) {
                    pq.insert(new Node(b, lastNode, lastNode.moves + 1));
                }
            }
            for (Board bTwin :  lastNodeTwin.board.neighbors()){
                if(!bTwin.equals(lastNodeTwin.prev.board)) {
                    pqTwin.insert(new Node(bTwin, lastNodeTwin, lastNodeTwin.moves + 1));
                }
            }
            lastNode = pq.delMin();
            lastNodeTwin = pqTwin.delMin();
        }
            
    }
        
      
    public boolean isSolvable() {            // is the initial board solvable?
        return lastNode.board.isGoal();
    }
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        if(isSolvable()) {
            return lastNode.moves;
        } else {
            return -1;
        }
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        if(isSolvable()) {
            Stack<Board> stack = new Stack<Board>();
            Node tracer = lastNode;
            while (tracer != null) {
                stack.push(tracer.board);
                tracer = tracer.prev;
            }
            return stack;
        } else {
            return null;
        }
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}