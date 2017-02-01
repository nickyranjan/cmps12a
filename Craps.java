import java.util.*;

/**
 * Assignment #2
 * Craps.java
 *
 * This program creates a simple game of Craps.
 * The program runs through dice, and specific totals designate wins and losses
 * 7 and 11 are automatic wins. 2, 3, and 12 are automatic losses.
 * Bets are placed, when surpassed its a loss.
 *
 * Authors: Nikhil Ranjan (nranjan@ucsc.edu)
 *
 */
public class Craps {

    static final boolean WINGAME = true;
    static final boolean LOSEGAME = false;
    static int money;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the seed.");
        int seed = input.nextInt();
        Random random = new Random(seed);
        System.out.println("How many chips do you want");
        money = input.nextInt();
        // Create a random number generator with the specified seed

        int bet;
        bet = getBet(input, money); // Get the bet from the user
        while (bet > 0 && money > 0) { // while user has wish and means to continue
            boolean won = rollDice(random); // play one round

            if (won) { // if  won, add bet money
                money = money + bet;
                System.out.println("You won, you now have " + money);
            } else { // if lost, subtract bet money
                money = money - bet;
                System.out.println("You lost, you now have " + money);
            }
            if (money > 0) {
                bet = getBet(input, money); //check
            }
        }
    }
   /**
   * Returns the bet by the user. Double checks input checking to ensure
   * that the user does not bet more than she has and she does not bet
   * a negative value.
   *
   * @param input, int for the bet
   * @param total, money inputted, chips
   * @return bet, send bet back up
   */
    static int getBet(Scanner input, int total) {
        System.out.println("Enter bet. Hit return to roll the dice");
        int bet = input.nextInt();
        while (bet < 0 || bet > total) {
            System.out.println("Not an ok bet.");
            System.out.println("Enter bet.");
            bet = input.nextInt();
        }
        return bet;
    }

    /**
    * This method calls the die method to get the total produced from the dice
    * Checks all possibilities and decides if the roll has won or lost
    * If certain numbers are produced, return won or lose booleans
    *
    * @param random, random number is the point created from the dice
    * @return WINGAME
    * @return LOSEGAME
    * @return winLose
    */
    static boolean rollDice(Random random) {
        int point = getDice(random);
        if (point == 7 || point == 11) {
            return WINGAME;
        }
        else if(point == 2 || point == 3 || point == 12){
            return LOSEGAME;
        }else
            System.out.println("The point is "+ point);

        // roll until point or 7
        int nextRoll = getDice(random);

        while (nextRoll != point && nextRoll != 7) {
            nextRoll = getDice(random);
        }
        if (nextRoll == 7)
            return LOSEGAME;
        else
           return WINGAME;
    }

  /**
   * Simulates rolling of dice by generating random numbers from a random number generator.
   * Uses random next int for both dice
   * Adds the totals and returns that number
   *
   * @param random, random class
   * @return total from both dice
   */
    static int getDice(Random random) {
        int die1 = random.nextInt(6) + 1;//getting values of each dice
        int die2 = random.nextInt(6) + 1;
        System.out.println("roll is " + die1 + ", " + die2);
        return die1 + die2;
    }

}
