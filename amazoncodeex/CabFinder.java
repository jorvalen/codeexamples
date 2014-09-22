package amazondudes;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class CabFinder implements CabStatusListener {

    //heap for all the cabs within 1km
    PriorityQueue<Cab> PQcabs1km;
    //heap for all the maxcabs cabs within 1km
    PriorityQueue<Cab> PQnearest;
    CabApp cabApp;
    int maxCabs;
    ExtendedPosition userPos;

/**
 * Take all the elements from Heap PQcabs1km and insert it to PQnearest until it reaches maxcabs
 * PQcabs1km always has its closes element at the top, so the first element is always the closest one which is not in
 * PQnearest
 * */
private void updateNearestCabs(){
    Cab aux;
    while(PQnearest.size() < this.maxCabs){
        aux = PQcabs1km.poll();
        if (aux == null){
            //not reached max cabs , but all cabs within 1km are in there.
            break;
        }
        else{
            //adding cab that is inside 1 km while i still have capacity in maxcabs.
            PQnearest.add(aux);
        }
    }
}

/**
* Initiates CabFinder. Called only once per app startup.
* @app An application object providing services implemented by
*      the rest of the application.
* @maxCabs Nearest number of cabs that can be returned to the user
*/
public void initialize(CabApp app, int maxCabs) {
  this.maxCabs = maxCabs;
  cabApp = app;
  this.userPos = (ExtendedPosition) cabApp.getUserPosition();
  
  
  //create a comparator for the distance of the cabs based on the current user position
  Comparator<Cab> distanceToCabComp = new CabDistanceComparator(this.userPos);
  //inverse comparator so that the max element is at the top of the heap
  Comparator<Cab> distanceToCabInvComp = new InverseCabDistanceComparator(this.userPos);
  PQcabs1km = new PriorityQueue<Cab> (this.maxCabs, distanceToCabComp);
  PQnearest = new PriorityQueue<Cab> (this.maxCabs, distanceToCabInvComp);
  
  /* traverse all the cabs available and check if they are within 1km
  i am assuming that only available cabs are shown.
  */
  Cab aux;
  for(Iterator<Cab> it = cabApp.getCabs(); it.hasNext(); ){
      aux = it.next();
      if (aux.isAvailable() && this.userPos.distanceTo(aux.getCabPosition()) < 1000){
          PQcabs1km.add(aux);
      }
  }
  
    updateNearestCabs();
  
}

/**
* Gets nearest cabs within 1km of the current user’s location. 
* These must be the *nearest possible* @maxCabs in the 1km area.
* @return An unordered list of the nearest cabs.
*/
public Cab[] getNearestCabs() {
    /*heap should contain at this point all the maxcabs that are nearest to the user.
    toarray function should return all of them unordered.
    */
    return (Cab[]) PQnearest.toArray();
}

/**
* Asynchronous Callback per CabStatusListener (see below). Called when the position of a cab has changed. 
*/
public void onCabPositionChanged(Cab cab) {


    if (PQnearest.contains(cab)){
        PQnearest.remove(cab);
    }
    else if (PQcabs1km.contains(cab)){
        PQcabs1km.remove(cab);
    }
    if (cab.isAvailable() && this.userPos.distanceTo(cab.getCabPosition()) < 1000 ){
        PQcabs1km.add(cab);
    }
    
    updateNearestCabs();

}

/**
* Asynchronous Callback per CabStatusListener (see below). Called when a cab’s availability changes.
* @cab The cab whose availability has changed
* @isAvailable true if the cab is now available, false otherwise
*/
public void onCabAvailabilityChanged (Cab cab, boolean isAvailable) {
    Cab aux;
    Double distanceToCab;
    //wasnt available before, so it wasn't on any of the heaps
    if (isAvailable){
        //if distance is lower than 1km then i add it to the 1km heap
        distanceToCab = this.userPos.distanceTo(cab.getCabPosition());
        if (distanceToCab < 1000){
            PQcabs1km.add(cab);
            /*this element could be lower distance than elements already in the nearest queue
            if is is then i remove to highest element at the top of the nearest heap and put it back in the 
            1km heap
            */
            
            aux = PQnearest.peek();
            if ( distanceToCab < this.userPos.distanceTo(aux.getCabPosition())){
                aux = PQnearest.poll();
                PQcabs1km.add(aux);
            }
        }
    }
    //if now it isnt available i remove it from the heaps
    else{
        if (PQnearest.contains(cab)){
            PQnearest.remove(cab);
        }
        else if (PQcabs1km.contains(cab)){
            PQcabs1km.remove(cab);
        }    
    }
    
    //i update the nearest cabs heap
    updateNearestCabs();
}
}
