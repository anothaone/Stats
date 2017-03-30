package stats.stats;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Mike on 2/5/2017.
 */










public class sorting extends AsyncTask<ArrayList<Cards>, Void, ArrayList> {
    ArrayList <Integer> deckIDs;
    String json;
    Context context;
    int i = 0;


    public interface AsyncResponse{
        Void porcessFinish(ArrayList output);

    }





    public sorting(ArrayList<Integer> deckIDs, String json, Context context) {
        this.deckIDs = deckIDs;
        this.json = json;
        this.context = context;

    }


    @SafeVarargs
    @Override
    protected final ArrayList<Integer> doInBackground(ArrayList<Cards>... params) {

        GetStats stats = new GetStats(json);
        double averageElixer = 0;
        double totalCost = 0;
        double cost = 0;
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

       // Collections.sort(cardsArry);

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

}



class sortByName extends AsyncTask<ArrayList<Cards>, Void, ArrayList<Integer>> {
    ArrayList<Integer> deckIDs;
    String json;
    Context context;
    int i = 0;


        public sortByName(ArrayList<Integer> deckIDs, String json, Context context) {
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


        return testArry;
    }




}




