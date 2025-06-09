package com.ict373.assignment1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.ict373.assignment1.customers.*;
import com.ict373.assignment1.payment.*;
import com.ict373.assignment1.magazines.Subscription;
import com.ict373.assignment1.utils.ANSI;
import com.ict373.assignment1.utils.IO;

import java.util.function.Consumer;

public class Page{
  /**
   * Prompts user to continue 
   * @param text Text to print to user
   */
  private static void prompt(String text){
    text = text == null ? "Press enter to continue" : text;

    IO.println("");
    IO.getEnter(text + "...");
    IO.println("");
  }

  /**
   * Displays data in rows and columns
   * @param <T>
   * @param format The format to follow
   * @param columns Column header 
   * @param arr The data to display
   * @param action A lambda function 
   */
  private static <T> void displayTable(String format, Object[] columns, Collection<T> arr, Consumer<T> action){
    IO.println(String.format(format, columns));
    
    for(T data : arr){
      action.accept(data);
    }
  }

  /**
   * Displays all the available menu
   */
  public static void home(){
    IO.printText(
      "Welcome to the Magazine Subscription System!",
      "1. Weekly Email",
      "2. Invoice for Paying Customers",
      "3. View Customers",
      "4. Add Customer",
      "5. Delete Customer",
      "6. Add Payer to Associate",
      "7. Remove Payer from Associate",
      "8. View Magazines and Supplements",
      "9. Add Subscription",
      "10. Remove Subscription",
      "11. Exit"
    );
  }

