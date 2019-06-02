
import org.sat4j.specs.TimeoutException;
public class Hybired {

	public static void main(String[] args) throws TimeoutException {
		long startTime = System.nanoTime();
	     Board bordGame = BordParser.parse("boards/b6-problem");
        //System.out.print(bordGame.toString());
        Naive n = new Naive(bordGame);
        bordGame =  n.act();

        System.out.print(" ");
      if(bordGame.isSolved()){
      System.out.println("I am done !!!!");
      System.out.println(bordGame.toString());
     }
          else{
        	  System.out.println("starting SAT");
        	  SATReduction s = new SATReduction(bordGame);
              bordGame.applySolution(s.act());
        	  System.out.println("printingT");

              System.out.println(bordGame.toString());
              System.out.print(" ");
              //System.out.print(BordParser.parse("f-solution").toString());
              if(bordGame.isSolved()) {
              	System.out.print("solved!!!!! ");
              }
  
          }

      long endTime = System.nanoTime();
	     System.out.println("run time: " + (endTime - startTime) );

}
}

