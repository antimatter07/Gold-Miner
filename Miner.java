import java.util.HashMap;

public class Miner {
  
  HashMap<Integer, DirectionType> Fronts;

  //front is a hashmap key referencing the hours in the clock divisible by 3
  private int currentFront;

  //Miner Coordinates
  private int row;
  private int col;

  private final char SYMBOL = 'M';

  public Miner(){
     
     //using clock hands for clarity
    Fronts = new HashMap<Integer, DirectionType>();
    Fronts.put(12, DirectionType.NORTH);
    Fronts.put(3, DirectionType.EAST);
    Fronts.put(6, DirectionType.SOUTH);
    Fronts.put(9, DirectionType.WEST);

    row = 0;
    col = 0;
    //front is randomized but for now it can face up
    currentFront = 12;
  }
  //NEW
  public Miner(int row, int col, int front) {
    //NEW
    Fronts = new HashMap<Integer, DirectionType>();
    Fronts.put(12, DirectionType.NORTH);
    Fronts.put(3, DirectionType.EAST);
    Fronts.put(6, DirectionType.SOUTH);
    Fronts.put(9, DirectionType.WEST);

    this.row = row;
    this.col = col;
    this.currentFront = front;

  }
  
  public char getSymbol() { return this.SYMBOL; }
  public int getRow() {return this.row;}
  public int getCol() {return this.col;}
  public UnitType scan(Board b) {
     //test loop conditions

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

    System.out.println("Miner is now at coordinate [" + row + "] [" + col + "]");

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

    System.out.println("Miner is now facing " + Fronts.get(currentFront));
  }

}