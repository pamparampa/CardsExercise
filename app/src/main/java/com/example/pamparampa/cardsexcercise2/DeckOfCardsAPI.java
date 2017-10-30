package com.example.pamparampa.cardsexcercise2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pamparampa on 2017-09-27.
 */

interface DeckOfCardsAPI {

    @GET("new/shuffle/")
    Call<ShuffleResponse> getShuffledDeck(@Query("deck_count") int deckCount);

    @GET("{deck_id}/draw/")
    Call<DrawResponse> drawCards(@Path("deck_id") String deckId, @Query("count") int cardCount);

}
