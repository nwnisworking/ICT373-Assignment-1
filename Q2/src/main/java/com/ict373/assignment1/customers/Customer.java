package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.IO;
// import com.ict373.assignment1.utils.IO;
import com.ict373.assignment1.utils.CSVParsable;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Represents a customer in the system.
 */
public abstract class Customer implements CSVParsable{
  /**
   * Unique identifier for the customer
   */
  protected int id;

  /**
   * Name of the customer
   */
  protected String name;

  /**
   * Customer's email address
   */
  protected String email;

  /**
   * List of available subscriptions 
   */
  protected ArrayList<Subscription> subscriptions = new ArrayList<>();

  /**
	 * Structure of Customer's column 
	 */
	private static String column_structure = "%-3s | %-32s | %-64s";

  public Customer(){
    this.id = 0;
    this.name = "";
    this.email = "";
  }

  /**
   * Constructor for Customer class
   * @param id Unique identifier for the customer
   * @param name Name of the customer
   * @param email Email address of the customer
   */
  public Customer(int id, String name, String email){
    this.id = id;
    this.name = name;
    this.email = email;
  }

  /**
   * Get the unique identifier of the customer
   * @return Unique identifier of the customer
   */
  public int getId(){
    return id;
  }

  /**
   * Set the name of the customer
   * @param name name of the customer
   */
  public void setName(String name){
    this.name = name;
  }
    
  /**
   * Get the name of the customer
   * @return name of the customer
   */
  public String getName(){
    return name;
  }
    
  /**
   * Set customer's email address
   * @param email email address of the customer
   */
  public void setEmail(String email){
    this.email = email;
  }

  /**
   * Get the email of the customer 
   * @return email address of the customer
   */
  public String getEmail(){
    return email;
  }

  public Subscription paidSubscription(int paid_by, int sub_id){
    for(Subscription subscription : subscriptions){
      if(subscription.getPaidBy() == paid_by && subscription.getId() == sub_id)
      return subscription;
    }

    return null;
  }

  /**
   * Check if the customer has a subscription to a magazine
   * @param id the ID of the magazine subscription
   * @return true if the customer has the magazine, false otherwise
   */
  public boolean hasMagazine(int id){
    for(Subscription subscription : subscriptions){
      if(subscription.isMagazine() && subscription.getId() == id)
        return true;
    }
    
    return false;
  }

  /**
   * Get the list of subscriptions for the customer
   * @return ArrayList of Subscription objects
   */
  public ArrayList<Subscription> getSubscriptions(){
    return subscriptions;
  }

  /**
   * Add a subscription to the customer's list of subscriptions
   * @param subscription subscription object to be added
   */
  public void setSubscriptions(Subscription subscription){
    subscriptions.add(subscription);
  }

  /**
   * Remove specific subscription from the paying customer
   * @param paid_by the paying customer ID
   * @param sub_id the subscription ID
   */
  public void removeSubscription(int paid_by, int sub_id){
    for(int i = 0; i < subscriptions.size(); i++){
      Subscription sub = subscriptions.get(i);

      if(sub.getPaidBy() == paid_by && sub.getId() == sub_id){
        subscriptions.remove(sub);
        break;
      }
    }
  }

  public void removeSubscription(Subscription sub){
    subscriptions.remove(sub);
  }

  public static void column(){
    IO.println(String.format(column_structure, "ID", "Name", "Email"));
  }

  public static void display(Customer cust){
    IO.println(String.format(column_structure, cust.id, cust.name, cust.email));
  }
  /**
	 * Display a list of customer in column-row format.
	 * @param <T> a generic type that indicates a Customer or its inherited class
	 * @param subs a treemap of customer with its id as the key
	 * @param cls a customer or its inherited class
	 * @param show_column display column if true
	 */
	public static <T> void display(TreeMap<Integer, Customer> custs, Class<T> cls, boolean show_column){
		if(show_column){
      column();
		}

		for(Customer cust : custs.values()){
			if(cls == null || cls.isInstance(cust)){
				display(cust);
			}
		}
	}

  @Override
  public void parse(CSVParser parser){
    id = parser.getInteger();
    name = parser.getString();
    email = parser.getString();
  }
  
  @Override
  public String toString(){
    return id + "," + name + "," + email;
  }
}