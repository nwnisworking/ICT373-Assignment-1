package com.ict373.assignment1;

// import java.util.ArrayList;

// import com.ict373.assignment1.customers.*;
// import com.ict373.assignment1.magazines.*;
// import com.ict373.assignment1.payment.Charge;
// import com.ict373.assignment1.payment.methods.*;

public class Data{
	// public static ArrayList<Subscription> loadSubscription(){
	// 	ArrayList<Subscription> subscriptions = new ArrayList<>();

	// 	subscriptions.add(new Magazine(1, "Daily News", 10.0));
	// 	subscriptions.add(new Magazine(2, "Health & Fitness", 12.50));
	// 	subscriptions.add(new Magazine(3, "Travel Weekly", 15.50));
	// 	subscriptions.add(new Magazine(4, "Tech Today", 12.20));
		
	// 	subscriptions.add(new Supplement(5, subscriptions.get(0).getId(), "Daily Sports", 2.5));
	// 	subscriptions.add(new Supplement(6,subscriptions.get(0).getId(), "Daily Finance", 2.2));
	// 	subscriptions.add(new Supplement(7, subscriptions.get(0).getId(), "Daily Business", 2.2));

	// 	subscriptions.add(new Supplement(8, subscriptions.get(1).getId(), "Health Tips", 1.5));
	// 	subscriptions.add(new Supplement(9, subscriptions.get(1).getId(), "Exercise Goals", 2.5));
	// 	subscriptions.add(new Supplement(10, subscriptions.get(1).getId(), "Sports Galore", 3.0));

	// 	subscriptions.add(new Supplement(11, subscriptions.get(3).getId(), "CyberSec Issue", 4.80));
	// 	subscriptions.add(new Supplement(12, subscriptions.get(3).getId(), "Fintech", 2.25));

	// 	return subscriptions;
	// }

	// public static ArrayList<Customer> loadCustomers(){
	// 	ArrayList<Customer> customers = new ArrayList<>();

	// 	customers.add(new PayingCustomer(1, "Alice Smith", "alicesmith245@hotmail.com",
	// 		new CreditCard(1, "1234-5678-9012-3456", "12/25")
	// 	));
	// 	customers.add(new AssociateCustomer(2, "Bob Johnson", "bob.johnson@yahoo.com"));
	// 	customers.add(new PayingCustomer(3, "Charlie Chaplin", "charles_the_comedian@gmail.com", 
	// 		new CreditCard(2, "9876-5432-1098-7654", "11/24")
	// 	));
	// 	customers.add(new AssociateCustomer(4, "Diana Prince", "d_prince@yahoo.com"));
	// 	customers.add(new AssociateCustomer(5, "Ethan Hunt", "hunter24255@gmail.com"));
	// 	customers.add(new AssociateCustomer(6, "Fiona Gallagher", "FG@gmail.com"));
		
	// 	return customers;
	// }

	// public static ArrayList<Charge> loadCharges(){
	// 	ArrayList<Charge> charges = new ArrayList<>();

	// 	// Example charges for customer paying for a subscription for himself/herself
	// 	charges.add(new Charge(1,1,2));

	// 	// Example charges for customer paying for a subscription for another customer (includes magazine and supplement)
	// 	charges.add(new Charge(3, 4, 4));
	// 	charges.add(new Charge(3, 4, 12));

	// 	// Example charges for customer paying for a subscription to another customer (includes magazine)
	// 	charges.add(new Charge(3, 5, 1)); 
		
	// 	return charges;
	// }
}