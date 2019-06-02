import java.io.File;

public class MassBoardGen {

	public static void main(String[] args) {
		if(args.length!=3){
            System.out.println("Usage: BorardGen <n> <count> <dir>");
            return;
        }

            
        int n = Integer.valueOf(args[0]);
        int blacks;
        int numbered;
        int count = Integer.valueOf(args[1]);
        String dir = args[2];
//        new File("/"+dir).mkdirs();
//        new File("/"+dir+"-solutions").mkdirs();
//        for(int i = 0; i<count; i++) {
//        	System.out.println("generating board "+"\\"+dir+"\\"+i);
//        	int totalBlacks = BoardGen.getRand(0, n*n);
//        	blacks = BoardGen.getRand(0, totalBlacks);
//        	numbered = totalBlacks-blacks;
//        	Board boardGame = BoardGen.gen(n, blacks, numbered);
//        	boardGame.toFile("\\"+dir+"-solutions\\"+i);
//        	boardGame.removeAllLamps();
//        	boardGame.toFile("\\"+dir+"\\"+i);
//        }
        
        File theDir = new File(dir);
        if(!theDir.exists()) {
        	try{
                theDir.mkdir();
                
            } 
            catch(SecurityException se){
            	System.out.print("failed making dir\n");
            	return;
            }
            
        }
        for(int i = 0; i<count; i++) {
        	System.out.println("generating board "+"\\"+dir+"\\"+i);
        	int totalBlacks = BoardGen.getRand(0, n*n);
        	blacks = BoardGen.getRand(0, totalBlacks);
        	numbered = totalBlacks-blacks;
        	Board boardGame = BoardGen.gen(n, blacks, numbered);
        	boardGame.toFile(dir+"-solutions\\", Integer.toString(i));
        	boardGame.removeAllLamps();
        	boardGame.toFile(dir+"\\", Integer.toString(i));
        }

	}

}
