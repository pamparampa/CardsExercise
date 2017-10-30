package com.example.pamparampa.cardsexcercise2;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pamparampa on 2017-10-05.
 */

class CardsEngine {

    private final Context context;

    private int figuresCount = 0;
    private static CardsEngine instance;
    private ArrayList<Card> cards = new ArrayList<>();
    private Map<String, Integer> suitsMap = new HashMap<>();
    private Map<String, Integer> twinsMap = new HashMap<>();
    private ArrayList<String> matches = new ArrayList<>();

    private CardsEngine(Context context) {
        this.context = context;
    }

    public static CardsEngine getInstance(Activity activity) {
        if (instance == null) {
            instance = new CardsEngine(activity);
        }
        return instance;
    }

    public void addCards(List<Card> addedCardList) {
        for (Card card :
                addedCardList) {
            addCard(card);
        }
    }

    private void addCard(Card card) {
        updateSuits(card);
        updateTwins(card);
        updateFigures(card);
        updateStairs(card);

        cards.add(card);
    }

    private void updateSuits(Card card) {
        Integer suitsCount = suitsMap.containsKey(card.getSuit())? suitsMap.get(card.getSuit()) : 0;
        suitsMap.put(card.getSuit(), suitsCount + 1);
        if (suitsMap.get(card.getSuit()) == 3) {
            matches.add(0, String.format(
                    context.getString(R.string.suit), card.getTranslatedSuit(context)));
        }
    }

    private void updateTwins(Card card) {
        Integer twinsCount = twinsMap.containsKey(card.getValue())? twinsMap.get(card.getValue()) : 0;
        twinsMap.put(card.getValue(), twinsCount + 1);
        if (twinsMap.get(card.getValue()) == 3) {
            matches.add(0, String.format(
                    context.getString(R.string.twins), card.getTranslatedValue(context)));
        }
    }

    private void updateFigures(Card card) {
        if (card.isFigure()) {
            figuresCount++;
            if (figuresCount == 3) {
                matches.add(0, context.getString(R.string.figures));
            }
        }
    }

    private void updateStairs(Card card) {
        checkIfAsc(card);
        checkIfDesc(card);
    }

    private void checkIfAsc(Card card) {

        int cardsSize = cards.size();
        if (cardsSize > 1) {
            if (card.getIntValue() > cards.get(cardsSize - 1).getIntValue() &&
                    cards.get(cardsSize - 1).getIntValue() > cards.get(cardsSize - 2).getIntValue()) {
                addStairsToMatches(card, cardsSize);
            }
        }
    }

    private void checkIfDesc(Card card) {
        int cardsSize = cards.size();
        if (cardsSize > 1) {
            if (card.getIntValue() < cards.get(cardsSize - 1).getIntValue() &&
                    cards.get(cardsSize - 1).getIntValue() < cards.get(cardsSize - 2).getIntValue()) {
                addStairsToMatches(card, cardsSize);
            }
        }
    }

    private void addStairsToMatches(Card card, int cardsSize) {
        matches.add(0, String.format(
                context.getString(R.string.stairs)
                , cards.get(cardsSize - 2).getTranslatedValue(context)
                + ", " + cards.get(cardsSize - 1).getTranslatedValue(context)
                + ", " + card.getTranslatedValue(context)
        ));
    }

    public void clear() {
        cards.clear();
        suitsMap.clear();
        twinsMap.clear();
        matches.clear();
    }

    public ArrayList<String> getMatches() {
        return matches;
    }
}
