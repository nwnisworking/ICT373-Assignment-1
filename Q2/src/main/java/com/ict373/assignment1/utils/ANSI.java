package com.ict373.assignment1.utils;

/**
 * <p><strong>ANSI class</strong></p>
 * <p>Handles ANSI related commands. The list of ANSI escape sequences can be found below.</p>
 * 
 * @see https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797
 * @author nwnisworking
 * @date 9/6/2025
 * @filename ANSI.java
 */
public class ANSI{
  /**
   * Clears the entire screen 
   */
  public static void clear(){
    IO.print("\033[2J");
  }

  /**
   * Sets the cursor position back to 0, 0
   */
  public static void homePosition(){
    IO.print("\033[H");
  }
}