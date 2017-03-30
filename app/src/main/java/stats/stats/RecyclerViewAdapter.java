package stats.stats;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mike on 7/12/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Integer> mData;
    private Context context;
    Listener mListener;


    public RecyclerViewAdapter(Context context, ArrayList<Integer> mData, Listener listener) {
        // mContext = c;
        this.context = context;
        this.mData = mData;
        this.mListener = listener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView gridImg;

        public ViewHolder(View itemView) {
            super(itemView);
            gridImg = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    public void add(int position, Integer item) {
        mData.add(position, item);
        notifyItemChanged(position);
    }

    public void remove(Integer item) {
        int position = mData.indexOf(item);
        mData.remove(item);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(ArrayList<Integer> myDataset) {
        mData = myDataset;
    }






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create View
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.recycler_row, parent, false);
       // v.setBackgroundResource(R.drawable.wood);

        //set attributes
        return new ViewHolder(v);
    }





    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Integer imgId = mData.get(position);
        //holder.gridImg.setImageResource(imgId);

        // holder.gridImg.setBackgroundColor(Color.BLACK);
        holder.gridImg.setOnDragListener(getDragInstance());


        Picasso.with(holder.gridImg.getContext())
                .load(imgId)
                .resize(260, 300)
                .into(holder.gridImg);
        holder.gridImg.setId(mData.get(position));
        holder.gridImg.setTag(position);


        holder.gridImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                shadowBuilder.getView().setAlpha(0);

                View.DragShadowBuilder shadow = ImageDragShadowBuilder.fromResource(context,view.getId());


               // ImageDragShadowBuilder.fromResource(context,view.getId());
                view.startDrag(data, shadowBuilder, view, 0);


                view.setVisibility(View.INVISIBLE);
                Log.d("LONG PRESS !~! ", String.valueOf(view.getId()));


                return true;
            }
        });


        holder.gridImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  remove(imgId);
                holder.gridImg.getId();


                /*Log.d("CLICKED !~! ", String.valueOf(v.getId()));
                Snackbar.make(v,"test",Snackbar.LENGTH_LONG).show();*/


            }
        });

        holder.gridImg.setOnTouchListener(new View.OnTouchListener() {
            int count = 0;
            long t1 = 0, t2 = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                switch (action){
                case MotionEvent.ACTION_DOWN:
                if(count == 0) {
                    t1 = System.currentTimeMillis();
                }
                if(count == 1){
                    t2 = System.currentTimeMillis();
                }
                count++;
                if(count > 1) count = 0;
                Log.d("DoubleTapImageView", "down t1: " + String.valueOf(t1) + " t2: " + String.valueOf(t2) + "," + String.valueOf(t2-t1));
                if(Math.abs(t2 - t1) < 300){
                    t1 = t2 = 0; count = 0;

                    Snackbar.make(v,String.valueOf("Double"),Snackbar.LENGTH_SHORT).show();

                }

                break;

            }

                int x = (int)event.getRawX();
                int y = (int)event.getRawY();

                Log.d("x", String.valueOf(x));
                Log.d("y", String.valueOf(y));

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    public DragListener getDragInstance() {
        if (mListener != null) {
            return new DragListener(mListener);
        } else {
            Log.e("Route Adapter: ", "Initialize listener first!");
            return null;
        }
    }

        public ArrayList<Integer> getCustomList() {
            return mData;
        }
    public void updateCustomList(ArrayList<Integer> mData) {
        this.mData = mData;

    }

    public interface Listener {
        void setEmptyList(boolean visibility);
    }

    public static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);
        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }

    public static boolean isTouchInsideOfView(View view, Point touchPosition) {
        Rect rScroll = new Rect();
        view.getGlobalVisibleRect(rScroll);
        return isTouchInsideOfRect(touchPosition, rScroll);
    }

    public static boolean isTouchInsideOfRect(Point touchPosition, Rect rScroll) {
        return touchPosition.x > rScroll.left && touchPosition.x < rScroll.right //within x axis / width
                && touchPosition.y > rScroll.top && touchPosition.y < rScroll.bottom; //withing y axis / height
    }


    Rect outRect = new Rect();
    int[] location = new int[2];


    private boolean isViewInBounds(View view, int x, int y){
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }




    public class DragListener implements View.OnDragListener {

        boolean isDropped = false;
        Listener mListener;
        ArrayList<Integer> deckList;

        public DragListener(Listener listener) {
            this.mListener = listener;
        }

        @Override
        public boolean onDrag(final View v, DragEvent event) {

            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:

                   /* View aCard = (View) event.getLocalState();

                    aCard.setAlpha(0);
*/




                    break;

                case DragEvent.ACTION_DRAG_LOCATION:

                   final View dragCard = (View) event.getLocalState();
/*


                    Log.d("card v", String.valueOf(v));
                    Log.d("card drag", String.valueOf(dragCard));

                    int test1[] = new int[2];
                    v.getLocationOnScreen(test1);

                    int test2[] = new int[2];
                    dragCard.getLocationOnScreen(test2);


                    final Point touchPoint = getTouchPositionFromDragEvent(dragCard, event);

                    Point locationPoint = getTouchPositionFromDragEvent(v, event);


                    if (touchPoint.x == locationPoint.x || touchPoint.y == locationPoint.x) {

                        Log.d("eq drag", String.valueOf(touchPoint.x) + ":" + String.valueOf(touchPoint.y));

                        Log.d("eq target", String.valueOf(locationPoint.x) + ":" + String.valueOf(locationPoint.y));

                    }

                    Log.d("card touch", String.valueOf(touchPoint));

                    Log.d("location source", String.valueOf(touchPoint));

                    Log.d("location  target ", String.valueOf(locationPoint));

                    if (v.getClass().getName().equalsIgnoreCase("android.support.v7.widget.AppCompatImageView")) {

                        Bool bool = new Bool();
                        PropertyChangeListener mProp = new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent event) {

                                Log.d("bool value", "test");
                                isTouchInsideOfView(dragCard, touchPoint);
                            }
                        };
                        bool.addPropertyChangeListener(mProp);
                        bool.setValue(  isTouchInsideOfView(dragCard, touchPoint));
                        bool.getValue();



                        if (isTouchInsideOfView(dragCard, touchPoint) || v.getId() != R.id.DeckRecyclerView) {

                            Log.d("out", String.valueOf(isTouchInsideOfView(dragCard, touchPoint)));


                        }
                    }
                        Log.d("true", String.valueOf(isTouchInsideOfView(dragCard, touchPoint)));
*/



                    break;


                case DragEvent.ACTION_DRAG_ENTERED:

                    View aCard = (View) event.getLocalState();

                    aCard.setAlpha(0);


                    break;

                case DragEvent.ACTION_DRAG_EXITED:
/*
                    aCard = (View) event.getLocalState();
                    aCard.setAlpha(1);*/


                    break;

                case DragEvent.ACTION_DROP:
                    isDropped = true;
                    Log.d("VIEW v", String.valueOf(v));
                    Log.d("VIEW v", String.valueOf(v.getId()));

                    View parent = (View) v.getParent();
                    Log.d("view v parent", String.valueOf(parent));

                    Log.d("view v gramps", String.valueOf(parent.getParent()));

                    View viewObject = (View) event.getLocalState();
                    Log.d("VIEW Object", String.valueOf(viewObject));
                    viewObject.setAlpha(1);

                    View test = (View) viewObject.getParent();
                    Log.d("VIEW obj parent", String.valueOf(test));


                    Log.d("VIEW obj parent gramps", String.valueOf(test.getParent()));



                    if (((View) v.getParent()).getId() == ((View) viewObject.getParent()).getId()){


                       // viewObject.setVisibility(View.INVISIBLE);
                       // v.setVisibility(View.INVISIBLE);
                        int position = -1;


                        RecyclerView target = (RecyclerView) v.getParent();

                        Log.d("target", String.valueOf(target));

                        Log.d("v tag", String.valueOf(v.getTag()));
                        Log.d("view tag", String.valueOf(viewObject.getTag()));

                        RecyclerViewAdapter targetAdapter = (RecyclerViewAdapter) target.getAdapter();


                        Log.d("same", ((View) v.getParent()).getId() + ":" + ((View) viewObject.getParent()).getId());



                        deckList = targetAdapter.getCustomList();


                        deckList.remove((int) viewObject.getTag());
                        deckList.add((int) viewObject.getTag(),v.getId());

                        deckList.remove((int) v.getTag());
                        deckList.add((int) v.getTag(),viewObject.getId());


                        targetAdapter.updateCustomList(deckList);
                        targetAdapter.notifyDataSetChanged();



                    }


                    if (((View) viewObject.getParent()).getId() == R.id.CardRecyclerView && v.getId() != R.id.DeckRecyclerView

                            && viewObject.getParent() != v.getParent() && viewObject.getParent() != v )
                    {


                        RecyclerView target;
                        RecyclerView source;

                        target = (RecyclerView) v.getParent();
                        source = (RecyclerView) v.getRootView().findViewById(R.id.CardRecyclerView);

                        Log.d("source", String.valueOf(source));
                        Log.d("target", String.valueOf(target));

                        RecyclerViewAdapter targetAdapter = (RecyclerViewAdapter) target.getAdapter();
                        RecyclerViewAdapter sourceAdapter = (RecyclerViewAdapter) source.getAdapter();


                        ArrayList<Integer> deckList = targetAdapter.getCustomList();


                        if (deckList.contains(v.getId())){

                            ArrayList<Integer> cardList = sourceAdapter.getCustomList();

                            Log.d("deck", String.valueOf(deckList));
                            Log.d("obj tag", String.valueOf(viewObject.getTag()));
                            Log.d("v tag ", String.valueOf(v.getTag()));

                            int position = (int) viewObject.getTag();
                            int deckPosition = (int) v.getTag();

                            cardList.remove(position);
                            cardList.add(position, v.getId());
                            sourceAdapter.updateCustomList(cardList);
                            sourceAdapter.notifyDataSetChanged();




                            deckList.remove(deckPosition);
                            deckList.add((int) v.getTag(),viewObject.getId());
                            targetAdapter.updateCustomList(deckList);
                            targetAdapter.notifyDataSetChanged();

                            Log.d("deck list", String.valueOf(deckList));


                            Log.d("FOUND", String.valueOf(v.getId()));




                        }

                    }



                    break;

                case DragEvent.ACTION_DRAG_ENDED:

                    aCard = (View) event.getLocalState();

                    aCard.setAlpha(1);



                    break;

                default:
                    break;
            }

            if (!isDropped) {
                View vw = (View) event.getLocalState();
                vw.setVisibility(View.VISIBLE);
            }

            return true;
        }
    }
}




