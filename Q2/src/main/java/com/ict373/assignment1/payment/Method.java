package com.ict373.assignment1.payment;

/**
 * <p><strong>Method interface</strong></p>
 * <p>Represents a payment method.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename Method.java
 */
public interface Method{
  /**
   * Get the available payment method type.
   * @param type The index which houses the available method type.
   * @return Returns a method type. Otherwise, it throws a RuntimeException
   */
  public static Method getType(int type){
    switch(type){
      case 1 : return new CreditCard();
      case 2 : return new DirectDebit();
      default : throw new RuntimeException("Method type does not exist");
    }
  }
}