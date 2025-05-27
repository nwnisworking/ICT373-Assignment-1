package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.CSVParsable;

import java.util.ArrayList;

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
   * @param subscription Subscription object to be added
   */
  public void setSubscriptions(Subscription subscription){
    subscriptions.add(subscription);
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