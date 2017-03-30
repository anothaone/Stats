package stats.stats;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Listener, PopupRecyclerAdapter.AdapterCallback{

    private SharedPreferences pref;
    private String[] mNavigationDrawerItemTitles;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;

    private CharSequence mTitle;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView rView;
    private RecyclerView savesView;

    PopupRecyclerAdapter savedDeckAdapter;
    private RecyclerViewAdapter randomDeckAdapter;
    private RecyclerViewAdapter cardRecyclerAdapter;
    private ArrayList<ArrayList<Integer>> savedDeckArray = new ArrayList<>();


    private Hashtable<String, String> savedDeckList = new Hashtable<>();
    private HashMap loadHash = new HashMap<>();
    private ArrayList<Integer> cards;

    PopupWindow pw;
    PopupWindow savePopUp;
    TextView toolbarDeckTitle;
    AverageElixir averageElixir = new AverageElixir(this);
    private  Handler mFragmentHandler;
    private ArrayList<Integer> test;
    SortElixir sortElixir;
    SortNames sortNames;
    SortRarity sortRarity;
    View loadPopUpView;
    Snackbar aSnackbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        setuNavDrawer();
        setAdapters();
        LoadJsonData loadJsonData = new LoadJsonData();


        LoadSortButton loadSortButton = new LoadSortButton(cardRecyclerAdapter,this,loadJsonData.loadJSONFromAsset(this),this);
        loadSortButton.loadSortButton();


        SortElixir.PostTaskListener<ArrayList> sortElixirListener = new SortElixir.PostTaskListener<ArrayList>() {
            @Override
            public void onPostTask(ArrayList<Integer> result) {

                Log.d("post mes", String.valueOf(result));

                cardRecyclerAdapter.updateCustomList(result);
                cardRecyclerAdapter.notifyDataSetChanged();


            }
        };

        sortElixir = new SortElixir(cardRecyclerAdapter.getCustomList(),loadJSONFromAsset(this),this,sortElixirListener);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            sortElixir.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            sortElixir.execute();


        pref = getSharedPreferences("info", MODE_PRIVATE);






        GetStats stats = new GetStats(loadJSONFromAsset(this));
       // Troop wizard = stats.getTroop("wizard");

        Cards wiz = stats.cardData("wizard");


         Cards wizzard = stats.cardData("wizard");


        Log.d("list", String.valueOf(randomDeckAdapter.getCustomList()));


        Spell zap = stats.getSpell("zap");
        Cards zap1 = stats.cardData("zap");


        Log.d("cost",wizzard.name);
        Log.d("cost",wizzard.name);

        Log.d("cost",wiz.cost);


    }


    public  void setAdapters(){

        // get card drawables / put into an array list
        GetCards getCards = new GetCards(this);
        cards = getCards.imgIDs();
        // create random stating deck
        Set<Integer> randomSet = new HashSet<>();
        while(randomSet.size() <= 7){
            randomSet.add((Integer) cards.toArray()[new Random().nextInt(cards.size())]);
            Log.d("Random set", randomSet.toString());

        }

        ArrayList<Integer> rngDeck = new ArrayList<>(randomSet);


        // remove random deck cards from card list
        cards.removeAll(randomSet);



        randomDeckAdapter = new RecyclerViewAdapter(this, rngDeck, this);
        randomDeckAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                averageElixir.averageElixir(randomDeckAdapter.getCustomList());


               Log.d("zchange","changed");
            }
        });

        rView = (RecyclerView)findViewById(R.id.DeckRecyclerView);
        GridLayoutManager lLayout = new GridLayoutManager(this, 4);
        lLayout.setOrientation(GridLayoutManager.VERTICAL);

        Log.d("random deck", String.valueOf(rView));

        Log.d("random deck lay", String.valueOf(lLayout));

        assert rView != null;
        rView.setOnDragListener(randomDeckAdapter.getDragInstance());
        rView.setLayoutManager(lLayout);
        rView.setAdapter(randomDeckAdapter);



        cardRecyclerAdapter = new RecyclerViewAdapter(this, cards, this);
        RecyclerView cardView = (RecyclerView)findViewById(R.id.CardRecyclerView);
        GridLayoutManager cardLayout = new GridLayoutManager(this, 4);
        cardLayout.setOrientation(GridLayoutManager.VERTICAL);



        assert cardView != null;
        cardView.setOnDragListener(randomDeckAdapter.getDragInstance());
        cardView.setLayoutManager(cardLayout);
        cardView.setAdapter(cardRecyclerAdapter);


    }


    public  void setuNavDrawer(){


        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        setupToolbar();

        DataModel[] drawerItem = new DataModel[5];

        drawerItem[0] = new DataModel(R.drawable.random, "Random Deck");
        drawerItem[1] = new DataModel(R.drawable.save, "save");
        drawerItem[2] = new DataModel(R.drawable.load, "load");
        drawerItem[3] = new DataModel(R.drawable.load, "records");
        drawerItem[4] = new DataModel(R.drawable.load, " sort by elixir");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert mDrawerLayout != null;
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();


    }




    @Override
    public void PopUpCallback(View view, ArrayList<Integer> newCards, String deckName) {

        Toast.makeText(this,"clicked"  +newCards,Toast.LENGTH_SHORT).show();
        randomDeckAdapter.updateCustomList(newCards);
        randomDeckAdapter.notifyDataSetChanged();


        cards.removeAll(newCards);

        cardRecyclerAdapter.updateCustomList(cards);
        cardRecyclerAdapter.notifyDataSetChanged();

         toolbarDeckTitle = (TextView) findViewById(R.id.toolbar_title);

        assert toolbarDeckTitle != null;
        toolbarDeckTitle.setText(deckName);

        mDrawerLayout.closeDrawers();
        pw.dismiss();

        Log.d("call back",view.toString());

        Log.d("adapter count", String.valueOf(savesView.getAdapter().getItemCount()));

        if(savesView.getAdapter().getItemCount() == 0){

            pw.dismiss();

        }


    }

    @Override
    public void empty(boolean empty) {

      //  Log.d("dur", String.valueOf(aSnackbar.getDuration()));
      //  pw.dismiss();

     /*   Log.d("view", String.valueOf(aSnackbar.isShown()));

        if (!aSnackbar.isShown()) {

         //   pw.dismiss();
        }

        Log.d("empty", String.valueOf(empty));*/
    }

    @Override
    public void undoCallBack(final String key, final String deck) {
/*

       LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View loadPopUpView = inflater.inflate(R.layout.popup, (ViewGroup) rView.getParent(), false);
*/

        Log.d("key", key);


       // Log.d("data", String.valueOf(data));


       // final String delDeck = data.get(key);
        // final HashMap aMap = new HashMap();


       // Log.d("deck", key + String.valueOf(savedDeckAdapter.getCustomList().get(key)));


       aSnackbar =  Snackbar.make(loadPopUpView,"Deleted ", Snackbar.LENGTH_LONG).
                setAction("Undo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                      //  Log.d("key2", position);
                     //   Log.d("key2", key);

                        Log.d("deck test", String.valueOf(savedDeckAdapter.getCustomList()));

                       // aMap.put(key,delDeck);
                       // Log.d("amap", String.valueOf(aMap));

                        pref.edit().putString(key,deck).apply();

               /*         // add data to shared prefs
                        SharedPreferences.Editor editPref = pref.edit();
                        editPref.putString(key,deck);
                        //  editPref.putString(key,delDeck);
                        editPref.apply();
*/
                        HashMap<String,String> test = savedDeckAdapter.getCustomList();
                        test.put(key,deck);
                        savedDeckAdapter.updateCustomList(test);

                        //savedDeckAdapter.getCustomList().put(key,data);
                      //  savedDeckAdapter.getCustomList().put(key,deck);

                       // savedDeckAdapter.updateCustomList(data);
                        savedDeckAdapter.notifyDataSetChanged();




                        /*

                        editPref.remove(keys.get(position)).apply();
                        // remove value from hash table with key
                        mData.remove(keys.get(position));
                        // remove the used key
                        keys.remove(position);
                        // update view

                      //  savedDeckAdapter.updateCustomList(data);

                        //

                       // savedDeckAdapter.getCustomList().put(key,undoDeck);
*/

                    }

                });
        aSnackbar.show();


    /*    if(savedDeckAdapter.getItemCount() == 0){

            pw.dismiss();


        }*/



    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);



        }

    }

        private void selectItem(int position, final View v) {

        Log.d("view id ", String.valueOf(v.getId()));

        savesView = (RecyclerView)findViewById(R.id.savedRec);


        switch (position) {

            // random deck
            case 0:
                //generate random deck
                GetCards getCards = new GetCards(this);
                cards = getCards.imgIDs();

                // create random stating deck
                final Set<Integer> randomSet = new HashSet<>();
                while(randomSet.size() <= 7){
                    randomSet.add((Integer) cards.toArray()[new Random().nextInt(cards.size())]);
                    Log.d("Random set", randomSet.toString());

                }
                ArrayList<Integer> rngDeck = new ArrayList<>(randomSet);



                randomDeckAdapter.updateCustomList(rngDeck);
                randomDeckAdapter.notifyDataSetChanged();


                cards.removeAll(randomSet);

                cardRecyclerAdapter.updateCustomList(cards);
               // cardRecyclerAdapter.notifyDataSetChanged();


                SortElixir.PostTaskListener<ArrayList> sortElixirListener = new SortElixir.PostTaskListener<ArrayList>() {
                    @Override
                    public void onPostTask(ArrayList<Integer> result) {

                        Log.d("post mes", String.valueOf(result));

                        cardRecyclerAdapter.updateCustomList(result);
                        cardRecyclerAdapter.notifyDataSetChanged();


                    }
                };

                sortElixir = new SortElixir(cards,loadJSONFromAsset(this),this,sortElixirListener);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    sortElixir.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    sortElixir.execute();

                toolbarDeckTitle = (TextView) findViewById(R.id.toolbar_title);


                assert toolbarDeckTitle != null;
                toolbarDeckTitle.setText("Random Deck");


                // averageElixer(randomDeckAdapter.getCustomList());


                //  averageElixir.averageElixir(randomDeckAdapter.getCustomList());

                Toast.makeText(this,"Random deck", Toast.LENGTH_LONG).show();

                mDrawerLayout.closeDrawers();


                break;
            // save deck
            case 1:
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View savePopUpView = inflater.inflate(R.layout.save_popup, (ViewGroup) v.getParent(), false);


                // create a 300px width and 470px height PopupWindow
                savePopUp = new PopupWindow(savePopUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                // display the popup in the center
                //pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                savePopUp.showAtLocation(v,Gravity.CENTER_VERTICAL,0,0);
                savePopUp.setFocusable(true);
                savePopUp.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                savePopUp.update();




                final Button savebutton = (Button) savePopUpView.findViewById(R.id.saveButton);
                final TextView savedDeckTitle = (TextView) savePopUpView.findViewById(R.id.deckNameEdit);

                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(0,InputMethodManager.HIDE_IMPLICIT_ONLY);
                if (!savePopUp.isShowing()){
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                            .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

                }

                savebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        // save current deck in random deck adapter to sharded prefrences
                        //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context

                        final SharedPreferences.Editor editor = pref.edit();

                        if (pref.contains(savedDeckTitle.getText().toString())){

                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    view.getContext());
                            builder.setTitle("Overwrite Deck");
                            builder.setMessage("are you sure you want to overwrite deck " + savedDeckTitle.getText() + "?");
                            builder.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {


                                        }
                                    });
                            builder.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            editor.putString(savedDeckTitle.getText().toString(),randomDeckAdapter.getCustomList().toString());
                                            editor.apply();

                                            Snackbar.make(view.getRootView(),"saved",Snackbar.LENGTH_SHORT);
                                            Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                                        }
                                    });


                            builder.show();



                            Log.d("same name", savedDeckTitle.getText().toString());


                        }else {


                            Log.d("title text", String.valueOf(savedDeckTitle.getText()));

                            if (savedDeckTitle.getText().toString().trim().isEmpty()) {

                                Log.d("null", String.valueOf(savedDeckTitle.getText()));

                                Random genRand = new Random();
                                int i = genRand.nextInt(50);
                                savedDeckTitle.setText("Deck" + i);

                                Log.d("null not", String.valueOf(savedDeckTitle.getText()));


                            }


                            savedDeckList.put(savedDeckTitle.getText().toString(), randomDeckAdapter.getCustomList().toString());

                            Log.d("list", String.valueOf(randomDeckAdapter.getCustomList()));
                            Log.d("list 0", String.valueOf(randomDeckAdapter.getCustomList().get(0)));


                            Log.d("null not", String.valueOf(savedDeckTitle.getText()));
                            // ArrayList<String> savedDeckList = new ArrayList<String>();

                            // put string value of current deck
                            editor.putString(savedDeckTitle.getText().toString(), randomDeckAdapter.getCustomList().toString());
                            //finally, when you are done saving the values, call the commit() method.
                            editor.apply();
                            Toast.makeText(v.getContext(), "saved", Toast.LENGTH_LONG).show();

                            savedDeckArray.add(randomDeckAdapter.getCustomList());

                            Log.d("saved", String.valueOf(savedDeckArray));
                            Log.d("hash", String.valueOf(savedDeckList));


                            savedDeckTitle.setText("");
                            savePopUp.dismiss();

                        }

                    }
                });
                mDrawerLayout.closeDrawers();

                break;
            // load deck
            case 2:

                //  String text = pref.getString("any_field","default");

                if (pref.getAll().isEmpty()){

                    Snackbar.make(v,"No decks saved",Snackbar.LENGTH_SHORT).show();

         /*           if (pw.isShowing()){

                        pw.dismiss();
                    }*/



                }
                else {


                    Snackbar.make(v,"Swipe to delete saves",Snackbar.LENGTH_SHORT).show();



                    if(pref.getAll() != null){
                        Map<String,?> keys = pref.getAll();
                        for(Map.Entry<String, ?> entry : keys.entrySet()){


                            loadHash.put(entry.getKey(),entry.getValue());
                            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());

                        }
                    }

                    Log.d("Load hash", String.valueOf(loadHash));






                    savedDeckAdapter = new PopupRecyclerAdapter(this,loadHash,pref) ;





                    View parent = findViewById(R.id.rl_custom_layout);


                    inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    loadPopUpView = inflater.inflate(R.layout.popup, (ViewGroup) v.getParent(), false);

                    savesView = (RecyclerView) loadPopUpView.findViewById(R.id.savedRec);




                    LinearLayoutManager savesLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);

                    Log.d("saves rec par", String.valueOf(parent));

                    Log.d("saves rec", String.valueOf(savesView));

                    Log.d("saves lay", String.valueOf(savesLayout));


                    // savesLayout.setOrientation(LinearLayoutManager.VERTICAL);


                    assert savesView != null;
                    savesView.setLayoutManager(savesLayout);
                    savesView.setAdapter(savedDeckAdapter);

                    ItemTouchHelper mItemTouchHelper;

                    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(savedDeckAdapter);
                    ItemTouchHelper.Callback call = new SimpleItemTouchHelperCallback(savedDeckAdapter);
                    mItemTouchHelper = new ItemTouchHelper(callback);
                    mItemTouchHelper.attachToRecyclerView(savesView);

                    DisplayMetrics dm = new DisplayMetrics();
                    this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                    int hight = dm.heightPixels / 2;

                    // create a 300px width and 470px height PopupWindow
                    pw = new PopupWindow(loadPopUpView, LinearLayout.LayoutParams.WRAP_CONTENT,hight, true);
                    // display the popup in the center
                    pw.showAtLocation(v, Gravity.CENTER, 0, 0);


                    mDrawerLayout.closeDrawers();




                    //  Snackbar.make(v,"Loaded deck",Snackbar.LENGTH_SHORT).show();


                }

                break;

            case 3:
                inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View records = inflater.inflate(R.layout.records, (ViewGroup) v.getParent(), false);


                savePopUp = new PopupWindow(records, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                // display the popup in the center
                //pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                savePopUp.showAtLocation(v,Gravity.CENTER_VERTICAL,0,0);
                savePopUp.setFocusable(true);
                savePopUp.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                savePopUp.update();




                break;

            case 4:


                SortElixir.PostTaskListener<ArrayList> elixirTaskListener = new SortElixir.PostTaskListener<ArrayList>() {
                    @Override
                    public void onPostTask(ArrayList<Integer> result) {

                        Log.d("post mes", String.valueOf(result));

                        cardRecyclerAdapter.updateCustomList(result);
                        cardRecyclerAdapter.notifyDataSetChanged();


                    }
                };

                SortElixir sortElixir = new SortElixir(cardRecyclerAdapter.getCustomList(),loadJSONFromAsset(getApplicationContext()),getApplicationContext(),elixirTaskListener);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    sortElixir.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    sortElixir.execute();


/*

                SortByElixir sortByElixir = new SortByElixir(cardRecyclerAdapter.getCustomList(),loadJSONFromAsset(getApplicationContext()),getApplicationContext());


                cardRecyclerAdapter.updateCustomList(sortByElixir.doInBackground());
                cardRecyclerAdapter.notifyDataSetChanged();
*/


/*
                sorting sortByRarity = new sorting(cardRecyclerAdapter.getCustomList(),loadJSONFromAsset(getApplicationContext()),getApplicationContext());


                cardRecyclerAdapter.updateCustomList(sortByRarity.doInBackground());
                cardRecyclerAdapter.notifyDataSetChanged();*/

              /*  sortByCost sort = new sortByCost(cardRecyclerAdapter.getCustomList(),loadJSONFromAsset(this),this);
                sort.doInBackground();
*/
          /*      sort.doInBackground();
            *//*    ArrayList<Integer> sortedIds = new ArrayList<>();


                for(int i= 0; i < sort.doInBackground().size(); i++ ){

                    sortedIds.add(i, sort.doInBackground().get(i).drawableID);

                  *//**//*  Log.d("col", String.valueOf(sort.byElixir().get(i).drawableID) + String.valueOf(sort.byElixir().get(i).name +

                            String.valueOf(sort.byElixir().get(i).cost)));*//**//*

                }*/



/*               cardRecyclerAdapter.updateCustomList(sortByRarity.doInBackground());
                cardRecyclerAdapter.notifyDataSetChanged();*/


                break;

            default:
                break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }



    public String loadJSONFromAsset(Context c) {
        String json;
        //   c = getApplicationContext();
        try {
            //  Log.d("test", "t");
            InputStream is = c.getAssets().open("building_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }





    @Override
    public void setEmptyList(boolean visibility) {


    }
}

