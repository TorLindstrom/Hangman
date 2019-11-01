package se.lexicon.tor;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {

    private static String[] words = {
            "Marauder", "Idianapolis", "Metropolis", "Cat", "Calculator",
            "Siobhan", "Smorgasbord", "Expedient", "Pumpkin", "Saturnus",
            "House", "Firetruck", "Washington", "Mastodon", "Godzilla",
            "Showstopper", "Method", "Jesus", "Metalhead", "Dramaqueen"};
    private static int numberOfWords = words.length;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    static void run() {
        while (true) { //keeps going for rounds

            System.out.println("I have come up with a word...\n");
            play(randomWord());

            askForContinue(); //if the user does not want to continue
        }
    }


    static void play(String theWord) {

        String upperCaseWord = theWord.toUpperCase(); //makes a more "compatible" word for usage, still prints out the normal word later
        int guessNumber = 0, correctNumber = 0; //what guess the user is on, and the index of the correct guesses array
        char[] correctGuesses = new char[theWord.length()]; //array with space for every correct letter as a guess
        char[] currentVisibleWord = makeInvisible(theWord); //the word is it is currently known, blank to begin with
        String[] guessedWords = new String[0]; //the guessed full words
        StringBuilder guesses = new StringBuilder(); //the guessed characters

        writeCurrentVisible(currentVisibleWord, guessNumber, false);

        while (guessNumber < 8) { //in here the user got 8 guesses to get the word right

            boolean valid;

            do {
                System.out.println("Do you want to: Guess a letter(1), or guess the word(2)?");

                valid = true;

                switch (sc.nextLine()) {
                    case "1": //single letter guess
                        char guessLetter = askForGuessLetter();

                        if (checkIfIn(guessLetter, upperCaseWord)) { //if the guess is in the word then

                            if (!checkIfIn(guessLetter, correctGuesses)) { //if the correct guess isn't registered then
                                correctGuesses[correctNumber] = guessLetter; //register
                                correctNumber++; //change the index amongst the correct guesses
                            }

                            currentVisibleWord = makeVisible(currentVisibleWord, guessLetter, upperCaseWord); //updates the currently visible word

                            if (checkIfFilled(currentVisibleWord)) { //check if all of the word is filled in
                                outcome(theWord, "Won"); //if it is then you win
                                return;
                            }

                        } else if (checkIfIn(guessLetter, guesses.toString())) { //if the letter hasn't already been incorrectly guessed
                            guessNumber++; //one more wrong guess
                            guesses.append(guessLetter); //then put it into wrong guesses
                        }
                        break;
                    case "2": //full word guess
                        String guessWord = askForGuessWord(theWord);
                        if (guessWord.equals(upperCaseWord)) { //win!!
                            outcome(theWord, "Won");
                            return;
                        } else if (!checkIfIn(guessWord, guessedWords)) {
                            guessedWords = add(guessedWords, guessWord);
                        }
                        guessNumber++;
                        break;
                    default: //incorrect input
                        System.out.println("\nYou didn't write valid input\n");
                        valid = false;
                }
            } while (!valid); //loop while it isn't valid input for the question
            writeCurrentVisible(currentVisibleWord, guessNumber, true);
        }
        outcome(theWord, "Lost");
    }


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

    static String askForGuessWord(String theWord) {
        while (true) {
            System.out.print("Make a word guess: ");
            String guess = sc.nextLine().toUpperCase();
            if (guess.length() != theWord.length() || !checkLettering(guess)) { //invalid input
                System.out.println("\nInvalid Guess, type in the full word as a guess\n");
            } else {
                return guess;
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

    static char askForGuessLetter() {
        while (true) {
            System.out.print("Make a letter guess: ");
            char guess = Character.toUpperCase(sc.nextLine().charAt(0));
            if (!Character.isAlphabetic(guess)) {
                System.out.println("\nInvalid Guess, type in a letter\n");
            } else {
                return guess;
            }
        }
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
