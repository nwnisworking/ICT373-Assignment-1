package com.ict373.assignment1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.ict373.assignment1.customers.*;
import com.ict373.assignment1.magazines.*;
import com.ict373.assignment1.payment.Charge;
import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.FileIO;
import com.ict373.assignment1.utils.IO;

// Assumptions
// 1. User can own more than one magazine subscription.
// 2. Transaction can contain customer id that does not exist (from deleted)

public class Main{
	private static final String SUBSCRIPTION_CSV = "csv/subscriptions.csv";

	private static final String CUSTOMER_CSV = "csv/customers.csv";

	private static final String CHARGE_CSV = "csv/charges.csv";

	private static boolean is_CSV = false;

	private static ArrayList<Subscription> subs = new ArrayList<>();

	private static ArrayList<Customer> custs = new ArrayList<>();

	private static ArrayList<Charge> trans = new ArrayList<>();
	public static void main(String[] args){
		is_CSV = IO.getBoolean(
			"Do you want to load sample data using CSV files? ", 
			Optional.of("Only yes or no is accepted.")
		);

		try{
			initSubscription();
			initCustomer();
			initTransaction();

			int choice = 9;

			while(choice != 0){
				Page.home();

				choice = IO.getInt("Pleae select your option: ", null);
				IO.println("");
				
				switch(choice){
					case 1 -> Page.viewSubscriptions(subs);
					case 2 -> Page.addSubscription(subs, custs);
					case 3 -> Page.removeSubscription(subs, custs);
					case 4 -> Page.viewCustomers(custs);
					case 5 -> Page.addCustomer(custs);
					case 6 -> Page.removeCustomer(custs, trans);
					case 7 -> Page.viewCharges(trans);
					case 8 -> Page.addCharge(trans, custs, subs);
					case 9 -> choice = 0;
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return;
		}
	}

	private static Subscription getSubscription(int id){
		for(Subscription sub : subs){
			if(sub.getId() == id){
				return sub;
			}
		}

		throw new RuntimeException("Subscription with ID " + id + " not found.");
	}

	private static void initTransaction() throws IOException{
		if(is_CSV){
			try{
				FileIO io = new FileIO(CHARGE_CSV);
				CSVParser parser = new CSVParser(io.readAll());
				
				io.close();
				
				while(!parser.finished()){
					Charge tran = new Charge();

					tran.parse(parser);
					trans.add(tran);
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read transactions from CSV file: " + e.getMessage());
			}
		}
		else{
			trans.addAll(Data.loadCharges());
		}

		for(Charge tran : trans){
			Customer paid_by_customer = null;
			Customer paid_for_customer = null;

			for(int i = 0; i < custs.size(); i++){
				if(tran.getPaidBy() == custs.get(i).getId()){
					paid_by_customer = custs.get(i);
				}
				
				if(tran.getPaidFor() == custs.get(i).getId()){
					paid_for_customer = custs.get(i);
				}
				
				if(paid_by_customer != null && paid_for_customer != null){
					try{
						Subscription sub = getSubscription(tran.getSubscriptionId()).clone();

						if(paid_by_customer instanceof AssociateCustomer){
							throw new RuntimeException("Associate customer cannot pay for a subscription.");
						}

						if(sub.isMagazine()){
							sub.setPaidBy(paid_by_customer.getId());
							sub.setPaidFor(paid_for_customer.getId());
							paid_for_customer.setSubscriptions(sub);
						}
						else{
							if(!paid_for_customer.hasMagazine(sub.getMagazineId())){
								throw new RuntimeException("Associate customer does not have the magazine for this subscription.");
							}
							sub.setPaidBy(paid_by_customer.getId());
							sub.setPaidFor(paid_for_customer.getId());
							paid_for_customer.setSubscriptions(sub);
						}

					}
					catch(CloneNotSupportedException e){
						throw new RuntimeException("Failed to clone subscription: " + e.getMessage());
					}

					break;
				}
			}
		}
	}

	private static void initCustomer() throws IOException{
		if(is_CSV){
			try{
				FileIO io = new FileIO(CUSTOMER_CSV);
				CSVParser parser = new CSVParser(io.readAll());
				
				io.close();

				while(!parser.finished()){
					int type = parser.getInteger();
					Customer customer;

					switch(type){
						case 0 -> customer = new AssociateCustomer();
						case 1 -> customer = new PayingCustomer();
						default -> throw new RuntimeException("Invalid customer type.");
					}

					customer.parse(parser);
					custs.add(customer);
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read customers from CSV file: " + e.getMessage());
			}
		}
		else{
			custs.addAll(Data.loadCustomers());
		}
	}

	private static void initSubscription() throws IOException{
		if(is_CSV){
			try{
				FileIO io = new FileIO(SUBSCRIPTION_CSV);
				CSVParser parser = new CSVParser(io.readAll());
				
				while(!parser.finished()){
					int type = parser.getInteger();
					Subscription sub;

					switch(type){
						case 0 ->sub = new Supplement();
						case 1 -> sub = new Magazine();
						default -> throw new RuntimeException("Invalid Subscription type.");
					}

					sub.parse(parser);
					subs.add(sub);
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read subscription from CSV file: " + e.getMessage());
			}
		}
		else{
			subs.addAll(Data.loadSubscription());
		}
	}
}