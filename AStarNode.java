import java.util.*;


public class AStarNode implements Comparable<AStarNode> {

    //Unit miner is
    private Unit state;
    private String actions = "";
    private Miner miner;
    
    private AStarNode parent;

    //g(n) path cost from prev node to get to this node
    private int gCost;
    //h(n) estimated path cost from current position to the goal
    private int hCost;
    public AStarNode() {

    }

    public AStarNode(Unit square) {
      gCost = 0;
      hCost = 0;
      this.state = square;

    }

    public void setGCost(int gCost) {
      this.gCost = gCost;
    }

    /*Manhattan distance: |x2 - x1| + |y2- y1| */
    public void setHCost(Board board) {
      hCost = Math.abs(board.getGoldCoord()[0] - miner.getRow()) +
      Math.abs(board.getGoldCoord()[1] - miner.getCol());
    }

    /**
     * This method is for the implementation of interface comparable.
     * Allows insertion into priority queue to be in ascending f(n) order
     * instead of LIFO.
     * 
     * @param otherNode other node to compare to
     */
    @Override
    public int compareTo(AStarNode otherNode) {
        if(this.getTotalCost() > otherNode.getTotalCost())
          return 1;
        else if(this.getTotalCost() < otherNode.getTotalCost())
          return -1;
        return 0;
    }

    public int getTotalCost() {
      return gCost + hCost;
    }

    public void setMiner(Miner m) {
      miner = new Miner(m.getRow(), m.getCol(), m.getFront());
  }

  public void setActions(String a) {
      actions = new String(a);
  }

  public Miner getMiner() {
      return miner;
  }


  public Unit getSquare() {
      return state;
  }

  public AStarNode getParent() {
      return parent;
  }

  public void setSquare(Unit s) {
      if(s instanceof Pit) 
          state = new Pit(s.getCoordinates(), 'P', UnitType.PIT);
      else if (s instanceof Gold) 
          state = new Gold(s.getCoordinates(), 'G', UnitType.GOLD);
      else if (s instanceof Beacon)
          state = new Beacon(s.getCoordinates(), 'B', UnitType.BEACON);
      else     
          state = new EmptySquare(s.getCoordinates(), ' ', UnitType.EMPTY);
  }

  public void setParent(AStarNode parent) {
      this.parent = parent;
  }

  public void addAction(String action) {
      actions += action;
  }

  public String getActions() {
      return actions;
  }


  public boolean isGoal() {
      if(state instanceof Gold) {
          return true;
      }
      return false;
  }
}