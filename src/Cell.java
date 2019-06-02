import java.util.Vector;

public class Cell{
    int x;
    int y;
    int n;
    int id;
    public Block xblock;
    public Block yblock;

    public Cell(int setX, int setY, int setN){
        x = setX;
        y = setY;
        n = setN;
        id = y*n+x;
    }

    public String toString(){
        return "S";
    }

    public boolean isFiledCell(){
        return false;

    }

    public boolean isBlackNumber(){
        return false;
    }

    //1.2
    public Vector<Cell> getNeighbors(Board b){
        Vector<Cell> ans = new Vector<Cell>();
        if(x>1){
            ans.add(b.cells[y][x-1]);
        }
        if(x<=n){
            ans.add(b.cells[y][x+1]);
        }
        if(y>1){
            ans.add(b.cells[y-1][x]);
        }
        if(y<=n){
            ans.add(b.cells[y+1][x]);
        }

        return ans;
    }

}