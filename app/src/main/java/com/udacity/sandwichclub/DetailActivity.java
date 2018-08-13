package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView alsoKnownTextview = findViewById(R.id.also_known_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.no_image_available)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        alsoKnownTextview.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        descriptionTextView.setText(sandwich.getDescription());
        originTextView.setText((sandwich.getPlaceOfOrigin()));

        // If not known by any other names, hide the fields
        if (alsoKnownTextview.getText().toString() == null || alsoKnownTextview.getText().toString().isEmpty()) {
            alsoKnownTextview.setVisibility(View.GONE);
            findViewById(R.id.also_known_label_tv).setVisibility(View.GONE);
        }

        // If country of origin is not known, display unknown
        if (originTextView.getText().toString() == null || alsoKnownTextview.getText().toString().isEmpty()) {
            originTextView.setText("It's a mystery!");
        }

        // Show ingredients as a bullet list
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (String ingredient : sandwich.getIngredients()) {
            ingredientsBuilder.append("• " + ingredient + "\n");
        }
        ingredientsBuilder.setLength(ingredientsBuilder.length() - 1);
        ingredientsTextView.setText(ingredientsBuilder);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
