import java.io.*;

/**
 * Created by nikhilranjan on 11/1/16.
 */
public class SimCraps {
    public static void main(String[] args)throws FileNotFoundException {
        double wins = 0.0;             // number of pass bets won

        int trials = Integer.parseInt(args[0]);

        for (int t = 0; t < trials; t++) {//runs through loop to count amount of trials
            if (winsPassBet())//if win, then increment number of wins
                wins++;
        }

        System.out.println("Winning percentage =   " + (1.0 * wins) / (double)trials);
    }
    public static int uniform(int n) {

        return (int) (Math.random() * n);//gets random number
    }

    // return sum of two dice
    public static int sumOfTwoDice() {
        int x = 1 + uniform(6);//receives random number
        int y = 1 + uniform(6);//receives random number
        return x + y;//adds numbers
    }

    public static boolean winsPassBet() {
        int x = sumOfTwoDice();//gets
        if (x == 7 || x == 11)
            return true;
        if (x == 2 || x == 3 || x == 12)
            return false;

        int nextRoll = sumOfTwoDice();
        while (nextRoll != x && nextRoll != 7) {
            nextRoll = sumOfTwoDice();
        }
            if (nextRoll == 7)
                return false;
            else
                return true;
        }

}
