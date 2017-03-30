package stats.stats;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Mike on 2/15/2017.
 */
public class LoadSortButton  {
    SortElixir sortElixir;
    SortNames sortNames;
    SortRarity sortRarity;
    LoadJsonData loadJsonData;
    RecyclerViewAdapter cardRecyclerAdapter;
    Context context;
    String json;
    Activity activity;




    public LoadSortButton(RecyclerViewAdapter cardRecyclerAdapter, Context context,String json, Activity activity){
        this.cardRecyclerAdapter = cardRecyclerAdapter;
        this.json = json;
        this.context = context;
        this.activity = activity;
    }

    public void loadSortButton() {


      //  final String json = loadJsonData.loadJSONFromAsset(mContext);

        final CompositeOnClickListener firstClickListener = new CompositeOnClickListener();
        final CompositeOnClickListener secondClickListner = new CompositeOnClickListener();
        final CompositeOnClickListener thirdClickListner = new CompositeOnClickListener();
        final Button sortButton = (Button) activity.findViewById(R.id.loadSortButton);



        assert sortButton != null;
        sortButton.setOnClickListener(firstClickListener);



        firstClickListener.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortNames!= null && sortNames.getStatus() == SortNames.Status.RUNNING) {
                    sortNames.cancel(true);
                }




                Log.d("1st click", "1");
                sortButton.setOnClickListener(secondClickListner);



                sortButton.setText("By Elixir");



                SortElixir.PostTaskListener<ArrayList> sortElixirListener = new SortElixir.PostTaskListener<ArrayList>() {
                    @Override
                    public void onPostTask(ArrayList<Integer> result) {

                        Log.d("post mes", String.valueOf(result));

                        cardRecyclerAdapter.updateCustomList(result);
                        cardRecyclerAdapter.notifyDataSetChanged();


                    }
                };

                sortElixir = new SortElixir(cardRecyclerAdapter.getCustomList(),json,context,sortElixirListener);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    sortElixir.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    sortElixir.execute();



                Snackbar.make(v,String.valueOf("clicked" + " " + v),Snackbar.LENGTH_SHORT).show();

            }
        });

        secondClickListner.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortElixir!= null && sortElixir.getStatus() == SortElixir.Status.RUNNING) {
                    sortElixir.cancel(true);
                }

                Log.d("2st click", "2");
                sortButton.setOnClickListener(thirdClickListner);

                sortButton.setText("By Rarity");


                SortRarity.PostTaskListener sortRarityListener = new SortRarity.PostTaskListener() {
                    @Override
                    public void onPostTask(ArrayList<Integer> result) {

                        Log.d("post mes", String.valueOf(result));

                        cardRecyclerAdapter.updateCustomList(result);
                        cardRecyclerAdapter.notifyDataSetChanged();


                    }
                };

                sortRarity = new SortRarity(cardRecyclerAdapter.getCustomList(),json,context,sortRarityListener);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    sortRarity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    sortRarity.execute();


                Snackbar.make(v,String.valueOf("second"),Snackbar.LENGTH_SHORT).show();

            }
        });
        thirdClickListner.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortRarity!= null && sortRarity.getStatus() == SortRarity.Status.RUNNING) {
                    sortRarity.cancel(true);
                }


                Log.d("2st click", "2");

                sortButton.setText("By Name");
                sortButton.setOnClickListener(firstClickListener);



                SortNames.PostTaskListener<ArrayList> sortNamesListener = new SortNames.PostTaskListener<ArrayList>() {
                    @Override
                    public void onPostTask(ArrayList<Integer> result) {

                        Log.d("post mes", String.valueOf(result));

                        cardRecyclerAdapter.updateCustomList(result);
                        cardRecyclerAdapter.notifyDataSetChanged();


                    }
                };

                sortNames = new SortNames(cardRecyclerAdapter.getCustomList(),json,context,sortNamesListener);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    sortNames.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    sortNames.execute();

                Snackbar.make(v,String.valueOf("third"),Snackbar.LENGTH_SHORT).show();

            }
        });


    }



}