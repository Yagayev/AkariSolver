import java.util.Random;
import java.util.Vector;

public class BoardGen{
    
    public static Board gen(int n, int blacks, int numbered){
        Cell [][] bordMatrix = new Cell [n+2][n+2]; //squares[y][x]

        int yCordinate = 0;
        int xCordinate;
        for (int i = 0 ; i < n +1 ; i++){
            bordMatrix[ 0][i] = new BlackCell( i , 0, n);
            bordMatrix[ n + 1][i] = new BlackCell( i , n + 1, n);
        }
        for(int j = 0; j<n;j++){
            
            yCordinate ++ ;
            bordMatrix[ yCordinate][0] = new BlackCell( 0 , yCordinate, n);
            for (int i = 0 ; i < n; i++ ) {
                xCordinate = i+1;
                bordMatrix[ yCordinate][xCordinate] = new FieldCell( xCordinate , yCordinate, n);////////////////////////////////////?????????????????????????
                }
            bordMatrix[ yCordinate][n + 1 ] = new BlackCell( n + 1 , yCordinate, n);
        }
        //at this point there should be a copletely white board with black edges


        while(blacks>0){
            int x = getRand(1, n+1);
            int y = getRand(1, n+1);
            if(bordMatrix[y][x] instanceof FieldCell){
                bordMatrix[y][x] = new BlackCell(x , y , n );
                blacks--;
            }
        }

        Vector<NumbedBlackCell> numCellsToFix = new Vector<NumbedBlackCell>();

        while(numbered>0){
            int x = getRand(1, n+1);
            int y = getRand(1, n+1);
            if(bordMatrix[y][x] instanceof FieldCell){
                NumbedBlackCell tmp = new NumbedBlackCell(x , y , n, 4);
                bordMatrix[y][x] = tmp;
                numCellsToFix.add(tmp);
                numbered--;
            }

        }
        //now all squeres set in places, so we'll create the board and start placing the lamps
        Board b = new Board(bordMatrix, n);
        // System.out.println(b.toString());
        RandomVector<FieldCell> whites = new RandomVector<FieldCell>();//adding all the white squares into a random bag
        for(int j = 1; j<n+2;j++){
            for(int i = 1; i<n+2;i++){
                if(bordMatrix[j][i] instanceof FieldCell){
                    whites.add((FieldCell) bordMatrix[j][i]);
                }
            }
        }
        while(!whites.isEmpty()){
            FieldCell s = whites.popRand();
            if(s.getCellStatus()==status.W){
                // System.out.println("adding lamp x:"+s.x+" y:"+s.y);
                b.addLamp(s.x, s.y);
            }
        }

            

        for(NumbedBlackCell s : numCellsToFix){
            int t = 0;
            for(Cell neighbor : s.getNeighbors(b)){
                if(neighbor instanceof FieldCell &&
                    ((FieldCell)neighbor).getCellStatus()==status.L ){
                        t++;
                    }
            }
            s.setConstraint(t);
        }
        return b;

    }

    public static void main(String[] args){
        if(args.length!=4){
            System.out.println("Usage: BorardGen <n> <blacks> <numbered> <filename>");
            return;
        }

            
        int n = Integer.valueOf(args[0]);
        int blacks = Integer.valueOf(args[1]);
        int numbered = Integer.valueOf(args[2]);
        String filename = args[3];
        if(n*n<blacks+numbered){

            System.out.printf("A %dx%d board can't fit %d+%d cells!\n", n,n,blacks,numbered);
            return;
           
        }

        Board boardGame = gen(n, blacks, numbered);

        System.out.print("Solved:\n"+boardGame.toString());

        boardGame.toFile(filename+"-solution");
        boardGame.removeAllLamps();
        System.out.print("Unsolved:\n"+boardGame.toString());
        boardGame.toFile(filename+"-problem");
        
    }

    static Random r = new Random();
    public static int getRand(int min, int max){
        //java lib only offers pseudo randomness, so maybe we could switch to pure random with:
        // https://sourceforge.net/projects/trng-random-org/
        
        return r.nextInt(max-min)+min;

    }
} 