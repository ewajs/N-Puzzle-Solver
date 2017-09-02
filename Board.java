import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] board;
//    private final int[][] goal;
    private int n;
    
    public Board(int[][] blocks) {         // construct a board from an n-by-n array of blocks
        
        if (blocks.length != blocks[0].length) {
            throw new java.lang.IllegalArgumentException();
        }
        
        n = blocks.length;
        board = new int[n][n];
        //goal = new int[n][n];
        
        for (int i = 0; i < n; i++) { // save the block in an inmmutable array
            for (int j = 0; j < n; j++) {
                board[i][j] = blocks[i][j];
            }
        }
        
//        int k = 1;
//        for (int i = 0; i < n; i++) { // compute goal
//            for (int j = 0; j < n; j++) {
//                if (k == n*n) {
//                    k = 0;
//                }
//                goal[i][j] = k;
//                k++;
//            }
//        }
                
    }                                      // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                // board dimension n
        return n;
    }
    public int hamming() {                  // number of blocks out of place
        int outOfPlace = 0;
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] !=  k && board[i][j] != 0) {
                    outOfPlace++;
                }
                k++;
            }
        }
        return outOfPlace;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int distance = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance += distanceToGoalSpot(j+1, i+1, board[i][j]);
            }
        }
        return distance;
    }
    
    private int distanceToGoalSpot(int x, int y, int tile) {
        if(tile == 0) {
            return 0;
        }
        int correctx = tile % n;
        int correcty = tile / n + 1;
        if (correctx == 0) {
            correctx = n;
            correcty = tile / n;
        }
        return Math.abs(correctx - x) + Math.abs(correcty - y);
    }
        
    
    public boolean isGoal() {                // is this board the goal board?
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] !=  k) {
                    return false;
                }
                if(++k == n*n) {
                    break;
                }
            }
        }
        return true;
    }
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
       int twinBoard[][] = new int[n][n];
       
       for (int i = 0; i < n; i++) { // create copy
            for (int j = 0; j < n; j++) {
                twinBoard[i][j] = board[i][j];
            }
        }
       
       int i = 0, j = 0, k = 0;
       
       if (twinBoard[i][j] == 0) {
          j++;
       } else if (twinBoard[i+1][k] == 0) {
          k++;
       }
       
       int aux = twinBoard[i][j];
       twinBoard[i][j] = twinBoard[i+1][k];
       twinBoard[i+1][k] = aux;
       
       return new Board(twinBoard);
           
    }
    public boolean equals(Object y) {        // does this board equal y?
        
        if (y == this) return true; //true equality
        
        if (y == null) return false; // null
        
        if (y.getClass() != this.getClass()) return false; // Different classes can't be equal.
        
        Board that = (Board) y;
        
        if (this.dimension() != that.dimension()) return false; // Different dimensions board can'y be equal
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;   
    }
    public Iterable<Board> neighbors() {     // all neighboring boards
        Stack<Board> stack = new Stack<Board>();
        int zeroi = n, zeroj = n; // these values should never persist
        int neighbor[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                }
                neighbor[i][j] = board[i][j];
            }
        }
        if (zeroi > 0) { // if row is greater than the first, we can move 0 up
            //swap
            neighbor[zeroi][zeroj] = neighbor[zeroi-1][zeroj]; 
            neighbor[zeroi-1][zeroj] = 0;
            // push and revert swap
            stack.push(new Board(neighbor)); 
            neighbor[zeroi-1][zeroj] = neighbor[zeroi][zeroj]; 
            neighbor[zeroi][zeroj] = 0;
        }
        if (zeroi < n - 1) { // if row is less than last, we can move 0 down
            //swap
            neighbor[zeroi][zeroj] = neighbor[zeroi+1][zeroj]; 
            neighbor[zeroi+1][zeroj] = 0;
            // push and revert swap
            stack.push(new Board(neighbor)); 
            neighbor[zeroi+1][zeroj] = neighbor[zeroi][zeroj]; 
            neighbor[zeroi][zeroj] = 0;
        }
        if (zeroj > 0) { // if col is greater than the first, we can move 0 left
            //swap
            neighbor[zeroi][zeroj] = neighbor[zeroi][zeroj-1]; 
            neighbor[zeroi][zeroj-1] = 0;
            // push and revert swap
            stack.push(new Board(neighbor)); 
            neighbor[zeroi][zeroj-1] = neighbor[zeroi][zeroj]; 
            neighbor[zeroi][zeroj] = 0;
        }
        if (zeroj < n - 1) { // if col is less than last, we can move 0 right
            //swap
            neighbor[zeroi][zeroj] = neighbor[zeroi][zeroj+1]; 
            neighbor[zeroi][zeroj+1] = 0;
            // push and revert swap
            stack.push(new Board(neighbor)); 
            neighbor[zeroi][zeroj+1] = neighbor[zeroi][zeroj]; 
            neighbor[zeroi][zeroj] = 0;
        }
        return stack;
            
    }
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        int board[][] = new int[3][3];
        int k = 0;
        for (int i = 0; i < board.length; i++) { // compute goal
            for (int j = 0; j < board.length; j++) {
                board[i][j] = k;
                k++;
            }
        }
        board[0][0] = 4;
        board[1][1] = 0;
        Board myboard = new Board(board);
        //Board otherBoard = new Board(board);
        System.out.println(myboard.toString());
        System.out.println(myboard.manhattan());
        System.out.println(myboard.hamming());
        System.out.println(myboard.isGoal());
        System.out.println(myboard.twin().toString());
//        System.out.println(myboard.equals(otherBoard));
//        System.out.println(otherBoard.equals(myboard));
//        System.out.println(myboard.equals(myboard));
//        System.out.println(myboard.equals(new int[4]));
//        System.out.println(myboard.equals(myboard.twin()));
        for (Board b : myboard.neighbors()) {
            System.out.println(b.toString());
        }
    }
}