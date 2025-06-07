package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.utils.IO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a customer in the system.
 */
public abstract class Customer{
  /**
   * Unique identifier for the customer.
   */
  private int id;

  /**
   * Name of the customer.
   */
  private String name;

  /**
   * Customer's email address.
   */
  private String email;

  /**
   * List of available subscriptions. 
   * Customer can make duplicate subscriptions
   */
  private ArrayList<Subscription> subscriptions = new ArrayList<>();

  /**
   * Represents the structure of the column
   */
  public static final String TABLE_COLUMN = "%-3s | %-32s | %-64s | %-10s";

  /**
   * Represents the column header
   */
  public static final String[] TABLE_COLUMN_NAME = {"ID", "Name", "Email", "Type"};

  /**
   * Default constructor for Customer class
   */
  public Customer(){
    this.id = 0;
    this.name = "";
    this.email = "";
  }

  /**
   * Constructor for Customer class.
   * @param id Unique identifier for the customer.
   * @param name Name of the customer.
   * @param email Email address of the customer.
   */
  public Customer(int id, String name, String email){
    this.id = id;
    this.name = name;
    this.email = email;
  }

  /**
   * Get the unique identifier of the customer.
   * @return Unique identifier of the customer.
   */
  public int getId(){
    return id;
  }

  /**
   * Set the unique identifier of the customer.
   * @param id A unique identifier.
   */
  public void setId(int id){
    this.id = id;
  }

  /**
   * Set the name of the customer.
   * @param name Name of the customer.
   */
  public void setName(String name){
    this.name = name;
  }
    
  /**
   * Get the name of the customer.
   * @return Name of the customer.
   */
  public String getName(){
    return name;
  }
    
  /**
   * Set customer's email address.
   * @param email Email address of the customer.
   */
  public void setEmail(String email){
    this.email = email;
  }

  /**
   * Get the email of the customer.
   * @return Email address of the customer.
   */
  public String getEmail(){
    return email;
  }

  /**
   * Checks if the customer has a subscription to a magazine.
   * @param magazine Magazine to find in the list of subscriptions.
   * @return True if the customer has the magazine, false otherwise.
   */
  public boolean hasMagazine(int id){
    for(Subscription subscription : subscriptions){
      if(subscription.isMagazine() && subscription.getId() == id){
        return true;
      }
    }

    return false;
  }

  /**
   * Get the list of subscriptions for the customer.
   * @return ArrayList of Subscription objects.
   */
  public ArrayList<Subscription> getSubscriptions(){
    return new ArrayList<>(subscriptions);
  }

  /**
   * Add a subscription to the customer's list of subscriptions.
   * @param subscription Subscription to be added.
   */
  public void addSubscriptions(Subscription subscription){
    subscriptions.add(subscription);
  }

  /**
   * Remove a subscription from the list of subscriptions.
   * 
   * If the subscription is a magazine, supplements associated with the magazine will be deleted alongside.
   * @param subscription Subscription to be removed.
   */
  public void removeSubscription(Subscription subscription){
    subscriptions.remove(subscription);

    subscriptions.removeIf(e->!subscriptions.contains(subscription) && e.isSupplement() && e.getMagazine().getId() == subscription.getId());
  }

  /**
	 * Display data in table format.
	 */
	public void display(){
		IO.println(String.format(TABLE_COLUMN, id, name, email, this instanceof PayingCustomer ? "Paying" : "Associate"));
	}

  /**
   * Get the available customer type.
   * @param type The index which houses the available customer type.
   * @return Returns a customer type. Otherwise, it throws a RuntimeException
   */
  public static Customer getType(int type){
    switch(type){
      case 0 : return new AssociateCustomer();
      case 1 : return new PayingCustomer();
      default : throw new RuntimeException("Customer type does not exist");
    }
  }

  /**
   * Filters customer based on a child of customer class
   * @param <T>
   * @param custs The customers in a collection 
   * @param cls The class to filter for
   * @return Result after filtering the class
   */
  public static <T> ArrayList<Customer> filterCustomer(Collection<Customer> custs, Class<T> cls){
		ArrayList<Customer> list = new ArrayList<>();

		for(Customer cust : custs){
			if(cls.isInstance(cust))
				list.add(cust);
		}

		return list;
	}

  /**
   * Get the total cost of all the subscriptions.
   * @return Cost of subscriptions.
   */
  public double getTotalCost(){
    double total = 0;

    for(Subscription sub : subscriptions){
      total+= sub.getCost();
    }

    return total;
  }

  /**
   * Gets the subscription size 
   * @return An integer value of the subscription size
   */
  public int getSubscriptionSize(){
    return subscriptions.size();
  }
}