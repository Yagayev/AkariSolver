import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

public class satLightUp {
		
	Board game;
	    
	satLightUp(Board b){
	        game = b;
	
	}    

    public ISolver toSAT(){
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        solver.newVar(game.n*game.n*game.n);	//setting maximal number of vaiables
        
        
        
        return solver;
        
    }

}
