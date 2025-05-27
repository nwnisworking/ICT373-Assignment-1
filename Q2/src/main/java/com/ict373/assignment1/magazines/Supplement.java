package com.ict373.assignment1.magazines;

/**
 * Represents a supplement to a magazine subscription.
 */
public class Supplement extends Subscription{
	public Supplement(){
		super();
	}
	
	/**
	 * Constructor for Supplement class.
	 * @param id Unique identifier for the supplement
	 * @param mag_id ID of the magazine this supplement is associated with
	 * @param name Name of the supplement
	 * @param cost Cost of the supplement
	 */
	public Supplement(int id, int mag_id, String name, double cost) {
		super(id, mag_id, name, cost);
	}

	@Override
	public String toString() {
		return 0 + "," + super.toString();
	}
}
