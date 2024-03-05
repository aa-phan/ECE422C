package assignment3;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {


    //STATIC VARIABLES
    static String start; //start word
    static String end; //end wordc
    static Set<String> dictionary;
    static Set<String> checkedSet = new HashSet<>(); //used in helper function for DFS
    static Set<String> visitedDFS = new HashSet<>(); //used in helper function for DFS





    public static void main(String[] args) throws Exception {

        Scanner kb;	// input Scanner for commands
        PrintStream ps;	// output file, for student testing and grading only
        // If arguments are specified, read/write from/to files instead of Std IO.
        if (args.length != 0) {
            kb = new Scanner(new File(args[0]));
            ps = new PrintStream(new File(args[1]));
            System.setOut(ps);			// redirect output to ps
        } else {
            kb = new Scanner(System.in);// default input from Stdin
            ps = System.out;			// default output to Stdout
        }
        initialize();


        //create target array parsed from user input, generate ladders with BFS and DFS
        ArrayList<String> targetWords = parse(kb);
        ArrayList<String> ladderBFS = getWordLadderBFS(targetWords.get(0), targetWords.get(1));
        if(ladderBFS == null) return; else printLadder(ladderBFS);


        targetWords = parse(kb);
        start = targetWords.get(0);
        end = targetWords.get(1);
        ArrayList<String> ladderDFS = getWordLadderDFS(start, end);
        if(ladderDFS == null) return; else printLadder(ladderDFS);

        // TODO methods to read in words, output ladder

    }

    public static void initialize() {
        // initialize your static variables or constants here.
        // We will call this method before running our JUNIT tests.  So call it
        // only once at the start of main.
        dictionary = makeDictionary();
    }

    /**
     * @param keyboard Scanner connected to System.in
     * @return ArrayList of Strings containing start word and end word.
     * If command is /quit, return empty ArrayList.
     */

    //Done//
    public static ArrayList<String> parse(Scanner keyboard) {
        // TO DO
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> userInput = new ArrayList<>();
        String start = scanner.next();
        if(start.equals("/quit")){
            userInput.add(start);
            userInput.add("test");
            return userInput;
        }
        else{
            userInput.add(start.toUpperCase());
            String end = scanner.next();
            userInput.add(end.toUpperCase());
        }

        return userInput;
    }


    public static ArrayList<String> getWordLadderDFS(String start, String end){

        // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.
        // TODO some code

        if(start.equals("/quit")){
            return null;
        }
        //keep track of visited nodes
        checkedSet.clear(); //empty it first
        checkedSet.add(start);

        start = start;
        end = end.toUpperCase(); //convert to upper case

        Node test = DFSHelper(new Node(null, start), end);
        ArrayList<String> input = new ArrayList<>();

        //ladder is empty
        if(test == null){
            return null;
        }
        else {
            while (test.getParent() != null) {
                input.add(0, test.getValue()); //add the start word
                test = test.getParent();
            }
            input.add(0, start);
            return input; //returns the ladder
        }
    }

    public static Node DFSHelper(Node start, String end){
        //keep track of visited nodes --> checked set
        checkedSet.add(start.getValue());

        //do we need a null check here?
        if(start.getValue().equals(end)){
            return start;
        }
        //input has all the children nodes of start
        ArrayList<Node> input = new ArrayList<>(findNeighbors(start));

        //iterate through children
        for (Node node : input){
            Node newNode = DFSHelper(node, end); //recursion --> add current node to set of checked ones
            //current node != end word
            if(!visitedDFS.contains(node.getValue())) //gets child of a node
                if(newNode != null)
                    return newNode; //if child leads to end word
        }
        return null;
    }

    //make it handle lowercase
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
        if(start.equals("/quit")){
            return null;
        }

        //generate graph where each key is an adjacency list
        ArrayList<String> wordLadder = new ArrayList<>();
        Map<String, List<String>> graph = constructGraph(dictionary);
        //set determining visited words
        Set<String> visited = new HashSet<>();
        //queue returning words to visit, find their adjacency lists, and determine a path
        Queue<String> toVisit = new LinkedList<>();
        //add starting word to both
        toVisit.offer(start);
        visited.add(start);


        Map<String, String> previous = new HashMap<>();


        while(!toVisit.isEmpty()){
            String current = toVisit.poll();
            if(current.equals(end)){
                wordLadder.add(end);
                while(!wordLadder.get(wordLadder.size()-1).equals(start)){
                    wordLadder.add(previous.get(wordLadder.get(wordLadder.size()-1)));
                }
                Collections.reverse(wordLadder);
                return wordLadder;
            }
            for(String neighbor: graph.get(current)){
                if(!visited.contains(neighbor)){
                    toVisit.offer(neighbor);
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
        }
        return null;

    }


    public static void printLadder(ArrayList<String> ladder) {
        if(ladder == null || ladder.size() == 1){
            //not working?
            //System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");
            System.out.println("no word ladder can be found between " + start + " and " + end + ".");
            return;
        }

        System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");

        for (String element : ladder) {
            System.out.println(element);
        }


    }





    // TODO
    // Other private static methods here

    /* Do not modify makeDictionary */
    public static Set<String>  makeDictionary () {
        Set<String> words = new HashSet<String>();
        Scanner infile = null;
        try {
            infile = new Scanner (new File("five_letter_words.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while (infile.hasNext()) {
            words.add(infile.next().toUpperCase());
        }
        return words;
    }

    //HELPER FUNCTIONS///
    private static Map<String, List<String>> constructGraph(Set<String> wordSet) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String word : wordSet) {
            graph.put(word, new ArrayList<>());
            for (String otherWord : wordSet) {
                if (!word.equals(otherWord) && isOneCharApart(word, otherWord)) {
                    graph.get(word).add(otherWord);
                }
            }
        }
        return graph;
    }


    private static boolean isOneCharApart(String word1, String word2) {
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
                if (diffCount > 1) {
                    return false;
                }
            }
        }
        return diffCount == 1;
    }

    //generate children nodes of a given node
    public static ArrayList<Node> findNeighbors(Node input){
        ArrayList<Node> neighbors = new ArrayList<Node>(); //has all the children nodes of the given input
        String x = input.getValue();
        String y = x.toUpperCase(); //gets upper case

        int letters = 0;

        //iterate ouver the input (char by char)
        for(int i = 0; i < y.length(); i++){
            //compare each letter
            for(char j = 'A'; j <= 'Z'; j++){
                //constructs a new word --> replaces each character by each letter
                String check = y.substring(0,i) + Character.toString(j) + y.substring(i+1);
                //number of same letters in the input and the constructed word
                int same = 0;

                //compare the words --> prioritize words that have more common letters
                for(int k = 0; k < y.length(); k++){
                    if(end.substring(k, k+1).equalsIgnoreCase(check.substring(k, k+1))){
                        same++;
                    }
                }

                //if word in dictionary and not checked --> create a new node and add to array
                if(dictionary.contains(check) && !checkedSet.contains(check.toUpperCase())){
                    if(same > letters){
                        //prioritized list
                        neighbors.add(0, new Node(input, check.toUpperCase()));
                        letters = same;
                    }
                    else {
                        neighbors.add(new Node(input, check.toUpperCase()));
                    }
                }
            }
        }
        return neighbors;
    }

    //NODE CLASS
    public static class Node{
        Node previous;
        String value;

        public Node(Node prev, String v) {
            previous = prev;
            value = v;
        }

        public Node getParent() {
            return previous;
        }

        public String getValue(){
            return value;
        }
    }

}