  /**
   * Render weekly email into the cli. 
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void weeklyEmail(TreeMap<Integer, Customer> customers){
    Object[] customers_arr = customers.values().toArray();
    
    for(int i = 0, size = customers.size(); i < size; i++){
      Customer customer = (Customer) customers_arr[i];

      // This customer does not have any subscription. 
      if(customer.getSubscriptionSize() == 0) continue;

      ANSI.clear();
      ANSI.homePosition();

      // Customer does not have any payer.
      if(customer instanceof AssociateCustomer && ((AssociateCustomer) customer).getPayer() == null){
        IO.printText(
          "To: " + customer.getEmail(),
          "Subject: Your weekly subscription is inactive :(",
          "",
          "Dear " + customer.getName() + ",",
          "",
          "A payer is required to keep your subscription active.",
          "",
          "If you have any enquires, feel free to reach out to us.",
          "",
          "Warm regards,",
          "Magazine Daily"
        );
      }
      // Paying customer and associate customer with payer.
      else{
        IO.printText(
          "To: " + customer.getEmail(),
          "Subject: Your weekly subscription is ready!",
          "",
          "Dear " + customer.getName() + ",",
          "",
          "Your magazine and supplement is ready for viewing!",
          "",
          "Here is what you currently subscribed to: "
        );
        
        for(Subscription subscription : customer.getSubscriptions()){
          IO.println("- " + subscription.getName());
        }

        IO.printText(
          "",
          "If you have any enquires, feel free to reach out to us.",
          "",
          "Warm regards,",
          "Magazine Daily"
        );
      }

      prompt(i < size - 1 ? "Press enter to continue to the next customer" : null);
    }
  }

  /**
   * Invoice paying customers of their monthly payment.
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void invoiceCustomer(TreeMap<Integer, Customer> customers){
    Object[] paying_customers = (Object[]) Customer
    .filterCustomer(
      customers.values(), 
      PayingCustomer.class
    )
    .toArray();
    
    double total[] = {0};
     
    for(int i = 0, size = paying_customers.length; i < size; i++){
      PayingCustomer paying_customer = (PayingCustomer) paying_customers[i];
      
      ANSI.clear();
      ANSI.homePosition();

      IO.printText(
        "To: " + paying_customer.getEmail(),
        "Subject: Invoice for Paying Customer " + paying_customer.getName(),
        "",
        "Dear " + paying_customer.getName() + ",",
        "",
        "Thank you for being our valued customer!",
        "",
        "Here is the summary for this month: ",
        ""
      );

      if(paying_customer.getSubscriptionSize() == 0){
        IO.printText("No available subscription.");
      }
      else{
        // Display paying customer's subscription.
        displayTable(
          Subscription.TABLE_COLUMN, 
          Subscription.TABLE_COLUMN_NAME, 
          paying_customer.getSubscriptions(), 
          e->{
            e.display();
            total[0]+= e.getCost();
          }
        );
        IO.printText("", String.format("Total cost: %.2f", total[0]), "");
      }

      if(paying_customer.getAssociateSize() == 0){
        IO.printText("No available associate customers", "");
      }
      else{
        // Display all of associate customers their subscriptions.
        for(AssociateCustomer associate : paying_customer.getAssociates()){
          IO.printText("", "Associate Customer: " + associate.getName());
          
          if(associate.getSubscriptionSize() == 0){
            IO.printText("", "Associate customer does not have any subscription yet.");
          }
          else{
            total[0] = 0;
            
            displayTable(
              Subscription.TABLE_COLUMN, 
              Subscription.TABLE_COLUMN_NAME, 
              associate.getSubscriptions(), 
              e->{
                e.display();
                total[0]+= e.getCost();
              }
            );
          }

          IO.printText("",String.format("Total cost for %s's subscription: $%.2f", associate.getName(), total[0]), "");
        }
      }

      IO.printText(
        String.format("Here is the total cost of all the subscriptions: $%.2f", paying_customer.getTotalCost()),
        "",
        "Warm regards,",
        "Magazine Daily"
      );

      prompt(i < size - 1 ? "Press enter to continue to the next customer" : null);
    }
  }

  /**
   * View customers in a list format and then select a specific customer to view additional details.
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void viewCustomers(TreeMap<Integer, Customer> customers){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("VIEW CUSTOMER", "");

    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME,
      customers.values(), 
      e->e.display()
    );

    IO.println("");

    Customer customer = customers.get(IO.getInt("Enter customer ID to view its information: ", null));

    IO.println("");

    if(customer == null){
      IO.println("Customer cannot be found.");
    }
    else{
      ANSI.clear();
      ANSI.homePosition();

      IO.printText(
        "Name: " + customer.getName(),
        "Email: " + customer.getEmail(),
        "Type: " + (customer instanceof PayingCustomer ? "Paying Customer" : "Associate Customer")
      );

      if(customer instanceof PayingCustomer){
        PayingCustomer paying_customer = (PayingCustomer) customer;

        IO.printText("", "Payment Details");

        if(paying_customer.getPaymentMethod() instanceof CreditCard){
          CreditCard m_credit = (CreditCard) paying_customer.getPaymentMethod();

          IO.printText(
            "Type: Credit Card",
            "Card Number: " + m_credit.getCardNumber(),
            "Card Expiry: " + m_credit.getExpiryDate()
          );
        }
        else{
          DirectDebit m_debit = (DirectDebit) paying_customer.getPaymentMethod();

          IO.printText(
            "Type: Direct Debit",
            "Account Number: " + m_debit.getAccountNumber(),
            "Bank Name: " + m_debit.getBankName()
          );
        }
      }

      IO.println("");

      if(customer.getSubscriptionSize() == 0){
        IO.println("Customer does not have subscription");
      }
      else{
        displayTable(
          Subscription.TABLE_COLUMN, 
          Subscription.TABLE_COLUMN_NAME,
          customer.getSubscriptions(), 
          e->e.display()
        );
      }
    }
    
    prompt(null);
  }

  /**
   * Add customer to the system. 
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void addCustomer(TreeMap<Integer, Customer> customers){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("ADD CUSTOMER", "");
    
    Method method = null;
    Customer customer = null;

    do{
      try{
        customer = Customer.getType(IO.getInt("Is this a paying customer? (1 for Yes, 0 for No): ", null));
      }
      catch(RuntimeException e){} 
    }
    while(customer == null);

    customer.setId(customers.isEmpty() ? 1 : customers.lastKey() + 1);
    customer.setName(IO.getString("Enter customer name: "));
    customer.setEmail(IO.getString("Enter customer email: "));

    if(customer instanceof PayingCustomer){
      IO.printText("", "PAYMENT METHOD");

      do{
        try{
          method = Method.getType(IO.getInt("Select customer's payment method (1 for Credit Card, 2 for Direct Debit): ", null));
        }
        catch(RuntimeException e){} 
      }
      while(method == null);

      if(method instanceof DirectDebit){
        DirectDebit m_debit = (DirectDebit) method;
        m_debit.setAccountNumber(IO.getString("Enter account number: "));
        m_debit.setBankName(IO.getString("Enter bank name: "));
      }
      else{
        CreditCard m_credit = (CreditCard) method;
        m_credit.setCardNumber(IO.getString("Enter credit card number: "));
        m_credit.setExpiryDate(IO.getString("Enter bank name: "));
      }
      
      ((PayingCustomer) customer).setPaymentMethod(method);
    }
    else{
      ArrayList<Customer> payers = Customer.filterCustomer(customers.values(), PayingCustomer.class);

      IO.printText("", "LIST OF PAYING CUSTOMERS", "");
      displayTable(
        Customer.TABLE_COLUMN, 
        Customer.TABLE_COLUMN_NAME, 
        payers, 
        e->e.display()
      );

      Customer paying_customer = customers.get(IO.getInt("Select payer: ", null));

      if(paying_customer == null){
        IO.println("Unable to find payer by ID.");
        prompt(null);
        return;
      }
      else if(paying_customer instanceof AssociateCustomer){
        IO.println("Customer is not a paying customer");
        prompt(null);
        return;
      }
      else{
        ((PayingCustomer) paying_customer).addAssociate((AssociateCustomer) customer);
      }
    }

    customers.put(customer.getId(), customer);
    IO.printText("", customer.getName() + " added successfully");

    prompt(null);
  }

  /**
   * Deletes the customer from the system. 
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void deleteCustomer(TreeMap<Integer, Customer> customers){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("DELETE CUSTOMER", "");

    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME,
      customers.values(), 
      e->e.display()
    );

    Customer customer = customers.get(IO.getInt("Select one of the customer: ", null));

    if(customer == null){
      IO.printText("", "Unable to find customer");
    }
    else{
      if(customer instanceof PayingCustomer){
        PayingCustomer p_customer = (PayingCustomer) customer;
        p_customer.removeAssociate();
      }
      else{
        AssociateCustomer a_customer = (AssociateCustomer) customer;
        
        if(a_customer.getPayer() != null){
          a_customer.getPayer().removeAssociate(a_customer);
        }
      }

      customers.entrySet().removeIf(e->e.getValue().equals(customer));
      IO.printText("", customer.getName() + " deleted successfully.");
    }
  
    prompt(null);
  }

  /**
   * Set a payer for associate customer.
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void setPayer(TreeMap<Integer, Customer> customers){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("SET PAYER", "");
    
    ArrayList<Customer> associate_customers = new ArrayList<>();
    ArrayList<Customer> paying_customers = new ArrayList<>();
    Customer associate_customer = null;
    Customer paying_customer = null;

    for(Customer customer : customers.values()){
      if(customer instanceof PayingCustomer){
        paying_customers.add(customer);
      }
      else{
        if(((AssociateCustomer) customer).getPayer() == null){
          associate_customers.add(customer);
        }
      }
    }

    if(associate_customers.isEmpty()){
      IO.println("All the associates have a payer");
      prompt(null);
      return;
    }

    IO.println("ASSOCIATE CUSTOMER");
    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME,
      associate_customers, 
      e->e.display()
    );

    associate_customer = customers.get(IO.getInt("Enter associate ID: ", null));

    if(!associate_customers.contains(associate_customer)){
      IO.printText("", "The ID is not part of the list.");
      prompt(null);
      return;
    }

    ANSI.clear();
    ANSI.homePosition();
    IO.printText("", "PAYING CUSTOMER");
    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME,
      paying_customers, 
      e->e.display()
    );

    IO.println("");
    paying_customer = customers.get(IO.getInt("Enter paying customer ID: ", null));

    if(!paying_customers.contains(paying_customer)){
      IO.printText("", "The ID is not part of the list.");
      prompt(null);
      return;
    }

    ((PayingCustomer) paying_customer).addAssociate((AssociateCustomer) associate_customer);

    IO.printText("", "Payer " + paying_customer.getName() + " set to associate " + associate_customer.getName() + " successfully");
    prompt(null);
  }
  
  public static void removePayer(TreeMap<Integer, Customer> customers){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("REMOVE PAYER", "");

    ArrayList<AssociateCustomer> associate_customers = null;
    ArrayList<Customer> paying_customers = Customer.filterCustomer(customers.values(), PayingCustomer.class);
    Customer paying_customer = null;
    Customer associate_customer = null;
    
    IO.println("PAYING CUSTOMER");
    displayTable(
        Customer.TABLE_COLUMN, 
        Customer.TABLE_COLUMN_NAME, 
        paying_customers, 
        e->e.display()
    );
    
    IO.println("");
    paying_customer = customers.get(IO.getInt("Enter ID of the paying customer: ", null));
    IO.println("");

    if(paying_customer == null || !paying_customers.contains(paying_customer)){
      IO.println("ID is not part of the list");
      prompt(null);
      return;
    }

    associate_customers = ((PayingCustomer) paying_customer).getAssociates();

    if(associate_customers.isEmpty()){
      IO.println("Paying customer " + paying_customer.getName() + " does not have an associate.");
      prompt(null);
      return;
    }
    
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("REMOVE PAYER", "");

    IO.println("ASSOCIATES");
    displayTable(
      Customer.TABLE_COLUMN,
      Customer.TABLE_COLUMN_NAME,
      associate_customers,
      e->e.display()
    );
    
    IO.println("");
    associate_customer = customers.get(IO.getInt("Enter ID of the associate customer: ", null));
    IO.println("");
    
    if(associate_customer == null || !associate_customers.contains((AssociateCustomer) associate_customer)){
      IO.println("ID is not part of the list");
      prompt(null);
      return;
    }
    
    ((PayingCustomer) paying_customer).removeAssociate((AssociateCustomer) associate_customer);
    IO.println("Paying customer " + paying_customer.getName() + " removed " + associate_customer.getName() + " from associate list");
    prompt(null);
  }

  /**
   * View all magazines and supplements.
   * @param subscriptions Subscriptions stored in TreeMap with ID used as a key.    * 
   */
  public static void viewMagazineSupplement(TreeMap<Integer, Subscription> subscriptions){
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("VIEW MAGAZINES AND SUPPLEMENTS", "");
  
    displayTable(
      Subscription.TABLE_COLUMN, 
      Subscription.TABLE_COLUMN_NAME,
      subscriptions.values(), 
      e->e.display()
    );
  
    prompt(null);
  }

