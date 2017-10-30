package com.example.pamparampa.cardsexcercise2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int DECK_COUNT_CHOOSE = 1;
    private static final int FIRST_SHUFFLE = 2;
    private static final int NEXT_SHUFFLE = 3;
    private static final int DEFAULT_DECK_COUNT = 1;
    private String BASE_URL;

    private int deckCount;
    private int remainCards;
    private String deckId;

    private TextView deckCountTextView;
    private TextView remainCardsTextView;
    private TextView matchesTextView;
    private GridView cardsGridView;

    DeckOfCardsAPI deckOfCardsAPI;
    private CardsAdapter cardsAdapter;
    private ArrayAdapter<String> matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateVariables();
        initiateLayout();
        manageShuffle(FIRST_SHUFFLE);
    }

    private void initiateVariables() {
        BASE_URL = getString(R.string.base_url);
        engine = CardsEngine.getInstance(this);
        deckOfCardsAPI = initDeckOfCardsAPI();
    }

    private void initiateLayout() {
        setContentView(R.layout.activity_main2);

        deckCountTextView = (TextView) findViewById(R.id.deckCountTextView);
        remainCardsTextView = (TextView) findViewById(R.id.remainCardsTextView);
        matchesTextView = (TextView) findViewById(R.id.matchesTextView);

        cardsAdapter = new CardsAdapter(this);
        cardsGridView = (GridView) findViewById(R.id.cardsGridView);
        cardsGridView.setAdapter(cardsAdapter);
    }

    private DeckOfCardsAPI initDeckOfCardsAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(DeckOfCardsAPI.class);
    }

    public void onShuffleClick(View view) {
        manageShuffle(NEXT_SHUFFLE);
    }

    private void manageShuffle(int whichShuffle) {
        Intent intent = new Intent(getApplicationContext(), DeckCountChooseActivity.class);
        intent.putExtra(getString(R.string.which_shuffle), whichShuffle);
        startActivityForResult(intent, DECK_COUNT_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (DECK_COUNT_CHOOSE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                shuffle(data.getIntExtra(getString(R.string.deck_count), DEFAULT_DECK_COUNT));
            }
            else if (Activity.RESULT_CANCELED == resultCode) {
                if (data.getIntExtra(
                        getString(R.string.which_shuffle), NEXT_SHUFFLE
                ) == FIRST_SHUFFLE) {
                    finish();
                }
            }
        }
    }

    private void shuffle(int deckCount) {
        updateDeckCount(deckCount);

        sendShuffleRequest();
    }


    private void sendShuffleRequest() {
        Call<ShuffleResponse> call = deckOfCardsAPI.getShuffledDeck (deckCount);
        call.enqueue(shuffleCallback);
    }

    private void clearLayout() {
        cardsAdapter.clear();
        matchesTextView.setText("");
    }

    public void onAdjustClick(View view) {
        adjustCards(1);
    }

    private void adjustCards(int cardsCount) {
        Call<DrawResponse> call = deckOfCardsAPI.drawCards(deckId, cardsCount);
        call.enqueue(drawCallback);
    }

    private void drawCards(List<Card> cards) {
        for (Card card : cards) {
            cardsAdapter.add(card);
        }
    }

    private void updateDeckCount(int deckCount) {
        this.deckCount = deckCount;
        deckCountTextView.setText(
                String.format(getString(R.string.number_of_decks), deckCount)
        );
        deckCountTextView.setTextColor(Color.BLACK);
    }

    private void updateRemainCards(int remainCards) {
        this.remainCards = remainCards;
        remainCardsTextView.setText(
                String.format(
                        getString(R.string.remains_number), remainCards
                )
        );
        remainCardsTextView.setTextColor(Color.BLACK);
    }

    private void announceNoConnection() {
        deckCountTextView.setText(getString(R.string.no_connection));
        remainCardsTextView.setText(getString(R.string.with_internet));

        deckCountTextView.setTextColor(Color.RED);
        remainCardsTextView.setTextColor(Color.RED);
    }

    private void updateMatches(ArrayList<String> matches) {
        String matchesString = "";
        for (String match: matches) {
            matchesString += match + "\n";
        }
        matchesTextView.setText(matchesString);
    }

    Callback<ShuffleResponse> shuffleCallback = new Callback<ShuffleResponse>() {
        @Override
        public void onResponse(@NonNull Call<ShuffleResponse> call
                , @NonNull Response<ShuffleResponse> response
        ) {
            if (response.isSuccessful()) {
                ShuffleResponse shuffleResponse = response.body();
                if (shuffleResponse != null) {
                    updateRemainCards(shuffleResponse.getRemaining());

                    clearLayout();
                    engine.clear();
                    deckId = shuffleResponse.getDeck_id();
                    adjustCards(5);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<ShuffleResponse> call, @NonNull Throwable t) {
            announceNoConnection();
        }
    };

    private CardsEngine engine;
    Callback<DrawResponse> drawCallback = new Callback<DrawResponse>() {
        @Override
        public void onResponse(
                @NonNull Call<DrawResponse> call
                , @NonNull Response<DrawResponse> response
        ) {
            if (response.isSuccessful()) {
                DrawResponse drawResponse = response.body();
                updateDeckCount(deckCount);
                updateRemainCards(drawResponse.getRemaining());
                List<Card> cards = drawResponse.getCards();
                drawCards(cards);
                engine.addCards(cards);
                updateMatches(engine.getMatches());
            }
        }

        @Override
        public void onFailure(@NonNull Call<DrawResponse> call, @NonNull Throwable t) {
            announceNoConnection();
        }
    };



}
