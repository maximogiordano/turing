package com.example.turing;

import java.util.HashMap;
import java.util.Map;

/**
 * Turing machine tape implementation.
 */
class Tape {
    /**
     * Maps a position to a given character.
     */
    private Map<Integer, Character> map;
    /**
     * Head position.
     */
    private int current;
    /**
     * Blank character.
     */
    private char blank;

    /**
     * Creates a new tape and sets its initial content.
     *
     * @param content the initial content of this tape
     * @param offset  the initial content offset
     * @param current the head position
     * @param blank   the blank character
     */
    Tape(String content, int offset, int current, char blank) {
        this.map = new HashMap<>();
        this.current = current;
        this.blank = blank;

        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != blank) {
                map.put(offset + i, content.charAt(i));
            }
        }
    }

    /**
     * Moves the head one position to the left.
     */
    void left() {
        current--;
    }

    /**
     * Moves the head one position to the right.
     */
    void right() {
        current++;
    }

    /**
     * @return The character in the head position.
     */
    char read() {
        Character c = map.get(current);

        return c == null ? blank : c;
    }

    /**
     * Sets the character in the head position.
     *
     * @param c the character to be set
     */
    void write(char c) {
        if (c == blank) {
            map.remove(current);
        } else {
            map.put(current, c);
        }
    }

    /**
     * @return The content of this tape as a string.
     */
    String getContent() {
        int left = getLeft();
        int right = getRight();

        StringBuilder s = new StringBuilder();

        for (int i = left; i <= right; i++) {
            if (map.containsKey(i)) {
                s.append(map.get(i));
            } else {
                s.append(blank);
            }
        }

        return s.toString();
    }

    /**
     * @return The position of the first non blank character (Integer.MAX_VALUE if the tape only contains blank
     * characters)
     */
    private int getLeft() {
        return map.keySet().stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
    }

    /**
     * @return The position of the last non blank character (Integer.MIN_VALUE if the tape only contains blank
     * characters)
     */
    private int getRight() {
        return map.keySet().stream().max(Integer::compare).orElse(Integer.MIN_VALUE);
    }

    /**
     * @return The position of the first non blank character (Integer.MAX_VALUE if the tape only contains blank
     * characters)
     */
    int getOffset() {
        return getLeft();
    }

    /**
     * @return The head position.
     */
    int getCurrent() {
        return current;
    }

    /**
     * @return The blank character being used.
     */
    char getBlank() {
        return blank;
    }
}
