import java.util.*;


public class TrySmartSearch {
  //smart level of rationality

  //A* search: https://www.peachpit.com/articles/article.aspx?p=101142&seqNum=2

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
        AStarNode solution;
        solution = AStarSearch(board);

        System.out.println("R: ROTATE, M: MOVE FORWARD. MINER INITIALLY FACING NORTH.");
        System.out.println("ACTIONS TO GET TO THE GOLD: " + solution.getActions());
        board.displayBoard();


    scan.close();

  }
  public static AStarNode AStarSearch(Board board) {

  
    //list of successor nodes after expanding
    ArrayList<AStarNode> successors = new ArrayList<AStarNode>();

    //fringe is the frontier of unexplored nodes
    //insteadd of LIFO, it is ordered by ascending f(n) cost
    PriorityQueue<AStarNode> fringe = new PriorityQueue<AStarNode>(10000000);
    AStarNode root;
    AStarNode node;
  
    //root is the initial pos of Miner
    root = new AStarNode(board.getMiningArea()[0][0]);
    root.setMiner(new Miner());

    fringe.add(root);

    //while there are nodes to explore and goal node is not reached, look for solution
    while(!fringe.isEmpty()) {

        //take latest unexplored node and expand
        node = fringe.remove();
        
        
      
        System.out.println("Have we reached goal:  " + node.isGoal());

        if(node.isGoal())
            return node;

        successors = expand(node, board);
        
        
        //for every successor generated that has not been visited, add to list of unexplored
        //nodes
        for(int i = 0; i < successors.size() && successors.get(i) != null; i++) {

            successors.get(i).getMiner().getCol();

            //if not visited and state exists, enqueue to open list
            if(board.getMiningArea()[successors.get(i).getMiner().getRow()]
               [successors.get(i).getMiner().getCol()].isVisited == false 
               && successors.get(i).getMiner() != null)
                fringe.add(successors.get(i));

                

            
        }

        //set as visited so that miner will not redundantly expand nodes already explored
        board.getMiningArea()[node.getMiner().getRow()][node.getMiner().getCol()].setVisited();
        
        successors.clear();
        

        
    }

    return null;

  }

  public static ArrayList<AStarNode> expand(AStarNode node, Board board) {
    ArrayList<AStarNode> successors = new ArrayList<AStarNode>(4);
    int gCost;
  
    

    //for every valid action, generate new states as a result of doing that action
    for(int i = 0; i < 4; i++) {

      gCost = 0;
      successors.add(i, new AStarNode());
        
      duplicateNode(node, successors.get(i));

      //rotate 0, 1, 2, then 3 times for all possible resulting states when moving
      for(int j = 0; j < i; j++) {

          //add R to track actions done to get to goal node

          successors.get(i).addAction("R");
          successors.get(i).setParent(node);
          successors.get(i).getMiner().rotate();

          //increment gCost everytime mienr rotates
          gCost++;
            
           

      }

      //if miner can move and scanned is not pit, move forward
      if(successors.get(i).getMiner().canMoveForward(board) && 
      (successors.get(i).getMiner().scan(board) == UnitType.GOLD|| 
      successors.get(i).getMiner().scan(board) == UnitType.BEACON || 
      successors.get(i).getMiner().scan(board) == UnitType.EMPTY)) {

        successors.get(i).setParent(node);

        //add M to track actions done to get to goal node
        successors.get(i).addAction("M");
              
        successors.get(i).getMiner().move(board);
              
        successors.get(i).setSquare(board.getMiningArea()[successors.get(i).getMiner().getRow()][successors.get(i).getMiner().getCol()]);
        
        //if miner moved, increment gCost
        gCost++;
      }

      successors.get(i).setGCost(gCost);
      successors.get(i).setHCost(board);

            
        
    }

    
    return successors;
  }


  public static void duplicateNode(AStarNode origNode, AStarNode dupNode) {

      dupNode.setSquare(origNode.getSquare());
      dupNode.setMiner(origNode.getMiner());
      dupNode.setActions(origNode.getActions());
      dupNode.setParent(origNode.getParent());


  }

}