package com.ict373.assignment1.customers;

/**
 * Represents an associate customer.
 * An associate customer is a type of customer that does not pay for subscriptions.
 */
public class AssociateCustomer extends Customer{
	/**
	 * Default constructor for AssociateCustomer
	 * Initializes the customer with default values
	 */
	public AssociateCustomer() {
		super();
	}

	/**
	 * Constructor for AssociateCustomer
	 * @param id Unique identifier for the customer
	 * @param name Name of the customer
	 * @param email Email address of the customer
	 */
	public AssociateCustomer(int id, String name, String email) {
		super(id, name, email);
	}

	@Override
	public String toString() {
		return 0 + ',' + super.toString();
	}
}
