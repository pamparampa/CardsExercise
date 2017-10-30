package com.example.pamparampa.cardsexcercise2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pamparampa on 2017-10-21.
 */

class CardsAdapter extends BaseAdapter {

    private final Context context;
    private int count = 0;
    private List<Card> cards;

    public CardsAdapter(Context context) {
        this.context = context;
        cards = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView cardImageView;

        if (convertView == null) {
            cardImageView = new ImageView(context);
        }
        else {
            cardImageView = (ImageView) convertView;
        }
        loadImageFromUrl(cardImageView, cards.get(position).getImage());

        return cardImageView;
    }

    public void add(Card card) {
        cards.add(card);
        notifyDataSetChanged();
    }

    private ImageView loadImageFromUrl(ImageView imageView, String url) {
        Picasso.with(context).load(url).placeholder(R.drawable.loading)
                .error(R.drawable.no_connection)
                .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        }
                );
        return imageView;
    }

    public void clear() {
        cards = new ArrayList<>();
        notifyDataSetChanged();
    }
}
