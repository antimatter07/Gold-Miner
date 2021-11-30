import java.util.HashMap;

public class Miner {
  
  HashMap<Integer, DirectionType> Fronts;

  //front is a hashmap key referencing the hours in the clock divisible by 3
  private int currentFront;
  private static int startingFront = 0;

  //Miner Coordinates
  private int row;
  private int col;

  private final char SYMBOL = 'M';

  public Miner(int row, int col, int front) { // for Node
    Fronts = new HashMap<Integer, DirectionType>();
    Fronts.put(12, DirectionType.NORTH);
    Fronts.put(3, DirectionType.EAST);
    Fronts.put(6, DirectionType.SOUTH);
    Fronts.put(9, DirectionType.WEST);

    this.row = row;
    this.col = col;

    if (front == 0) { // if front is 0, initial front is not yet initialized
      startingFront = randomizeFront();
      currentFront = startingFront;
    }
    else this.currentFront = front;
  }

  private int randomizeFront() {
    return (int) (Math.random() * 4 + 1) * 3;
  }

  public void resetMiner () {
    row = 0;
    col = 0;
    currentFront = startingFront;
  }
  
  public char getSymbol() { return this.SYMBOL; }
  public int getRow() {return this.row;}
  public int getCol() {return this.col;}
  public UnitType scan(Board b) {

    switch(Fronts.get(currentFront)){
      case NORTH:

        if(row - 1 < 0){ 
          return UnitType.EMPTY;  
        }else{
          for(int i = row; i >= 1; i--){            
            UnitType Tile = b.getMiningArea()[i-1][col].getUnitType();
          
            if(Tile == UnitType.GOLD || Tile == UnitType.BEACON || Tile == UnitType.PIT) 
              return Tile;
            
          } 
        }
        
      break;

      case EAST:

        if(col + 1 >= b.getSize()){
          return UnitType.EMPTY;
        }else{
          for(int i = col; i < b.getSize() - 1; i++) {   
              UnitType Tile = b.getMiningArea()[row][i+1].getUnitType();
            
              if(Tile == UnitType.GOLD || Tile == UnitType.BEACON || Tile == UnitType.PIT) 
              return Tile;                
          }
        }
        
      break;

      case SOUTH:

        if(row + 1 > b.getSize()){
          return UnitType.EMPTY;
        }else{
          for(int i = row; i < b.getSize() - 1; i++) {
              UnitType Tile = b.getMiningArea()[i+1][col].getUnitType();           
              
              if(Tile == UnitType.GOLD || Tile == UnitType.BEACON || Tile == UnitType.PIT) 
                return Tile;

          }
        }
      break;

      case WEST:
        if (col - 1 < 0 ){
          return UnitType.EMPTY;
        }else{
          for(int i = col; i >= 1; i--) {          
              UnitType Tile = b.getMiningArea()[row][i-1].getUnitType();
            
              if(Tile == UnitType.GOLD || Tile == UnitType.BEACON || Tile == UnitType.PIT) 
                return Tile;

          }
        }
        
      break;
    }
    

    return UnitType.EMPTY;
  }

  
  public int getFront() {
    return currentFront;
  }

 // return DirectionType enumfor currentFront
 public DirectionType getDirectionFront() {
    return Fronts.get(currentFront);
  }
  
  public void move(Board b) {
  
    switch(Fronts.get(currentFront)){
      case NORTH:  
        if(row - 1 >= 0) row--; 
      break;
      case EAST:
        if(col + 1 < b.getSize()) col++;
      break;
      case SOUTH:
        if(row + 1 < b.getSize()) row++;
      break;
      case WEST:
        if(col - 1 >= 0) col--;
      break;
    }
  }


  public boolean canMoveForward(Board b) {
    switch(Fronts.get(currentFront)){
      case NORTH:  
        if(row - 1 >= 0) return true;
      break;
      case EAST:
        if(col + 1 < b.getSize()) return true;
      break;
      case SOUTH:
        if(row + 1 < b.getSize()) return true;
      break;
      case WEST:
        if(col - 1 >= 0) return true;
      break;
    }
    return false;
  }
  
  //rotate miner clockwise
  public void rotate() {
      if(currentFront == 12) 
          currentFront = 3;
      else currentFront += 3;
  }
}