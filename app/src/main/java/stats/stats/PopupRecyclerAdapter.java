package stats.stats;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by Mike on 7/12/2016.
 */
public class PopupRecyclerAdapter extends RecyclerView.Adapter<PopupRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter  {


    private final SharedPreferences mPref;
    private HashMap<String,String> mData;
    private Context context;
    private String saveName;
    ArrayList<String> keys = new ArrayList<>();
    ArrayList values = new ArrayList<>();
    private AdapterCallback mAdapterCallback;
    View v;


    public PopupRecyclerAdapter(Context context, HashMap mData, SharedPreferences pref) {
        // mContext = c;
        this.context = context;
        this.mData = mData;
        this.mPref = pref;
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create View
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         v = inflater.inflate(R.layout.saves_list, parent, false);
        // v.setBackgroundResource(R.drawable.wood);

        //set attributes
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {



     /*   holder.setClickListener(new ItemClickListener(){

            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + keys.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "#" + position + " - " + keys.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        for(Object key: mData.keySet()){

            keys.add(key.toString());

        }

        //put hash values into an array
        final ArrayList<String> currentDeckID = new ArrayList<String>(mData.values());

        //split array id values
        String[] split = currentDeckID.get(holder.getAdapterPosition()).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        Log.d("split", String.valueOf(split));


        // put id into array as Integers
        final ArrayList<Integer> cardImageIds = new ArrayList<Integer>();

        for(String id:split)
        {
            cardImageIds.add(Integer.parseInt(id));

        }

        Picasso.with(holder.card1.getContext())
                .load(Integer.parseInt(split[0]))
                .resize(87,100)
                .into(holder.card1);

        Picasso.with(holder.card2.getContext())
                .load(Integer.parseInt(split[1]))
                .resize(87,100)
                .into(holder.card2);

        Picasso.with(holder.card3.getContext())
                .load(Integer.parseInt(split[2]))
                .resize(87,100)
                .into(holder.card3);

        Picasso.with(holder.card4.getContext())
                .load(Integer.parseInt(split[3]))
                .resize(87,100)
                .into(holder.card4);

        Picasso.with(holder.card5.getContext())
                .load(Integer.parseInt(split[4]))
                .resize(87,100)
                .into(holder.card5);

        Picasso.with(holder.card6.getContext())
                .load(Integer.parseInt(split[5]))
                .resize(87,100)
                .into(holder.card6);

        Picasso.with(holder.card7.getContext())
                .load(Integer.parseInt(split[6]))
                .resize(87,100)
                .into(holder.card7);

        Picasso.with(holder.card8.getContext())
                .load(Integer.parseInt(split[7]))
                .resize(87,100)
                .into(holder.card8);



        holder.saveTitle.setText(keys.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapterCallback.PopUpCallback(v, cardImageIds, keys.get(holder.getAdapterPosition()));


            }
        });

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(keys, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(keys, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Log.d("pos", String.valueOf(position));
        Log.d("pos", mData.get(keys.get(position)));

        final String deletedKey = keys.get(position);
        Log.d("pos", deletedKey);
        final String deletedDeck = mData.get(keys.get(position));
        Log.d("pos", deletedDeck);

        mAdapterCallback.undoCallBack(deletedKey,deletedDeck);
      //  mAdapterCallback.undoCallBack(keys.get(position),mData);





        // delete data from shared prefs
        final SharedPreferences.Editor editPref = mPref.edit();
        editPref.remove(keys.get(position)).apply();
        // remove value from hash table with key
        mData.remove(keys.get(position));
        // remove the used key
        keys.remove(position);
        // update view
        notifyItemRemoved(position);
        notifyDataSetChanged();



        Log.d("count", String.valueOf(getItemCount()));
        empty();


  /*   Snackbar aSnackbar = Snackbar.make(v,"Deleted Saved Selection.", Snackbar.LENGTH_LONG).
                setAction("Undo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        editPref.putString(deletedKey,deletedDeck);
                        editPref.apply();

                        mData.put(deletedKey,deletedDeck);
                        Log.d("pos put", deletedKey);
                        notifyDataSetChanged();

                    }

                });
        aSnackbar.show();*/

    }

    public interface AdapterCallback {
        void PopUpCallback(View view, ArrayList<Integer> newCards, String deckName);
        void empty(boolean empty);
        void undoCallBack(String keys, String data);
       // void undoCallBack(int pos, HashMap<String,String> data);



    }

    public void empty(){

        if (getItemCount() == 0){
            mAdapterCallback.empty(true);
        }
    }

    @Override
    public int getItemCount() {

        return this.mData.size();

    }

    public HashMap<String,String> getCustomList() {
        return mData;
    }


    public void updateCustomList(HashMap mData) {
        this.mData = mData;

    }
/*
    public void addDeck(String key, Object deck) {

        this.mData.put(key,deck);

        this.mData = mData;

    }*/






    public class ViewHolder extends RecyclerView.ViewHolder {

        private  ItemClickListener clickListener;
        public TextView saveTitle;
        public ImageView card1;
        public ImageView card2;
        public ImageView card3;
        public ImageView card4;
        public ImageView card5;
        public ImageView card6;
        public ImageView card7;
        public ImageView card8;


        public ViewHolder(View itemView) {
            super(itemView);
            saveTitle = (TextView) itemView.findViewById(R.id.saveNamePop);
            card1 = (ImageView) itemView.findViewById(R.id.savedCard1);
            card2 = (ImageView) itemView.findViewById(R.id.savedCard2);
            card3 = (ImageView) itemView.findViewById(R.id.savedCard3);
            card4 = (ImageView) itemView.findViewById(R.id.savedCard4);
            card5 = (ImageView) itemView.findViewById(R.id.savedCard5);
            card6 = (ImageView) itemView.findViewById(R.id.savedCard6);
            card7 = (ImageView) itemView.findViewById(R.id.savedCard7);
            card8 = (ImageView) itemView.findViewById(R.id.savedCard8);

        }



    }


}