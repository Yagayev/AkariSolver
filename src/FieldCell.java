import sun.net.www.content.text.plain;

enum status {
    Y , W , L , X , C ,B
}

public  class FieldCell extends Cell{


    private status cellStatus;
    private int timelighted;
    private boolean can;
    private boolean hasLamp;
//    public Block xblock;
//    public Block yblock;
    

    FieldCell(int x , int y ,int n ){
        super(x,y , n);
        cellStatus =status.B;
        timelighted = 0;
        can = true;
        hasLamp= false;
    }

    public status getStatus(){
    	boolean yello =this.xblock.blockstatus == status.Y ||this.yblock.blockstatus == status.Y;
    	if(cellStatus == status.C ||cellStatus == status.X ) {
    		if(yello) {
    			return  status.Y ;
    		}
    		else {
    			return cellStatus;
			}
    	}
    	if(cellStatus == status.B) {
    		if(yello) {
    			return  status.Y ;
    		}
    		else {
    			return status.W;
			}
    		
    	}
    	return status.L;
    }
    
//    public String toString(){
//    	
//        return getStatus().name();
//    }
    public String toString(){
    	boolean yello =this.xblock.blockstatus == status.Y ||this.yblock.blockstatus == status.Y;
    	if(cellStatus == status.B) {
    	
    		if(yello) {
    	
    			return  status.Y.name() ;
    		}
    		else {
    			return status.W.name();
			}
    		
    	}
    	if(cellStatus == status.L) {
    		
    		if(this.xblock.numOfLamps > 1 || this.yblock.numOfLamps > 1) {
    			this.cellStatus = status.X;
    			return  status.X.name() ;
    		}
    		else {
    			return status.L.name();
			}
    		
    	}
    	if(cellStatus == status.X) {
    		if(this.xblock.numOfLamps > 1 || this.yblock.numOfLamps > 1) {
    			return  status.X.name() ;
    		}
    		else {
    			this.cellStatus = status.L;
    			return status.L.name();
			}
    		
    	}
    	return cellStatus.name();
        //return getStatus().name();
    }
    
void setStatus (status sqareStatus) {
	this.cellStatus =sqareStatus ;

}
    public void putLemp(){
        if(!hasLamp) {
       	 this.xblock.numOfLamps++;
         this.yblock.numOfLamps++;
         hasLamp = true;
       }
    	
    	if(getStatus() == status.L || getStatus() == status.X  ) {
    		return;
    	}
        if (getStatus() == status.Y){
            cellStatus = status.X;
                }
        else {
            cellStatus = status.L;

        }
    
       
    }



    public void lighstOn(){
        if (getStatus() == status.L || getStatus() == status.X ){
            cellStatus = status.X;
            timelighted ++;
        }
        else if (getStatus() == status.B || cellStatus == status.C) {
            timelighted ++;
            cellStatus = status.B;
        }
        else{
            timelighted ++;

        }
    }

    public void remobeLamp(){
    	if(!hasLamp){
    		return;
    	}
    	hasLamp = false;
        cellStatus = status.B;
        this.xblock.numOfLamps--;
        this.yblock.numOfLamps--;
      
    }

    public void lightsOff(){
        if (cellStatus == status.X ){
            timelighted --;
            if(timelighted == 1){
                cellStatus = status.L;
        }
    }
        else if(cellStatus == status.L){
            // I assume ther wont be anather lamp lighting this one  it whold be an X
            cellStatus = status.B;
            timelighted --;

        }
        else if(cellStatus == status.B){
            timelighted --;

        }
    }


    public status getCellStatus() {
        return cellStatus;
    }
    @Override
    public boolean isFiledCell(){
        return true;

    }
    public void declareCant() {
    	cellStatus = status.C;
    	can = false;
    }
    public boolean can() {
    	return can;
    }
 public boolean haseLamp() {
	 return hasLamp;
}

}