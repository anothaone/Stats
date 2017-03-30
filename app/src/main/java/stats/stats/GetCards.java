package stats.stats;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Mike on 1/26/2017.
 */
public class GetCards {
        private Context context;

        public GetCards(Context context){

            this.context = context;

        }

        ArrayList<Integer> imgIDs() {

            String[] cards = {"miner", "lava_hound", "guards", "fire_spirits", "royal_giant", "skeletons", "bomber", "archers", "knight", "three_musketeers",
                    "baby_dragon", "barbarians", "dark_prince", "minion_horde", "princess",
                    "ice_wizard", "golem", "wizard", "hog_rider", "giant", "giant_skeleton",
                    "pekka", "ballon", "prince", "minions", "goblins", "witch",
                    "mini_pekka", "musketeer", "valkyrie", "skeleton_army",
                    "spear_goblin", "poison", "mirror", "zap", "freeze", "rage", "rocket", "goblin_barrel",
                    "fireball", "lightning", "arrows", "sparky", "furnace", "mortar", "elixir_collector", "xbow", "cannon", "barbarian_hut",
                    "inferno_tower", "bomb_tower", "goblin_hut", "tesla", "tombstone", "elite_barbarians"};
            int[] idArr = new int[cards.length];
            ArrayList<Integer> mData = new ArrayList<>();
            int i = 0;
            for (String card : cards) {
                    idArr[i] = context.getResources().getIdentifier(card, "drawable", context.getPackageName());
                    mData.add(idArr[i]);
                    i++;
                }
            return mData;

        }

}

   /* public final static ArrayList<Integer> cardImg = new ArrayList<>(Arrays.asList(R.drawable.arrows, R.drawable.archers, R.drawable.skeleton_army,
            R.drawable.baby_dragon, R.drawable.ballon, R.drawable.giant,
            R.drawable.mini_pekka, R.drawable.tesla, R.drawable.dark_prince, R.drawable.zap, R.drawable.bomb_tower));

    public final static ArrayList<Integer> deck = new ArrayList<>(Arrays.asList(R.drawable.arrows, R.drawable.archers, R.drawable.skeleton_army,
            R.drawable.baby_dragon, R.drawable.ballon, R.drawable.giant,
            R.drawable.mini_pekka, R.drawable.tesla));*/
