package com.ict373.assignment1.payment.methods;

import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.payment.Charge;

public class CreditCard implements Method{
	/**
	 * The customer ID associated with this credit card. 
	 */
	private int customer_id;
	
	/**
	 * The credit card number.
	 */
	private String card_number;
	
	/**
	 * The expiry date of the credit card in the format "MM/YY".
	 */
	private String expiry_date;

	/**
	 * Default constructor for creating a CreditCard instance.
	 */
	public CreditCard(){
		customer_id = 0;
		card_number = "";
		expiry_date = "";
	}

	/**
	 * Constructor to create a CreditCard instance.
	 * @param customer_id the ID of the customer who owns this credit card.
	 * @param card_number the credit card number.
	 * @param expiry_date the expiry date of the credit card in the format "MM/YY".
	 */
	public CreditCard(int customer_id, String card_number, String expiry_date){
		this.customer_id = customer_id;
		this.card_number = card_number;
		this.expiry_date = expiry_date;
	}

	/**
	 * Get the credit card number.
	 * @return the credit card number.
	 */
	public String getCardNumber() {
		return card_number;
	}

	/**
	 * Get the customer ID associated with this credit card.
	 * @return the customer ID.
	 */
	public int getCustomerId(){
		return customer_id;
	}

	/**
	 * Get the expiry date of the credit card.
	 * @return the expiry date in the format "MM/YY".
	 */
	public String getExpiryDate(){
			return expiry_date;
	}

	/**
	 * Set the credit card number for this credit card.
	 * @param card_number the credit card number to set.
	 */
	public void setCardNumber(String card_number){
		this.card_number = card_number;
	}

	/**
	 * Set the expiry date of the credit card.
	 * @param expiry_date the expiry date to set in the format "MM/YY".
	 */
	public void setExpiryDate(String expiry_date){
		this.expiry_date = expiry_date;
	}

	@Override
	public void parse(CSVParser parser){
		customer_id = parser.getInteger();
		card_number = parser.getString();
		expiry_date = parser.getString();
	}

	@Override
	public String toString() {
		return Charge.CREDIT_CARD + "," + customer_id + "," + card_number + "," + expiry_date;
	}
}