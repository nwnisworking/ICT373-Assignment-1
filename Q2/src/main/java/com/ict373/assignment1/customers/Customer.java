package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.payment.methods.Method;
import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.CSVParsable;

import java.util.ArrayList;

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
   * Customer's default payment method
   */
  protected Method payment_method;

  /**
   * List of available subscriptions 
   */
  protected ArrayList<Subscription> subscriptions = new ArrayList<>();

  public Customer(){
    this.id = 0;
    this.name = "";
    this.email = "";
    this.payment_method = null; 
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
    this.payment_method = null; 
  }

  /**
   * Constructor for Customer class with payment method
   * @param id Unique identifier for the customer
   * @param name Name of the customer
   * @param email Email address of the customer
   * @param payment_method Payment method used by the customer
   */
  public Customer(int id, String name, String email, Method payment_method){
    this.id = id;
    this.name = name;
    this.email = email;
    this.payment_method = payment_method; 
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
   * Get the payment method used by the customer
   * @return payment method used by the customer
   */
  public Method getPaymentMethod(){
    return payment_method;
  }

  /**
   * Set the payment method used by the customer
   * @param payment_method payment method to be set for the customer
   */
  public void setPaymentMethod(Method payment_method){
    this.payment_method = payment_method;
  }

  /*
   * Check if the customer has a subscription to a magazine
   */
  public boolean hasMagazine(int id){
    for(Subscription subscription : subscriptions){
      if(subscription.isMagazine() && subscription.getId() == id)
        return true;
    }
    
    return false;
  }

  public ArrayList<Subscription> getSubscriptions(){
    return subscriptions;
  }

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
    return id + "," + name + "," + email + "," + payment_method;
  }
}