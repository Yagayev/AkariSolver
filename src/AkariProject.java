
import java.util.Scanner;

public class AkariProject {
	/*
	 * 
	 * 
	This project was written by Meir Yagayev and Bar Siman Tov
	as an assistant at the course "Topic in Logic Puzzel"
  	by prof. Daniel Berend 
  	Ben Gurion University of the Negev 
  	2018
	 * 
	 */
    static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		  System.out.println("What do you want to do? : \n1.Create boards \n2.Solve a board");
		  int action =  sc.nextInt();
		  if(action == 1) {
			  GenerateManyBoards.gen();
			  System.out.println("DONE!");
		  }
		  else if(action == 2) {
			  AkariSolver.solve();
		  }
		  else {
			System.out.println("iligal action");
		}
	}

}
