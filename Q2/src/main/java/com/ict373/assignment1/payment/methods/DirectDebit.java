package com.ict373.assignment1.payment.methods;

import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.payment.Transaction;

public class DirectDebit implements Method{
	/**
	 * The customer ID associated with this direct debit.
	 */
	private int customer_id;

	/**
	 * The bank account number for the direct debit.
	 */
	private String account_number;

	/**
	 * The name of the bank associated with this direct debit.
	 */
	private String bank_name;

	/**
	 * Default constructor for creating a DirectDebit instance.
	 */
	public DirectDebit() {
		customer_id = 0;
		account_number = "";
		bank_name = "";
	}

	/**
	 * Constructor to create a DirectDebit instance.
	 * @param customer_id the ID of the customer who owns this direct debit.
	 * @param account_number the bank account number for the direct debit.
	 * @param bank_name the name of the bank associated with this direct debit.
	 */
	public DirectDebit(int customer_id, String account_number, String bank_name){
		this.customer_id = customer_id;
		this.account_number = account_number;
		this.bank_name = bank_name;
	}

	/**
	 * Get the bank account number for this direct debit.
	 * @return the bank account number.
	 */
	public String getAccountNumber() {
		return account_number;
	}

	/**
	 * Get the customer ID associated with this direct debit.
	 * @return the customer ID.
	 */
	public int getCustomerId() {
		return customer_id;
	}

	public String getBankName() {
		return bank_name;
	}

	@Override
	public void parse(CSVParser parser) {
		customer_id = parser.getInteger();
		account_number = parser.getString();
		bank_name = parser.getString();
	}

	@Override
	public String toString(){
		return Transaction.DEBIT_CARD + ',' + customer_id + ',' + account_number + ',' + bank_name;
	}
}