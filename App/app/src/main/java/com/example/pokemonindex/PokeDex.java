package com.example.pokemonindex;


import android.os.Build;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Takes a pokemon name and fetches it's details.
 * Name: soham chakraborti.
 *
 * @author sohamc.
 */
public class PokeDex {

    private static final String HTTP_SERVER_API = "https://floating-waters-42176.herokuapp.com/getpokemon";

    /**
     * Make a JSON object of the metrics.
     * refer: https://stackoverflow.com/questions/3103196/android-os-build-data-examples-please
     * https://www.programcreek.com/java-api-examples/?class=android.os.Build&method=PRODUCT
     * @param pokemon name
     * @return jsonobject
     */
    private JSONObject getMetrics(String pokemon) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pokemon", pokemon);
            jsonObject.put("model", Build.MODEL);
            jsonObject.put("codename", Build.VERSION.CODENAME);
            jsonObject.put("brand", Build.BRAND);
            jsonObject.put("manufacturer", Build.MANUFACTURER);
            jsonObject.put("product",  Build.PRODUCT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Makes request to backend webserver to fetch the pokemon details.
     * @param pokemon to search
     * @return Pokemonstats object.
     */
    public PokemonStats search(String pokemon) {
        try {
            // Connect to web server.
            URL serverURL = new URL(HTTP_SERVER_API);
            HttpURLConnection connection = (HttpURLConnection) serverURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            JSONObject jsonObject = getMetrics(pokemon);
            writer.write(jsonObject.toString());
            writer.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine = "";
            while ((currentLine = in.readLine()) != null) {
                stringBuilder.append(currentLine);
            }

            JSONObject json = new JSONObject(stringBuilder.toString());
            PokemonStats pokemonStats = new PokemonStats();
           // extract values from json as keys and populate our object.
            pokemonStats.setName(json.get("name").toString());
            pokemonStats.setWeight(json.get("weight").toString());
            pokemonStats.setBack_default(json.get("back_default").toString());
            pokemonStats.setFront_default(json.get("front_default").toString());
            JSONArray jsonArray = (JSONArray) json.get("stats");
            fetchStats(jsonArray,pokemonStats);
            URL picURL = new URL(pokemonStats.getFront_default());
            pokemonStats.setPicture(Helper.getBitmapFromURL(picURL));
            return pokemonStats;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Extracts pokemon's stats from the json.
     * @param jsonArray of pokemon stats
     * @param pokemonStats object to store pokemon details.
     */
    private static void fetchStats(JSONArray jsonArray, PokemonStats pokemonStats) throws JSONException {
        Map<String,String> stats = new HashMap<>();
        for(int i =0; i< jsonArray.length(); i++ ) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            String name = jsonObject.getJSONObject("stat").get("name").toString();
            String value = jsonObject.get("base_stat").toString();
            stats.put(name, value);
        }
        pokemonStats.setStats(stats);
    }
}
