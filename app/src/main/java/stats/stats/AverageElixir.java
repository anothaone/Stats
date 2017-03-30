package stats.stats;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mike on 2/15/2017.
 */
public class AverageElixir {
    public Activity activity;


    public AverageElixir(Activity activity){

        this.activity = activity;

    }


    public double averageElixir(ArrayList deckIDs) {
        double averageElixer = 0;
        double totalCost = 0;
        double cost;

        for (Object id : deckIDs) {

            String cardName = activity.getResources().getResourceEntryName(Integer.parseInt(id.toString()));
            Log.d("names", cardName);

            LoadJsonData loadJsonData = new LoadJsonData();

            GetStats stats = new GetStats(loadJsonData.loadJSONFromAsset(activity.getApplicationContext()));

            Cards Acard = stats.cardData(cardName);

            if(cardName.equals("mirror")){

                Acard.cost = "0";

            }

            cost = Integer.parseInt(Acard.cost);
            totalCost = totalCost + cost;
            averageElixer = totalCost / 8;

            String name = Acard.name;

            Log.d("total cost", String.valueOf(totalCost));
            Log.d("cost avg", String.valueOf(averageElixer));
            Log.d("cost avg rd", String.valueOf( Math.round(averageElixer)));
            Log.d("cost", String.valueOf(cost));
            Log.d("name", name);

            TextView elixir = (TextView) activity.findViewById(R.id.averageElixir);

            assert elixir != null;
            elixir.setText(activity.getResources().getString(R.string.average_elixir) +  Math.round(averageElixer * 100) / 100.0 );

        }


        return averageElixer;
    }



}
