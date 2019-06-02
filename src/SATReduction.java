
import java.util.Vector;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;


public class SATReduction {
	
	Board game;
	
	public SATReduction(Board b) {
		game = b;
	}
	
	public Solution act () throws TimeoutException {
		Coordinates.n=game.getSize();
		ISolver solver = generateSolver();
       	Solution ans = new Solution();
   	 
			int[] sol = solver.findModel();
			for(int i :  sol) {
				if(i > 0 ) {
					ans.add(new Coordinates(i));
				}
			}
			return ans;
	}

	ISolver generateSolver() {
		ISolver solver = SolverFactory.newDefault();
//		solver.setTimeout(360); // 6 minits timeout
		solver.setTimeout(3600); // 60 minitues timeout
		Cell s;
		for(Block b : game.verticalBlocks()) {
			handlebBlock(solver, b);
		}
		for(Block b : game.horizontalBlocks()) {
			handlebBlock(solver, b);
		}
	    for( int y = 1 ; y <= game.getSize() ; y ++){
            for( int x = 1 ; x <= game.getSize() ; x ++){
            	s = game.cells[y][x];
            	if(s instanceof NumbedBlackCell) {
            		handleNumber( solver, (NumbedBlackCell)s);
            	}
            	else if(s instanceof FieldCell) {
            			handleField(solver , (FieldCell)s);
            	}
 
            }
         }
		return solver;
	}
	
	
	private void handleNumber(ISolver solver ,NumbedBlackCell number ) {
		Vector<Cell> nighbords = number.getNeighbors(game);
		 VecInt v = new VecInt();
	    for (Cell s : nighbords) {
	        if( s instanceof FieldCell){
	            FieldCell fi = (FieldCell) s;
	        	   v.push(fi.id);
	          
	            
	        
	        }   
	    }
	    try {
			solver.addAtLeast(v, number.getConstraint());
		} catch (ContradictionException e) {
			System.out.println("Contradiction addAtLeast at : " +  number.id);

			e.printStackTrace();
		}
	    try {
			solver.addAtMost(v, number.getConstraint());
		} catch (ContradictionException e) {
			System.out.println("Contradiction addAtMost at : " +  number.id);
			e.printStackTrace();
		}	
	    }
	
	private void handleField(ISolver solver ,FieldCell cell) {
		status fieldStatus = cell.getStatus();
		 VecInt v;
		switch (fieldStatus) {
		case Y:
			 v = new VecInt();
			 //if the square is yellow add it as negative
			 v.push(-cell.id);
			 try {
				solver.addClause(v);
			} catch (ContradictionException e) {
				System.out.println("Contradiction at : " +  cell.id);
				e.printStackTrace();
			}
			break;
		case L:
			 v = new VecInt();
			 v.push(cell.id);
			//if the square is lamp add it as positive
			 try {
				solver.addClause(v);
			} catch (ContradictionException e) {
				System.out.println("Contradiction at handleField: " +  cell.id);
				e.printStackTrace();
			}
			break;
		case W: 
			handleWhite( solver , cell);
			break;
		case C:
			 v = new VecInt();
			 //if the square is yellow add it as negative
			 v.push(-cell.id);
			 try {
				solver.addClause(v);
			} catch (ContradictionException e) {
				System.out.println("Contradiction at : " +  cell.id);
				e.printStackTrace();
			}
			handleWhite( solver , cell);
			break;

		default:
			System.out.println("Reached default at handleField " +  cell.id+"status ="+fieldStatus.name());
			break;
		}
		
	}
	
	private void handleWhite(ISolver solver ,FieldCell cell) {
		VecInt v = new VecInt();
		for(FieldCell s : cell.xblock) {
			if(s.getStatus()==status.W||s.getStatus()==status.L||s.getStatus()==status.C) {
				v.push(s.id);
			}
		}
		for(FieldCell s : cell.yblock) {
			if(s.getStatus()==status.W||s.getStatus()==status.L||s.getStatus()==status.C) {
				v.push(s.id);
			}
		}
		
		try {
			solver.addClause(v);
		} catch (ContradictionException e) {
			System.out.println("Contradiction at handleWhite: " +  cell.id);
			e.printStackTrace();
		}

	}
	
	
	private void handlebBlock(ISolver solver ,Block block) {
		VecInt v = new VecInt();
		for(FieldCell f : block) {
			v.push(f.id);
		}
		try {
			solver.addAtMost(v, 1);
		} catch (ContradictionException e) {
			System.out.println("Contradiction at handlebBlock");
			e.printStackTrace();
		}
		
	}
}
