package com.ict373.assignment1.magazines;

/**
 * Represents a supplement to a magazine subscription.
 */
public class Supplement extends Subscription{
	/**
	 * Default constructor for Magazine class.
	 * Initializes the magazine with default values.
	 */
	public Supplement() {
		super();
	}
	
	/**
	 * Parameterized constructor for Supplement class without magazine.
	 * @param id Unique identifier for the supplement
	 * @param mag_id ID of the magazine this supplement is associated with
	 * @param name Name of the supplement
	 * @param cost Cost of the supplement
	 */
	public Supplement(int id, String name, double cost){
		super(id, name, cost, null);
	}

	/**
	 * Parameterized constructor for Supplement class without magazine.
	 * @param id Unique identifier for the supplement
	 * @param mag_id ID of the magazine this supplement is associated with
	 * @param name Name of the supplement
	 * @param cost Cost of the supplement
	 * @param magazine Magazine associated with this supplement
	 */
	public Supplement(int id, String name, double cost, Magazine magazine){
		super(id, name, cost, magazine);
	}
}
