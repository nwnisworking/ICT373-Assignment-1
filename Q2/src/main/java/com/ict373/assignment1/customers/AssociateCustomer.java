package com.ict373.assignment1.customers;

public class AssociateCustomer extends Customer{
	public AssociateCustomer() {
		super();
	}

	public AssociateCustomer(int id, String name, String email) {
		super(id, name, email);
	}

	@Override
	public String toString() {
		return 0 + ',' + super.toString();
	}
}
