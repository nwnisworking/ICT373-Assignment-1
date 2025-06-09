package com.ict373.assignment1.magazines;

/**
 * Represents a magazine.
 * 
 * @author nwnisworking
 */
public class Magazine extends Subscription{
	/**
	 * Default constructor for Magazine class.
	 * Initializes the magazine with default values.
	 */
	public Magazine() {
		super();
	}
	
	/**
	 * Constructor for Magazine class.
	 * @param id Unique identifier for the magazine
	 * @param name Name of the magazine
	 * @param cost Cost of the magazine
	 */
	public Magazine(int id, String name, double cost) {
		super(id, name, cost, null);
	}
}
