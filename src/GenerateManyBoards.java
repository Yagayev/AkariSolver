import java.io.File;
import java.util.Scanner;

public class GenerateManyBoards {
    static Scanner sc = new Scanner(System.in);
	public static void gen() {

		System.out.println("enter dimension (if board is n*n what is n?) ");
        int n = Integer.valueOf(sc.nextInt());
		System.out.println("enter skip size ");

        int blacksPerIter = Integer.valueOf(sc.nextInt());
        System.out.println("enter dir (save location) ");
        String dir = sc.next();
     
        File theDir = new File(dir);
        File solDir = new File(dir+"-solutions");
        if(!theDir.exists()) {
        	try{
                theDir.mkdir();    
            } 
            catch(SecurityException se){
            	System.out.print("failed making dir\n");
            	return;
            }
        }
        if(!solDir.exists()) {
        	try{
        		solDir.mkdir();    
            } 
            catch(SecurityException se){
            	System.out.print("failed making dir\n");
            	return;
            }
        }
        
        int blacks = blacksPerIter;
        int numbered = blacksPerIter;
        int i = 0;
        while(numbered<n*n-blacksPerIter) {
        	blacks = blacksPerIter;
        	while(numbered+blacks<n*n-blacksPerIter) {
        		System.out.println("generating board "+"\\"+dir+"\\"+i);
        		Board boardGame = BoardGen.gen(n, blacks, numbered);
        		boardGame.createHorizontalBlocks();
        		boardGame.createVerticalBlocks();
            	boardGame.toFile(dir+"-solutions\\", Integer.toString(i));
            	boardGame.removeAllLamps();
            	boardGame.toFile(dir+"\\", Integer.toString(i)+"-"+n+"-"+numbered+"-"+blacks);
            	
        		blacks += blacksPerIter;
        		i++;
        	}
        	numbered += blacksPerIter;
        	
        }
        

	}

}

