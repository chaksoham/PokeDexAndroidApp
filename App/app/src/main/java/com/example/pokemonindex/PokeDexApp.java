package com.example.pokemonindex;

import android.os.AsyncTask;


/**
 * Class controls app lifecycle
 *  Name: soham chakraborti.
 *
 *  @author sohamc.
 */
public class PokeDexApp {

    private MainActivity mainActivity;

    /**
     * Gets called from MainActivity. Schedules background events.
     * @param pokemon
     * @param mainActivity
     */
    public void search(String pokemon, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        new AsyncAPICall().execute(pokemon);
    }

    /**
     * work in the background.
     */
    private class AsyncAPICall extends AsyncTask<String, Void, PokemonStats> {

        protected PokemonStats doInBackground(String... pokemon) {
            try {
                return search(pokemon[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Calls the update screen method.
         * @param pokemonStats the pokemon details.
         */
        protected void onPostExecute(PokemonStats pokemonStats) {
            mainActivity.updateScreen(pokemonStats);
        }

        /**
         * Search Method that Interacts with the Webserver.
         * @param pokemon to search
         * @return details about the pokemon.
         */
        private PokemonStats search(String pokemon) {
            try {
                PokeDex pokeDex = new PokeDex();
                PokemonStats pokemonStats = pokeDex.search(pokemon.toLowerCase());
                return pokemonStats;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
