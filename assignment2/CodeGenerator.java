package assignment2;

import java.math.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class CodeGenerator {

    public static void correctPos(char[] guessArr, char[] targetArr, char[] guessSeq, int len){
        for(int i = 0; i<len; i++){
            for(int j = 0; j<len; j++){
                if(guessArr[i] == targetArr[j] && i == j){
                    guessSeq[i] = 'G';
                    targetArr[j] = '_';
                }
            }
        }
    }
    public static void correctChar(char[] guessArr, char[] targetArr, char[] guessSeq, int len) {
        for (int i = 0; i < len; i++) {
            if (guessSeq[i] != 'G') {
                for (int j = 0; j < len; j++) {
                    if (guessArr[i] == targetArr[j]) {
                        guessSeq[i] = 'Y';
                        targetArr[j] = '_';
                    }
                }
            }
        if(guessSeq[i]!='Y' && guessSeq[i]!= 'G') guessSeq[i] = '_';
        }
    }
    public static String compareGuess(String x, GameConfiguration config){
        char[] guessSeq = new char[config.wordLength];
        char[] guessArr = x.toCharArray();
        char[] targetArr = Game.targetWord.toCharArray();
        //first loop to detect position matches and char matches
        correctPos(guessArr, targetArr, guessSeq, config.wordLength);
        //second loop to detect char matches
        correctChar(guessArr, targetArr, guessSeq, config.wordLength);

        return new String(guessSeq);
    }
}
