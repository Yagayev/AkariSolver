import java.util.List;
import java.util.Vector;

import org.graalvm.compiler.nodes.extended.NullCheckNode;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
public class BordParser{
    public static List<String> readFile(String filename){
        List<String> lines = new ArrayList<String>();
        try
        {
          BufferedReader reader = new BufferedReader(new FileReader(filename));
          String line;
          while ((line = reader.readLine()) != null)
          {
                lines.add(line);
          }
          reader.close();
          return lines;
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", filename);
          e.printStackTrace();
          return null;
        }
      
    }
    public static Board parse(String filename){
        List<String> lines = readFile(filename);
        int lineLength = lines.get(0).length();
        char caracter;
        Cell [][] bordMatrix = new Cell [lineLength+2][lineLength+2];  //squares[y][x]
        int yCordinate = 0;
        int xCordinate;
        Vector<Block> horizontalBlocks =  new  Vector<Block>();
        Vector<Block> verticalalBlocks = new  Vector<Block>();
     	 Block tmpH;
     	 Block tmpV;
        for (int i = 0 ; i < lineLength +1 ; i++){
            bordMatrix[ 0][i] = new BlackCell( i , 0, lineLength);
            bordMatrix[ lineLength + 1][i] = new BlackCell( i , lineLength + 1, lineLength);
        }
        tmpH = new Block();
        tmpV = new Block();
			horizontalBlocks.add(tmpH);
			verticalalBlocks.add(tmpV);

        for(String line : lines){
            if(line.length()!=lineLength){
                throw new IllegalArgumentException(String.format("there are %d ines but length is %d", yCordinate, lineLength));

            }
            if(!tmpV.isEmpty()) {
       			tmpV = new Block();
       			verticalalBlocks.add(tmpV);

       		 }
            yCordinate ++ ;
            bordMatrix[ yCordinate][0] = new BlackCell( 0 , yCordinate, lineLength);
            for (int i = 0 ; i < lineLength ; i++ ) {
                caracter = line.charAt(i);
                xCordinate = i+1;
                switch (caracter){
                        case  'b' :
                        case  'B' :
                        bordMatrix[ yCordinate][xCordinate] = new BlackCell( xCordinate , yCordinate , lineLength);
               		 if(!tmpV.isEmpty()) {
               			tmpV = new Block();
               			verticalalBlocks.add(tmpV);

               		 }

                        break;

                        case  'w' :
                        case  'W' :
                        bordMatrix[ yCordinate][xCordinate] = new FieldCell( xCordinate , yCordinate, lineLength);
                        bordMatrix[ yCordinate][xCordinate].xblock = tmpV;
                        tmpV.add( (FieldCell) bordMatrix[ yCordinate][xCordinate]);
                        if(! (bordMatrix[ yCordinate -1 ][xCordinate] instanceof FieldCell)) {
                       	 if(!tmpH.isEmpty()) {
                 			 tmpH = new Block();
                 			horizontalBlocks.add(tmpH);
                       	 }
                             bordMatrix[ yCordinate][xCordinate].yblock = tmpH;
                             tmpH.add( (FieldCell) bordMatrix[ yCordinate][xCordinate]);
       				 
                        }
                        else {
                            bordMatrix[ yCordinate][xCordinate].yblock = bordMatrix[ yCordinate -1 ][xCordinate].yblock;
                            bordMatrix[ yCordinate -1 ][xCordinate].yblock.add( (FieldCell) bordMatrix[ yCordinate][xCordinate]);
						}
                        break;

                        case  '0': case  '1': case  '2': case  '3': case  '4': 
                        int  constraint= caracter - '0';
                        bordMatrix[ yCordinate][xCordinate] = new NumbedBlackCell( xCordinate , yCordinate, lineLength, constraint);
                  		 if(!tmpV.isEmpty()) {
                    			tmpV = new Block();
                    			verticalalBlocks.add(tmpV);
                    		 }
                 
                        break;

                        default:
                        throw new IllegalArgumentException("Illegal caracter - " + caracter);

                }

                }
                bordMatrix[ yCordinate][lineLength + 1 ] = new BlackCell( lineLength + 1 , yCordinate, lineLength);

            }
              


        if(yCordinate != lineLength){
            
            throw new IllegalArgumentException(String.format("there are %d ines but length is %d", yCordinate, lineLength));
        }
        Board noradGame = new Board(bordMatrix, lineLength);
        noradGame.verticalalBlocks = verticalalBlocks; //1.2
        noradGame.horizontalBlocks = horizontalBlocks; //1.2
        return noradGame;
        
        

    }
}




