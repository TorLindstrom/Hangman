package se.lexicon.tor;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {

    private static String[] words = {
            "Marauder", "Indianapolis", "Metropolis", "Cat", "Calculator",
            "Siobhan", "Smorgasbord", "Expedient", "Pumpkin", "Saturnus",
            "House", "Firetruck", "Washington", "Mastodon", "Godzilla",
            "Showstopper", "Method", "Jesus", "Metalhead", "Dramaqueen"};
    private static int numberOfWords = words.length;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        runMenu();
    }

    static void runMenu() {
        while (true) { //keeps going for rounds

            System.out.println("I have come up with a word...\n");
            playGame(randomWord());

            askForContinue(); //if the user does not want to continue
        }
    }

    static void playGame(String theWord) {

        String upperCaseWord = theWord.toUpperCase(); //makes a more "compatible" word for usage, still prints out the normal word later
        int guessNumber = 0, correctNumber = 0; //what guess the user is on, and the index of the correct guesses array
        char[] currentVisibleWord = makeInvisible(theWord); //the word is it is currently known, blank to begin with
        String[] guessedWords = new String[0]; //the guessed full words
        StringBuilder guesses = new StringBuilder(); //the guessed characters

        writeCurrentVisible(currentVisibleWord, guessNumber, false);

        while (guessNumber < 8) { //in here the user got 8 guesses to get the word right

            boolean valid; //puts valid variable outside the do scope

            do {

                String guess = getGuess();
                int sizeOfGuess = guess.length();

                valid = true;

                if (sizeOfGuess == 1) { //single letter guess

                    char guessLetter = guess.charAt(0); //askForGuessLetter();

                    if (checkIfIn(guessLetter, upperCaseWord)) { //if the guessed letter is in the word then

                        currentVisibleWord = makeVisible(currentVisibleWord, guessLetter, upperCaseWord); //updates the currently visible word

                        if (checkIfFilled(currentVisibleWord)) { //check if all of the word is filled in
                            outcome(theWord, "Won"); //if it is then it's a win
                            return; //exits the round
                        }

                    } else if (!checkIfIn(guessLetter, guesses.toString())) { //if the letter is wrong and hasn't already been incorrectly guessed
                        guessNumber++; //one more wrong guess
                        guesses.append(guessLetter); //then put it into wrong guesses
                    }

                } else if (sizeOfGuess == theWord.length()) { //full word guess

                    if (guess.equals(upperCaseWord)) { //win!! if the word is the correct one
                        outcome(theWord, "Won");
                        return; //exits the round
                    }

                    if (!checkIfIn(guess, guessedWords)) { //if the guessed word isn't in the guessed words array then count it as an incorrect guess
                        guessNumber++; //increase the guessed number
                        guessedWords = add(guessedWords, guess); //add the guessed word into the wrong guesses so it doesn't raise the wrong guesses number next time
                    }

                } else { //incorrect input
                    System.out.println("\nRemember to count how many letters there are in the word\n");
                    valid = false; //loops the input and switch
                }

            } while (!valid); //loop while it isn't valid input for the question
            writeCurrentVisible(currentVisibleWord, guessNumber, true); //show the word as the user knows it
        } //out of attempts loop
        outcome(theWord, "Lost"); //used up all the guesses, loss
    } //end of play


    static char[] makeVisible(char[] array, char element, String upperCaseWord) {
        for (int i = 0; i < array.length; i++) { //makes every letter that was correctly guessed visible
            if (upperCaseWord.charAt(i) == element) { //goes through the word
                array[i] = element; //make the letter visible in the currently visible word
            }
        }
        return array;
    }

    static String pad(char[] array) {
        StringBuilder paddington = new StringBuilder();
        for (char letter : array) {
            paddington.append(letter);
            paddington.append(' ');
        }
        return paddington.toString();
    }

    static String[] add(String[] array, String word) {
        int oldLength = array.length;
        array = Arrays.copyOf(array, array.length + 1);
        array[oldLength] = word;
        return array;
    }

    static void writeCurrentVisible(char[] currentVisibleWord, int guessNumber, boolean showGuess) {
        StringBuilder maker = new StringBuilder();
        maker.append("\n---Current word---\n");
        maker.append(pad(currentVisibleWord));
        if (showGuess) {
            maker.append("\n\n---Current wrong guesses: ");
            maker.append(guessNumber);
            maker.append("/8---\n");
        }
        System.out.println(maker.toString());
    }

    static void outcome(String theWord, String what) {
        System.out.println("---You " + what + "!!---\nThe word was: " + theWord + "\n");
        return;
    }

    static boolean checkIfFilled(char[] array) {
        for (char dash : array) {
            if (dash == '_') {
                return false;
            }
        }
        return true;
    }

    static char[] makeInvisible(String theWord) {
        char[] invisible = new char[theWord.length()];
        Arrays.fill(invisible, '_');
        return invisible;
    }

    static boolean checkIfIn(char letter, char[] array) {
        for (char correctChars : array) { //checks if the guessed letter is already in the correctGuesses
            if (correctChars == letter) { //if the letter isn't in the correct guesses array then
                return true;
            }
        }
        return false; //else
    }

    static boolean checkIfIn(char letter, String upperCaseWord) {
        for (char correctChars : upperCaseWord.toCharArray()) { //checks if the guessed letter is already in the correctGuesses
            if (correctChars == letter) { //if the letter isn't in the correct guesses array then
                return true;
            }
        }
        return false; //else
    }

    static boolean checkIfIn(String word, String[] array) {
        for (String guessedWords : array) { //checks if the guessed letter is already in the correctGuesses
            if (guessedWords.equals(word)) { //if the letter isn't in the correct guesses array then
                return true;
            }
        }
        return false; //else
    }

    static String getGuess(){

        while (true) {
            System.out.print("Guess either a single letter or the full word: ");
            String word = sc.nextLine().trim().toUpperCase();
            if (checkLettering(word)) {
                return word;
            } else {
                System.out.println("\nYou can only write alphabetical characters\n");
            }
        }
    }

    static boolean checkLettering(String guess) {
        for (int i = 0; i < guess.length(); i++) {
            if (!Character.isAlphabetic(guess.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static void askForContinue() {
        while (true) {
            System.out.print("Want to go again? y/n: ");
            switch (sc.nextLine().toLowerCase()) {
                case "y":
                    return;
                case "n":
                    System.out.println("Good fun playing against you, byebye");
                    System.exit(0);
                default:
                    System.out.println("\nPlease answer the question\n");
            }
        }
    }

    static String randomWord() {
        return words[randomNumber()];
    }

    static int randomNumber() {
        Random numberMaker = new Random();
        int theNumber = numberMaker.nextInt(numberOfWords);

        return theNumber;
    }

}
