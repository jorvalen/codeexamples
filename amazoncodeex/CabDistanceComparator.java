package amazondudes;

import java.util.Comparator;

public class CabDistanceComparator  implements Comparator<Cab>{
	ExtendedPosition userPos;
    
    public CabDistanceComparator(ExtendedPosition userPos){
        this.userPos = userPos;
    }

    public int compare (Cab a, Cab b){
        Double distToCabA;
        Double distToCabB;
        distToCabA = this.userPos.distanceTo(a.getCabPosition());
        distToCabB = this.userPos.distanceTo(b.getCabPosition());
        if (distToCabA < distToCabB){
            return -1;
        }
        if (distToCabA > distToCabB){
            return 1;
        }
        return 0;
    }    
}
