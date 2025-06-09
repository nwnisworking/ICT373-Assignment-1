package com.ict373.assignment1.customers;

/**
 * Associate Customer class
 * Represents an associate customer.
 * An associate customer is a type of customer that does not pay for subscriptions.
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename AssociateCustomer.java
 */
public class AssociateCustomer extends Customer{
	/**
	 * Customer that is paying for associate's subscription.
	 */
	private PayingCustomer payer;

	/**
	 * Default constructor for AssociateCustomer.
	 * Initializes the customer with default values.
	 */
	public AssociateCustomer() {
		super();
	}

	/**
	 * Constructor for AssociateCustomer
	 * @param id Unique identifier for the customer.
	 * @param name Name of the customer.
	 * @param email Email address of the customer.
	 */
	public AssociateCustomer(int id, String name, String email){
		super(id, name, email);
	}

	/**
	 * Get the paying customer.
	 * @return Paying customer for this associate
	 */
	public PayingCustomer getPayer(){
		return payer;
	}

	/**
	 * Set the Paying customer. 
	 * @param payer Customer paying for the associate.
	 */
	public void setPayer(PayingCustomer payer){
		this.payer = payer;
	}
}
