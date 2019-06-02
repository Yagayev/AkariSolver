import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.sat4j.specs.TimeoutException;

public class MassParse {
	public static void main(String[] args) throws IOException {
		
		if(args.length!=2){
            System.out.println("Usage: MassParse <dir> <output file name>");
            return;
        }
		String header = "name,n,white+,black,num,sum of all nums,naive time,Hybrid's SAT time,SAT time,How many lights,How many Naive found,How many solutions\n";
		String dir = args[0];
		String outputFile = args[1]+".csv";
		File[] files = new File(dir).listFiles();

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(header);
        String str;
        Board bordGame;
        long startTime;
        long endTime;
        boolean timeout;
        boolean outOfMem;
        try {
			for (File file : files) {
				try {
					System.out.println("solving "+file.getAbsolutePath());
					bordGame = BordParser.parse(file.getAbsolutePath());
					//adding board data to the file
					str = file.getAbsolutePath()+","+bordGame.CSVData(); 
					writer.write(str);
					
					//running Naive
					startTime = System.nanoTime();
					Naive n = new Naive(bordGame);
			        bordGame =  n.act();
			        endTime = System.nanoTime();
			        writer.write(Double.toString(endTime-startTime)+","); //WRITE TO CSV
					int lightsNaiveFound = bordGame.lightsCount();
					
					//finishing Hybrid by running SAT
					timeout = false;
					outOfMem = false;
					startTime = System.nanoTime();
		//	        if(!bordGame.isSolved()) {
			        	SATReduction s = new SATReduction(bordGame);
			        	
			            try {
							bordGame.applySolution(s.act());
							endTime = System.nanoTime();
						} catch (TimeoutException e) {
							timeout=true;
						}catch (OutOfMemoryError e) {
							outOfMem = true;
						}
		//	        }
			        
			        if(timeout) {
			        	writer.write("timeout,");
			        } 
			        else if(outOfMem) {
			        	writer.write("outOfMem,");
			        }
			        else {
			        	writer.write(Double.toString(endTime-startTime)+","); //WRITE TO CSV
			        }
		
					
					
			        //running SAT
			        bordGame = BordParser.parse(file.getAbsolutePath());
					startTime = System.nanoTime();
					SATReduction red = new SATReduction(bordGame);
			        try {
						bordGame.applySolution(red.act());
						endTime = System.nanoTime();
				        writer.write(Double.toString(endTime-startTime)+","); //WRITE TO CSV
					} catch (TimeoutException e1) {
						writer.write("timeout,");
					} catch (OutOfMemoryError e) {
						writer.write("outOfMem,");
					}
			        
			        int totalLights = bordGame.lightsCount();
		
			        
			        bordGame = BordParser.parse(file.getAbsolutePath());
			        red = new SATReduction(bordGame);
			        
//			        ISolver solver = red.generateSolver();
//			        SolutionCounter counter = new SolutionCounter(solver);
//			        long solutionCount;
//					try {
//						solutionCount = counter.countSolutions();
//					} catch (TimeoutException e) {
//						solutionCount = -1;
//					} catch (OutOfMemoryError e) {
//						solutionCount = -2;
//					}
			        long solutionCount = -1;
			        writer.write(Integer.toString(totalLights)+","+Integer.toString(lightsNaiveFound)+","+Long.toString(solutionCount)+"\n"); //WRITE TO CSV
			        
			        //writer.write("\n");
				} catch(Exception e) {
					writer.write("FAILURE\n");
				}
			}
        } catch(Exception e) {
        	writer.close();
        	System.out.println("FAILURE");
        }
//		showFiles(files);
		System.out.println("done");
		writer.close();
	}

//	public static void showFiles(File[] files) {
//	    for (File file : files) {
//	        if (file.isDirectory()) {
//	            System.out.println("Directory: " + file.getName());
//	            showFiles(file.listFiles()); // Calls same method again.
//	        } else {
//	            System.out.println("File: " + file.getName());
//	        }
//	    }
//	}
}
