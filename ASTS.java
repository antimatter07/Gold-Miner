import java.util.*;


public class ASTS {
  //smart level of rationality


  private static int scanCount = 0;

  //A* search: https://www.peachpit.com/articles/article.aspx?p=101142&seqNum=2
  public AStarNode AStarSearch(Board board) {

  
    //list of successor nodes after expanding
    ArrayList<AStarNode> successors = new ArrayList<AStarNode>();

    //fringe is the frontier of unexplored nodes
    //insteadd of LIFO, it is ordered by ascending f(n) cost
    PriorityQueue<AStarNode> fringe = new PriorityQueue<AStarNode>(10000000);
    AStarNode root;
    AStarNode node;
  
    //root is the initial pos of Miner
    root = new AStarNode(board.getMiningArea()[0][0]);
    root.setMiner(board.getMiner());

    fringe.add(root);

    //while there are nodes to explore and goal node is not reached, look for solution
    while(!fringe.isEmpty()) {

        //take latest unexplored node and expand
        node = fringe.remove();

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

  public ArrayList<AStarNode> expand(AStarNode node, Board board) {
    ArrayList<AStarNode> successors = new ArrayList<AStarNode>(4);
    int gCost;
    //flag for detecting if miner has moved while generating states
    //if this remains false, miner is stuck in infinite rotating loop
    boolean hasMoved = false;
  
    

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
      successors.get(i).getMiner().scan(board) != UnitType.PIT) {

        hasMoved = true;
        scanCount++;
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

     //if miner has not changed position for any node generated
        //move forward on any random node to prevent being stuck rotating forever
        if(hasMoved == false) {
            Random rand = new Random();
            int index;

            

            while(hasMoved == false) {

              index = rand.nextInt(4);

              if(successors.get(index).getMiner() != null 
              && successors.get(index).getMiner().canMoveForward(board)) {

                successors.get(index).getMiner().move(board);
                //add M to track actions done to get to goal node
                  successors.get(index).getMiner().move(board);
              
                 successors.get(index).addAction("M");
                 successors.get(index).setParent(node);
                 successors.get(index).setSquare(board.getMiningArea()[successors.get(index).getMiner().getRow()][successors.get(index).getMiner().getCol()]);
                  hasMoved = true;
                  successors.get(index).incrementGCost();
              }
              
            }
        }

    
    return successors;
  }


  public static void duplicateNode(AStarNode origNode, AStarNode dupNode) {

      dupNode.setSquare(origNode.getSquare());
      dupNode.setMiner(origNode.getMiner());
      dupNode.setActions(origNode.getActions());
      dupNode.setParent(origNode.getParent());


  }

  public void resetScanCount() {
      scanCount = 0;
  }

  public int getScanCount () {
      return scanCount;
  }

}