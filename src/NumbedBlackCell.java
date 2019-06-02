public  class NumbedBlackCell extends BlackCell{
    private int remain;
    private int constraint ;
    private int whiteAriund;
    NumbedBlackCell(int x , int y , int n , int constraint){
        super(x,y , n);
        this.constraint = constraint;
        remain = constraint;
        whiteAriund = 0;
    }

    public int getConstraint(){
        return constraint;
    }

    public int getremain(){ 
        return remain;
     }
     public void addLampNear(){ 
         remain  -- ;
 
     }
     public void removeLampNear(){ 
         remain  ++ ;
 
     }
     public String toString(){
        //  return Integer.toString(whiteAriund);
        return Integer.toString(constraint);
     }
 
     public boolean isBlackNumber(){
         return true;
     }
    
     public int getWhitAround(){
         return whiteAriund;
     }
     public void settWhitAround( int newVal){
         whiteAriund = whiteAriund + newVal;
    }

    //added for the generator;
    public void setConstraint(int n){
        this.constraint = n;
        remain = 0;
    }

}