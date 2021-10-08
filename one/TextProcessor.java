package one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Scanner;


/**
 * This class asks the user for a regex query and prints the results of the query from the Dracula text by Bram Stoker.
 * 
 * @author Christina Porter
 * @author Kaitlyn Reed
 * @version 10/04/2021
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */
public class TextProcessor {

    /**
     * This is my constructor that find the requested pattern and parses the text file.
     * @param fileName
     * @param pattern
     * @throws Exception
     */
    public TextProcessor(String fileName, String pattern) throws Exception{

        File file = new File(fileName); //This sets up a new file Object with the given file name
        BufferedReader br = new BufferedReader(new FileReader(file)); //This sets up a new reader object for the File

        String line; //This will be used to represent each line in the file.

        Pattern newPattern = Pattern.compile(pattern); //This creates the pattern we want to search for
        int occurances = 0; //This will be used to count the number of occurances of the pattern

        /**
         * While the next line is not null, search for the pattern. If a match is found, print the match
         * out to the screen and add 1 to the number of occurances.
         */
        while ((line = br.readLine()) != null){

            Matcher matcher = newPattern.matcher(line); //Sets up a line to match the pattern with
            boolean matchFound = matcher.find(); //Find the query in the query

            /**
             * If a match is found, print the match to the screen and add 1 to occurances.
             */
            if(matchFound){
                System.out.println(matcher.group());
                occurances = occurances + 1;
            }else{
                continue;
            }

        }
        System.out.printf("Number of occurances: %d", occurances); //Prints out the number of occurances.
    }

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the file name:");
        String fileName = sc.nextLine();

        System.out.println("Please enter a regex pattern:");
        String regexPattern = sc.nextLine();

        new TextProcessor(fileName, regexPattern);

        /**
         * Regex Patterns:
         * 1) (\ba\b|\ban\b|\bthe\b)
         * 2) (?:Mina Harker|Mrs\W\sHarker)
         * 3) (.*\bTransylvania\b.*)
         * 4) (?:\bto\s.\w+)
         * 5) (\b(?!Helsing|Godalming)\b\w+ing\b)
         */
    }

}//end class