  /**
   * Add subscription to a selected customer.
   * @param subscriptions Subscriptions stored in TreeMap with ID used as a key.
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void addSubscription(TreeMap<Integer, Subscription> subscriptions, TreeMap<Integer, Customer> customers){
    Customer customer = null;
    Subscription subscription = null;

    ANSI.clear();
    ANSI.homePosition();
    IO.printText("ADD SUBSCRIPTION", "");

    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME, 
      customers.values(), 
      e->e.display()
    );

    customer = customers.get(IO.getInt("Select customer from the list: ", null));

    if(customer == null){
      IO.printText("", "Unable to set subscription from a non-existing customer");
      prompt(null);
      return;
    }

    ANSI.clear();
    ANSI.homePosition();
    IO.printText("ADD SUBSCRIPTION", "");

    displayTable(
      Subscription.TABLE_COLUMN, 
      Subscription.TABLE_COLUMN_NAME,
      subscriptions.values(), 
      e->e.display()  
    );

    subscription = subscriptions.get(IO.getInt("Select subscription from the list: ", null));

    if(subscription == null){
      IO.printText("", "Subscription is invalid.");
      prompt(null);
      return;
    }

    if(subscription.isSupplement() && !customer.hasMagazine(subscription.getMagazine().getId())){
      IO.printText("", "Customer do not have the required magazine to read the subscription.");
      prompt(null);
      return;
    }

    customer.addSubscriptions(subscription);
    IO.printText("", "Subscription " + subscription.getName() + " added to " + customer.getName());
    prompt(null);
  }

  /**
   * Remove subscription from a selected customer
   * @param subscriptions Subscriptions stored in TreeMap with ID used as a key.
   * @param customers Customers stored in TreeMap with ID used as a key.
   */
  public static void removeSubscription(TreeMap<Integer, Subscription> subscriptions, TreeMap<Integer, Customer> customers){
    Customer customer = null;
    Subscription subscription = null;
    
    ANSI.clear();
    ANSI.homePosition();
    IO.printText("REMOVE SUBSCRIPTION", "");

    displayTable(
      Customer.TABLE_COLUMN, 
      Customer.TABLE_COLUMN_NAME, 
      customers.values(), 
      e->e.display()
    );

    customer = customers.get(IO.getInt("Select customer from the list: ", null));

    if(customer == null){
      IO.printText("", "Unable to remove subscription from a non-existing customer");
      prompt(null);
      return;
    }

    if(customer.getSubscriptionSize() == 0){
      IO.printText("", "Nothing to remove from customer's subscription");
      prompt(null);
      return;
    }

    ANSI.clear();
    ANSI.homePosition();
    IO.printText("REMOVE SUBSCRIPTION", "");

    displayTable(
      Subscription.TABLE_COLUMN, 
      Subscription.TABLE_COLUMN_NAME,
      customer.getSubscriptions(), 
      e->e.display()  
    );

    subscription = subscriptions.get(IO.getInt("Select subscription from the list: ", null));

    if(subscription == null){
      IO.printText("", "Subscription is invalid.");
      prompt(null);
      return;
    }
    else if(!customer.getSubscriptions().contains(subscription)){
      IO.printText("", "Customer " + customer.getName() + " does not have a subscription " + subscription.getName());
      prompt(null);
      return;
    }

    customer.removeSubscription(subscription);
    IO.printText("", "Subscription " + subscription.getName() + " removed from " + customer.getName());
    prompt(null);
  }
}