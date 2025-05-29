package com.ict373.assignment1;

import java.util.ArrayList;

import com.ict373.assignment1.customers.*;
import com.ict373.assignment1.magazines.*;
import com.ict373.assignment1.payment.methods.*;
import com.ict373.assignment1.payment.Charge;
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
		IO.println("9. Exit");
		IO.println("");
	}

	public static void viewSubscriptions(ArrayList<Subscription> subs){
		IO.println("VIEW SUBCRIPTIONS");
		IO.println("");

		int mag_count = 0;
		int sup_count = 0;

		if(subs.isEmpty()){
			IO.println("No subscriptions available.");
		}
		else{
			Subscription.column();

			for(Subscription sub : subs){
				if(sub.isMagazine()){
					mag_count++;
				} 
				else{
					sup_count++;
				}
	
				sub.display();
			}

			IO.println("");
			IO.println("Total Subscriptions: " + subs.size());
			IO.println("Total Magazines: " + mag_count);
			IO.println("Total Supplements: " + sup_count);
		}
		
		continuePrompt();
	}

	public static void addSubscription(ArrayList<Subscription> subs, ArrayList<Customer> custs){
		IO.println("ADD SUBCRIPTIONS");
		IO.println("");

		boolean is_magazine = IO.getBoolean("Is this a magazine subscription? (1 for Yes, 0 for No): ", null);
		// Always get the last ID and increment by 1, otherwise, using subs.size() might lead to duplicate ID
		int id = subs.size() == 0 ? 1 : subs.getLast().getId() + 1; 
		Subscription new_sub = null;

		if(is_magazine){
			new_sub = new Magazine(
				id,
				IO.getString("Enter magazine name: "), 
				IO.getDouble("Enter magazine cost: ", null)
			);
		}
		else{
			int mag_id = IO.getInt("Enter magazine ID for this supplement: ", null);

			// Magazine can only start with > 0.
			if(mag_id <= 0){
				IO.println("");
				IO.println("Invalid magazine ID. Supplement cannot be added.");
				continuePrompt();
				return;
			}

			Subscription search_mag = Subscription.getSubscriptionById(subs, mag_id);

			if(search_mag == null){
				IO.println("");
				IO.println("No magazine with ID " + mag_id + " found. Supplement cannot be added.");
			}
			else if(search_mag.isSupplement()){
				IO.println("");
				IO.println("ID is a supplement, not a magazine. Supplement cannot be added.");
			}
			else{
				new_sub = new Supplement(
					subs.size() == 0 ? 1 : subs.getLast().getId() + 1, 
					mag_id, 
					IO.getString("Enter supplement name: "), 
					IO.getDouble("Enter supplement cost: ", null)
				);
			}
		}

		subs.add(new_sub);

		IO.println("");
		IO.println((new_sub.isMagazine() ? "Magazine " : "Supplement ") + new_sub.getName() + " added successfully.");
		continuePrompt();
	}

	public static void removeSubscription(ArrayList<Subscription> subs, ArrayList<Customer> custs, ArrayList<Charge> charges){
		IO.println("REMOVE SUBCRIPTION");
		IO.println("");

		if(subs.isEmpty()){
			IO.println("No subscriptions available.");
		}
		else{
			Subscription.column();
			
			for(Subscription sub : subs){
				sub.display();
			}

			IO.println("");
			int selected_id = IO.getInt("Select subscription ID to remove: ", null);
			IO.println("");
			boolean valid_id = false; // Checks whether anything is deleted.

			ArrayList<Integer> deleted_subs = new ArrayList<>();

			// It is important to remove all the supplement from a magazine.
			// This means looping through subs to find all the affected supplements. 
			// If the selected ID is a subscription, we can immediately break out of the loop.
			for(int i = 0; i < subs.size(); i++){
				Subscription sub = subs.get(i);

				if(sub.isMagazine() && sub.getId() == selected_id){
					IO.println("Magazine " + sub.getName() + " deleted");
					valid_id = true;
					deleted_subs.add(sub.getId());
					subs.remove(sub);
					i--;
				}
				else if(sub.isSupplement() && sub.getMagazineId() == selected_id){
					IO.println("Supplement " + sub.getName() + " is deleted");
					deleted_subs.add(sub.getId());
					subs.remove(sub);

					i--;
				}
				else if(sub.isSupplement() && sub.getId() == selected_id){
					valid_id = true;
					IO.println("Supplement " + sub.getName() + " is deleted");
					subs.remove(sub);
					deleted_subs.add(sub.getId());

					break;
				}
			}

			// We also need to remove subscription from customer and charge list to avoid paying
			// for non-existent subscription and ensure uniformity 
			for(int i = 0; i < charges.size(); i++){
				Charge charge = charges.get(i);

				if(deleted_subs.contains(charge.getSubscriptionId())){
					Customer cust = Customer.getCustomerById(custs, charge.getPaidFor());
					cust.removeSubscription(charge.getPaidBy(), charge.getSubscriptionId());
					charges.remove(charge);
					i--;
				}
			}

			if(!valid_id){
				IO.println("Subscription ID " + selected_id + " does not exist");
			}
		}
		
		continuePrompt();	
	}

	public static void viewCustomers(ArrayList<Customer> custs){
		IO.println("VIEW CUSTOMERS");
		IO.println("");

		if(custs.isEmpty()){
			IO.println("No customer available.");
		}
		else{
			int assoc_count = 0;
			int pay_count = 0;

			Customer.column();

			for(Customer cust : custs){
				if(cust instanceof PayingCustomer){
					pay_count++;
				}
				else{
					assoc_count++;
				}

				cust.display();
			}

			IO.println("");
			IO.println("Total Customer: " + (assoc_count + pay_count));
			IO.println("Total Associate Customer: " + assoc_count);
			IO.println("Total Paying Customer: " + pay_count);
		}

		int id = IO.getInt("Search for a customer ID to view subscriptions: ", null);
		Customer searched_customer = Customer.getCustomerById(custs, id);

		if(searched_customer == null){
			IO.println("The customer you searched for does not exist");
		}
		else{
			IO.println("");
			IO.println("Customer ID#" + searched_customer.getId());
			IO.println("Name: " + searched_customer.getName());
			IO.println("Email: " + searched_customer.getEmail());
			IO.println("Is paying customer: " + (searched_customer instanceof PayingCustomer ? "true" : "false"));
			IO.println("");

			Subscription.column();
			
			for(Subscription sub : searched_customer.getSubscriptions()){
				sub.display();	
			}
		}
	
		continuePrompt();
	}

	public static void addCustomer(ArrayList<Customer> custs){
		IO.println("ADD CUSTOMER");
		IO.println("");

		boolean is_paying = IO.getBoolean("Is this a paying customer? (1 for Yes, 0 for No): ", null);
		int id = custs.size() == 0 ? 1 : custs.getLast().getId() + 1;
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

		custs.add(new_cust);

		IO.println("");
		IO.println((new_cust instanceof PayingCustomer ? "Paying" : "Associate") + " customer " + new_cust.getName() + " created");
		continuePrompt();
	}

	public static void removeCustomer(ArrayList<Customer> custs, ArrayList<Charge> charges){
		IO.println("REMOVE CUSTOMER");
		IO.println("");

		if(custs.isEmpty()){
			IO.println("No customers available.");
		}
		else{
			Customer.column();
			
			for(Customer cust : custs){
				cust.display();
			}

			IO.println("");
			int selected_id = IO.getInt("Select customer ID to remove: ", null);
			IO.println("");

			Customer cust_to_delete = Customer.getCustomerById(custs, selected_id);

			if(cust_to_delete == null){
				IO.println("Customer with ID " + selected_id + " cannot be found.");
			}
			else{
				// Charges must be cleared along with Associate customer
				// subscription data.
				for(int i = 0; i < charges.size(); i++){
					Charge charge = charges.get(i);

					if(charge.getPaidBy() == cust_to_delete.getId()){
						Customer assoc = Customer.getCustomerById(custs, charge.getPaidFor());
						assoc.removeSubscription(charge.getPaidBy(), charge.getSubscriptionId());
						charges.remove(charge);
						i--;
					}
				}

				IO.println("Customer " + cust_to_delete.getName() + " deleted successfully.");
				custs.remove(cust_to_delete);
			}
		}
		
		continuePrompt();	
	}

	public static void viewCharges(ArrayList<Charge> charges){
		IO.println("VIEW CHARGES");
		IO.println("");

		Charge.column();

		for(Charge charge : charges){
			charge.display();
		}

		continuePrompt();
	}

	public static void addCharge(ArrayList<Charge> charges, ArrayList<Customer> custs, ArrayList<Subscription> subs) throws CloneNotSupportedException{
		IO.println("ADD CHARGE");
		IO.println("");
		IO.println("Paying Customer");
		IO.println("");

		Customer paid_for;
		Customer paid_by;
		Subscription subscription;
		Charge charge;

		Customer.column();
		for(Customer cust : custs){
			if(cust instanceof PayingCustomer)
				cust.display();
		}
		
		IO.println("");
		paid_by = Customer.getCustomerById(custs, IO.getInt("Select paying customer: ", null));
		IO.println("");

		if(paid_by != null){
			if(!(paid_by instanceof PayingCustomer)){
				IO.println("The selected customer is not a paying customer. Charge cancelled.");
				continuePrompt();
				return;
			}
		}
		else{
			IO.println("Customer does not exist. Charge cancelled.");
			continuePrompt();
			return;
		}

		IO.println("");
		IO.println("All Customers");
		IO.println("");

		Customer.column();
		for(Customer cust : custs){
			cust.display();
		}

		IO.println("");
		
		paid_for = Customer.getCustomerById(custs, IO.getInt("Select customer: ", null));

		IO.println("");

		if(paid_for == null){
			IO.println("Customer does not exist. Charge cancelled.");
			continuePrompt();
			return;
		}

		IO.println("All Subscriptions");
		IO.println("");

		Subscription.column();
		for(Subscription sub : subs){
			sub.display();
		}

		IO.println("");
		subscription = Subscription.getSubscriptionById(subs, IO.getInt("Select subscription: ", null));
		IO.println("");
		
		if(subscription != null){
			if(subscription.isSupplement() && !paid_for.hasMagazine(subscription.getMagazineId())){
				IO.println("Customer did not owned the required magazine to purchase this supplement.");
				continuePrompt();
				return;
			}

			subscription = subscription.clone();

			if(paid_by.equals(paid_for)){
				charge = ((PayingCustomer)paid_by).paySubscription(subscription);
			}
			else{
				charge = ((PayingCustomer)paid_by).paySubscription(paid_for, subscription);
			}

			if(paid_by.equals(paid_for)){
				IO.println("Paying customer " + paid_by.getName() + " made a purchase for " + subscription.getName());
			}
			else{
				IO.println("Paying customer " + paid_by.getName() + " purchased a subscription for " + paid_for.getName());
			}
			charges.add(charge);
			continuePrompt();
			return;
		}
		else{
			IO.println("Subscription does not exist. Charge cancelled.");
			continuePrompt();
			return;
		}
	}
}