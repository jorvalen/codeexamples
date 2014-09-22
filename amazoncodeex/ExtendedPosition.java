package amazondudes;

public class ExtendedPosition extends Position {
	public Double distanceTo (Position to){
        /*Euclidean Distance - Pithagoras
            May not be the best way to measure distance on a map, but for simplicity's sake its ok.
        */
        return Math.sqrt(Math.pow(to.x - this.x, 2) + Math.pow(to.y - this.y, 2));
    }
}
