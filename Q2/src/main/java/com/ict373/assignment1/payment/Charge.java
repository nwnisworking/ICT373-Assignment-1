package com.ict373.assignment1.payment;

import java.util.ArrayList;

import com.ict373.assignment1.customers.Customer;
import com.ict373.assignment1.customers.PayingCustomer;
import com.ict373.assignment1.magazines.Subscription;
// import com.ict373.assignment1.payment.methods.*;
import com.ict373.assignment1.utils.IO;

public class Charge{
	/**
	 * Constants to reflect credit card.
	 */
	public static final int CREDIT_CARD = 1;

	/**
	 * Constants to reflect debit card.
	 */
	public static final int DEBIT_CARD = 2;

	/**
	 * The customer who paid in the charges.
	 * Only paying customer can pay for subscription.
	 */
	private PayingCustomer paid_by;

	/**
	 * The customer who is being paid for in the charges.
	 */
	private Customer paid_for;

	/**
	 * The subscription associated with the charges.
	 */
	private Subscription subscription;

	/**
	 * Structure of Customer's column 
	 */
	private static String column_structure = "%-8s | %-9s | %-16s";

	/**
	 * Default constructor for creating a charges.
 	 * @param paid_by the ID of the customer who paid in the charges.
	 * @param paid_for the ID of the customer who is being paid for in the charges.
	 */
	public Charge(PayingCustomer paid_by, Customer paid_for, Subscription subscription){
		if(paid_by == null){
			throw new RuntimeException("Paid by customer is invalid");
		}

		if(paid_for == null){
			throw new RuntimeException("Paid for customer is invalid");
		}

		if(subscription == null){
			throw new RuntimeException("Subscription is invalid");
		}

		this.paid_by = paid_by;
		this.paid_for = paid_for;
		this.subscription = subscription;
	}

	/**
	 * Get the ID of the customer who paid in the charges.
	 * @return The customer who paid in the charges.
	 */
	public PayingCustomer getPaidBy(){
		return paid_by;
	}

	/**
	 * Get the ID of the customer who is being paid for in the charges.
	 * @return The customer who is being paid for in the charges.
	 */
	public Customer getPaidFor(){
		return paid_for;
	}

	/**
	 * Set the subscription associated with the charges
	 * @param subscription a subscription 
	 */
	public void setSubscription(Subscription subscription){
		this.subscription = subscription;
	}

	/**
	 * Get the subscription associated with the charges.
	 * @return The ID of the subscription associated with the charges.
	 */
	public Subscription getSubscription(){
		return subscription;
	}

	public static void column(){
		IO.println(String.format(column_structure, "Paid By", "Paid For", "Subscription ID"));
	}

	public static void display(Charge charge){
		IO.println(String.format(column_structure, charge.paid_by.getId(), charge.paid_for.getId(), charge.subscription.getId()));

	}

	public static <T> void display(ArrayList <Charge> charges, Class<T> cls, boolean show_column){
		if(show_column){
			column();
		}

		for(Charge charge : charges){
			if(cls == null || cls.isInstance(charge)){
				display(charge);
			}
		}
	}

	@Override
	public String toString(){
		return paid_by.getId() + "," + paid_for.getId() + "," + subscription.getId();
	}
}