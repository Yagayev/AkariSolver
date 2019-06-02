import java.util.Vector;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
public class Board{
    Cell[][] cells; //squares[y][x]
    int n;
    int yellowCell;
    Vector<NumbedBlackCell> numCells; //1.2
    Vector<Block> verticalalBlocks; //1.2
    Vector<Block> horizontalBlocks; //1.2

    int numOfIllegalLamps;
    public String toString(){

        // String ans = "Boardsize = "+n+"\n" + "yello squares : " + yelloSquare +"\n iligal lampe :" + numOfIllegalLamps + "\n    ";//triple didgit
//        String ans = "Boardsize = "+n+"\n" +" iligal lampe :" + numOfIllegalLamps + "\n    ";//double didgit
        String ans = "   ";//double didgit

        for(int j = 1; j<=n; j++){
            ans+=String.format("%02d  ",j);//double didgit
            // ans+=String.format("%03d ",j);//triple didgit
        }
        ans+="\n";

        for(int j = 1; j<=n; j++){
            // ans+=String.format("%03d |",j);//triple didgit
            ans+=String.format("%02d |",j);//double didgit
            for(int i = 1; i<=n; i++){
                ans+=cells[j][i].toString()+"   ";
            }
            ans+="\n";
        }
        return ans;
    }

