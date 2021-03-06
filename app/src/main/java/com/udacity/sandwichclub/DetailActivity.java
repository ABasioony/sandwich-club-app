package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mImageView;
    private TextView mOrginTextView;
    private TextView mAlsoKnownTextView;
    private TextView mIngrediantsTextView;
    private TextView mDescTextView;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = (ImageView) findViewById(R.id.image_iv);
        mOrginTextView = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngrediantsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mDescTextView = (TextView) findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(mImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            mOrginTextView.setText("Data is Empty");
        }else {
            mOrginTextView.setText(" " + sandwich.getPlaceOfOrigin());
        }
        if(sandwich.getAlsoKnownAs().isEmpty()){
            mAlsoKnownTextView.setText("Data is Empty");
        } else {
            mAlsoKnownTextView.setText(" " + sandwich.getAlsoKnownAs());
        }
        if(sandwich.getIngredients().isEmpty()){
            mIngrediantsTextView.setText("Data is Empty");
        } else {
            mIngrediantsTextView.setText(" " +  sandwich.getIngredients());
        }
        if(sandwich.getDescription().isEmpty()){
            mDescTextView.setText("Data is Empty");
        } else {
            mDescTextView.setText(" " + sandwich.getDescription());
        }
    }
}
