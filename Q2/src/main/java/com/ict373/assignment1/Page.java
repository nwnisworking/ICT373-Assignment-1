package com.ict373.assignment1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.stream.Stream;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.HashSet;

import com.ict373.assignment1.customers.*;
import com.ict373.assignment1.magazines.*;
import com.ict373.assignment1.payment.Charge;
import com.ict373.assignment1.payment.methods.*;
// import com.ict373.assignment1.payment.Charge;
import com.ict373.assignment1.utils.IO;

public class Page{
	private static void continuePrompt(){
		IO.println("");
		IO.getString("Press enter to continue...");
		IO.println("");
	}

	public static void home(){
		IO.println("Welcome to the Magazine Subscription System!");
		IO.println("");
		IO.println("1. View Subscriptions");
		IO.println("2. Add Subscription");
		IO.println("3. Remove Subscription");
		IO.println("4. View Customers");
		IO.println("5. Add Customer");
		IO.println("6. Remove Customer");
		IO.println("7. View Charges");
		IO.println("8. Add Charge");
		IO.println("9. Remove Charge");
		IO.println("10. Exit");
		IO.println("");
	}

	public static void viewSubscriptions(TreeMap<Integer,Subscription> subs){
		IO.println("VIEW SUBCRIPTIONS");
		IO.println("");

		int mag_count = 0;
		int sup_count = 0;

		if(subs.isEmpty()){
			IO.println("No subscriptions available.");
			continuePrompt();
			return;
		}

		for(Subscription sub : subs.values()){
			if(sub.isMagazine()){
				mag_count++;
			} 
			else{
				sup_count++;
			}
		}
	
		Subscription.display(subs, Subscription.class, true);
		IO.println("");
		IO.println("Total Subscriptions: " + subs.size());
		IO.println("Total Magazines: " + mag_count);
		IO.println("Total Supplements: " + sup_count);
		
		continuePrompt();
	}

	public static void addSubscription(TreeMap<Integer, Subscription> subs, TreeMap<Integer, Customer> custs){
		IO.println("ADD SUBCRIPTIONS");
		IO.println("");

		boolean is_magazine = IO.getBoolean("Is this a magazine subscription? (1 for Yes, 0 for No): ", null);
		int id = subs.lastKey() + 1;
		Subscription new_sub = null;
		
		if(is_magazine){
			new_sub = new Magazine(
				id,
				IO.getString("Enter magazine name: "), 
				IO.getDouble("Enter magazine cost: ", null)
			);
		}
		else{
			Subscription.display(subs, Magazine.class, true);
			IO.println("");

			int mag_id = IO.getInt("Enter magazine ID for this supplement: ", null);
			Subscription mag = subs.get(mag_id);

			if(mag == null){
				IO.println("Magazine cannot be found.");
				continuePrompt();
				return;
			}
			else if(mag.isSupplement()){
				IO.println("Subscription + " + mag_id + " + is a supplement, not a magazine.");
				continuePrompt();
				return;
			}
			else{
				new_sub = new Supplement(
					id,
					mag_id,
					IO.getString("Enter supplement name: "), 
					IO.getDouble("Enter supplement cost: ", null)
				);
			}
		}

		subs.put(id, new_sub);

		IO.println("");
		IO.println((is_magazine ? "Magazine " : "Supplement ") + new_sub.getName() + " added successfully.");
		
		continuePrompt();
	}

	public static void removeSubscription(TreeMap<Integer, Subscription> subs, TreeMap<Integer, Customer> custs, ArrayList<Charge> charges){
		IO.println("REMOVE SUBCRIPTION");
		IO.println("");

		if(subs.isEmpty()){
			IO.println("No subscriptions available.");
			continuePrompt();
			return;
		}

		Subscription.display(subs, Subscription.class, true);

		IO.println("");

		int deleted_sub_id = IO.getInt("Select subscription ID to remove: ", null);
		Subscription deleted_sub = subs.get(deleted_sub_id);
		IO.println("");

		if(deleted_sub == null){
			IO.println("Unable to delete invalid Subscription.");
			continuePrompt();
			return;
		}

		// Stores magazine (along with its supplements) or just supplement.  
		ArrayList<Integer> deleted_subs = new ArrayList<>();

		// Since subscription is magazine, any supplements related to this magazine should be removed.
		if(deleted_sub.isMagazine()){
			Iterator<Subscription> iterator = subs.values().iterator();

			while(iterator.hasNext()){
				Subscription sub = iterator.next();

				if(sub.isSupplement() && sub.getMagazineId() == deleted_sub_id || sub.isMagazine() && sub.getId() == deleted_sub_id){
					IO.println((sub.isMagazine() ? "Magazine" : "Supplement") + " " + sub.getName() + " removed from subscription");
					deleted_subs.add(sub.getId());
					iterator.remove();
				}
			}
		}
		else{
			IO.println("Supplement " + deleted_sub.getName() + " removed from subscription");

			deleted_subs.add(deleted_sub_id);
			subs.remove(deleted_sub_id);
		}

		// Customer subscription should also be updated to reflect the change. This can be done using charges.
		for(int i = 0; i < charges.size(); i++){
			Charge charge = charges.get(i);

			// The subscription matches the one from deleted subscription list.
			if(deleted_subs.contains(charge.getSubscription().getId())){
				IO.println("An inactive subscription has been removed from " + charge.getPaidFor().getName());
				charge.getPaidFor().removeSubscription(charge.getSubscription());
				charges.remove(charge);
				i--;
			}
		}

		continuePrompt();
	}

