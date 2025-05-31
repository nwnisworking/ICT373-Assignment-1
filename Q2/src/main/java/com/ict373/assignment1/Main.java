package com.ict373.assignment1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.ict373.assignment1.customers.*;
import com.ict373.assignment1.magazines.*;
import com.ict373.assignment1.payment.Charge;
import com.ict373.assignment1.utils.CSVParser;
import com.ict373.assignment1.utils.FileIO;
import com.ict373.assignment1.utils.IO;

// Assumptions
// 1. User can own more than one magazine subscription.
// 2. We should store customer in transaction

public class Main{
	private static final String SUBSCRIPTION_CSV = "csv/subscriptions.csv";

	private static final String CUSTOMER_CSV = "csv/customers.csv";

	private static final String CHARGE_CSV = "csv/charges.csv";

	private static boolean is_CSV = false;

	private static TreeMap<Integer, Subscription> subs = new TreeMap<>();

	private static TreeMap<Integer, Customer> custs = new TreeMap<>();

	private static ArrayList<Charge> charges = new ArrayList<>();
	public static void main(String[] args){
		is_CSV = IO.getBoolean(
			"Do you want to load sample data using CSV files? ", null);

		try{
			initSubscription();
			initCustomer();
			initCharge();

			int choice = 10;

			while(choice != 0){
				Page.home();

				choice = IO.getInt("Pleae select your option: ", null);
				IO.println("");
				
				switch(choice){
					case 1 -> Page.viewSubscriptions(subs);
					case 2 -> Page.addSubscription(subs, custs);
					case 3 -> Page.removeSubscription(subs, custs, charges);
					case 4 -> Page.viewCustomers(custs);
					case 5 -> Page.addCustomer(custs);
					case 6 -> Page.removeCustomer(custs, charges);
					case 7 -> Page.viewCharges(charges);
					case 8 -> Page.addCharge(charges, custs, subs);
					case 9 -> Page.removeCharge(charges, custs, subs);
					case 10 -> choice = 0;
				}
			}
		}
		// This catches all the exceptions. Only IMPORTANT exceptions that disrupt the program flow should be caught. 
		catch(Exception e){
			System.out.println(e.getMessage());
			return;
		}
	}

	private static void initCharge() throws IOException, ClassCastException, CloneNotSupportedException, RuntimeException{
		int line = 1;

		if(is_CSV){
			try{
				FileIO io = new FileIO(CHARGE_CSV);
				CSVParser parser = new CSVParser(io.readAll());
				
				io.close();

				while(!parser.finished()){
					Charge charge = new Charge(
						(PayingCustomer) custs.get(parser.getInteger()),
						custs.get(parser.getInteger()),
						subs.get(parser.getInteger())
					);

					charges.add(charge);
					line++;
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read charges from CSV: " + e.getMessage());
			}
			catch(ClassCastException e){
				throw new ClassCastException("Customer is not a paying customer in line " + line);
			}
		}

		try{
			line = 1;
			for(Charge charge : charges){
				PayingCustomer paid_by = (PayingCustomer)charge.getPaidBy();
				Customer paid_for =  charge.getPaidFor();
				Subscription sub = charge.getSubscription().clone();

				charge.setSubscription(sub);

				// If subscription is a supplement and it does not contain the required magazine
				if(!sub.isMagazine() && !paid_for.hasMagazine(sub.getMagazineId())){
					throw new RuntimeException("Associate customer does not have the magazine for this subscription in line " + line);
				}

				sub.setPaidBy(paid_by.getId());
				sub.setPaidFor(paid_for.getId());
				paid_for.setSubscriptions(sub);
				line++;
			}
		}
		catch(CloneNotSupportedException e){
			throw new CloneNotSupportedException("Unable to clone subscription.");
		}
		catch(ClassCastException e){
			throw new ClassCastException("Customer is not a paying customer in line " + line);
		}
	}

	private static void initCustomer() throws IOException, RuntimeException{
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
					custs.put(customer.getId(), customer);
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read customers from CSV: " + e.getMessage());
			}
		}
	}

	private static void initSubscription() throws IOException, RuntimeException{
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
					subs.put(sub.getId(), sub);
				}
			}
			catch(IOException e){
				throw new IOException("Failed to read subscription from CSV: " + e.getMessage());
			}
		}
	}
}