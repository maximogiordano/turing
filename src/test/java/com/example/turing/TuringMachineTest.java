package com.example.turing;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for TuringMachine class.
 */
public class TuringMachineTest {
    @Test
    public void testAddStateWithNullName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState(null, false);

            fail();
        } catch (NullPointerException e) {
            assertEquals("the state name is null", e.getMessage());
        }
    }

    @Test
    public void testAddStateWithExistingName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addState("0", false);

            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("the state name already exists", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithNullStateFromName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition(null, '0', '0', Action.HALT, "0");

            fail();
        } catch (NullPointerException e) {
            assertEquals("the source state name is null", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithNullStateToName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition("0", '0', '0', Action.HALT, null);

            fail();
        } catch (NullPointerException e) {
            assertEquals("the destination state name is null", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithNullAction() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition("0", '0', '0', null, "0");

            fail();
        } catch (NullPointerException e) {
            assertEquals("the action to be taken is null", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithInvalidStateFromName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition("1", '0', '0', Action.HALT, "0");

            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("the source state name does not exist", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithInvalidStateToName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition("0", '0', '0', Action.HALT, "1");

            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("the destination state name does not exist", e.getMessage());
        }
    }

    @Test
    public void testAddTransitionWithExistingTransition() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.addState("0", false);
            turingMachine.addTransition("0", '0', '0', Action.HALT, "0");
            turingMachine.addTransition("0", '0', '0', Action.HALT, "0");

            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("the transition already exists", e.getMessage());
        }
    }

    @Test
    public void testSetInitialStateWithNullName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.setInitialState(null);

            fail();
        } catch (NullPointerException e) {
            assertEquals("the initial state name is null", e.getMessage());
        }
    }

    @Test
    public void testSetInitialStateNameWithInvalidName() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.setInitialState("0");

            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("the initial state name does not exist", e.getMessage());
        }
    }

    @Test
    public void testExecuteWithNullContent() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.execute(null, 0, 0, '\0');

            fail();
        } catch (NullPointerException e) {
            assertEquals("the initial content is null", e.getMessage());
        }
    }

    @Test
    public void testExecuteWithoutInitialState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.execute("", 0, 0, '\0');

            fail();
        } catch (IllegalStateException e) {
            assertEquals("the initial state is not set", e.getMessage());
        }
    }

    @Test
    public void testIsAcceptedInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.isAccepted();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testGetCurrentStateNameInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.getCurrentStateName();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testGetContentInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.getContent();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testGetContentOffsetInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.getContentOffset();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testGetHeadPositionInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.getHeadPosition();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testGetBlankInIllegalState() {
        try {
            TuringMachine turingMachine = new TuringMachine();

            turingMachine.getBlank();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("this machine has not already been executed", e.getMessage());
        }
    }

    @Test
    public void testMultiplyByTwo() {
        final int FIRST = 0;
        final int LAST = 1000000;

        TuringMachine turingMachine = getMultiplyByTwoTuringMachine();

        for (int i = FIRST; i <= LAST; i++) {
            testMultiplyByTwo(turingMachine, i);
        }
    }

    private TuringMachine getMultiplyByTwoTuringMachine() {
        TuringMachine turingMachine = new TuringMachine();

        turingMachine.addState("0", false);
        turingMachine.addState("1", false);
        turingMachine.addState("2", false);
        turingMachine.addState("H", true);

        turingMachine.addTransition("0", '0', '0', Action.LEFT, "0");
        turingMachine.addTransition("0", '1', '2', Action.LEFT, "0");
        turingMachine.addTransition("0", '2', '4', Action.LEFT, "0");
        turingMachine.addTransition("0", '3', '6', Action.LEFT, "0");
        turingMachine.addTransition("0", '4', '8', Action.LEFT, "0");
        turingMachine.addTransition("0", '5', '0', Action.LEFT, "1");
        turingMachine.addTransition("0", '6', '2', Action.LEFT, "1");
        turingMachine.addTransition("0", '7', '4', Action.LEFT, "1");
        turingMachine.addTransition("0", '8', '6', Action.LEFT, "1");
        turingMachine.addTransition("0", '9', '8', Action.LEFT, "1");
        turingMachine.addTransition("0", '\0', '\0', Action.HALT, "H");
        turingMachine.addTransition("1", '0', '1', Action.LEFT, "0");
        turingMachine.addTransition("1", '1', '3', Action.LEFT, "0");
        turingMachine.addTransition("1", '2', '5', Action.LEFT, "0");
        turingMachine.addTransition("1", '3', '7', Action.LEFT, "0");
        turingMachine.addTransition("1", '4', '9', Action.LEFT, "0");
        turingMachine.addTransition("1", '5', '1', Action.LEFT, "1");
        turingMachine.addTransition("1", '6', '3', Action.LEFT, "1");
        turingMachine.addTransition("1", '7', '5', Action.LEFT, "1");
        turingMachine.addTransition("1", '8', '7', Action.LEFT, "1");
        turingMachine.addTransition("1", '9', '9', Action.LEFT, "1");
        turingMachine.addTransition("1", '\0', '1', Action.HALT, "H");

        turingMachine.setInitialState("0");

        return turingMachine;
    }

    private void testMultiplyByTwo(TuringMachine turingMachine, int n) {
        String initialContent = String.valueOf(n);
        String expectedContent = String.valueOf(2 * n);

        turingMachine.execute(initialContent, 0, initialContent.length() - 1, '\0');

        assertEquals(true, turingMachine.isAccepted());
        assertEquals("H", turingMachine.getCurrentStateName());
        assertEquals(expectedContent, turingMachine.getContent());
        assertEquals(initialContent.length() - expectedContent.length(), turingMachine.getContentOffset());
        assertEquals(-1, turingMachine.getHeadPosition());
        assertEquals('\0', turingMachine.getBlank());
    }

    @Test
    public void testABCTuringMachineForEveryString() {
        final int MAX_LENGTH = 12;

        TuringMachine turingMachine = getABCTuringMachine();

        for (String s : getStrings(Arrays.asList('a', 'b', 'c'), MAX_LENGTH)) {
            testABCTuringMachine(turingMachine, s);
        }
    }

    private TuringMachine getABCTuringMachine() {
        TuringMachine turingMachine = new TuringMachine();

        turingMachine.addState("0", false);
        turingMachine.addState("1", false);
        turingMachine.addState("2", false);
        turingMachine.addState("3", false);
        turingMachine.addState("4", false);
        turingMachine.addState("5", true);

        turingMachine.addTransition("0", 'a', 'A', Action.RIGHT, "1");
        turingMachine.addTransition("1", 'a', 'a', Action.RIGHT, "1");
        turingMachine.addTransition("1", 'B', 'B', Action.RIGHT, "1");
        turingMachine.addTransition("1", 'b', 'B', Action.RIGHT, "2");
        turingMachine.addTransition("2", 'b', 'b', Action.RIGHT, "2");
        turingMachine.addTransition("2", 'C', 'C', Action.RIGHT, "2");
        turingMachine.addTransition("2", 'c', 'C', Action.LEFT, "3");
        turingMachine.addTransition("3", 'C', 'C', Action.LEFT, "3");
        turingMachine.addTransition("3", 'b', 'b', Action.LEFT, "3");
        turingMachine.addTransition("3", 'B', 'B', Action.LEFT, "3");
        turingMachine.addTransition("3", 'a', 'a', Action.LEFT, "3");
        turingMachine.addTransition("3", 'A', 'A', Action.RIGHT, "0");
        turingMachine.addTransition("0", 'B', 'B', Action.RIGHT, "4");
        turingMachine.addTransition("4", 'B', 'B', Action.RIGHT, "4");
        turingMachine.addTransition("4", 'C', 'C', Action.RIGHT, "4");
        turingMachine.addTransition("4", '\0', '\0', Action.HALT, "5");
        turingMachine.addTransition("0", '\0', '\0', Action.HALT, "5");

        turingMachine.setInitialState("0");

        return turingMachine;
    }

    private List<String> getStrings(List<Character> alphabet, int maxLength) {
        List<String> strings = new ArrayList<>();

        if (maxLength == 0) {
            strings.add("");
        } else {
            List<String> smallerStrings = getStrings(alphabet, maxLength - 1);
            List<String> maxLengthStrings = getProduct(
                    alphabet.stream().map(String::valueOf).collect(Collectors.toList()),
                    smallerStrings.stream().filter(s -> s.length() == maxLength - 1).collect(Collectors.toList())
            );

            strings.addAll(smallerStrings);
            strings.addAll(maxLengthStrings);
        }

        return strings;
    }

    private List<String> getProduct(List<String> list1, List<String> list2) {
        List<String> product = new ArrayList<>();

        for (String s1 : list1) {
            for (String s2 : list2) {
                product.add(s1 + s2);
            }
        }

        return product;
    }

    private void testABCTuringMachine(TuringMachine turingMachine, String s) {
        turingMachine.execute(s, 0, 0, '\0');

        if (s.isEmpty()) {
            assertTrue(turingMachine.isAccepted());
            assertEquals("5", turingMachine.getCurrentStateName());
            assertEquals("", turingMachine.getContent());
            assertEquals(Integer.MAX_VALUE, turingMachine.getContentOffset());
            assertEquals(0, turingMachine.getHeadPosition());
        } else if (matchesABC(s)) {
            assertTrue(turingMachine.isAccepted());
            assertEquals("5", turingMachine.getCurrentStateName());
            assertEquals(s.toUpperCase(), turingMachine.getContent());
            assertEquals(0, turingMachine.getContentOffset());
            assertEquals(s.length(), turingMachine.getHeadPosition());
        } else {
            assertFalse(turingMachine.isAccepted());
            assertNotEquals("5", turingMachine.getCurrentStateName());
        }

        assertEquals('\0', turingMachine.getBlank());
    }

    private boolean matchesABC(String s) {
        long a = s.chars().filter(ch -> ch == 'a').count();
        long b = s.chars().filter(ch -> ch == 'b').count();
        long c = s.chars().filter(ch -> ch == 'c').count();

        return Pattern.matches("a*b*c*", s) && a == b && b == c;
    }

    @Test
    public void testABCTuringMachineForValidStrings() {
        final int MAX = 200;

        TuringMachine turingMachine = getABCTuringMachine();

        for (int n = 0; n <= MAX; n++) {
            testABCTuringMachine(turingMachine, getABCString(n));
        }
    }

    private String getABCString(int n) {
        StringBuilder s = new StringBuilder();

        for (int i = 1; i <= n; i++) {
            s.append('a');
        }

        for (int i = 1; i <= n; i++) {
            s.append('b');
        }

        for (int i = 1; i <= n; i++) {
            s.append('c');
        }

        return s.toString();
    }
}
