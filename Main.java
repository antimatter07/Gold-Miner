import java.util.Scanner;

//testset
public class Main {
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    int input;
    
    try{
      
        do{
          System.out.print("Input Board Size: ");
          input = scan.nextInt();

          if(input < 8 || input > 64){
            System.out.println("Input Invalid");
          }

        }while(input < 8 || input > 64);

        new Board(input);

    }catch(Exception e){
        System.out.println("Input Error");
    }

    scan.close();

  }
}