	public static void viewCustomers(TreeMap<Integer, Customer> custs){
		IO.println("VIEW CUSTOMERS");
		IO.println("");

		if(custs.isEmpty()){
			IO.println("No customer available.");
			continuePrompt();
			return;
		}

		int assoc_count = 0;
		int pay_count = 0;

		for(Customer cust : custs.values()){
			if(cust instanceof PayingCustomer){
				pay_count++;
			}
			else{
				assoc_count++;
			}
		}

		Customer.display(custs, Customer.class, true);

		IO.println("");
		IO.println("Total Customer: " + (assoc_count + pay_count));
		IO.println("Total Associate Customer: " + assoc_count);
		IO.println("Total Paying Customer: " + pay_count);
		IO.println("");

		// Ask to find a specific customer to view more info (payment method, subscriptions)
		int cust_id = IO.getInt("Search for a customer ID to view subscriptions: ", null);
		IO.println("");
		Customer searched_customer = custs.get(cust_id);

		if(searched_customer == null){
			IO.println("The customer you searched for does not exist");
		}
		else{
			IO.println("Customer ID#" + searched_customer.getId());
			IO.println("Name: " + searched_customer.getName());
			IO.println("Email: " + searched_customer.getEmail());
			IO.println("Is paying customer: " + (searched_customer instanceof PayingCustomer ? "true" : "false"));
			IO.println("");

			// Applicable for paying customer only
			if(searched_customer instanceof PayingCustomer){
				PayingCustomer pc = (PayingCustomer) searched_customer;

				IO.println("Payment Detail");

				if(pc.getPaymentMethod() instanceof CreditCard){
					CreditCard cc = (CreditCard) pc.getPaymentMethod();
					IO.println("Card Type: Credit Card");
					IO.println("Card Number: " + cc.getCardNumber());
					IO.println("Card Expiry: " + cc.getExpiryDate());
				}
				else{
					DirectDebit dd = (DirectDebit) pc.getPaymentMethod();
					IO.println("Card Type: Direct Debit");
					IO.println("Bank Name: " + dd.getBankName());
					IO.println("Account Number: " + dd.getAccountNumber());
				}

				IO.println("");
			}

			Subscription.display(searched_customer.getSubscriptions(), Subscription.class, true);
		}

		continuePrompt();
	}

	public static void addCustomer(TreeMap<Integer, Customer> custs){
		IO.println("ADD CUSTOMER");
		IO.println("");

		boolean is_paying = IO.getBoolean("Is this a paying customer? (1 for Yes, 0 for No): ", null);
		int id = custs.lastKey() + 1;
		Customer new_cust;

		if(!is_paying){
			new_cust = new AssociateCustomer(
				id, 
				IO.getString("Enter customer name: "), 
				IO.getString("Enter customer email: ")
			);
		}
		else{
			new_cust = new PayingCustomer(
				id, 
				IO.getString("Enter customer name: "), 
				IO.getString("Enter customer email: ")
			);

			IO.println("");
			IO.println("PAYMENT METHOD");

			Method method = null;

			do{
				int type = IO.getInt("Select customer's payment method (1 for credit, 2 for debit)", null);

				switch(type){
					case 1 -> method = new CreditCard(
							new_cust.getId(), 
							IO.getString("Enter credit card number: "),
							IO.getString("Enter card expiry date: ")
						);
					case 2 -> method = new DirectDebit(
							new_cust.getId(),
							IO.getString("Enter account number: "),
							IO.getString("Enter bank name: ")
						);
				}
			}
			while(method == null);

			((PayingCustomer) new_cust).setPaymentMethod(method);
		}

		custs.put(id, new_cust);

		IO.println("");
		IO.println((new_cust instanceof PayingCustomer ? "Paying" : "Associate") + " customer " + new_cust.getName() + " created");
		continuePrompt();
	}

