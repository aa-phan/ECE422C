package assignment2;

import java.util.ArrayList;
import java.util.Scanner;
public class Game {
//TODO: Design a Game.java class to handle top-level gameplay
//You may add whatever constructor or methods you like
public static ArrayList<String> guesses = new ArrayList<String>();

static String targetWord = "";

public static Dictionary setup(GameConfiguration config){
    Dictionary bank = null;
    if(config.wordLength == 4){
        bank = new Dictionary("4_letter_words.txt");
    }
    else if(config.wordLength == 5){
        bank = new Dictionary("5_letter_words.txt");
    }
    else if(config.wordLength == 6){
        bank = new Dictionary("6_letter_words.txt");
    }

    return bank;
}


public static void runGame(Scanner keyboard,GameConfiguration config){
    Dictionary bank = setup(config);
    targetWord = bank.getRandomWord();
    guesses.clear();
    targetWord = "sieve";        //testing purposes, for writing test outputs
    if(config.testMode){
        System.out.println(targetWord);
    }
    int guessNumber = config.numGuesses;
    String curGuess = "";
    while(guessNumber>0){
        System.out.println("Enter your guess:");
        curGuess = keyboard.nextLine();
        //case where input word is history
        if(curGuess.equals("[history]")){
            GuessHistory.previousGuesses();
        }
        else{
            if(curGuess.length() == config.wordLength){
                if(bank.containsWord(curGuess)){
                    String guessResult = "";            //correct length and in dictionary
                    guessResult+= CodeGenerator.compareGuess(curGuess, config);
                    System.out.println(guessResult);
                    if(!(guessResult.contains("_") || guessResult.contains("y"))){
                        System.out.println("Congratulations! You have guessed the word correctly.");
                        return;
                    }
                    guesses.add(curGuess + "->" + guessResult);
                    guessNumber--;
                    //terminate loop if out of guesses, or continue
                    if(guessNumber == 0){
                        System.out.println("You have run out of guesses.");
                        System.out.println("The correct word was \"" + targetWord + "\"." );
                    }
                    else{
                        System.out.println("You have " + guessNumber + " guess(es) remaining.");
                    }

                }
                else{
                    System.out.println("This word is not in the dictionary. Please try again.");
                }
            }
            else{
                System.out.println("This word has an incorrect word length. Please try again.");
            }
        }
    }
}



}
