package stats.stats;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortNames extends AsyncTask<Void, Void, ArrayList<Integer>> {


    public interface PostTaskListener<Array> {
        // K is the type of the result object of the async task
        void onPostTask(ArrayList<Integer> result);
    }

    private static final String TAG = "MyTask";
  //  private Handler mCallersHandler;
    private PostTaskListener<ArrayList> postTaskListener;
    ArrayList<Integer> deckIDs;
    String json;
    Context context;
    int i = 0;


    // Return codes
  //  public static final int MSG_FINISHED = 1001;

    public SortNames(ArrayList<Integer> deckIDs, String json, Context context, PostTaskListener<ArrayList> postTaskListener) {
        this.postTaskListener = postTaskListener;
        this.deckIDs = deckIDs;
        this.json = json;
        this.context = context;
    }

    @Override
    protected ArrayList<Integer> doInBackground(Void... params) {


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

        Collections.sort(cardsArry, new Comparator<Cards>() {
            @Override
            public int compare(Cards aCard, Cards otherCard) {
                return aCard.name.compareTo(otherCard.name);
            }
        });

           /* Collections.sort(cardsArry, new Comparator<Cards>() {
                        @Override
                        public int compare(Cards lhs, Cards rhs) {
                            return lhs.get;
                        }
                    });

                    Log.d("size", String.valueOf(cardsArry.size()));*/

        ArrayList<Integer> testArry = new ArrayList<>();

        for (int i = 0; i < cardsArry.size(); i++) {

            Log.d("cards arry", cardsArry.get(i).rarity);

            testArry.add(i, cardsArry.get(i).drawableID);


        }



       // postTaskListener.onPostTask(testArry);


        //return testArry;



        // Do all of the processing that you want to do...
        // You already have the private fields because of the constructor
        // so you can use mObject1 and mObject2
      //  Dessert objectToReturn = mObject1 + mObject2;

        // Tell the handler (usually from the calling thread) that we are finished,
        // returning an object with the message
       // mCallersHandler.sendMessage( Message.obtain( mCallersHandler, MSG_FINISHED, testArry ) );

        return testArry;
    }


    @Override
    protected void onPostExecute(ArrayList<Integer> result) {

        super.onPostExecute(result);


        if (result != null && postTaskListener != null)
            postTaskListener.onPostTask(result);
    }




}


