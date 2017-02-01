import java.util.*;
import java.io.*;
/**
 * Assignment #2
 * Created by Nikhil Ranjan on 10/10/16.
 * This program reads a text file consisting of regular dictionary words.
 * The programs reads this text file and stores and saves the entire list.
 * This program uses an algorithm where letters are assigned values and added up and added up as a total
 * I used the same algorithm throughout the wordtext file, and found each value of the words. I then
 * took the value of the user word and compared it throughout the list. Same values would be printed
 * out to the user
 *
 * Authors: Nikhil Ranjan (nranjan@ucsc.edu)
 *
 * RESOURCES USED:
 * https://classes.soe.ucsc.edu/cmps012a/Fall16/
 * Dean Bailey 10/19/16)
 */

/**
 * class and main method
 * main method creates an instance of a class
 * throws FileNotFoundException
 */
public class FindAnagrams {
    private int[] letterValues = new int[26];//global arraylist to hold values for lettersv in alphabet
    private ArrayList<Integer> anagramValue = new ArrayList<Integer>();//global arraylist to hold values of wordlist words
    private ArrayList<String> list = new ArrayList<String>();// global to hold wordlist words
    String anagramWord = "";//global string to hold user word

    public static void main(String[] args) throws FileNotFoundException{
        FindAnagrams apg = new FindAnagrams();
        apg.run(args[0]);//call main method
    }

    /**
     * run method
     * throws FileNotFoundException
     * Scanner to read the word text file, arraylist to store the words reads
     * calls 4 different methods, alphabetical assign, prompt user, creates values for
     * each of the words in word list
     * finds value of user word, compares value, ask again to loop for more words
     * close scanner
     *
     * @param filename inputted in command line
     */
    public void run(String filename)throws FileNotFoundException{

        Scanner s = new Scanner(new FileInputStream(filename));
        s.nextLine();
        while (s.hasNext()) {
            list.add(s.nextLine());
        }

        assignRandom();
        anagramWord = promptUser();//returned user word
        dictionaryValue(list);//pass wordlist arraylist
        assignLetters(anagramWord);//compares user word to wordlist

       s.close();
    }

    /**
     * assignRandom
     * Random class
     * accesses global arraylist
     * 0-26, fills each index with a random value to represent letters of alphabets
     * a->index 0, b-> index2, c-> index3
     */
    public void assignRandom() {
        Random rnd = new Random();

        for (int i = 0; i < letterValues.length; i++)//runs through array, places random letters
            letterValues[i] = rnd.nextInt(10000000);//each index will have a random number

    }

    /**
     * dictionaryValue
     * for loop to run through the wordlist file, set variable equal to selected word
     * loop through the word, index by index, adding total to each other
     * that value is then stored in an arraylist to later be accessed for comparison
     *
     * @param list, arraylist, holding words in word text file
     */
    public void dictionaryValue(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {//run through the wordlist
            String temp = list.get(i);// variable for string
            int randomValue = 0;
            for (int x = 0; x < temp.length(); x++) {//runs through specific word
                char c = temp.toLowerCase().charAt(x);
                int tempIndex = (int) (c) - 97;//gets letter value
                randomValue += letterValues[tempIndex];//sum of all letters
            }
            anagramValue.add(randomValue);//adds total value to arraylist
        }
    }

    /**
     * promptUser
     * scanner created
     * gets word inputted from the user
     *
     * @return input, the word inputted
     */
    public String promptUser(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nType a string of letters: ");
        String input = in.nextLine();

        return input;
    }

    /**
     * assignLetters
     * for loop to loop through the inputted word
     * add uo letter values
     * 2nd part of the method is to loop through arraylist with all wordlist values
     * compare values and then print words that share same values
     * calls method to ask user again
     *
     * @param anagramWord
     */
    public void assignLetters(String anagramWord) {
        int wordValue = 0;

        for (int i = 0; i < anagramWord.length(); i++){//loops through user word
            char c = anagramWord.charAt(i);//selects letters
            int value = (int)c - 97;//value of that letter
            wordValue += letterValues[value];//adds to total
        }

        for(int i = 0; i < anagramValue.size(); i++) {//loops through arraylist with values of wordlist words
            if (anagramValue.get(i).equals(wordValue)) {//compares values
                if(!list.get(i).equals(anagramWord)) {//if statement to not print same word again
                    System.out.println(list.get(i));//print
                }
            }
        }
        askAgain();

    }

    /**
     * askAgain
     * create new scanner
     * ask user if he/she wants to continue with more anagrams
     * y for yes
     * n for no
     * calls assignletter() when y is entered, and inputted word is passed back up
     */
    public void askAgain(){
        Scanner answer = new Scanner(System.in);
        System.out.print("\nDo another (y/n) ");//ask user
        String loop = answer.nextLine();
        if(loop.equals("y")) {
            System.out.print("\nType a string of letters ");
            String input = answer.nextLine();
            assignLetters(input);//calls assign letters to check new input
        }
        else if(loop.equals("n")) {
            System.exit(100);//exits program when 'n' is entered
            System.out.print("\n");
        }
    }

}
