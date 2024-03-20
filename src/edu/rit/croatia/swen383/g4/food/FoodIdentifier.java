package edu.rit.croatia.swen383.g4.food;

/**
 * The enum Food identifier.
 */
public enum FoodIdentifier {
    /**
     * B food identifier.
     */
    B('b'),
    /**
     * R food identifier.
     */
    R('r');

    private final char c;

    FoodIdentifier(char c) {
        this.c = c;
    }

    /**
     * Gets char.
     *
     * @return the char
     */
    public char getChar() {
        return c;
    }

}
