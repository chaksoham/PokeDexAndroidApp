package com.example.pokemonindex;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to store Pokemon details.
 *
 * Name: soham chakraborti.
 * @author sohamc.
 */
public class PokemonStats {

    // stores the stats of the pokemon
    private Map<String, String> stats = new HashMap<>();

    // stores the weight
    private String weight;

    // stores the back image url
    private String back_default;

    // stores the front image url
    private String front_default;

    // pokemon name
    private String name;

    // bitmap representation of the pokemon image
    private Bitmap picture;

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
