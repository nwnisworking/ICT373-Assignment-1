package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.payment.Charge;
import com.ict373.assignment1.payment.methods.Method;
import com.ict373.assignment1.utils.CSVParser;

/**
 * Represents a paying customer in the system.
 * A paying customer is a type of customer that can pay subscriptions for themselves or for associate customers.
 * They can also have a payment method associated with them, such as a credit card or debit card.
 */
public class PayingCustomer extends Customer{
  /**
   * Customer's default payment method. It can be a credit card, debit card, etc.
   */
  protected Method payment_method;

	/**
	 * Default constructor for PayingCustomer class.
	 * Initializes the customer with default values.
	 */
	public PayingCustomer() {
		super();
	}

	/**
	 * Constructor for PayingCustomer class.
	 * @param id Unique identifier for the customer
	 * @param name Name of the customer
	 * @param email Email address of the customer
	 */
	public PayingCustomer(int id, String name, String email) {
		super(id, name, email);
	}

	/**
	 * Constructor for PayingCustomer class with payment method.
	 * @param id Unique identifier for the customer
	 * @param name Name of the customer
	 * @param email Email address of the customer
	 * @param payment_method The payment method used by the customer
	 */
	public PayingCustomer(int id, String name, String email, Method payment_method){
		super(id, name, email);
		this.payment_method = payment_method;
	}

	/**
	 * Pay subscription for an associate.
	 * @param customer an associate customer
	 * @param subscription the subscription to pay for the associate
	 * @return a new Charge object representing the payment
	 * @throws IllegalArgumentException if the associate does not have the magazine for the subscription
	 */
	public Charge paySubscription(Customer customer, Subscription subscription){
		if(subscription.isSupplement() && !customer.hasMagazine(subscription.getMagazineId())){
			throw new IllegalArgumentException("Associate does not have the magazine for this subscription.");
		}
		else{
			subscription.setPaidBy(id);
			subscription.setPaidFor(customer.getId());
			customer.setSubscriptions(subscription);
		}

		return new Charge(this, customer, subscription);
	}

	/**
   * Set the payment method used by the customer
   * @param payment_method payment method to be set for the customer
   */
  public void setPaymentMethod(Method payment_method){
    this.payment_method = payment_method;
  }

	/**
   * Get the payment method used by the customer
   * @return payment method used by the customer
   */
  public Method getPaymentMethod(){
    return payment_method;
  }

	@Override
	public void parse(CSVParser parser){
		super.parse(parser);
		payment_method = Method.getPaymentMethod(parser.getInteger());
		payment_method.parse(parser);
	}

	@Override
	public String toString() {
		return 1 + "," + super.toString() + "," + payment_method;
	}
}
