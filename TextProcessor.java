import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Scanner;

public class TextProcessor {

    public TextProcessor(String fileName, String pattern) throws Exception{

        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        Pattern newPattern = Pattern.compile(pattern);
        int occurances = 0;

        while ((line = br.readLine()) != null){

            Matcher matcher = newPattern.matcher(line);
            boolean matchFound = matcher.find();

            if(matchFound){
                System.out.println(matcher.group());
                occurances = occurances + 1;
            }else{
                continue;
            }

        }
        System.out.printf("Number of occurances: %d", occurances);
    }

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
