package amazondudes;

import java.util.Iterator;

public interface CabApp {
	/**
	* Gets the current location of the user
	*/
	Position getUserPosition();

	/**
	* Returns an iterator that gives access to the list of all cabs in the city
	*/
	Iterator<Cab> getCabs();

	/**
	* Registers a CabStatusListener object for change notifications of cab object data.
	*/
	void register(CabStatusListener listener);

}
