package com.ict373.assignment1.magazines;

import java.util.ArrayList;
import java.util.Collection;

import com.ict373.assignment1.utils.IO;

/**
 * Represents a subscription to a magazine or a supplement.
 * This class is abstract and should be extended by subscription types: magazine or supplement.
 */
public abstract class Subscription{
	/**
	 * Unique identifier for a subscription.
	 */
	private int id;

	/**
	 * If this subscription is a supplement to a magazine, magazine will be set.
	 * Otherwise, it results in null
	 */
	private Magazine magazine;

	/**
	 * The name of the subscription.
	 */
	private String name;

	/**
	 * The cost of the subscription.
	 */
	private double cost;

	public static final String TABLE_COLUMN = "%-3s | %-32s | %-7s | %-10s";
	
	public static final String[] TABLE_COLUMN_NAME = {"ID", "Name", "Cost", "Type"};

	/**
	 * Default constructor for creating a subscription.
	 */
	public Subscription(){
		id = 0;
		magazine = null;
		cost = 0;
		name = "";
	}

	/**
	 * Constructor for creating a subscription.
	 * 
	 * @param name The name of the subscription.
	 * @param cost The cost of the subscription.
	 * @param id The unique identifier for the subscription.
	 * @param magazine Magazine of the subscription.
	 */
	public Subscription(int id, String name, double cost, Magazine magazine){
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.magazine = magazine == null ? null : magazine;
	}

	/**
	 * Check if this subscription is a supplement.
	 * @return true if this subscription is a supplement, false if it is a magazine.
	 */
	public boolean isSupplement(){
		return this instanceof Supplement;
	}

	/**
	 * Check if this subcsription is a magazine.
	 * @return true if this subscription is a magazine, false if it is a supplement.
	 */
	public boolean isMagazine(){
		return this instanceof Magazine;
	}


	/**
	 * Get the unique identifier for this subscription.
	 * @return the unique identifier for this subscription.
	 */
	public int getId(){
		return id;
	}

	/**
	 * Set the unique identifier for this subscription.
	 * @param id the unique identifier.
	 */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * Get the magazine.
	 * @return the magazine attached to this subscription.
	 */
	public Magazine getMagazine(){
		return magazine;
	}

	/**
	 * Set the magazine.
	 * @param magazine magazine object attached to this subscription.
	 */
	public void setMagazine(Magazine magazine){
		if(magazine == null)
			throw new RuntimeException("Magazine cannot be null");

		this.magazine = magazine;
	}

	/**
	 * Get the name of the subscription.
	 * @return the name of the subscription.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Set the name of the subscription
	 * @param name name of the subscription
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Get the cost of the subscription.
	 * @return the cost of the subscription.
	 */
	public double getCost(){
		return cost;
	}

	/**
	 * Set the cost of the subscription.
	 * @param cost the cost of the subscription 
	 */
	public void setCost(double cost){
		this.cost = cost;
	}

	/**
	 * Display data in table format.
	 */
	public void display(){
		IO.println(String.format(TABLE_COLUMN, id, name, cost, isMagazine() ? "Magazine" : "Supplement"));
	}

	/**
   * Get the available subscription type.
   * @param type The index which houses the available subscription type.
   * @return Returns a subscription type. Otherwise, it throws a RuntimeException
   */
	public static Subscription getType(int type){
		switch(type){
      case 0 : return new Supplement();
      case 1 : return new Magazine();
      default : throw new RuntimeException("Method type does not exist");
    }
	}

	/**
   * Filters subscription based on a child of subscription class
   * @param <T>
   * @param custs The subscription in a collection 
   * @param cls The class to filter for
   * @return Result after filtering the class
   */
	public static <T> ArrayList<Subscription> filterSubscription(Collection<Subscription> subs, Class<T> cls){
		ArrayList<Subscription> list = new ArrayList<>();

		for(Subscription sub : subs){
			if(cls.isInstance(sub))
				list.add(sub);
		}

		return list;
	}
}
