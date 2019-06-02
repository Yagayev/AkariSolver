import java.util.Vector;



public class Block extends Vector<FieldCell> {
	status blockstatus ;
	int numOfLamps;

	public Block() {
		super();
		blockstatus =status.W;
		numOfLamps = 0;
	}
	
	public void searchX() {
		Vector<FieldCell> iligal = new Vector<FieldCell>();
		for (FieldCell fieldCell : this) {
			if(fieldCell.getStatus() == status.X) {
				iligal.add(fieldCell);
			}
			
		}
		if(iligal.size() == 1) {
			iligal.firstElement().setStatus(status.B);
	}
	}
	
	public void  print() {
		System.out.println("{");
		for (FieldCell f : this) {
			System.out.println("( " +f.x + " , " +f.y + " )");
			
		}
		System.out.println("}");

	}
	}

