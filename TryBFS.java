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
// import java.util.Scanner;
// import java.util.ArrayList;


// //random level of rationality
// public class TryBFS {
//   public static void main(String[] args) {
    
//     Scanner scan = new Scanner(System.in);
//     int input;
    
    
      
//         do{
//           System.out.print("Input Board Size: ");
//           input = scan.nextInt();

//           if(input < 8 || input > 64){
//             System.out.println("Input Invalid");
//           }

//         }while(input < 8 || input > 64);

//         Board board = new Board(input);
//         Node solution;
//         solution = BFS(board);

//         System.out.println("R: ROTATE, M: MOVE FORWARD. MINER INITIALLY FACING NORTH.");
//         System.out.println("ACTIONS TO GET TO THE GOLD: " + solution.getActions());
//         board.displayBoard();


//     scan.close();

//   }
//   //returns solution Node with path to the Gold
//   public static Node BFS(Board board) {

  
//     //list of successor nodes after expanding
//     ArrayList<Node> successors = new ArrayList<Node>();
//     //fringe is the frontier of unexplored nodes
//     Queue fringe = new Queue(100000);
//     Node root;
//     Node node;
  
//     //root is the initial pos of Miner
//     root = new Node(board.getMiningArea()[0][0]);
//     root.setMiner(new Miner());

//     fringe.enqueue(root);

//     //while there are nodes to explore and goal node is not reached, look for solution
//     while(!fringe.isEmpty()) {

//         //take latest unexplored node and expand
//         node = fringe.dequeue();
        
        
      
//         System.out.println("Have we reached goal:  " + node.isGoal());

//         if(node.isGoal())
//             return node;

//         successors = expand(node, board);
        
        
//         //for every successor generated that has not been visited, add to list of unexplored
//         //nodes
//         for(int i = 0; i < successors.size() && successors.get(i) != null; i++) {

//             successors.get(i).getMiner().getCol();

//             //if not visited and state exists, enqueue to open list
//             if(board.getMiningArea()[successors.get(i).getMiner().getRow()]
//                [successors.get(i).getMiner().getCol()].isVisited == false 
//                && successors.get(i).getMiner() != null)
//                 fringe.enqueue(successors.get(i));

                

            
//         }

//         //set as visited so that miner will not redundantly expand nodes already explored
//         board.getMiningArea()[node.getMiner().getRow()][node.getMiner().getCol()].setVisited();
        
//         successors.clear();
        

        
//     }

//     return null;

//   }

//   public static ArrayList<Node> expand(Node node, Board board) {
//     ArrayList<Node> successors = new ArrayList<Node>(4);
  
//     System.out.println("ARRAYLIST SIZE: " + successors.size());

//     //for every valid action, generate new states as a result of doing that action
//     for(int i = 0; i < 4; i++) {

       
//         successors.add(i, new Node());
        
//         duplicateNode(node, successors.get(i));

//         //rotate 0, 1, 2, then 3 times for all possible resulting states when moving
//         for(int j = 0; j < i; j++) {

//             //add R to track actions done to get to goal node

//             successors.get(i).addAction("R");
//             successors.get(i).setParent(node);
//             successors.get(i).getMiner().rotate();
            
           

//         }
//         //if miner can move and scanned is not pit, move forward
//         if(successors.get(i).getMiner().canMoveForward(board) && 
//         (successors.get(i).getMiner().scan(board) == UnitType.GOLD|| 
//         successors.get(i).getMiner().scan(board) == UnitType.BEACON || 
//         successors.get(i).getMiner().scan(board) == UnitType.EMPTY)) {

//             successors.get(i).setParent(node);

//             //add M to track actions done to get to goal node
//             successors.get(i).addAction("M");
            
//             successors.get(i).getMiner().move(board);
            
//             successors.get(i).setSquare(board.getMiningArea()[successors.get(i).getMiner().getRow()][successors.get(i).getMiner().getCol()]);
            
//         }

            
        
//     }

    
//     return successors;
// }


// public static void duplicateNode(Node origNode, Node dupNode) {

//     dupNode.setSquare(origNode.getSquare());
//     dupNode.setMiner(origNode.getMiner());
//     dupNode.setActions(origNode.getActions());
//     dupNode.setParent(origNode.getParent());


// }

  
// }

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
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.*;


// EDITED TO WORK WITH GUI
//random level of rationality 
public class TryBFS {

    private static Board board;
    private static int pathCost = 0;

    //list of successor nodes after expanding
    private ArrayList<Node> successors;
    private Queue fringe; //fringe is the frontier of unexplored nodes
    private Node root;
    private Node node;
    private Node solution;

    public TryBFS(Board board) {
        this.board = board;

        successors = new ArrayList<Node>();
        fringe = new Queue(100000);
        //root is the initial pos of Miner
        root = new Node(board.getMiningArea()[0][0]);
        root.setMiner(new Miner());

        fringe.enqueue(root);
    }

    //returns solution Node with path to the Gold
    public Node BFS() {

        //while there are nodes to explore and goal node is not reached, look for solution
        while (!fringe.isEmpty()) {

            //take latest unexplored node and expand
            node = fringe.dequeue();


            System.out.println("Have we reached goal:  " + node.isGoal());

            if (node.isGoal())
                return node;

            successors = expand(node, board);


            //for every successor generated that has not been visited, add to list of unexplored
            //nodes
            for (int i = 0; i < successors.size() && successors.get(i) != null; i++) {

                successors.get(i).getMiner().getCol();

                //if not visited and state exists, enqueue to open list
                if (board.getMiningArea()[successors.get(i).getMiner().getRow()]
                        [successors.get(i).getMiner().getCol()].isVisited() == false
                        && successors.get(i).getMiner() != null)
                    fringe.enqueue(successors.get(i));

            }

            //set as visited so that miner will not redundantly expand nodes already explored
            board.getMiningArea()[node.getMiner().getRow()][node.getMiner().getCol()].setVisited();

            successors.clear();

        }

        return null;

    }

    public static ArrayList<Node> expand(Node node, Board board) {
        ArrayList<Node> successors = new ArrayList<Node>(4);

        System.out.println("ARRAYLIST SIZE: " + successors.size());

        //for every valid action, generate new states as a result of doing that action
        for (int i = 0; i < 4; i++) {


            successors.add(i, new Node());

            duplicateNode(node, successors.get(i));

            //rotate 0, 1, 2, then 3 times for all possible resulting states when moving
            for (int j = 0; j < i; j++) {

                //add R to track actions done to get to goal node

                successors.get(i).addAction("R");
                successors.get(i).setParent(node);
                successors.get(i).getMiner().rotate();
            }
            //if miner can move and scanned is not pit, move forward
            if (successors.get(i).getMiner().canMoveForward(board) &&
                    (successors.get(i).getMiner().scan(board) == UnitType.GOLD ||
                            successors.get(i).getMiner().scan(board) == UnitType.BEACON ||
                            successors.get(i).getMiner().scan(board) == UnitType.EMPTY)) {

                successors.get(i).setParent(node);

                //add M to track actions done to get to goal node
                successors.get(i).addAction("M");

                successors.get(i).getMiner().move(board);
                successors.get(i).setSquare(board.getMiningArea()[successors.get(i).getMiner().getRow()][successors.get(i).getMiner().getCol()]);
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
}