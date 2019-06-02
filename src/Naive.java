import java.util.PriorityQueue;
import java.util.Vector;



public class Naive{

    PriorityQueue<NumbedBlackCell> numberdBlockes;
    Board game;
    boolean iterateWhite;
    boolean aearchCabt;
    boolean checkMust;
    
    Naive(Board b){
        game = b;
        iterateWhite = true;
        aearchCabt = true;
        checkMust = true;
}    
    Naive(Board b, boolean iterateWhite , boolean aearchCabt ,boolean checkMust){
        game = b;
        this.aearchCabt = aearchCabt;
        this.iterateWhite =iterateWhite;
        this.checkMust =checkMust;

}    


public Board act (){
//	game.createVerticalBlocks();
//	game.createHorizontalBlocks(); 
    boolean keepGoing = false;
 
do{
		    keepGoing = iterateBlack(1 ,1);
		    if(!keepGoing) {
			    keepGoing = iterateWhite(1 ,1);

//			    if(!keepGoing && aearchCabt) {
//
//			    	keepGoing = searchCant(1 ,1);
//			    }

    }
	   } while(keepGoing);
  

    return game;
    }


    private boolean iterateBlack (int xBigin , int yBigin){
        boolean didSomthing = false;
            for (NumbedBlackCell current : game.numCells) {

                    didSomthing = checkNumber(current)|| didSomthing;
            }     
        return didSomthing;
    }
    
    private boolean iterateWhite (int xBigin , int yBigin){

        Cell current;
        boolean didSomthing = false;
        for( int y = yBigin ; y <= game.n ; y ++){
            for( int x = xBigin ; x <= game.n ; x ++){
               	
                        //current  = game.getAt(x , y );
                current  = game.cells[y][x];
                	if(current instanceof FieldCell ){
                   
                        FieldCell  s = (FieldCell)current;

//                        if(s.getStatus() == status.W && canPut(s)){
//                            didSomthing =  checkBlockes(((FieldSquare)current)) || didSomthing ;
//                        }
                        if(s.getStatus() == status.W) {
                        	  didSomthing =  checkBlockesBetter(((FieldCell)current)) || didSomthing ;
//                            }
                        }
                    }
            }
        }
        return didSomthing;

       

    }
    private boolean   checkBlockesBetter (FieldCell f) {
    	FieldCell potantial = null;
    	for (FieldCell s : f.xblock) {
    		if(s.getStatus() == status.W && canPut(s) ) {
    			if(potantial == null || potantial == s) {
    				potantial = s;
    			}
    			else {
    				return false;
    			}
    		}
    		
		}
    	for (FieldCell s : f.yblock) {
    		if(s.getStatus() == status.W && canPut(s) ) {
    			if(potantial == null || potantial == s) {
    				potantial = s;
    			}
    			else {
    				return false;
    			}
    		}
		}
    	
    	game.addLamp(potantial.x, potantial.y);
    	return true;
    }

    

 
 private boolean checkNumber(NumbedBlackCell bs){
    boolean didsomthing = false;
    Vector<Cell> nighbords = bs.getNeighbors(game);
    Vector<FieldCell> options = new Vector<FieldCell>();
    for (Cell s : nighbords) {
        if( s instanceof FieldCell){
            FieldCell fi = (FieldCell) s;
            if(fi.getStatus() == status.W &&  canPut(fi)){
                options.add(fi);
            }
        
        }   
    }

    if(bs.getremain() == options.size()){
        for (FieldCell s : options) {
                    game.addLamp(s.x, s.y);
                    didsomthing = true;
          
        }

    }


    return didsomthing;

}

    private boolean checkRowBlockes(FieldCell bs){ 
        Cell check;
        for(int x = bs.x+1 ; x <= game.n ;x ++){
            // game.getAt(x , bs.y );
            check = game.cells[bs.y][x];
            if(check instanceof FieldCell ){
                FieldCell fild = (FieldCell)check;
                if(fild.getStatus() == status.W && canPut(fild)  ){
                    return false;
                }
            }
            else{
                x = game.n;
            }
          
        }
        for(int x = bs.x-1 ; x >= 1 ;x --){
            // game.getAt(x , bs.y );
            check = game.cells[bs.y][x];
            if(check instanceof FieldCell ){
                FieldCell fild = (FieldCell)check;
                if(fild.getStatus() == status.W && canPut(fild) ){

                    return false;
                }
            }
            else{
                x = 0;
            }
       
        }
        return true;
    }
    private boolean checkColmBlockes(FieldCell bs){
        Cell check;
        for(int y = bs.y+1 ; y <= game.n ;y ++){
            // game.getAt(bs.x , y );
            check = game.cells[y][bs.x];
            if(check instanceof FieldCell ){
                FieldCell fild = (FieldCell)check;
                if(fild.getStatus() == status.W && canPut(fild) ){
                    return false;
                }
            }
            else{
                y = game.n;
            }
        }

        for(int y = bs.y-1 ; y >= 1 ;y --){
            // game.getAt(bs.x , y );
            check = game.cells[y][bs.x];
            if(check instanceof FieldCell ){
                FieldCell fild = (FieldCell)check;
                if(fild.getStatus() == status.W && canPut(fild) ){

                    return false;
                }
            }
            else{
                y = -1;
            }
        }


        return true;
    }
    private boolean canPut(FieldCell bs){
    	if(bs.getStatus() == status.C) {
    		return false;
    	}
        Vector<Cell> nighbords = bs.getNeighbors(game);
        for (Cell s : nighbords) {
            if(s instanceof NumbedBlackCell){
                NumbedBlackCell b = (NumbedBlackCell) s ;
                if(b.getremain() == 0){
                    return false;
                }
            }
            
        }
        return true;
    }

    
    }