    public Board(Cell[][] arr, int setN){
        cells = arr;
        n = setN;
        numOfIllegalLamps = 0;
        //1.2
        numCells = new Vector<NumbedBlackCell>();
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n; j++){
                if(cells[j][i] instanceof NumbedBlackCell){
                    numCells.add((NumbedBlackCell)cells[j][i]);
                    Vector<Cell> bighbords = cells[j][i].getNeighbors(this);
                    for (Cell s : bighbords) {
                        if(s instanceof FieldCell){
                           ((NumbedBlackCell) cells[j][i]).settWhitAround(1);
                        }
                        
                    }
                }
            }
        }
        yellowCell = 0;
 

    }
    
    public int getSize() {
    	return n;
    }

    public boolean addLamp (int x , int y){
        Cell cell = cells[y][x];
        if(cell.isFiledCell()){
            FieldCell target = (FieldCell) cell;
            if(target.getStatus() == status.W ||target.getStatus() == status.Y ||  target.getStatus() == status.C){
                target.putLemp();
                if(target.getStatus() ==status.X ){
                    numOfIllegalLamps ++ ;
                }
                else{
                    yellowCell ++;
                }
                target.xblock.blockstatus = status.Y;
                target.yblock.blockstatus = status.Y;
                updatNighbordsNumber(target ,true);;
                return true;
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeLamp (int x , int y){
        Cell cell = cells[y][x];
        if(cell.isFiledCell()){
            FieldCell target =(FieldCell) cell;
            if ( target.haseLamp()){
            	System.out.println("removeLamp haseLamp" );
                target.remobeLamp();
                yellowCell --;
                if(target.xblock.numOfLamps == 0){
                	target.xblock.blockstatus = status.W;
                }
                if(target.yblock.numOfLamps == 0){
                	target.yblock.blockstatus = status.W;
                }
                updatNighbordsNumber(target ,false);;
                return true;               
            }
            else if (target.getStatus() == status.X ){
                target.remobeLamp();
                    numOfIllegalLamps -- ;

                    target.xblock.searchX();
                    target.yblock.searchX();
                updatNighbordsNumber(target ,false);;
                return true;               
            }
            return true;

        }
        else{
            return false;
        }
    }



    private void updatNighbordsNumber(FieldCell target ,boolean onPff){
    	Vector<Cell> nighbords =  target.getNeighbors(this);
        for (Cell s : nighbords) {
            if (s.isBlackNumber()){
                updateNumber( s , onPff);
            }      
        }
    }

    private void updateNumber(Cell s , boolean onPff){
        NumbedBlackCell ns =  (NumbedBlackCell) s;
        if(onPff){
            ns.addLampNear();
            ns.settWhitAround(-1);
        }
        else{
            ns.removeLampNear();
            ns.settWhitAround(1);

        }
    }

    public boolean isLegal(){
        if (numOfIllegalLamps > 0){
            //assuminbg there is such a field that is updated whenever a lamp is added or removed
            return false;
        }
        for(NumbedBlackCell numCell : numCells){
            int lamps = 0;

            for(Cell s : numCell.getNeighbors(this)){
                if(s instanceof FieldCell && ((FieldCell)s).getStatus() == status.L){
                    lamps++;
                }
            }
            if(lamps>numCell.getConstraint()){
                return false;
            }

        }
        //at this point all black conditions are legal
        return true;
    }
    public boolean isCompleteable(FieldCell s) {
        NumbedBlackCell tested;
        if (numOfIllegalLamps > 0){
            //assuminbg there is such a field that is updated whenever a lamp is added or removed

            //System.out.printf("isCompleteable numOfIllegalLamps =%d\n", numOfIllegalLamps);
            return false;
        }

        for (FieldCell blockBoddy : s.xblock) {
        	for (Cell nighberd : blockBoddy.getNeighbors(this)) {
				if(nighberd instanceof NumbedBlackCell) {
					tested = (NumbedBlackCell)nighberd;
					if(!checkBlackNum(tested)) {
						return false;
					}
				}
			}
			
		}
        for (FieldCell blockBoddy : s.yblock) {
        	for (Cell nighberd : blockBoddy.getNeighbors(this)) {
				if(nighberd instanceof NumbedBlackCell) {
					tested = (NumbedBlackCell)nighberd;
					if(!checkBlackNum(tested)) {
						return false;
					}
				}
			}
			
		}
        return true;
    }
//    }
    
    
    public boolean checkBlackNum(NumbedBlackCell numCell){

            int lamps = 0;
            int whites = 0;
            int constraint = numCell.getConstraint();
            for(Cell s : numCell.getNeighbors(this)){
                if(s instanceof FieldCell){
                    status stat = ((FieldCell)s).getStatus();
                    if(stat == status.L){
                        lamps++;
                    }
                    else if(stat == status.W ){
                        whites++;
                    }
                }
            }
            if(lamps>constraint){
                //System.out.printf("isCompleteable lamps =%d; constraint =%d\n", lamps, constraint);
                return false;
            }
            if(constraint-lamps>whites){
                //number of lamps still needed<number of lamps possible to add
                //System.out.printf("isCompleteable lamps =%d; constraint =%d whites=%d\n", lamps, constraint, whites);
                return false;
            }

        
        //at this point all black conditions are legal
        return true;
    }

    public boolean isCompleteable(){
        //same as isLegal() but also demanding that every numbered square have enough squares to put numbers on
        if (numOfIllegalLamps > 0){
            //assuminbg there is such a field that is updated whenever a lamp is added or removed

            //System.out.printf("isCompleteable numOfIllegalLamps =%d\n", numOfIllegalLamps);
            return false;
        }
        for(NumbedBlackCell numCell : numCells){
            int lamps = 0;
            int whites = 0;
            int constraint = numCell.getConstraint();
            for(Cell s : numCell.getNeighbors(this)){
                if(s instanceof FieldCell){
                    status stat = ((FieldCell)s).getStatus();
                    if(stat == status.L){
                        lamps++;
                    }
                    else if(stat == status.W ){
                        whites++;
                    }
                }
            }
            if(lamps>constraint){
                //System.out.printf("isCompleteable lamps =%d; constraint =%d\n", lamps, constraint);
                return false;
            }
            if(constraint-lamps>whites){
                //number of lamps still needed<number of lamps possible to add
                //System.out.printf("isCompleteable lamps =%d; constraint =%d whites=%d\n", lamps, constraint, whites);
                return false;
            }

        }
        //at this point all black conditions are legal
        return true;
    }

    public boolean isSolved(){
        if (numOfIllegalLamps > 0){
            //assuminbg there is such a field that is updated whenever a lamp is added or removed
            return false;
        }
        for(NumbedBlackCell numCell : numCells){
            int lamps = 0;

            for(Cell s : numCell.getNeighbors(this)){
                if(s instanceof FieldCell && ((FieldCell)s).getStatus() == status.L){
                    lamps++;
                }
            }
            if(lamps!=numCell.getConstraint()){
                //System.out.printf("isSolved lamps =%d; constraint =%d\n", lamps, numSquare.getConstraint());
                return false;
            }

        }
        //at this point all black conditions are fullfiled

        for(int i = 1; i<n; i++){
            for(int j = 1; j<n; j++){
                if(cells[j][i] instanceof FieldCell &&(((FieldCell) cells[j][i]).getStatus()==status.W||((FieldCell) cells[j][i]).getStatus()==status.X ||((FieldCell) cells[j][i]).getStatus()==status.C)){
                    return false;
                }
            }
        }
        return true;
    }

    public void removeAllLamps(){
        for(int i = 1; i<n+1; i++){
            for(int j = 1; j<n+1; j++){
//                Square t = squares[j][i];
                // if(t instanceof FieldSquare &&
                //     ((FieldSquare)t).getSqareStatus()==status.L ){
                    removeLamp(i, j);
                    // }
            }
        }
    }

    public void toFile(String filename){
        String str = "";
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n;j++){
                str+=cells[i][j].toString();
            }
            str+="\n";
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(str);
            writer.close();
        }catch(Exception e){
            System.out.println("exception in Board.toFile:\n"+e.getMessage());
        }
    }
    
    public void toFile(String dir, String filename){
        String str = "";
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n;j++){
                str+=cells[i][j].toString();
            }
            str+="\n";
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, filename)));
            writer.write(str);
            writer.close();
        }catch(Exception e){
            System.out.println("exception in Board.toFile:\n"+e.getMessage());
        }
    }
    

    public void createVerticalBlocks(){
   	 Block tmp;
   	 for(int i = 1; i<=n; i++) {
   		 tmp = new Block();
   		 for(int j = 1; j<=n; j++) {
   			 if(cells[i][j] instanceof FieldCell) {
   				 FieldCell f = (FieldCell)cells[i][j];
   				 tmp.add(f);
   				 f.xblock = tmp;
   			 }
   			 else {
   				 if(!tmp.isEmpty()) {
	    				 tmp = new Block();
   				 }
   			 }
   			 
   		 }

   	 }
   	 
   }

    public Vector<Block> verticalBlocks(){
//    	 Vector<Block> ans = new  Vector<Block>();
//    	 Block tmp;
//    	 for(int i = 1; i<=n; i++) {
//    		 tmp = new Block();
//    		 for(int j = 1; j<=n; j++) {
//    			 if(squares[i][j] instanceof FieldSquare) {
//    				 FieldSquare f = (FieldSquare)squares[i][j];
//    				 tmp.add(f);
//    				 f.xblock = tmp;
//    			 }
//    			 else {
//    				 if(!tmp.isEmpty()) {
//	    				 ans.add(tmp);
//	    				 tmp = new Block();
//    				 }
//    			 }
//    			 
//    		 }
//    		 if(!tmp.isEmpty()) {
//    			 ans.add(tmp);
//    		 }
//    	 }
    	 
    	 return verticalalBlocks;
    }
    public void createHorizontalBlocks(){
      	 Block tmp;
      	 for(int j = 1; j<=n; j++) {
      		 tmp = new Block();
      		 for(int i = 1; i<=n; i++) {

      			 if(cells[i][j] instanceof FieldCell) {
      				FieldCell f = (FieldCell)cells[i][j];
   				 tmp.add(f);
   				 f.yblock = tmp;
      			 }
      			 else {
      				 if(!tmp.isEmpty()) {
   	    				 tmp = new Block();
      				 }
      			 }
      			 
      		 }

      	 }
       
      }
    public Vector<Block> horizontalBlocks(){
//   	 Vector<Block> ans = new  Vector<Block>();
//   	 Block tmp;
//   	 for(int j = 1; j<=n; j++) {
//   		 tmp = new Block();
//   		 for(int i = 1; i<=n; i++) {
//
//   			 if(squares[i][j] instanceof FieldSquare) {
//   				FieldSquare f = (FieldSquare)squares[i][j];
//				 tmp.add(f);
//				 f.yblock = tmp;
//   			 }
//   			 else {
//   				 if(!tmp.isEmpty()) {
//	    				 ans.add(tmp);
//	    				 tmp = new Block();
//   				 }
//   			 }
//   			 
//   		 }
//   		 if(!tmp.isEmpty()) {
//   			 ans.add(tmp);
//   		 }
//   	 }
//   	 
   	 return horizontalBlocks;
   }

    public void applySolution (Solution sol) {
    	for(Coordinates c : sol) {
//    		System.out.println("x :" + c.x + " y : " + c.y);
    		addLamp(c.x, c.y);
    	}
    }
    public void cant(int x ,int y) {
    	Cell s = cells[y][x];
    	if(s instanceof FieldCell) {
    		FieldCell f = (FieldCell)s;
    		f.declareCant();
    	}
    		
    }
    
    public String CSVData() {
    	int black = 0;
    	int white = 0;
    	int num = 0;
    	int totalnums = 0;
    	for(int i = 1; i<=n; i++) {
    		for(int j = 1; j<=n; j++) {
    			if(cells[j][i] instanceof NumbedBlackCell) {
    				num++;
    				totalnums+= ((NumbedBlackCell)cells[j][i]).getConstraint();
    			}
    			else if(cells[j][i] instanceof FieldCell) {
    				white++;
    			}
    			else if(cells[j][i] instanceof BlackCell) {
    				black++;
    			}
    		}
    	}
    	String str= Integer.toString(n)+","+white+","+black+","+num+","+totalnums+",";
    	return str;
    }

	public int lightsCount() {
		int count = 0;
		for(int i = 1; i<=n; i++) {
    		for(int j = 1; j<=n; j++) {
    			if(cells[j][i] instanceof FieldCell && ((FieldCell)cells[j][i]).getStatus() == status.L) {
    				count++;
    			}
    		}
		}
		return count;
	}
		

}