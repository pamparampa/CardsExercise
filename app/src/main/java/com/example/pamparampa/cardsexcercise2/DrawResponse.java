package com.example.pamparampa.cardsexcercise2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pamparampa on 2017-09-30.
 */

class DrawResponse {

    private boolean succsess;
    private List<Card> cards;
    private String deckId;
    private int remaining;

    public DrawResponse(boolean succsess, List<Card> cards, String deckId, int remaining) {
        this.succsess = succsess;
        this.cards = cards;
        this.deckId = deckId;
        this.remaining = remaining;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getRemaining() {
        return remaining;
    }
}
