public class Board{

    private int N;
    private Unit[][] boardArray;
    private int beaconCount;
    private int pitCount;
    private int[] goldCoord;
    private Miner miner;

    //initialize Board when instantiated
    public Board(int N){
        this.N = N;
        boardArray = new Unit[N][N];
        goldCoord = new int[2];
        miner = new Miner();
        pitCount = computePit();
        beaconCount = computeBeacon();
        generateBoard();
        displayBoard();
    }

    // initializes the position of beacons, pits, and gold on the board
    private void generateBoard() {
        boolean goldPlaced = false;
        int beaconsPlaced = 0;
        int pitsPlaced = 0;

        // first, initialize all board units as EmptySquare
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                int[] coord = {i, j};
                boardArray[i][j] = new EmptySquare (coord, ' ', UnitType.EMPTY);
            }
        }

        // set the gold pot
        while (!goldPlaced) {
            int[] tempCoord = getRandomCoord();
            if (isValidCoord(tempCoord)) {
                boardArray[tempCoord[0]][tempCoord[1]] = new Gold(tempCoord, 'G', UnitType.GOLD);
                goldCoord = tempCoord;
                goldPlaced = true;
            }
        }

        // set the pits
        while (pitsPlaced < pitCount) {
            int[] tempCoord = getRandomCoord();
            // if tempCoord is not yet occupied by another object, add pit
            if (isValidCoord(tempCoord)) {
                boardArray[tempCoord[0]][tempCoord[1]] = new Pit (tempCoord, 'P', UnitType.PIT);
                pitsPlaced++;
            }
        }

        // set the beacons (must be in direct line of sight from gold)
        while (beaconsPlaced < beaconCount) {
            int[] tempCoord = new int[2];
            DirectionType dir; // the path direction to be checked for pits
            int randGoldCoord = (int) (Math.random() * 2);
            int newCoord = (int) (Math.random() * N);

            if (randGoldCoord == 0) { //generated coord is on the same row as gold
                tempCoord[0] = goldCoord[0];
                tempCoord[1] = newCoord;
                if (tempCoord[1] < goldCoord[1])
                    dir = DirectionType.EAST;
                else dir = DirectionType.WEST;
            }
            else { //generated coord is on the same col as gold
                tempCoord[0] = newCoord;
                tempCoord[1] = goldCoord[1];
                if (tempCoord[0] < goldCoord[0])
                    dir = DirectionType.SOUTH;
                else dir = DirectionType.NORTH;
            }

            // if tempCoord is not yet occupied by another object, add beacon
            if (isValidCoord(tempCoord)) {
                if (isPathClear(tempCoord, dir)) {
                    boardArray[tempCoord[0]][tempCoord[1]] = new Beacon(tempCoord, 'B', UnitType.BEACON);
                    beaconsPlaced++;
                }
            }
        }
    }

    // generates two rand numbers from 0 to N-1
    private int[] getRandomCoord(){
        int[] randCoord = new int[2];
        randCoord[0] = (int) (Math.random() * N);
        randCoord[1] = (int) (Math.random() * N);
        return randCoord;
    }

    // checks if the param coord is the starting pos (0,0)
    // or is already occupied
    private boolean isValidCoord (int[] coord){
        if (coord[0] == 0 && coord[1] == 0) //starting pos
            return false;
        if (boardArray[coord[0]][coord[1]].isObject()) //is occupied by another object
            return false;
        return true;
    }

    // checks if the generated coord has a direct line of sight to gold (ie. has no pits)
    private boolean isPathClear (int[] coord, DirectionType direction) {
        switch (direction) {
            case NORTH:
                for (int i = coord[0] - 1; i > goldCoord[0]; i--){
                    if (boardArray[i][coord[1]].getUnitType().equals(UnitType.PIT))
                        return false;
                }
                break;
            case SOUTH:
                for (int i = coord[0] + 1; i < goldCoord[0]; i++){
                    if (boardArray[i][coord[1]].getUnitType().equals(UnitType.PIT))
                        return false;
                }
                break;
            case EAST:
                for (int i = coord[1] + 1; i < goldCoord[1]; i++){
                    if (boardArray[coord[0]][i].getUnitType().equals(UnitType.PIT))
                        return false;
                }
                break;
            case WEST:
                for (int i = coord[1] - 1; i > goldCoord[1]; i--){
                    if (boardArray[coord[0]][i].getUnitType().equals(UnitType.PIT))
                        return false;
                }
                break;
        }
        return true;
    }

    public int getSize() {
        return N;
    }

    // computes the number of pits the board should have based on N
    private int computePit () {
        if (N/4 < 1)
            return 1;
        return (int) Math.floor(N/4);
    }

    // computes the number of beacons the board should have based on N
    private int computeBeacon () {
        if (N/10 < 1)
            return 1;
        return (int) Math.floor(N/10);
    }

    public Unit[][] getMiningArea() {
        return boardArray;
    }


    public Unit getUnit(int row, int col){
        return boardArray[row][col];
    }

    public void moveMiner () {
        miner.move(this);
    }
    
    public int getMinerRow () {
        return miner.getRow() + 1;
    }

    public int getMinerCol () {
        return miner.getCol() + 1;
    }

    public char getMinerSymbol () {
        return miner.getSymbol();
    }


    //for testing
    public void displayBoard(){

        for(int i =0; i < N; i++){
           
            for(int j =0; j < N; j++){
                System.out.print("----");
            }
            System.out.println("-");
            
            for(int j =0; j < N; j++){
                if(miner.getRow() == i && miner.getCol() ==j){
                  System.out.print("| M ");  
                }else if(boardArray[i][j] != null){
                  System.out.print("| " + boardArray[i][j].getSymbol() + " ");
                }else if(boardArray[i][j] == null){
                  System.out.print("|   ");   
                }

            }
            System.out.println("|");
        }
        for(int i =0; i < N; i++){       
            System.out.print("----");
        }
        System.out.print("-");
    }
}