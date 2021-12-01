import java.util.Queue;
import java.util.*;

/* NOTES:
  each call of expand() can return at most 2 successors: MOVE and ROTATE
  miner.scan() is not used, Miner will explore each square until GOLD is found
   if BEACON is found, it will clear the fringe, and will directly go to GOLD
  ^^ if BEACON is found, this is the only time MINER will use scan
  solution is always found, but not always the most optimal
*/


//random level of rationality
public class BFS {

    private Board board;
    private static int scanCount = 0;

    //list of successor nodes after expanding
    private ArrayList<Node> successors;
    private Queue<Node> fringe; //fringe is the frontier of unexplored nodes
    private Node root;
    private Node node;

    public BFS(Board board) {
        this.board = board;

        successors = new ArrayList<Node>();
        fringe = new LinkedList<>();

        //root is the in  initial pos of Miner
        root = new Node(board.getMiningArea()[0][0]);
        root.setMiner(board.getMiner());

        fringe.add(root);
        scanCount = 0;
    }

    //returns solution Node with path to the Gold
    public Node BFS() {

        //while there are nodes to explore and goal node is not reached, look for solution
        while (fringe.peek() != null) {

            //take latest unexplored node and expand
            node = fringe.remove();
 
            switch (node.getSquare().getUnitType()) {
                case GOLD:
                    return node;
                case BEACON:
                    // if miner already landed on a beacon and gold is found, no need to scan, just move forward
                    if (node.isBeaconFound()) {
                        successors.add(generateMoveNode(node));
                    }
                    else {
                        fringe.clear();
                        successors = expandBeacon(node, board);
                    }
                    break;
                case PIT: // treat as a leaf node, check next Node in the fringe
                    node = fringe.remove();
                    successors = expand(node, board);
                    break;
                case EMPTY:
                    // if miner already landed on a beacon and gold is found, no need to scan, just move forward
                    if (node.isBeaconFound()) {
                        successors.add(generateMoveNode(node));
                    }
                    else successors = expand(node, board);
                    break;
            }


            //for every successor generated that has not been visited, add to list of unexplored nodes
            for (int i = 0; i < successors.size() && successors.get(i) != null; i++) {
                    fringe.add(successors.get(i));
            }

            //set as current square as visited so that miner will not redundantly expand nodes already explored
            board.getMiningArea()[node.getMiner().getRow()][node.getMiner().getCol()].setVisited();

            successors.clear();

        }

        return null;

    }

    // expands the current node
    private ArrayList<Node> expand(Node node, Board board) {
        ArrayList<Node> successors = new ArrayList<Node>();

        // if Miner can move forward and front is not yet visited, create moveNode
        if (node.getMiner().canMoveForward(board)) {
            Node newNode = generateMoveNode(node);
            // if newNode's position is not yet visited, enqueue to fringe
            if (!board.getMiningArea()[newNode.getMiner().getRow()][newNode.getMiner().getCol()].isVisited()) {
                successors.add(newNode);
            }
        }

        // add a rotate Node
        successors.add(generateRotateNode(node));

        return successors;
    }


    // expands if the current node has a beacon, rotate Miner to face Gold
    private ArrayList<Node> expandBeacon(Node node, Board board) {
        ArrayList<Node> successors = new ArrayList<Node>();

        Node newNode;

        // if MINER scanned GOLD or another BEACON, miner MOVES
        if (node.getMiner().scan(board).equals(UnitType.GOLD) ||
                node.getMiner().scan(board).equals(UnitType.BEACON)) {
            newNode = generateMoveNode(node);
            newNode.setBeaconFound(true); // Miner will no longer need to scan for further successors
        }
        else { // if MINER did not scan GOLD, miner ROTATES
            newNode = generateRotateNode(node);
        }
        scanCount++;
        successors.add(newNode);
        return successors;
}

    public static void duplicateNode(Node origNode, Node dupNode) {
        dupNode.setSquare(origNode.getSquare());
        dupNode.setMiner(origNode.getMiner());
        dupNode.setActions(origNode.getActions());
        dupNode.setParent(origNode.getParent());
        dupNode.setBeaconFound(origNode.isBeaconFound());
    }

    public void resetScanCount() {
        scanCount = 0;
    }

    public int getScanCount() {
        return scanCount;
    }

    private Node generateMoveNode (Node node) {
        Node newNode = new Node();
        duplicateNode(node, newNode);
        newNode.getMiner().move(board);
        newNode.addAction("M");
        newNode.setSquare(board.getMiningArea()[newNode.getMiner().getRow()][newNode.getMiner().getCol()]);
        newNode.setParent(node);
        return newNode;
    }

    private Node generateRotateNode (Node node) {
        Node newNode = new Node();
        duplicateNode(node, newNode);
        newNode.getMiner().rotate();
        newNode.addAction("R");
        newNode.setParent(node);
        return newNode;
    }
}