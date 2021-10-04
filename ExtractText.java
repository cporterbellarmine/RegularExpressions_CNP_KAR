import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 * This class extracts the text from Bellarmine's PDF schedule and finds the course number and title, the open/closed status of the
 * course and the total seats/seats taken, and the number of courses for each course topic using regex queries. The results
 * of each query is placed in its own file.
 * 
 * @author Christina Porter
 * @author Kaitlyn Reed
 * @version 10/04/2021
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */

public class ExtractText {

    /**
     * Main-constructor. This will utilize each of my methods to gather course information.
     * @param inputFileName
     * @param outputFileName
     */
    public ExtractText(String inputFileName, String outputFileName){

        try{
            textExtractor(inputFileName, outputFileName);
            classInfoExtractor(outputFileName, "ClassInfo.txt");
            classOpenExtractor(outputFileName, "ClassStatus.txt");
            classCounter(outputFileName, "ClassCounter.txt");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method loads a PDF file and strips the text from the file to place it in a text document.
     * @param inputFileName
     * @param outputFileName
     * @throws IOException
     */
    public void textExtractor(String inputFileName, String outputFileName) throws IOException{

        File file = new File(inputFileName); //Creates File Object 
        PDDocument doc = PDDocument.load(file); //Places the file into a doc object so we can use the text stripper
        String text = new PDFTextStripper().getText(doc); //Strips the text from the PDF
        PrintWriter writer = new PrintWriter(outputFileName); //Sets up a PrintWriter Object to the designated file
        writer.print(text); //Writes the text to the file


    }//end textExtractor

    /**
     * This method will extract the course information from the text file and place this in a different text file.
     * @param inputFileName
     * @param outputFileName
     * @throws Exception
     */
    public void classInfoExtractor(String inputFileName, String outputFileName) throws Exception{
        File file = new File(inputFileName); //This creates a File object from the input file.
        BufferedReader br = new BufferedReader(new FileReader(file)); //This creates a reader object to parse through my file

        String line; //This will represent each line in the file
        PrintWriter writer = new PrintWriter(outputFileName); //This creates a writer to write to the new file

        /**
         * As long as a new line exists in the file, then we search for a specified regex query in each line. If a match
         * is found, then we write the match to a file.
         */
        while((line = br.readLine()) != null){
            Pattern pattern = Pattern.compile("((\\b\\w{2}\\b|\\b\\w{3}\\b|\\b\\w{4}\\b|\\b\\w{6}\\b)[-](\\b\\d{3}\\b|\\b\\d{3}\\w\\b)[-](\\b\\d{2}||\\b\\w{2}\\d{2}\\b)(.*?)((?!(\\sOpen|\\sCLOSED)).)*)");
            Matcher matcher = pattern.matcher(line);
            boolean matchFound = matcher.find();

            if(matchFound){
                writer.println(matcher.group());
            }//end if
        }//end while

        writer.close(); //closes the writer
        br.close(); //closes the reader
    }//end classInfoExtractor

    /**
     * This method will extract the course availability information from the file.
     * @param inputFileName
     * @param outputFileName
     * @throws Exception
     */
    public void classOpenExtractor(String inputFileName, String outputFileName) throws Exception{
        File file = new File(inputFileName); //This creates a new File object from the Input File.
        BufferedReader br = new BufferedReader(new FileReader(file)); //This creates a reader for the File.

        String line; //This will represent each line in the file.
        PrintWriter writer = new PrintWriter(outputFileName); //This will create a writer to the give output file name.
        

        /**
         * As long as a new line exists in the file, then we search for a specified regex query in each line. If a match
         * is found, then we write the match to a file.
         */
        while((line = br.readLine()) != null){
            Pattern pattern = Pattern.compile("(\\b(Open|CLOSED)\\s(\\d{1}|\\d{2})\\s(\\d{1}|\\d{2})\\b)");
            Matcher matcher = pattern.matcher(line);
            boolean matchFound = matcher.find();

            if(matchFound){
                writer.println(matcher.group());
            }//end if
        }//end while

        writer.close(); //Closes the writer
        br.close(); //Closes the reader

    }//end classOpenExtractor

    public void classCounter(String inputFileName, String outputFileName) throws Exception{

        File file = new File(inputFileName); //Creates a File object from the passed in file
        BufferedReader br = new BufferedReader(new FileReader(file)); //Creates a reader for the File object

        String line; //This will represent each line in the file.

        PrintWriter writer = new PrintWriter(outputFileName); //Creates a writer for the designated output file.

        /**
         * As long as a new line exists in the file, then we search for a specified regex query in each line. If a match
         * is found, then we write the match to a file.
         */
        while((line = br.readLine()) != null){
            Pattern pattern = Pattern.compile("(^(?!Sub|for)(\\b\\w\\D{1}\\b|\\b\\w\\D{2}\\b|\\b\\w\\D{3}\\b|\\b\\w\\D{4}\\b|\\b\\w\\D{5}\\b|\\b\\w\\D{6}\\b)[-](.*?))");
            Matcher matcher = pattern.matcher(line);
            boolean matchFound = matcher.find();

            if(matchFound){

                writer.println(matcher.group());

            }//end if
        }//end while

        writer.close(); //Closes the writer
        br.close(); //Closes the reader

        File outputFile = new File(outputFileName); //Creates a new File for the newly created output file.
        BufferedReader br2 = new BufferedReader(new FileReader(outputFile)); //Creates a new reader for the newly created output file.

        Dictionary<String, Integer> classDict = new Hashtable<>(); //Creates a hash table to store each class value and the occurances.

        /**
         * While there exists a new line in the file, if the line is not included as a key in the hash table, then create the key
         * and set the key value to 1. If the line is already included as a key in the hash table, then add 1 to the already created value.
         */
        while((line = br2.readLine()) != null){

            if(classDict.get(line) != null){

                int num = classDict.get(line); //Returns the key value for the line.
                num = num + 1; //Adds one to the key value.
                classDict.put(line, num); //Replaces the current key value with the new key value.

            }//end if
            else{
                classDict.put(line, 1); //Adds the line to the hash table
            }

        }//end while

        br2.close(); //closes the writer

        PrintWriter writer2 = new PrintWriter(outputFileName); //Creates a new writer to replace the values
        writer2.println(classDict); //Prints the dictionary to the file

        writer2.close(); //Closes the writer
        
    }//end classCounter
    
    /**
     * Main method2
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        new ExtractText("2021FA_Class_Schedule_Daily.pdf", "2021FA_Class_Schedule_Daily.txt");
    
    }
}
