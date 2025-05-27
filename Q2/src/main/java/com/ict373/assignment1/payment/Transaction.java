package com.ict373.assignment1.payment;

import com.ict373.assignment1.payment.methods.*;
import com.ict373.assignment1.utils.CSVParsable;
import com.ict373.assignment1.utils.CSVParser;

public class Transaction implements CSVParsable{
	/**
	 * Constants to reflect credit card.
	 */
	public static final int CREDIT_CARD = 1;

	/**
	 * Constants to reflect debit card.
	 */
	public static final int DEBIT_CARD = 2;

	/**
	 * The ID of the customer who paid in the transaction.
	 */
	private int paid_by;

	/**
	 * The ID of the customer who is being paid for in the transaction.
	 */
	private int paid_for;

	/**
	 * The ID of the subscription associated with the transaction.
	 */
	private int subscription_id;

	/**
	 * Default constructor for creating a transaction.
	 * Initializes the transaction with default values.
	 */
	public Transaction(){
		this.paid_by = 0;
		this.paid_for = 0;
		this.subscription_id = 0;
	}

	/**
	 * Default constructor for creating a transaction.
 	 * @param paid_by the ID of the customer who paid in the transaction.
	 * @param paid_for the ID of the customer who is being paid for in the transaction.
	 */
	public Transaction(int paid_by, int paid_for, int subscription_id){
		this.paid_by = paid_by;
		this.paid_for = paid_for;
		this.subscription_id = subscription_id;
	}

	/**
	 * Constructor for creating a transaction with a specific payment method.
	 * @param paid_by The ID of the customer who paid in the transaction.
	 * @param paid_for The ID of the customer who is being paid for in the transaction.
	 * @param method The payment method used for the transaction.
	 */
	public Transaction(int paid_by, int paid_for, int subscription_id, Method method){
		this.paid_by = paid_by;
		this.paid_for = paid_for;
		this.subscription_id = subscription_id;
	}

	/**
	 * Get the ID of the customer who paid in the transaction.
	 * @return The ID of the customer who paid in the transaction.
	 */
	public int getPaidBy(){
		return paid_by;
	}

	/**
	 * Get the ID of the customer who is being paid for in the transaction.
	 * @return The ID of the customer who is being paid for in the transaction.
	 */
	public int getPaidFor(){
		return paid_for;
	}

	/**
	 * Get the ID of the subscription associated with the transaction.
	 * @return The ID of the subscription associated with the transaction.
	 */
	public int getSubscriptionId(){
		return subscription_id;
	}

	public static Method getPaymentMethod(int type){
		switch(type){
			case CREDIT_CARD : return new CreditCard();
			case DEBIT_CARD : return new DirectDebit();
			default : throw new IllegalArgumentException("Invalid payment method type: " + type);
		}
	}

	@Override
	public void parse(CSVParser parser) {
		paid_by = parser.getInteger();
		paid_for = parser.getInteger();
		subscription_id = parser.getInteger();
	}

	@Override
	public String toString(){
		return paid_by + "," + paid_for + "," + subscription_id;
	}
}