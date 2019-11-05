package se.lexicon.tor;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AppTest {

    /*@Test
    public void testCheckIfInRight(){
        //arrange

        //act

        //assert
    }*/


    //checks the makeVisible method
    @Test
    public void testVisibleUpdateRight() {
        //arrange
        String theWord = "MASTODON";
        char letter = 'M';
        char[] toBeUpdated = {'_', '_', '_', '_', '_', '_', '_', '_'};
        char[] expected = {'M', '_', '_', '_', '_', '_', '_', '_'};

        //act
        App.makeVisible(toBeUpdated, letter, theWord);

        //assert
        assertArrayEquals(toBeUpdated, expected);
    }

    @Test
    public void testVisibleUpdateWrong() {
        //arrange
        String theWord = "MASTODON";
        char letter = 'Q';
        char[] toBeUpdated = {'_', '_', '_', '_', '_', '_', '_', '_'};
        char[] expected = {'_', '_', '_', '_', '_', '_', '_', '_'};

        //act
        App.makeVisible(toBeUpdated, letter, theWord);

        //assert
        assertArrayEquals(toBeUpdated, expected);
    }

    @Test
    public void testVisibleUpdateRightMany() {
        //arrange
        String theWord = "MASTODON";
        char letter1 = 'M', letter2 = 'A', letter3 = 'O';
        char[] toBeUpdated = {'_', '_', '_', '_', '_', '_', '_', '_'};
        char[] expected = {'M', 'A', '_', '_', 'O', '_', 'O', '_'};

        //act
        App.makeVisible(toBeUpdated, letter1, theWord);
        App.makeVisible(toBeUpdated, letter2, theWord);
        App.makeVisible(toBeUpdated, letter3, theWord);

        //assert
        assertArrayEquals(toBeUpdated, expected);
    }


    //checks the makeInvisible method
    @Test
    public void testMakeInvisibleRight(){
        //arrange
        String word = "CAT";
        char[] expected = {'_','_','_'};

        //act
        char[] result = App.makeInvisible(word);

        //assert
        assertArrayEquals(expected, result);
    }

    @Test
    public void testMakeInvisibleRightLong(){
        //arrange
        String word = "CATASTROPHY";
        char[] expected = {'_','_','_','_','_','_','_','_','_','_','_'};

        //act
        char[] result = App.makeInvisible(word);

        //assert
        assertArrayEquals(expected, result);
    }


    //check pad method
    @Test
    public void testPad(){
        //arrange
        char[] toBePadded = {'C','_','_',};
        String expected = "C _ _ ";

        //act
        String result = App.pad(toBePadded);

        //assert
        assertEquals(expected, result);
    }

    @Test
    public void testPadLong(){
        //arrange
        char[] toBePadded = {'_','A','_','_','_','_','N',};
        String expected = "_ A _ _ _ _ N ";

        //act
        String result = App.pad(toBePadded);

        //assert
        assertEquals(expected, result);
    }


    //check add method
    @Test
    public void testAdd(){
        //arrange
        String[] array = {"GODZILLA", "JESUS"};
        String[] expected = {"GODZILLA", "JESUS", "CAT"};
        String word = "CAT";

        //act
        String[] result = App.add(array, word);

        //assert
        assertArrayEquals(result,expected);
    }


    //check input validation
    @Test
    public void testLetteringRight(){
        //arrange
        String input = "HELLO";

        //act
        boolean result = App.checkLettering(input);

        //assert
        assertTrue(result);
    }

    @Test
    public void testLetteringWrong(){
        //arrange
        String input = "H3LL0";

        //act
        boolean result = App.checkLettering(input);

        //assert
        assertFalse(result);
    }


    //checks the checkIfIn method
    @Test
    public void testCheckIfInRight() {
        //arrange
        char letter = 'C';
        String word = "CAT";

        //act
        boolean result = App.checkIfIn(letter, word);

        //assert
        assertTrue(result);
    }

    @Test
    public void testCheckIfInWrong(){
        //arrange
        char letter = 'Q';
        String word = "CAT";

        //act
        boolean result = App.checkIfIn(letter, word);

        //assert
        assertFalse(result);
    }

    @Test
    public void testCheckIfInWordRight(){
        //arrange
        String[] array = {"GODZILLA", "JESUS", "CAT"};
        String word = "CAT";

        //act
        boolean result = App.checkIfIn(word, array);

        //assert
        assertTrue(result);
    }

    @Test
    public void testCheckIfInWordWrong(){
        //arrange
        String[] array = {"GODZILLA", "JESUS", "CAT"};
        String word = "METROPOLIS";

        //act
        boolean result = App.checkIfIn(word, array);

        //assert
        assertFalse(result);
    }


    //checks the checkIfFilled method
    @Test
    public void testCheckIfFilledRight(){
        //arrange
        char[] array = {'C','A','T'};

        //act
        boolean result = App.checkIfFilled(array);

        //assert
        assertTrue(result);
    }

    @Test
    public void testCheckIfFilledWrong(){
        //arrange
        char[] array = {'_','A','T'};

        //act
        boolean result = App.checkIfFilled(array);

        //assert
        assertFalse(result);
    }


    //checks the check if right method
    @Test
    public void testCheckIfRightRight(){
        //arrange
        String guess = "CAT";
        String answer = "CAT";

        //act
        boolean result = App.checkIfRight(guess,answer);

        //assert
        assertTrue(result);
    }

    @Test
    public void testCheckIfRightWrong(){
        //arrange
        String guess = "HAT";
        String answer = "CAT";

        //act
        boolean result = App.checkIfRight(guess,answer);

        //assert
        assertFalse(result);
    }
}
