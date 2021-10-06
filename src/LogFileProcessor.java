/**
 * 
 *
 * @authors Christina Porter and Kaitlyn Reed
 * Version 4.12.0
 * Compiler Project 3
 * Fall 2021
 */
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class LogFileProcessor {
public static int parseValue = 0; //counter for # of lines parsed
public static int IPAddresses = 0; //counter for # of unique addresses
public static int uniqueUsers = 0; //counter for # of unique users
public static String parsedString; //for printing the parsed lines
public static String IPString; //for printing unique IP addresses
public static String UsersString; //for printing unique users
public static HashMap<String, Integer> ipmap = new HashMap<String,Integer>(); //HashMap of IP addresses and occurrences
public static HashMap<String, Integer> usermap = new HashMap<String,Integer>(); //HashMap of users and occurrences

	/**
	 * main method asks for the file name and print flag to parse the data.
	 * This method uses nested if statements to decide on format.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
			//asking the user to input the name of the file and print flag
			Scanner scan = new Scanner(System.in);
			String fileName;
			int flag;
			System.out.println("Please enter the file name: ");
			fileName = scan.nextLine();
			System.out.println("Please enter the print flag");
			flag = scan.nextInt();
			
			try {
				IPAddresses(fileName);
				Users(fileName);
			}//end try
			catch(Exception e) {
				System.out.println("Error");
				e.printStackTrace(); // prints exception if IPAdresses() or Users() cannot be carried out
			}//end catch
				
			if(flag == 1) {
				for (String print: ipmap.keySet()) {
					
					System.out.println(print + ": " + ipmap.get(print)); //prints every key and its respective value in hashmap
           
				} //end for loop
				System.out.println(parsedString); //prints output
				System.out.println(IPString);
				System.out.println(UsersString); 
				
			} //end if
			else if(flag == 2) {
				for (String print: ipmap.keySet()) {
					
					System.out.println(print + ": " + ipmap.get(print)); //prints every key and its respective value in hashmap
           
				} //end for loop
				
				System.out.println(parsedString); //prints output
				System.out.println(IPString);
				System.out.println(UsersString); 
			}//end else
			else {
				System.out.println(parsedString); //prints output
				System.out.println(IPString);
				System.out.println(UsersString); 
			}//end else
			
			scan.close(); //closes scanner
			
	} //end main

	public static void IPAddresses(String fileName) throws Exception { 
		String regexPattern = "\\d*[.]\\d*[.]\\d*[.]\\d*"; //regex pattern to isolate IP addresses
		
		File inputFile = new File(fileName); //create file object logFile
		BufferedReader br = new BufferedReader(new FileReader(inputFile)); //create buffered reader object to read logFile
		
		Pattern pattern = Pattern.compile(regexPattern); //pattern holds regex statement
		String currentLine; //holds current line to be parsed
		
		int occurrence; //holds value of occurrence from hashmap to be incremented later
		
		while((currentLine = br.readLine()) != null) {
			parseValue ++; //adds to the # of lines that have been parsed
			Matcher matcher = pattern.matcher(currentLine); //Matcher object to match with regex pattern
				if(matcher.find()) {
   
					//if the IP address is already in the hashmap then the occurrence of the IP is incremented by 1
					if(ipmap.containsKey(matcher.group())) {
   
						occurrence = ipmap.get(matcher.group());
						occurrence ++;
						ipmap.put(matcher.group(), occurrence);
   
					}//end if
   
					//if the IP address is NOT already in the hashmap then a new location is added
					else {
   
						IPAddresses ++; //increases # of unique IP addresses by 1
						ipmap.put(matcher.group(), 1);
   
					} //end else
					
				}//end if
				
		}//end while
   
		parsedString = parseValue + "lines in the log file were parsed."; //string telling how many lines were parsed
		IPString = "There are" + IPAddresses + "different IP addresses in the file."; //string telling how many unique IP's were in the file
			
		br.close(); //closes the buffered reader
		
	} //end IPAddress
	
	public static void Users(String fileName) throws Exception {
		String regexPattern = "\\[(\\d{5})\\]"; //regex pattern to isolate users
		
		File inputFile = new File(fileName); //create file object inputFile
		BufferedReader br = new BufferedReader(new FileReader(inputFile)); //create buffered reader object to read file
		
		Pattern pattern = Pattern.compile(regexPattern); //pattern holds regex statement
		String currentLine; //holds current line to be parsed
		
		int occurrence; //holds value of occurrence from hashmap to be incremented later
		
		while((currentLine = br.readLine()) != null) {

			Matcher matcher = pattern.matcher(currentLine); //Matcher object to match with regex pattern
			if(matcher.find()) {
   
				//if the user is already in the hashmap then the occurrence of the IP is incremented by 1
				if(usermap.containsKey(matcher.group())) {
   
					occurrence = usermap.get(matcher.group());
					occurrence ++;
					usermap.put(matcher.group(), occurrence);
   
				}//close if
   
				//if the IP address is NOT already in the hashmap then a new location is added
				else {
   
					uniqueUsers ++; //increases # of unique users by 1
					usermap.put(matcher.group(), 1);
				
				}//end else
				
			} //end if
			
		} //end while
		
		UsersString = "There are" + uniqueUsers + "different users in the file."; //string telling # of unique users in the file
		
		br.close(); //closes the buffered reader
		
	}//end Users 
	
}//end class