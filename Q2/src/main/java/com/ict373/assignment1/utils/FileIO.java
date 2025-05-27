package com.ict373.assignment1.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * FileIO Utility Class
 * 
 * Provides methods for reading from and writing to files.
 * 
 * @author nwnisworking
 * @version 1.0.0 Initial release
 * @date 2025-5-24
 */
public class FileIO{
  /**
   * An enumerable containing access mode
   */
  private enum Mode { READ, WRITE }

  /**
   * Reads the file
   */
  private Scanner reader;
  
  /**
   * Write to a file
   */
  private PrintWriter writer;

  /**
   * Represents the file in the system
   */
  private final File file;

  /**
   * Specifies the access mode
   */
  private Mode mode;
    
  public FileIO(String path) throws IOException{
    file = new File(path);

		if(!file.exists()){
			file.createNewFile();
		}
  }

  public String readAll() throws FileNotFoundException{
    setMode(Mode.READ);
    String str = "";

    while(hasLine()){
      str+= readLine() + "\n";
    }
    
    return str;
  }
  
  /**
   * Checks if the file contains line
   * @return true if the file contains a line
   * @throws FileNotFoundException 
   */
  public boolean hasLine() throws FileNotFoundException{
    setMode(Mode.READ);
    return reader.hasNextLine();
  }
  
  /**
   * Reads line inside a file
   * @return string containing the current line
   * @throws FileNotFoundException 
   */
  public String readLine() throws FileNotFoundException{
    setMode(Mode.READ);
    return reader.nextLine();
  }
  
  /**
   * Write text to a file
   * @param text text to write to the file
   * @throws FileNotFoundException 
   */
  public void write(String text) throws FileNotFoundException{
    setMode(Mode.WRITE);
    writer.print(text);
    writer.flush();
  }
  
  /**
   * Write text to a file and appends newline at the end
   * @param text text to write to the file
   * @throws FileNotFoundException 
   */
  public void writeLine(String text) throws FileNotFoundException{
    setMode(Mode.WRITE);
    writer.println(text);
    writer.flush();
  }
  
  /**
   * Set file to read or write mode
   * @param mode selected mode 
   * @throws FileNotFoundException 
   */
  private void setMode(Mode mode) throws FileNotFoundException{
    if(mode.equals(this.mode)) 
      return;
    
    try{
      close();

      switch(mode){
        case READ -> reader = new Scanner(file);
        case WRITE -> writer = new PrintWriter(file);
      }
    }
    catch(FileNotFoundException ex){
      throw new FileNotFoundException("File " + file.getName() + " cannot be found");
    }

    this.mode = mode;
  }
  
  /**
   * Close reader and writer
   */
  public void close(){
    if(reader != null){
      reader.close();
      reader = null;
    }
    if(writer != null){
      writer.close();
      writer = null;
    }

    mode = null;
  }
}