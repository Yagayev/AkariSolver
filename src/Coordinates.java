
public class Coordinates {
	public int x;
	public int y;
	public static int n = 0;
	Coordinates(int y, int x){
		this.x=x;
		this.y=y;
	}
	
	Coordinates(int id){
		x=id%n;
		y=id/n;
		if(x == 0) {
			x = n;
			y--;
		}
		
	}

}
