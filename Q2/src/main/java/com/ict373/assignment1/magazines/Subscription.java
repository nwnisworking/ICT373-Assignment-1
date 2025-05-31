package com.ict373.assignment1.magazines;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.TreeMap;

import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.IO;
import com.ict373.assignment1.utils.CSVParsable;

/**
 * Represents a subscription to a magazine or a supplement.
 * This class is abstract and should be extended by subscription types: magazine or supplement.
 */
public abstract class Subscription implements Cloneable, CSVParsable{
	/**
	 * Unique identifier for a subscription.
	 */
	protected int id;

	/**
	 * If this subscription is a supplement to a magazine, this is the ID of that magazine.
	 * Otherwise, the value is empty.
	 */
	protected OptionalInt mag_id;

	/**
	 * The cost of the subscription.
	 */
	protected double cost;

	/**
	 * The name of the subscription.
	 */
	protected String name;

	/**
	 * The ID of the customer that links to this subscription. 
	 */
	protected int paid_for = 0;

	/**
	 * The ID of the customer that paid for this subscription.
	 */
	protected int paid_by = 0;

	/**
	 * Structure of Subscription's column 
	 */
	private static String column_structure = "%-3s| %-12s | %-8s | %-64s | %-5s";

	/**
	 * Default constructor for creating a subscription.
	 */
	public Subscription(){
		this.id = 0;
		this.mag_id = OptionalInt.empty();
		this.name = "";
		this.cost = 0.0;
	}

	/**
	 * Constructor for creating a subscription.
	 * 
	 * @param name The name of the subscription.
	 * @param cost The cost of the subscription.
	 * @param id The unique identifier for the subscription.
	 * @param mag_id Optional ID of the magazine if this is a supplement.
	 */
	public Subscription(int id, int mag_id, String name, double cost){
		this.id = id;
		this.mag_id = mag_id == 0 ? OptionalInt.empty() : OptionalInt.of(mag_id);
		this.name = name;
		this.cost = cost;
	}

	/**
	 * Check if this subscription is a supplement.
	 * @return true if this subscription is a supplement, false if it is a magazine.
	 */
	public boolean isSupplement(){
		return mag_id.isPresent();
	}

	/**
	 * Check if this subcsription is a magazine.
	 * @return true if this subscription is a magazine, false if it is a supplement.
	 */
	public boolean isMagazine(){
		return !mag_id.isPresent();
	}

	/**
	 * Get the unique identifier for this subscription.
	 * @return the unique identifier for this subscription.
	 */
	public int getId(){
		return id;
	}

	/**
	 * Get the magazine ID.
	 * @return the magazine ID if this is a supplement, otherwise returns 0 if subscription is a magazine.
	 */
	public int getMagazineId(){
		return mag_id.orElse(0);
	}

	/**
	 * Get the name of the subscription.
	 * @return the name of the subscription.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Get the cost of the subscription.
	 * @return the cost of the subscription.
	 */
	public double getCost(){
		return cost;
	}

	/**
	 * Set the customer ID that this subscription is paid for.
	 * @param customer_id the ID of the customer that this subscription is paid for.
	 */
	public void setPaidFor(int customer_id){
		paid_for = customer_id;
	}

	/**
	 * Get the customer ID that this subscription is paid for.
	 * @return the ID of the customer that this subscription is paid for.
	 */
	public int getPaidFor(){
		return paid_for;
	}

	/**
	 * Set the customer ID that paid for this subscription.
	 * @param customer_id the ID of the customer that paid for this subscription.
	 */
	public void setPaidBy(int customer_id){
		paid_by = customer_id;
	}

	/**
	 * Get the customer ID that paid for this subscription.
	 * @return the ID of the customer that paid for this subscription.
	 */
	public int getPaidBy(){
		return paid_by;
	}

	public static void column(){
		IO.println(String.format(column_structure, "ID", "Magazine ID", "Paid By", "Name", "Cost"));
	}

	public static void display(Subscription sub){
		IO.println(String.format(column_structure, sub.id, sub.mag_id.orElse(0), sub.paid_by, sub.name, sub.cost));
	}

	/**
	 * Display a list of subscription in column-row format.
	 * @param <T> a generic type that indicates a Subscription or its inherited class
	 * @param subs a treemap of subscription with its id as the key
	 * @param cls a subscription or its inherited class
	 * @param show_column display column if true
	 */
	public static <T> void display(TreeMap<Integer, Subscription> subs, Class<T> cls, boolean show_column){
		if(show_column){
			column();
		}

		for(Subscription sub : subs.values()){
			if(cls == null || cls.isInstance(sub)){
				display(sub);
			}
		}
	}

	public static <T> void display(ArrayList <Subscription> subs, Class<T> cls, boolean show_column){
		if(show_column){
			column();
		}

		for(Subscription sub : subs){
			if(cls == null || cls.isInstance(sub)){
				display(sub);
			}
		}
	}

	@Override
	public void parse(CSVParser parser){
		id = parser.getInteger();
		int mag_id_value = parser.getInteger();
		mag_id = mag_id_value == 0 ? OptionalInt.empty() : OptionalInt.of(mag_id_value);
		name = parser.getString();
		cost = parser.getDouble();
	}

	@Override
	public Subscription clone() throws CloneNotSupportedException{
		return (Subscription) super.clone();
	}

	@Override
	public String toString(){
		return id + "," + mag_id.orElse(0) + "," + name + "," + cost;
	}
}
