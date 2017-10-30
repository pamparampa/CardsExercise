package com.example.pamparampa.cardsexcercise2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DeckCountChooseActivity extends AppCompatActivity {

    Spinner deckCountSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initiateLayout();
    }

    private void initiateLayout() {
        setContentView(R.layout.activity_deck_count_choose);

        deckCountSpinner = (Spinner) findViewById(R.id.deckCountSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.deckCounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deckCountSpinner.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    public void onOk(View view) {
        Intent intent = getIntent();
        intent.putExtra(getString(R.string.deck_count)
                , Integer.parseInt(deckCountSpinner.getSelectedItem().toString()));
        setResult(RESULT_OK, intent);
        finish();
    }
    public void onCancel(View view) {
        cancel();
    }

    private void cancel() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
