package com.ict373.assignment1.payment.methods;

import com.ict373.assignment1.utils.CSVParsable;

public interface Method extends CSVParsable{
	public static Method getPaymentMethod(int type){
		switch(type){
			case 1 : return new CreditCard();
			case 2 : return new DirectDebit();
			default : throw new IllegalArgumentException("Payment method is invalid");
		}
	}
}