public class Node {

  private Node parent;

    //Unit miner is
    private Unit state;
    //a string of actions that will lead to gold "M" for move, "R" for rotate
    //cant figure out how to backtrack the moves yet so for now string muna siya with the actions
    private String actions = "";
    private Miner miner;
    public boolean isVisited = false;
    

    public Node(){
        
    }

    public Node(Unit state) {
        this.state = state;
    }

    public Node(Unit state, Node parent, Miner miner) {
        this.state = state;
        this.parent = parent;
        this.miner = miner;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void markVisited() {
        isVisited = true;
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

    public Node getParent() {
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

    public void setParent(Node parent) {
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