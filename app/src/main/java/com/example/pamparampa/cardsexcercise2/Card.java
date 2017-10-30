package com.example.pamparampa.cardsexcercise2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.text.TextUtils;

/**
 * Created by Pamparampa on 2017-10-05.
 */

class Card {


    private String image;
    private String suit;
    private String value;
    private Drawable imageDraw;

    public String getCode() {
        return code;
    }

    private String code;

    public Card(String image, String suit, String value, String code) {
        this.image = image;
        this.suit = suit;
        this.value = value;
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return this.value;
    }

    public int getIntValue() {
        if (TextUtils.isDigitsOnly(value)) return Integer.parseInt(value);
        else {
            switch (value) {
                case "JACK":
                    return 11;
                case "QUEEN":
                    return 12;
                case "KING":
                    return 13;
                case "ACE":
                    return 14;
            }
        }
        return 0;
    }

    public boolean isFigure() {
        return !TextUtils.isDigitsOnly(value);
    }

    public String getTranslatedValue(Context context) {
        switch (value) {
            case "JACK":
                return context.getString(R.string.jack);
            case "QUEEN":
                return context.getString(R.string.queen);
            case "KING":
                return context.getString(R.string.king);
            case "ACE":
                return context.getString(R.string.ace);
            default:
                return value;
        }
    }

    public String getTranslatedSuit(Context context) {
        switch (suit) {
            case "SPADES":
                return context.getString(R.string.spade);
            case "HEARTS":
                return context.getString(R.string.heart);
            case "DIAMONDS":
                return context.getString(R.string.diamond);
            case "CLUBS":
                return context.getString(R.string.club);
            default:
                return "";
        }
    }
}
