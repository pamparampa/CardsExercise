package com.example.pamparampa.cardsexcercise2;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void drawResponse_makesCorrectJson() throws Exception {
        List<Card> cards = new ArrayList<Card>();

        cards.add(new Card("abc.dumo.pl", "czerwo", 4, "4CW"));
        cards.add(new Card("abc.dumo.pl", "wino", 7, "7WN"));

        DrawResponse drawResponse = new DrawResponse(true, cards, "asda1", 31);

        Gson gson = new Gson();

        String json = gson.toJson(drawResponse, DrawResponse.class);
        System.out.println(json);
    }
}