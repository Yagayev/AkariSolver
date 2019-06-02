import java.util.Vector;
import java.util.Random;

public class RandomVector<T> extends Vector<T>{
    
    Random r;
    public RandomVector(){
        super();
        r = new Random();
    }
    
    private int getRand(int min, int max){
        //java lib only offers pseudo randomness, so maybe we could switch to pure random with:
        // https://sourceforge.net/projects/trng-random-org/
        
        return r.nextInt(max-min)+min;
    }

    public T getRand(){
        int k = getRand(0, size());
        return get(k);
    }

    public T popRand(){
        int k = getRand(0, size());
        T ans = get(k);
        remove(k);
        return ans;
    }
}