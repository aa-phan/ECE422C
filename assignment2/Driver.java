package assignment2;

import java.util.Scanner;

public class Driver {
    public void start(GameConfiguration config) {
        // TODO: complete this method
        // We will call this method from our JUnit test cases.
        Scanner keyboard = new Scanner(System.in);
        Game newGame = new Game();
        boolean gameStateFlag = true;
        System.out.println("Hello! Welcome to Wordle.");
        while(gameStateFlag) {
            System.out.println("Do you want to play a new game? (y/n)");
            String userInput = keyboard.nextLine();
            if(userInput.equals("n")){
                gameStateFlag = false;
            }
            else if(userInput.equals("y")){
                newGame.runGame(keyboard, config);
            }
        }
    }

    public void start_hardmode(GameConfiguration config) {
        // TODO: complete this method for extra credit
        // We will call this method from our JUnit test cases.
    }

    public static void main(String[] args) {
        // Use this for your testing.  We will not be calling this method.
        Driver test = new Driver();
        GameConfiguration testC = new GameConfiguration(6,2,true);
        test.start(testC);
    }
}
