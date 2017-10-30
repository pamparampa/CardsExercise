package com.example.pamparampa.cardsexcercise2;

/**
 * Created by Pamparampa on 2017-09-23.
 */

public class ShuffleResponse{

    private boolean success;
    private String deck_id;
    private boolean shuffled;
    private int remaining;

    public ShuffleResponse(boolean success, String deck_id, boolean shuffled, int remaining) {
        this.success = success;
        this.deck_id = deck_id;
        this.shuffled = shuffled;
        this.remaining = remaining;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public int getRemaining() {
        return remaining;
    }
}
