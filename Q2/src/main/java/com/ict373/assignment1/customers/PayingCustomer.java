package com.ict373.assignment1.customers;

import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.payment.Transaction;

public class PayingCustomer extends Customer{
	public PayingCustomer() {
		super();
	}

	public PayingCustomer(int id, String name, String email) {
		super(id, name, email);
	}

	/**
	 * Pay subscription for an associate.
	 * @param customer an associate customer
	 * @param subscription the subscription to pay for the associate
	 * @return a new Transaction object representing the payment
	 * @throws IllegalArgumentException if the associate does not have the magazine for the subscription
	 */
	public Transaction paySubscription(AssociateCustomer customer, Subscription subscription){
		if(subscription.isSupplement() && !customer.hasMagazine(subscription.getMagazineId())){
			throw new IllegalArgumentException("Associate does not have the magazine for this subscription.");
		}
		else{
			subscription.setPaidBy(id);
			subscription.setPaidFor(customer.getId());
			customer.setSubscriptions(subscription);
		}

		return new Transaction(id, customer.getId(), subscription.getId());
	}

	/**
	 * Pay subscription for self.
	 * @param subscription the subscription to pay for self
	 * @return a new Transaction object representing the payment
	 * @throws IllegalArgumentException if the customer does not have the magazine for the subscription
	 */
	public Transaction paySubscription(Subscription subscription){
		if(subscription.isSupplement() && !hasMagazine(subscription.getMagazineId())){
			throw new IllegalArgumentException("Customer does not have the magazine for this subscription.");
		}
		else{
			subscription.setPaidBy(id);
			subscription.setPaidFor(id);
			setSubscriptions(subscription);
		}

		return new Transaction(id, id, subscription.getId());
	}

	@Override
	public String toString() {
		return 1 + ',' + super.toString();
	}
}
