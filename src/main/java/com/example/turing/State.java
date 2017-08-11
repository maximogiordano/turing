package com.example.turing;

import java.util.HashMap;
import java.util.Map;

/**
 * Turing machine state implementation.
 */
class State {
    /**
     * The state name.
     */
    private String name;
    /**
     * true if and only if this is a final or accepting state.
     */
    private boolean accepting;
    /**
     * The corresponding transition for each symbol.
     */
    private Map<Character, Transition> transitions;

    /**
     * Creates a new state with no transitions.
     *
     * @param name      the state name
     * @param accepting true if and only if this is a final or accepting state
     */
    State(String name, boolean accepting) {
        this.name = name;
        this.accepting = accepting;
        this.transitions = new HashMap<>();
    }

    /**
     * Sets the corresponding transition for the given symbol.
     *
     * @param symbol     the symbol
     * @param transition the transition
     */
    void addTransition(char symbol, Transition transition) {
        transitions.put(symbol, transition);
    }

    /**
     * @return The state name.
     */
    String getName() {
        return name;
    }

    /**
     * @return true if and only if this is a final or accepting state.
     */
    boolean isAccepting() {
        return accepting;
    }

    /**
     * Gets the corresponding transition for the given symbol.
     *
     * @param symbol the symbol
     * @return The corresponding transition for the given symbol (null if no transition is set for the given symbol)
     */
    Transition getTransition(char symbol) {
        return transitions.get(symbol);
    }
}
