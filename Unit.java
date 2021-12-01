
//Unit represents a square in the grid
public class Unit{

  protected char symbol;
  protected int[] coordinates;
  protected UnitType type;
  protected boolean hasObject; //tells if the square has gold, pit or beacon
  
  protected boolean isVisited; // determines if the square has been visited by the miner
  
  //constructor can be like this maybe with the row and col position on the board
  public Unit(int[] coord, char symbol, UnitType type, boolean hasObject){
    coordinates = coord;
    this.symbol = symbol;
    this.type = type;
    this.hasObject = hasObject;
    isVisited = false;
  }

  public int[] getCoordinates(){
    return this.coordinates;
  }

  public char getSymbol(){
    return this.symbol;
  }

  public boolean isObject(){
    return hasObject;
  }
  
  public UnitType getUnitType(){
    return this.type;
  }

  
  public void setVisited() {
    isVisited = true;
  }

  public boolean isVisited() {
    return isVisited;
  }

  public void removeVisited() {isVisited = false;}
}