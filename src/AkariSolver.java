import java.util.Scanner;

import org.sat4j.specs.TimeoutException;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class AkariSolver {
	
	 static Board bordGame;
     static Scanner sc = new Scanner(System.in);
	public static void solve() {
		  System.out.println("enter file name (enter path if in an internal folder)" );
		  String fileName = sc.nextLine(); 
		try {
		      bordGame = BordParser.parse(fileName);

		} catch (Exception e) {
			  System.out.println("Cant find file " + fileName );
			  return;
		}
		  long startTime ;
		  long endTime;
		  System.out.println("Pick a solver : \n1.Human \n2.Naive \n3.SAT  \n4.hybrid (Naive + SAT) ");
	      int player =  sc.nextInt();
	      switch (player) {
		case 1:
			Human();  
			break;
		case 2:
			startTime = System.nanoTime();
			naiv(); 
			endTime = System.nanoTime();
		  	System.out.println("run time: " + (endTime - startTime) );
			break;
		case 3:
			startTime = System.nanoTime();
			sat();  
			endTime = System.nanoTime();
		  	System.out.println(" run time: " + (endTime - startTime) );
			break;
		case 4:
			startTime = System.nanoTime();
			hybrid();
			endTime = System.nanoTime();
		  	System.out.println(" run time: " + (endTime - startTime) );
			break;
		default:
		  	System.out.println("Iligal input");
			break;
		}
	      if(bordGame.isSolved()) {
	    	  System.out.println("S~O~L~V~E~D!");
	      }
	      else {
			System.out.println("Failed to solve :( " );
		}
	      System.out.println(bordGame.toString());
	      System.out.println("TO FILE? : \n1.YES \n2.NO \n ");
          int toFile =  sc.nextInt();
          if(toFile == 1) {
          	bordGame.toFile(fileName + "-sol");
          }
	}
	
	private static void Human() {
        int x ;
        int y;
        int keepGoing;
        System.out.print(bordGame.toString());
        System.out.println("light on? 1 off? 2 stop ? 3");
         keepGoing =  sc.nextInt();
        while (keepGoing != 3){
            if(bordGame.isLegal()){
                System.out.println("Board is leagal");
            }
            else{
                System.out.println("Board is NOT leagal");
            }

            if(bordGame.isCompleteable()){
                System.out.println("Board is completable");
            }
            else{
                System.out.println("Board is NOT completable");
            }
            
            if(bordGame.isSolved()){
                System.out.println("Board is Solved");
                return;
            }
            else{
                System.out.println("Board is NOT solved");
            }

            System.out.print("X: ");
            x = sc.nextInt();
            System.out.print("Y: ");
            y = sc.nextInt();
            if(keepGoing == 1){
                bordGame.addLamp(x, y);
                

            }
            else{
                bordGame.removeLamp(x, y);

            }
            System.out.print(bordGame.toString());
            System.out.println("light on? 1 off? 2 stop ? 3");
            keepGoing =  sc.nextInt();
           
        }

	}
	
	private static void hybrid() {
		Naive n = new Naive(bordGame);
        bordGame =  n.act();
        	SATReduction s = new SATReduction(bordGame);	
            try {
				bordGame.applySolution(s.act());
			} catch (TimeoutException e) {
				System.out.println(" SAT TimeOut!");;
			}catch (OutOfMemoryError e) {
				System.out.println(" SAT Out Of Memory Error!");;

			}
	}
	
	private static void sat() {
        SATReduction n = new SATReduction(bordGame);
        try {
			bordGame.applySolution(n.act());
		} catch (TimeoutException e) {
			 System.out.print("sat is unable to solved. ");
			e.printStackTrace();
		}
        
	}
	
	private static void naiv() {
	    Naive n = new Naive(bordGame  );
	    bordGame =  n.act();
	}

	}


