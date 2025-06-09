package com.ict373.assignment1.utils;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

/** 
 * Provides methods for reading input from the console and printing output.
 */
public class IO{
	/**
	 * Reads a line of input from the console.
	 * 
	 * @return The line of input as a String
	 */
	private static final Scanner reader = new Scanner(System.in);

	/**
	 * Reads a line of input from the console.
	 * @param msg display message to prompt user
	 * @return The line of input as a String
	 */
  public static String getString(String msg){
    do{
      print(msg);
      
      String text = reader.nextLine();
      
      if(!text.isBlank())
        return text;
    }
    while(true);
  }
  
  /**
   * Get a \n input from the user
   * @param msg display message to prompt user
   */
  public static void getEnter(String msg){
    println(msg);
    reader.nextLine();
  }

	/**
	 * Get a character from the input
	 * @param msg display message to prompt user
	 * @param err_msg an optional error message to display if input is empty
	 * @return a character from the input
	 */
  public static char getChar(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;
    do{
      print(msg);
      String value = reader.nextLine();

      if(value.isEmpty())
        if(err_msg.isPresent())
          println(err_msg);
      else
        return value.charAt(0);
    }
    while(true);
  }

  /**
	 * Get the integer value of the input
	 * @param msg display message to prompt user
	 * @param err_msg an optional error message to display if input is invalid
	 * @return a number
	 */
  public static int getInt(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      try{
        int value = reader.nextInt();
        reader.nextLine();
        
        return value;
      }
      catch(InputMismatchException ex){
        reader.nextLine();
        
        if(err_msg.isPresent()){
          println(err_msg.get());
        }
      }
    }
    while(true);
  }
  
  /**
	 * Get the short value of the input
	 * @param msg display message to prompt user
	 * @param err_msg an optional error message to display if input is invalid
	 * @return a number
	 */
  public static short getShort(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      try{
        short value = reader.nextShort();
        reader.nextLine();
        
        return value;
      }
      catch(InputMismatchException ex){
        reader.nextLine();
        
        if(err_msg.isPresent()){
          println(err_msg.get());
        }
      }
    }
    while(true);
  }
  
  /**
   * Get the boolean value
   * 
   * @param msg display message to prompt user
   * @param err_msg an optional error message to display if input is invalid 
   * @return a boolean value 
   */
  public static boolean getBoolean(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      String value = reader.nextLine();
      
      if(
        value.equalsIgnoreCase("true") || 
        value.equals("1") ||
        value.equalsIgnoreCase("yes")
      )
        return true;
      else if(
        value.equalsIgnoreCase("false") || 
        value.equals("0") ||
        value.equalsIgnoreCase("no")
      )
        return false;
      else
        if(err_msg.isPresent())
          println(err_msg.get());
    }
    while(true);
  }
  
  /**
   * Get the long value of the input
   * 
   * @param msg display message to prompt user
   * @param err_msg an optional error message to display if input is invalid 
   * @return a number 
   */
  public static long getLong(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      try{
        long value = reader.nextLong();
        reader.nextLine();
        
        return value;
      }
      catch(InputMismatchException ex){
        reader.nextLine();
        
        if(err_msg.isPresent()){
          println(err_msg.get());
        }
      }
    }
    while(true);
  }
  
  /**
	 * Get 32-bit floating point number
	 * @param msg display message to prompt user
	 * @param err_msg an optional error message to display if input is invalid
	 * @return a 32-bit floating point number
	 */
  public static float getFloat(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      try{
        float value = reader.nextFloat();
        reader.nextLine();
        
        return value;
      }
      catch(InputMismatchException ex){
        reader.nextLine();
        
        if(err_msg.isPresent()){
          println(err_msg.get());
        }
      }
    }
    while(true);
  }
  
  /**
	 * Get 64-bit floating point number
	 * @param msg display message to prompt user
	 * @param err_msg an optional error message to display if input is invalid
	 * @return a 64-bit floating point number
	 */
  public static double getDouble(String msg, Optional<String> err_msg){
    err_msg = err_msg == null ? Optional.empty() : err_msg;

    do{
      print(msg);
      
      try{
        double value = reader.nextDouble();
        reader.nextLine();
        
        return value;
      }
      catch(InputMismatchException ex){
        reader.nextLine();
        
        if(err_msg.isPresent()){
          println(err_msg.get());
        }
      }
    }
    while(true);
  }
  
  /**
   * Prints the message
   * 
   * @param <V> type of object valid to built-in print parameter
   * @param data message to display
   */
  public static <V> void print(V data){
    System.out.print(data);
  }
  
  /**
   * Prints the message with a newline
   * 
   * @param <V> type of object valid to built-in println parameter
   * @param data message to display
   */
  public static <V> void println(V data){
    
    System.out.println(data);
  }

  /**
   * Prints text in a series of lines
   * @param data A spread operator containing string
   */
  public static void printText(String ...data){
    for(String text : data){
      println(text);
    }
  }
  
  /**
   * Closes reader and releases any resources associated with it
   */
  public static void close(){
    reader.close();
  }
}