	public static void removeCustomer(TreeMap<Integer, Customer> custs, ArrayList<Charge> charges){
		IO.println("REMOVE CUSTOMER");
		IO.println("");

		if(custs.isEmpty()){
			IO.println("No customers available.");
			continuePrompt();
			return;
		}

		Customer.display(custs, Customer.class, true);

		IO.println("");
		int deleted_cust_id = IO.getInt("Select customer ID to remove: ", null);
		Customer deleted_cust = custs.get(deleted_cust_id);
		IO.println("");

		if(deleted_cust == null){
			IO.println("Customer with ID " + deleted_cust_id + " cannot be found.");
			continuePrompt();
			return;
		}

		for(int i = 0; i < charges.size(); i++){
			Charge charge = charges.get(i);
			Customer paid_by = charge.getPaidBy();
			Customer paid_for = charge.getPaidFor();
			Subscription sub = charge.getSubscription();

			if(paid_by.equals(deleted_cust) || paid_for.equals(deleted_cust)){
				// Delete subscription from paid for.
				if(paid_by.equals(deleted_cust)){
					paid_for.removeSubscription(sub);
					IO.println("The subscription " + sub.getName() +  " bought by " + paid_by.getName() + " for " + paid_for.getName() + " is cancelled");
				}


				charges.remove(charge);
				i--;
			}
		}

		// Remove supplements that does not have magazine any more.
		for(int i = 0; i < charges.size(); i++){
			Charge charge = charges.get(i);
			Customer paid_by = charge.getPaidBy();
			Customer paid_for = charge.getPaidFor();
			Subscription sub = charge.getSubscription();

			if(sub.isSupplement() && paid_for.hasMagazine(sub.getMagazineId()) || sub.isMagazine()){
				continue;
			}

			IO.println("The subscription bought by " + paid_by.getName() + " for " + paid_for.getName() + " is cancelled as the supplement " + sub.getName() + " is missing magazine subscription");

			paid_for.removeSubscription(sub);
			charges.remove(charge);
			i--;
		}
		
		// for(int i = 0; i < charges.size(); i++){
		// 	Charge charge = charges.get(i);

		// 	// It did not delete supplement that the magazine is already deleted for
		// 	if(charge.getPaidBy().equals(deleted_cust) || charge.getPaidFor().equals(deleted_cust)){
		// 		charge.getPaidFor().removeSubscription(charge.getSubscription());

		// 		charges.remove(charge);
		// 		i--;
		// 	}
		// }

		// // Evaluate whether supplement is missing magazine. If so, delete the supplement too.
		// for(int i = 0; i < charges.size(); i++){
		// 	Charge charge = charges.get(i);
		// 	Customer paid_for = charge.getPaidFor();
		// 	Customer paid_by = charge.getPaidBy();
		// 	boolean remove_charge = false;

		// 	for(Subscription sub : paid_for.getSubscriptions()){
		// 		if(sub.isMagazine() || paid_for.hasMagazine(sub.getMagazineId())) continue;

		// 		paid_for.removeSubscription(paid_by.getId(), sub.getId());
		// 		IO.println(paid_by.getName() + " unable to pay for " + paid_for.getName() + " subscription " + sub.getName());
		// 		remove_charge = true;
		// 		break;
		// 	}

		// 	if(remove_charge){
		// 		charges.remove(charge);
		// 		i--;
		// 	}
		// }

		IO.println("Customer " + deleted_cust.getName() + " deleted successfully.");
		custs.remove(deleted_cust_id);

		continuePrompt();	
	}

	public static void viewCharges(ArrayList<Charge> charges){
		IO.println("VIEW CHARGES");
		IO.println("");

		if(charges.isEmpty()){
			IO.println("No charges available.");
		}
		else{
			Charge.display(charges, Charge.class, true);
		}

		continuePrompt();
	}

