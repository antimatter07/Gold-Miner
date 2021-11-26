 /* Some notes from the slides that can help with coding the search algo :

        given a state s, ACTIONS(s) returns a finite set of actions taht can be executed in s
        RESULT(s, a) returns the state that results from doing action a in state s

        node.STATE: the state to which the node corresponds;
        node.PARENT: the node in the tree that generated this node;
        node.ACTION: the action that was applied to the parent’s state to generate this node;
        node.PATH-COST: the total cost of the path from the initial state to this node

        IS-EMPTY(frontier) returns true only if there are no nodes in the frontier.
        POP(frontier) removes the top node from the frontier and returns it.
        TOP(frontier) returns (but does not remove) the top node of the frontier.
        ADD(node, frontier) inserts node into its proper place in the queue.

    
     */
import java.util.Scanner;
import java.util.ArrayList;

public class TryBFS {
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    int input;
    
    
      
        do{
          System.out.print("Input Board Size: ");
          input = scan.nextInt();

          if(input < 8 || input > 64){
            System.out.println("Input Invalid");
          }

        }while(input < 8 || input > 64);

        Board board = new Board(input);
        Node solution;
        solution = BFS(board);

        System.out.println("R: ROTATE, M: MOVE FORWARD. MINER INITIALLY FACING NORTH.");
        System.out.println("ACTIONS TO GET TO THE GOLD: " + solution.getActions());
        board.displayBoard();


       
        
    

    



    scan.close();

  }
  //returns solution Node with path to the Gold
  public static Node BFS(Board board) {

    ArrayList<Node> visited = new ArrayList<Node>();
    //list of successor nodes after expanding
    ArrayList<Node> successors = new ArrayList<Node>();
    //fringe is the frontier of unexplored nodes
    Queue fringe = new Queue(100000);
    Node root;
    Node node;
  
    //root is the initial pos of Miner
    root = new Node(board.getMiningArea()[0][0]);
    root.setMiner(new Miner());

    fringe.enqueue(root);

    //while there are nodes to explore and goal node is not reached, look for solution
    while(!fringe.isEmpty()) {

        //take latest unexplored node and expand
        node = fringe.dequeue();
        visited.add(node);
        
      
        System.out.println("Have we reached goal:  " + node.isGoal());

        if(node.isGoal())
            return node;

        successors = expand(node, board);
        visited.add(node);
        
        //for every successor generated that has not been visited, add to list of unexplored
        //nodes
        for(int i = 0; i < successors.size() && successors.get(i) != null; i++) {

            successors.get(i).getMiner().getCol();

            if(!isVisited(visited, node) == false)
                fringe.enqueue(successors.get(i));

            
        }

        successors.clear();
        

        
    }

    return null;

  }

  public static ArrayList<Node> expand(Node node, Board board) {
    ArrayList<Node> successors = new ArrayList<Node>();
    Node newNode = new Node();
    Node newNode2 = new Node();

    //hindi pwede yung assignmenet statement since it will aloways point
    //to original node :<
    duplicateNode(node, newNode2);
    duplicateNode(node, newNode);


    
    //newNode.getSquare().getMiner().move(board.getMiningArea());
    //System.out.println("COL!!" + newNode.getMiner().getCol());
    //System.out.println("INIT CONTENTS OF NEWNODE AND 2: " + newNode.getMiner().getRow() + 
    //newNode.getMiner().getCol() + " " + newNode2.getMiner().getRow() + newNode2.getMiner().getCol());
    //System.out.println("INIT ACTIONS OF NEWNODE AND 2: " + newNode.getActions() + "\n" + newNode2.getActions());

    //for every valid action, generate new states as a result of doing that action
    for(int i = 0; i < 2; i++) {
        
       
        //System.out.println("ACTIONS OF NEWNODE: " + newNode.getActions());
        //newNode.getSquare().getMiner().move(board.getMiningArea());

        //if miner does not scan pit in front, move forward
        if(i == 0  && 
        (newNode.getMiner().scan(board) == UnitType.GOLD || 
        newNode.getMiner().scan(board) == UnitType.BEACON || 
        newNode.getMiner().scan(board) == UnitType.EMPTY)) {
            
            
            
            
            newNode.setParent(node);

            //add M to track actions done to get to goal node
            newNode.addAction("M");
            
            newNode.getMiner().move(board);
           
            newNode.setSquare(board.getMiningArea()[newNode.getMiner().getRow()][newNode.getMiner().getCol()]);
            
            
            

            System.out.println(newNode.getMiner().getRow());
            successors.add(newNode);
            
            

        } else if(i == 1) {
            //System.out.println("ACTIONS OF NEWNODE2: " + newNode2.getActions());

            //add R to track actions done to get to goal node
            newNode2.addAction("R");
            newNode2.setParent(node);
            newNode2.getMiner().rotate();
            
            successors.add(newNode2);

        }

            
        
    }


    return successors;
}


public static void duplicateNode(Node origNode, Node dupNode) {

    dupNode.setSquare(origNode.getSquare());
    dupNode.setMiner(origNode.getMiner());
    dupNode.setActions(origNode.getActions());
    dupNode.setParent(origNode.getParent());


}

public static boolean isVisited(ArrayList<Node> visited, Node currentNode) {

    boolean isVisited = false;

    for(int i = 0; i < visited.size() && isVisited == false; i++) {

        if(currentNode.getMiner().getFront() == visited.get(i).getMiner().getFront()) {

            if(currentNode.getMiner().getRow() == visited.get(i).getMiner().getRow() 
            && currentNode.getMiner().getCol() == visited.get(i).getMiner().getCol()) 
                isVisited = true;
        }
    }

    return isVisited;
}

  
}