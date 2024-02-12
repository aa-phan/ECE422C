package assignment2;

public class GuessHistory {
    public static void previousGuesses(){
        for(String x: Game.guesses){
            System.out.println(x);
        }
        System.out.println("--------");
    }
}