	/**
	 * @param charges
	 * @param custs
	 * @param subs
	 * @throws CloneNotSupportedException
	 */
	public static void addCharge(ArrayList<Charge> charges, TreeMap<Integer, Customer> custs, TreeMap<Integer, Subscription> subs) throws CloneNotSupportedException{
		IO.println("ADD CHARGE");
		IO.println("");
		IO.println("Paying Customer");
		IO.println("");

		Customer paid_for;
		Customer paid_by;
		Subscription sub;
		Charge charge;

		Customer.display(custs, PayingCustomer.class, true);
		
		IO.println("");
		paid_by = custs.get(IO.getInt("Select paying customer: ", null));
		IO.println("");

		if(paid_by == null){
			IO.println("Paying customer does not exist.");
			continuePrompt();
			return;			
		}
		else if(paid_by instanceof AssociateCustomer){
			IO.println("The selected customer is not a paying customer.");
			continuePrompt();
			return;
		}

		IO.println("");
		IO.println("All Customers");
		IO.println("");

		Customer.display(custs, Customer.class, true);

		IO.println("");
		
		paid_for = custs.get(IO.getInt("Select customer: ", null));

		IO.println("");

		if(paid_for == null){
			IO.println("Customer does not exist. Charge cancelled.");
			continuePrompt();
			return;
		}

		IO.println("All Subscriptions");
		IO.println("");

		Subscription.display(subs, Subscription.class, true);

		IO.println("");
		sub = subs.get(IO.getInt("Select subscription: ", null));
		IO.println("");
		
		if(sub == null){
			IO.println("Subscription does not exist.");
			continuePrompt();
			return;
		}
		else if(sub.isSupplement() && !paid_for.hasMagazine(sub.getMagazineId())){
			IO.println("Customer did not owned the require magazine to purchase this supplement.");
			continuePrompt();
			return;
		}

		sub = sub.clone();
		charge = ((PayingCustomer) paid_by).paySubscription(paid_for, sub);

		if(paid_by.equals(paid_for)){
			IO.println("Paying customer " + paid_by.getName() + " made a purchase for " + sub.getName());
		}
		else{
			IO.println("Paying customer " + paid_by.getName() + " purchased a subscription " + sub.getName() + " for " + paid_for.getName());
		}

		charges.add(charge);
		continuePrompt();
	}

	public static void removeCharge(ArrayList<Charge> charges, TreeMap<Integer, Customer> custs, TreeMap<Integer, Subscription> subs){
		Customer paid_for;
		Charge charge = null;

		Customer.column();

		// Display all customer that have subscriptions
		for(Customer cust : custs.values()){
			if(cust.getSubscriptions().size() > 0)
				Customer.display(cust);
		}

		IO.println("");

		paid_for = custs.get(IO.getInt("Enter customer ID from the list: ", null));

		IO.println("");

		if(paid_for == null){
			IO.println("Customer does not exist");
			continuePrompt();
			return;
		}
		else if(paid_for.getSubscriptions().size() == 0){
			IO.println("Customer does not have an active subscription.");
			continuePrompt();
			return;
		}

		Subscription.display(paid_for.getSubscriptions(), Subscription.class, true);

		IO.println("");

		int sub_id = IO.getInt("Enter subscription ID: ", null);
		int paid_by_id = IO.getInt("Enter paying customer ID: ", null);
		boolean has_match = false;

		for(int i = 0; i < charges.size(); i++){
			charge = charges.get(i);

			if(charge.getPaidBy().getId() == paid_by_id && charge.getPaidFor().getId() == paid_for.getId() && charge.getSubscription().getId() == sub_id){
				has_match = true;
				paid_for.removeSubscription(charge.getSubscription());
				charges.remove(charge);
				break;
			}
		}

		if(has_match){
			if(charge.getPaidBy().equals(charge.getPaidFor())){
				IO.println(paid_for.getName() + " cancelled subscription for " + subs.get(sub_id).getName());
			}
			else{
				IO.println(charge.getPaidBy().getName() + " has removed the subscription for " + charge.getSubscription().getName() + " from " + charge.getPaidFor().getName());
			}

			// If there is a duplicated magazine, this will be ignored. This also means the supplements will not be deleted.
			// Otherwise, supplement should be deleted.
			if(charge.getSubscription().isMagazine() && !paid_for.hasMagazine(charge.getSubscription().getId())){
				for(int i = 0; i < charges.size(); i++){
					Charge supp_charge = charges.get(i);
					Subscription supp = supp_charge.getSubscription();

					if(
						supp_charge.getPaidFor().equals(paid_for) && // supplement charge must match paid_for object 
						supp.isSupplement() && // supplement charge must be supllement
						supp.getMagazineId() == charge.getSubscription().getId() // Supplement charge must match the magazine that is deleted
					){

						IO.println("Supplement " + supp.getName() + " has been automatically unsubscribed from " + charge.getPaidFor().getName());
						paid_for.removeSubscription(supp);
						charges.remove(supp_charge);
						i--;
					}
				}
			}
		}
		else{
			IO.println("No charges were removed");
		}

		continuePrompt();
	}
}