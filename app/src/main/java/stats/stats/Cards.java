package stats.stats;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**averageElixir
 * Created by Mike on 6/17/2016.
 */
public class Cards implements Comparable<Cards> {

    String name;
    String type;
    String cost;
    String rarity;
    Integer drawableID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setDrawableID(Integer drawableID) {
        this.drawableID = drawableID;
    }

    public Integer getDrawableID() {
        return drawableID;
    }


    private Context context;

/*    Integer[] health;
    Integer[] attack;
    Integer[] dps;*/


    public Cards(String name, String type, String cost, String rarity) {
        this.name = name;
        this.type = type;
        //   this.deployTime = deployTime;
        this.cost = cost;
        //   this.count = count;
        this.rarity = rarity;
        this.drawableID = drawableID;
    }

    @Override
    public int compareTo(@NonNull Cards otherCard) {

        //String otherCost = otherCard.getCost();
        //String otherCost;

        return Integer.parseInt(this.cost) - Integer.parseInt(otherCard.getCost());

    }

    public static Comparator<Cards> raritayComaparator = new Comparator<Cards>() {

        @Override
        public int compare(Cards aCard, Cards otherCard) {

            if (aCard.getRarity().equals("common")){
                return -1;
            }else if(aCard.rarity.equals("rare")){
                return 0;
            }else if(aCard.rarity.equals("epic")){
                return 1;
            }else if (aCard.rarity.equals("legendary")){

                return 2;
            }

            return 0;
        }
    };







}


