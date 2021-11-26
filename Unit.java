public class Unit{

  protected char symbol;
  protected int[] coordinates;
  protected UnitType type;
  protected boolean hasObject; //tells if the square has gold, pit or beacon
  
  //constructor can be like this maybe with the row and col position on the board
  public Unit(int[] coord, char symbol, UnitType type, boolean hasObject){
    coordinates = coord;
    this.symbol = symbol;
    this.type = type;
    this.hasObject = hasObject;
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
}