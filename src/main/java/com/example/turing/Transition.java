package com.example.turing;

/**
 * Turing machine state transition implementation.
 */
class Transition {
    /**
     * The replacement character.
     */
    private char replacement;
    /**
     * The action to be taken.
     */
    private Action action;
    /**
     * The destination state.
     */
    private State stateTo;

    /**
     * Creates a new transition with the given data.
     *
     * @param replacement the replacement character
     * @param action      the action to be taken
     * @param stateTo     the destination state
     */
    Transition(char replacement, Action action, State stateTo) {
        this.replacement = replacement;
        this.action = action;
        this.stateTo = stateTo;
    }

    /**
     * @return The replacement character.
     */
    char getReplacement() {
        return replacement;
    }

    /**
     * @return The action to be taken.
     */
    Action getAction() {
        return action;
    }

    /**
     * @return The destination state.
     */
    State getStateTo() {
        return stateTo;
    }
}
