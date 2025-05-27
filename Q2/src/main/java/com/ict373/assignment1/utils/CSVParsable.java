package com.ict373.assignment1.utils;

public interface CSVParsable{
	/**
	 * Parses data from a CSVParser into an object
	 * @param parser the CSVParser instance containing the data to parse
	 * @return an instance of type T populated with data from the parser
	 */
	public void parse(CSVParser parser);

	/**
	 * Converts the object to a CSV string representation
	 * @return a string in CSV format representing the object
	 */
	public String toString();
}