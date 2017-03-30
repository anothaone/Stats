package stats.stats;

/**
 * Created by Mike on 2/15/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortRarity extends AsyncTask<Void, Void, ArrayList<Integer>> {


    public interface PostTaskListener {
        // K is the type of the result object of the async task
        void onPostTask(ArrayList<Integer> result);
    }

    private static final String TAG = "MyTask";
    //  private Handler mCallersHandler;
    private PostTaskListener postTaskListener;
    ArrayList<Integer> deckIDs;
    String json;
    Context context;
  //  int i = 0;


    // Return codes
    //  public static final int MSG_FINISHED = 1001;

    public SortRarity(ArrayList<Integer> deckIDs, String json, Context context, PostTaskListener postTaskListener) {
        this.postTaskListener = postTaskListener;
        this.deckIDs = deckIDs;
        this.json = json;
        this.context = context;
    }

    @Override
    protected ArrayList<Integer> doInBackground(Void... params) {
        GetStats stats = new GetStats(json);
        ArrayList elxir = new ArrayList();
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


        Collections.sort(cardsArry, new Comparator<Cards>() {
            @Override
            public int compare(Cards aCard, Cards otherCard) {



                if (aCard.rarity.equals("common")) {

                    Log.d("acard smaller1", aCard.rarity + ":" + otherCard.rarity);
                    return -1;
                }


                if (aCard.rarity.equals("rare")
                        &&  otherCard.rarity.equals("epic") || otherCard.rarity.equals("legendary")){

                    Log.d("acard smaller2", aCard.rarity + ":" + otherCard.rarity);
                    return -1;
                }


                if (aCard.rarity.equals("epic")
                        &&  otherCard.rarity.equals("legendary")) {

                    Log.d("acard smaller3", aCard.rarity + ":" + otherCard.rarity);
                    return -1;
                }


                if (aCard.rarity.equals("legendary")  && otherCard.rarity.equals("common") ||  otherCard.rarity.equals("rare") ||
                        otherCard.rarity.equals(("epic"))) {

                    Log.d("acard smaller3", aCard.rarity + ":" + otherCard.rarity);
                    return 1;
                }



                if (otherCard.rarity.equals("common")) {

                    Log.d("acard smaller1", aCard.rarity + ":" + otherCard.rarity);
                    return 1;
                }


                if (otherCard.rarity.equals("rare")
                        &&  aCard.rarity.equals("common")) {

                    Log.d("acard smaller1", aCard.rarity + ":" + otherCard.rarity);
                    return 1;
                }

                if (otherCard.rarity.equals("epic")
                        &&  aCard.rarity.equals("common") ||  aCard.rarity.equals("rare")) {

                    Log.d("acard smaller1", aCard.rarity + ":" + otherCard.rarity);
                    return 1;
                }

                if (otherCard.rarity.equals("legendary") && aCard.rarity.equals("common") ||  aCard.rarity.equals("rare") ||
                        aCard.rarity.equals(("epic"))) {

                    Log.d("acard smaller1", aCard.rarity + ":" + otherCard.rarity);
                    return 1;
                }




                return 0;
            }

        });
        Log.d("size", String.valueOf(cardsArry.size()));

        ArrayList<Integer> testArry = new ArrayList<>();

        for(int i= 0; i < cardsArry.size(); i++ ){

            Log.d("cards arry", cardsArry.get(i).rarity);

            testArry.add(i, cardsArry.get(i).drawableID);



        }

        return testArry;

    }


    @Override
    protected void onPostExecute(ArrayList<Integer> result) {

        super.onPostExecute(result);


        if (result != null && postTaskListener != null)
            postTaskListener.onPostTask(result);
    }




}



