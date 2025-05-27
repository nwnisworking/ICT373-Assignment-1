package com.ict373.assignment1.utils;

import java.lang.IndexOutOfBoundsException;

/**
 * CSV Parser 
 * 
 * Handles CSV formatted lines by reading each line
 * 
 * @version 1.0.0 Initial release
 * @author nwnisworking
 * @date 2025-5-24
 */
public class CSVParser{
	/**
	 * The CSV text to be parsed
	 */
	private String lines;

	/**
	 * Current cursor position in the CSV text
	 */
	private int cursor = 0;

	/**
	 * Constructs a CSVParser with the given CSV text.
	 * 
	 * @param lines The CSV formatted text to parse
	 */
	public CSVParser(String lines){
		this.lines = lines;
	}

	/**
	 * Sets the CSV text to be parsed and reset the cursor.
	 * @param text The new CSV text to parse
	 */
	public void setText(String text){
		lines = text;
		cursor = 0;
	}

	/**
	 * Sets the cursor to a specific position in the CSV text.
	 * @param cursor The position to set the cursor to
	 */
	public void setCursor(int cursor){
		outOfBound();
		this.cursor = cursor;
	}

	/**
	 * Calcultes the total number of columns in the first row of the CSV text.
	 * @return The total number of columns in the first row
	 */
	public int totalColumns(){
		int total_fields = 0, index = 0;
		char c;
		
		while(index < lines.length() && (c = lines.charAt(index)) != '\n'){
			if(c == ','){ 
				total_fields++;
			}

			index++;
		}

		return total_fields + 1;
	}

	/**
	 * Reads the next CSV fields as a string.
	 * @return String representation of the column value
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public String getString(){
		trim();
		outOfBound();

		String str = "";
		int len = lines.length();
		char c;

		while(cursor < len){
			c = lines.charAt(cursor++);

			if(c != ',' && c!= '\n')
				str+= c;
			else
				break;
		}

		return str;
	}

	/**
	 * Check whether the cursor reaches the new line.
	 * @return The next field as a boolean
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public boolean nextLine(){
		if(cursor == 0) return false;
		return lines.charAt(cursor - 1) == '\n';
	}

	/**
	 * Reads the next CSV field as a short.
	 * @return The next field as a short
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public short getShort(){
		return Short.parseShort(getString());
	}

	/**
	 * Reads the next CSV field as an integer.
	 * @return The next field as an integer
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public int getInteger(){
		return Integer.parseInt(getString());
	}

	/**
	 * Reads the next CSV field as a long.
	 * @return The next field as a long
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public long getLong(){
		return Long.parseLong(getString());
	}

	/**
	 * Reads the next CSV field as a float.
	 * @return The next field as a float
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public float getFloat(){
		return Float.parseFloat(getString());
	}

	/**
	 * Reads the next CSV field as a double.
	 * @return The next field as a double
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	public double getDouble(){
		return Double.parseDouble(getString());
	}

	/**
	 * Checks if the CSV parsing has reached the end of the text.
	 * @return true if the cursor has reached or exceeded the length of the text, false otherwise
	 */
	public boolean finished(){
		return cursor >= lines.length();
	}

	/**
	 * Skips any leading commas or spaces in the CSV text.
	 */
	private void trim(){
		while(cursor < lines.length() && (lines.charAt(cursor) == ',' || lines.charAt(cursor) == ' ')){
			cursor++;
		}
	}

	/**
	 * Checks if the cursor is out of bounds and throws an exception if so.
	 * @throws IndexOutOfBoundsException if the cursor exceeds the size of the text
	 */
	private void outOfBound(){
		if(cursor >= lines.length())
			throw new IndexOutOfBoundsException("Cursor exceeds the size of the text");
	}
}