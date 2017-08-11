package com.example.turing;

import java.util.HashMap;
import java.util.Map;

/**
 * Turing machine implementation.
 */
public class TuringMachine {
    /**
     * This turing machine states.
     */
    private Map<String, State> states;
    /**
     * The initial state.
     */
    private State initialState;
    /**
     * The current state.
     */
    private State currentState;
    /**
     * This turing machine tape.
     */
    private Tape tape;

    /**
     * Creates a new turing machine with no states.
     */
    public TuringMachine() {
        states = new HashMap<>();
        initialState = null;
        currentState = null;
        tape = null;
    }

    /**
     * Adds a given state to this machine.
     *
     * @param name      the state name
     * @param accepting true if and only if this is a final or accepting state
     * @throws NullPointerException     if the state name is null
     * @throws IllegalArgumentException if the state name already exists
     */
    public void addState(String name, boolean accepting) throws NullPointerException, IllegalArgumentException {
        validateAddStateArguments(name);

        states.put(name, new State(name, accepting));
    }

    /**
     * Validates the {@link #addState(String, boolean)} arguments.
     *
     * @param name the state name
     * @throws NullPointerException     if the state name is null
     * @throws IllegalArgumentException if the state name already exists
     */
    private void validateAddStateArguments(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException("the state name is null");
        }

