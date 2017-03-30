package stats.stats;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mike on 2/8/2017.
 */
class SortByElixir extends AsyncTask<ArrayList<Cards>, Void, ArrayList<Integer>> {
    ArrayList<Integer> deckIDs;
    String json;
    Context context;
    int i = 0;


    public SortByElixir(ArrayList<Integer> deckIDs, String json, Context context) {
        this.deckIDs = deckIDs;
        this.json = json;
        this.context = context;
    }


    @SafeVarargs
    @Override
    protected final ArrayList<Integer> doInBackground(ArrayList<Cards>... params) {

        GetStats stats = new GetStats(json);
        ArrayList<Cards> cardsArry = new ArrayList<>();

        // int i = 0;
        for (Integer id : deckIDs) {


            String cardName = context.getResources().getResourceEntryName(Integer.parseInt(id.toString()));
            Log.d("names", cardName);


            Cards Acard = stats.cardData(cardName);


            Acard.setDrawableID(id);

            cardsArry.add(Acard);

            if (cardName.equals("mirror")) {

                Acard.cost = "0";

            }

        }

        Collections.sort(cardsArry);

        Log.d("size", String.valueOf(cardsArry.size()));

        ArrayList<Integer> testArry = new ArrayList<>();

        for (int i = 0; i < cardsArry.size(); i++) {

            Log.d("cards arry", cardsArry.get(i).rarity);

            testArry.add(i, cardsArry.get(i).drawableID);


        }


        return testArry;
    }


}
