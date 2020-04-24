package com.example.pokemonindex;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;


/**
 * Name: soham chakraborti.
 *  @author sohamc.
 * Android main activity class
 */
public class MainActivity extends AppCompatActivity {

    // refer to the app textview ( the output of details)
    private TextView pokemonStatsTextView;

    // refer to the app user input
    private EditText userInputEditTextView;

    // refers to the imageView
    private ImageView pokemonPicView;

    // progress bar
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity mainActivity = this;

        // Assign all elements in app to variables.
        Button submitButton = findViewById(R.id.getpokemon);
        userInputEditTextView = findViewById(R.id.input);
        pokemonStatsTextView = findViewById(R.id.poke_details);
        pokemonPicView = findViewById(R.id.pokemonPic);
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                // get the text the user has given
                Editable text = userInputEditTextView.getText();
                PokeDexApp pokeDexApp = new PokeDexApp();
                // Call search to fetch the details about the pokemon.
                pokeDexApp.search(text.toString(), mainActivity);
                progressBar.setVisibility(View.VISIBLE);
                pokemonPicView.setVisibility(View.INVISIBLE);
                pokemonStatsTextView.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * Formats the stas output.
     *
     * @param pokemonStats stats on various abilities of a pokemon
     * @return a formatted string.
     */
    private String formatStats(Map<String, String> pokemonStats) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : pokemonStats.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" : ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Once response comes from server this method updates the view.
     *
     * @param pokemonStats details about the pokemon.
     */
    @SuppressLint("SetTextI18n")
    public void updateScreen(PokemonStats pokemonStats) {
        progressBar.setVisibility(View.GONE);
        try {
            if (pokemonStats != null) {
                pokemonStatsTextView.setText("\nName:: " + pokemonStats.getName()
                        + "\nWeight:: " + pokemonStats.getWeight()
                        + "\nStats\n" + formatStats(pokemonStats.getStats()));
                Bitmap pokemonImg = pokemonStats.getPicture();
                pokemonPicView.setImageBitmap(pokemonImg);
                pokemonPicView.setVisibility(View.VISIBLE);
                pokemonStatsTextView.setVisibility(View.VISIBLE);
            } else {
                pokemonPicView.setImageResource(R.mipmap.ic_launcher);
                pokemonStatsTextView.setText("nothing found !!");
                pokemonPicView.setVisibility(View.VISIBLE);
                pokemonStatsTextView.setVisibility(View.VISIBLE);
            }
            pokemonPicView.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