        if (states.containsKey(name)) {
            throw new IllegalArgumentException("the state name already exists");
        }
    }

    /**
     * Adds a given transition to this machine.
     *
     * @param stateFromName     the source state name
     * @param currentSymbol     the input symbol
     * @param replacementSymbol the replacement symbol
     * @param action            the action to be taken
     * @param stateToName       the destination state name
     * @throws NullPointerException     if the source state name is null
     * @throws NullPointerException     if the action to be taken is null
     * @throws NullPointerException     if the destination state name is null
     * @throws IllegalArgumentException if the source state name does not exist
     * @throws IllegalArgumentException if the destination state name does not exist
     * @throws IllegalArgumentException if the transition already exists
     */
    public void addTransition(String stateFromName, char currentSymbol, char replacementSymbol, Action action, String stateToName) throws NullPointerException, IllegalArgumentException {
        validateAddTransitionArguments(stateFromName, currentSymbol, action, stateToName);

        State stateFrom = states.get(stateFromName);
        State stateTo = states.get(stateToName);

        stateFrom.addTransition(currentSymbol, new Transition(replacementSymbol, action, stateTo));
    }

    /**
     * Validates the {@link #addTransition(String, char, char, Action, String)} arguments.
     *
     * @param stateFromName the source state name
     * @param currentSymbol the input symbol
     * @param action        the action to be taken
     * @param stateToName   the destination state name
     * @throws NullPointerException     if the source state name is null
     * @throws NullPointerException     if the action to be taken is null
     * @throws NullPointerException     if the destination state name is null
     * @throws IllegalArgumentException if the source state name does not exist
     * @throws IllegalArgumentException if the destination state name does not exist
     * @throws IllegalArgumentException if the transition already exists
     */
    private void validateAddTransitionArguments(String stateFromName, char currentSymbol, Action action, String stateToName) throws NullPointerException, IllegalArgumentException {
        if (stateFromName == null) {
            throw new NullPointerException("the source state name is null");
        }

        if (action == null) {
            throw new NullPointerException("the action to be taken is null");
        }

        if (stateToName == null) {
            throw new NullPointerException("the destination state name is null");
        }

        if (!states.containsKey(stateFromName)) {
            throw new IllegalArgumentException("the source state name does not exist");
        }

        if (!states.containsKey(stateToName)) {
            throw new IllegalArgumentException("the destination state name does not exist");
        }

        if (states.get(stateFromName).getTransition(currentSymbol) != null) {
            throw new IllegalArgumentException("the transition already exists");
        }
    }

    /**
     * Sets the initial state.
     *
     * @param name the initial state name
     * @throws NullPointerException     if the initial state name is null
     * @throws IllegalArgumentException if the initial state name does not exist
     */
    public void setInitialState(String name) {
        validateSetInitialStateArguments(name);

        initialState = states.get(name);
    }

    /**
     * Validates the {@link #setInitialState(String)} arguments.
     *
     * @param name the initial state name
     * @throws NullPointerException     if the initial state name is null
     * @throws IllegalArgumentException if the initial state name does not exist
     */
    private void validateSetInitialStateArguments(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException("the initial state name is null");
        }

        if (!states.containsKey(name)) {
            throw new IllegalArgumentException("the initial state name does not exist");
        }
    }

    /**
     * Executes this turing machine for the given input.
     *
     * @param content the initial content of this turing machine tape
     * @param offset  the initial content offset
     * @param current the head position
     * @param blank   the blank character
     * @throws NullPointerException  if the initial content is null
     * @throws IllegalStateException if the initial state is not set
     */
    public void execute(String content, int offset, int current, char blank) throws NullPointerException, IllegalStateException {
        validateExecuteArguments(content);
        validateState();

        tape = new Tape(content, offset, current, blank);
        currentState = initialState;

        while (performStep()) {
            // continue...
        }
    }

    /**
     * Validates the {@link #execute(String, int, int, char)} arguments.
     *
     * @param content the initial content of this turing machine tape
     * @throws NullPointerException if the initial content is null
     */
    private void validateExecuteArguments(String content) throws NullPointerException {
        if (content == null) {
            throw new NullPointerException("the initial content is null");
        }
    }

    /**
     * Determines if this machine is ready to execute.
     *
     * @throws IllegalStateException if the initial state is not set
     */
    private void validateState() throws IllegalStateException {
        if (initialState == null) {
            throw new IllegalStateException("the initial state is not set");
        }
    }

    /**
     * Performs a single execution step.
     *
     * @return true if and only if there are still more steps.
     */
    private boolean performStep() {
        Transition transition = currentState.getTransition(tape.read());

        if (transition == null) {
            return false; // stop
        }

        tape.write(transition.getReplacement());
        currentState = transition.getStateTo();

        switch (transition.getAction()) {
            case LEFT:
                tape.left();
                return true; // continue
            case RIGHT:
                tape.right();
                return true; // continue
            default:
                return false; // stop
        }
    }

    /**
     * @return true if and only if the current state is a final or accepting state.
     * @throws IllegalStateException if this machine has not already been executed
     */
    public boolean isAccepted() throws IllegalStateException {
        validateExecution();

        return currentState.isAccepting();
    }

    /**
     * @return The current state name.
     * @throws IllegalStateException if this machine has not already been executed
     */
    public String getCurrentStateName() throws IllegalStateException {
        validateExecution();

        return currentState.getName();
    }

    /**
     * @return The content of the tape as a string.
     * @throws IllegalStateException if this machine has not already been executed
     */
    public String getContent() throws IllegalStateException {
        validateExecution();

        return tape.getContent();
    }

    /**
     * @return The position of the first non blank character (Integer.MAX_VALUE if the tape only contains blank
     * characters)
     * @throws IllegalStateException if this machine has not already been executed
     */
    public int getContentOffset() throws IllegalStateException {
        validateExecution();

        return tape.getOffset();
    }

    /**
     * @return The head position.
     * @throws IllegalStateException if this machine has not already been executed
     */
    public int getHeadPosition() throws IllegalStateException {
        validateExecution();

        return tape.getCurrent();
    }

    /**
     * @return The blank character being used.
     * @throws IllegalStateException if this machine has not already been executed
     */
    public char getBlank() throws IllegalStateException {
        validateExecution();

        return tape.getBlank();
    }

    /**
     * Validates that this machine has been executed at least once.
     *
     * @throws IllegalStateException if this machine has not already been executed
     */
    private void validateExecution() throws IllegalStateException {
        if (currentState == null || tape == null) {
            throw new IllegalStateException("this machine has not already been executed");
        }
    }
}
