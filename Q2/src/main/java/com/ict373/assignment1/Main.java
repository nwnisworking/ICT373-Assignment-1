package com.ict373.assignment1;

import java.io.IOException;
import java.util.Optional;
import java.util.TreeMap;

import com.ict373.assignment1.magazines.*;
import com.ict373.assignment1.payment.*;
import com.ict373.assignment1.customers.*;

import com.ict373.assignment1.utils.ANSI;
import com.ict373.assignment1.utils.IO;

public class Main{
	/**
	 * A collection of subscriptions mapped in their ID.
	 */
	private static TreeMap<Integer, Subscription> subscriptions = new TreeMap<>();
	
	/**
	 * A collection of customer mapped in their ID.
	 */
	private static TreeMap<Integer, Customer> customers = new TreeMap<>();

	public static void main(String[] args){
		try{
			init(); 

			int options = -1;

			while(options != 0){
				ANSI.clear();
				ANSI.homePosition();
				Page.home();

				options = IO.getInt("Select one of the following options: ", Optional.of("The option is in invalid format"));

				IO.println("");

				switch(options){
					case 1 -> Page.weeklyEmail(customers);
					case 2 -> Page.invoiceCustomer(customers);
					case 3 -> Page.viewCustomers(customers);
					case 4 -> Page.addCustomer(customers);
					case 5 -> Page.deleteCustomer(customers);
					case 6 -> Page.setPayer(customers);
					case 7 -> Page.removePayer(customers);
					case 8 -> Page.viewMagazineSupplement(subscriptions);
					case 9 -> Page.addSubscription(subscriptions, customers);
					case 10 -> Page.removeSubscription(subscriptions, customers);
					case 11 -> Page.createMagazineSupplement(subscriptions);
					case 12 -> Page.deleteMagazineSupplement(subscriptions, customers);
					case 13 -> options = 0;
					default -> {
						options = -1;
						IO.println("Selection unavailable.");
					}
				}
			}
		}
		catch(IOException | RuntimeException e){
			IO.println(e.getMessage());
		}

		IO.println("Thank you for using Magazine subscription system!");
		IO.close();
	}

	public static void init() throws IOException{
		subscriptions.put(1, new Magazine(1, "MTV", 5.90));
		subscriptions.put(2, new Supplement(2, "MTV Chinese", 3.5));
		subscriptions.put(3, new Supplement(3, "MTV English", 3.5));
		subscriptions.put(4, new Magazine(4, "Daily News", 2.50));
		subscriptions.put(5, new Supplement(5, "Daily Sports", 2.80));
		subscriptions.put(6, new Supplement(6, "Daily Finance", 3));
		subscriptions.put(7, new Magazine(7, "Solo Magazine", 15.20));
		
		subscriptions.get(2).setMagazine((Magazine) subscriptions.get(1));
		subscriptions.get(3).setMagazine((Magazine) subscriptions.get(1));
		
		subscriptions.get(5).setMagazine((Magazine) subscriptions.get(4));
		subscriptions.get(6).setMagazine((Magazine) subscriptions.get(4));

		// Paying customer with empty data
		customers.put(1, new PayingCustomer(1, "New Wei Nern", "nwnisworking@gmail.com"));

		// Paying customer with 2 associates and multiple subscriptions!
		customers.put(2, new PayingCustomer(2, "John Leroy", "j_leroy@gmail.com"));
		customers.put(3, new AssociateCustomer(3, "David Smith", "david_smith2342@yahoo.com"));
		customers.put(7, new AssociateCustomer(7, "Alice Smith", "alicesmith@yahoo.com")); // This user has similar subscription!

		// Associate with subscriptions but no paying customer
		// This can happen when the paying customer decides to remove itself
		customers.put(4, new AssociateCustomer(4, "Adam West", "ada_wew@ty.com"));
		
		// Associate with a paying customer but no subscription
		customers.put(5, new AssociateCustomer(5, "Dave Luka", "dave.luka@gmail.com"));
		customers.put(6, new PayingCustomer(6, "Ashton", "ash.ton@gmail.com"));

		((PayingCustomer) customers.get(1)).setPaymentMethod(new CreditCard("1234-1234-4321-4321", "05/29"));
		((PayingCustomer) customers.get(6)).setPaymentMethod(new CreditCard("1234-6543-1235-8731", "02/27"));
		((PayingCustomer) customers.get(2)).setPaymentMethod(new DirectDebit("1232-245-24123", "DBS"));
		
		((PayingCustomer) customers.get(2)).addAssociate((AssociateCustomer) customers.get(3));
		((PayingCustomer) customers.get(2)).addAssociate((AssociateCustomer) customers.get(7));
		((PayingCustomer) customers.get(6)).addAssociate((AssociateCustomer) customers.get(5));
		
		customers.get(2).addSubscriptions(subscriptions.get(1));
		customers.get(2).addSubscriptions(subscriptions.get(2));
		customers.get(2).addSubscriptions(subscriptions.get(3));

		customers.get(3).addSubscriptions(subscriptions.get(4));
		customers.get(3).addSubscriptions(subscriptions.get(5));
		
		customers.get(7).addSubscriptions(subscriptions.get(1));
		customers.get(7).addSubscriptions(subscriptions.get(1));
		customers.get(7).addSubscriptions(subscriptions.get(2));
		customers.get(7).addSubscriptions(subscriptions.get(3));
	}
}