package stats.stats;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by Mike on 1/27/2017.
 */
public class GetStats {

    String json;


    public GetStats(String json){
        this.json = json;

    }



    public Cards cardData(final String troopName) {
        Troop thisTroop = null;
        Troop troopCard = null;
        Cards card = null;
        Cards thisCard;
        //Hashtable<String, Troop> troopHash = new Hashtable<>();
        Hashtable<String, Cards> cardHash = new Hashtable<>();
        //Set JSON file to string
      //  String jsonData = loadJSONFromAsset();

        //= loadJSONFromAsset();

        try {
            // set json file to object
            JSONObject fileObj = new JSONObject(json);
            // find character name in json file
            JSONObject characterObj = fileObj.getJSONObject(troopName);
            // find stats in character obj
            String cost = characterObj.getString("cost");
            String rarity = characterObj.getString("rarity").toLowerCase();
            String type = characterObj.getString("type");

            thisCard = new Cards(troopName, type, cost, rarity);
            // Cards cards = new Cards(troopName,cost,rarity,type);

            cardHash.put(troopName, thisCard);
            card = cardHash.get(troopName);


            switch (type){

                case "Troop":
                    String hitSpeed = characterObj.getString("hit_speed");
                    String speed = characterObj.getString("speed");
                    String range = characterObj.getString("range");
                    String target = characterObj.getString("target");
                    String count = characterObj.getString("count");
                    String time = characterObj.getString("deploy_time");
                    //get lvl info. Find level array in json
                    final JSONArray lvlArray = characterObj.getJSONArray("levels");
                    //   Log.d("level", lvlArray.toString());
                    Hashtable<String, Integer> rarityLevelHash = new Hashtable<>();
                    rarityLevelHash.put("common", 12);
                    rarityLevelHash.put("rare", 10);
                    rarityLevelHash.put("epic", 7);
                    rarityLevelHash.put("legendary", 4);
                    for (Integer foo : rarityLevelHash.values()) {
                        //       Log.d("!!! LEVEL HASH", foo.toString());
                    }
                    //   Log.d("!!! RARITY", rarity);
                    //   Log.d("!!! RARITY LEVEL", rarityLevelHash.get(rarity).toString());

                    Integer[] lvlHealth = new Integer[rarityLevelHash.get(rarity)];
                    Integer[] lvlAttack = new Integer[rarityLevelHash.get(rarity)];
                    Integer[] lvlDPS = new Integer[rarityLevelHash.get(rarity)];
                    Integer[] lvlCTD = new Integer[rarityLevelHash.get(rarity)];
                    Integer[] childTroop = new Integer[rarityLevelHash.get(rarity)];
                    for(Integer i = 0; i < rarityLevelHash.get(rarity); i++) {
                        JSONObject lvlObj = lvlArray.getJSONObject(i);
                        Log.d("iteration", i.toString());
                        Log.d("level", lvlObj.toString());

                        // select specific lvl stat from lvlObj
                        lvlHealth[i] =  Integer.parseInt(lvlObj.getString("hitpoints").replace(",", "")); // TODO CONVERT TO INT
                        if(lvlObj.has("damage per second")) {
                            lvlDPS[i] = Integer.parseInt(lvlObj.getString("damage per second").replace(",", "")); // TODO CONVERT TO INT
                        }
                        if(lvlObj.has("area damage")){
                            lvlAttack[i] =  Integer.parseInt(lvlObj.getString("area damage").replace(",", "")); // TODO CONVERT TO INT
                        }
                        if(lvlObj.has("damage")){
                            lvlAttack[i] =  Integer.parseInt(lvlObj.getString("damage").replace(",", "")); // TODO CONVERT TO INT
                        }
                        if(lvlObj.has("crown tower damage")){
                            lvlCTD[i] =  Integer.parseInt(lvlObj.getString("crown tower damage").replace(",", "")); // TODO CONVERT TO INT
                        }
                        if(lvlObj.has("skeleton_level") ){
                            childTroop[i] =  Integer.parseInt(lvlObj.getString("skeleton_level").replace(",", "")); // TODO CONVERT TO INT
                        }


                    }

                  //  thisCard = new Cards(troopName, type, cost, rarity);
                    thisCard = new Troop(troopName, hitSpeed, speed, time, range, target, cost, count, rarity, type, lvlHealth, lvlAttack, lvlDPS, lvlCTD, childTroop);
                    cardHash.put(troopName, thisCard);
                    card = cardHash.get(troopName);


                    /*  cardHash.put(troopName, thisCard);
                    card = cardHash.get(troopName);
*/

                case "Building":

                    Log.d("FOUND !!",type);

                    break;


                case "Spell":


            }



        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
        }



        return card;
    }



    public Troop getTroop(final String troopName) {
        Troop thisTroop = null;
        Troop card = null;
        Hashtable<String,Troop> troopHash = new Hashtable<>();
        // Hashtable<String,Cards> cardHash = new Hashtable<>();
        //Set JSON file to string
        // String jsonData = loadJSONFromAsset() ;

        //= loadJSONFromAsset();

        try {
            // set json file to object
            JSONObject fileObj = new JSONObject(json);
            Log.d("file", fileObj.toString());
            // find character name in json file
            JSONObject characterObj = fileObj.getJSONObject(troopName);
            Log.d("char", characterObj.toString());
            // find stats in character obj
            String hitSpeed = characterObj.getString("hit speed");
            String speed = characterObj.getString("speed");
            String time = characterObj.getString("deploy time");
            String range = characterObj.getString("range");
            String target = characterObj.getString("target");
            String cost = characterObj.getString("cost");
            String count = characterObj.getString("count");
            String rarity = characterObj.getString("rarity").toLowerCase();
            String type = characterObj.getString("type");





            //get lvl info. Find level array in json
            final JSONArray lvlArray = characterObj.getJSONArray("levels");

            Log.d("level", lvlArray.toString());
            Log.d("level len", String.valueOf(lvlArray.length()));


            Hashtable<String, Integer> rarityLevelHash = new Hashtable<>();
            rarityLevelHash.put("common", 13);
            rarityLevelHash.put("rare", 11);
            rarityLevelHash.put("epic", 8);
            rarityLevelHash.put("legendary", 5);


            for (Integer foo : rarityLevelHash.values()) {
                //       Log.d("!!! LEVEL HASH", foo.toString());
            }


            Integer[] lvlHealth = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlAttack = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlDPS = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlCTD = new Integer[rarityLevelHash.get(rarity)];
            Integer[] childTroop = new Integer[rarityLevelHash.get(rarity)];
            for(Integer i = 0; i < rarityLevelHash.get(rarity); i++) {
                JSONObject lvlObj = lvlArray.getJSONObject(i);
                Log.d("iteration", i.toString());
                Log.d("level", lvlObj.toString());

                // select specific lvl stat from lvlObj
                lvlHealth[i] =  Integer.parseInt(lvlObj.getString("hitpoints").replace(",", "")); // TODO CONVERT TO INT
                if(lvlObj.has("damage per second")) {
                    lvlDPS[i] = Integer.parseInt(lvlObj.getString("damage per second").replace(",", "")); // TODO CONVERT TO INT
                }
                if(lvlObj.has("area damage")){
                    lvlAttack[i] =  Integer.parseInt(lvlObj.getString("area damage").replace(",", "")); // TODO CONVERT TO INT
                }
                if(lvlObj.has("damage")){
                    lvlAttack[i] =  Integer.parseInt(lvlObj.getString("damage").replace(",", "")); // TODO CONVERT TO INT
                }
                if(lvlObj.has("crown tower damage")){
                    lvlCTD[i] =  Integer.parseInt(lvlObj.getString("crown tower damage").replace(",", "")); // TODO CONVERT TO INT
                }
                if(lvlObj.has("skeleton_level") ){
                    childTroop[i] =  Integer.parseInt(lvlObj.getString("skeleton_level").replace(",", "")); // TODO CONVERT TO INT
                }


            }

            thisTroop = new Troop(troopName, hitSpeed, speed, time, range, target, cost, count, rarity, type, lvlHealth, lvlAttack, lvlDPS, lvlCTD, childTroop);
            troopHash = new Hashtable<>();

            troopHash.put(troopName, thisTroop);

            Log.d("HASH", troopHash.toString());


            card = troopHash.get(troopName);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
        }
        return card;
    }



    public Buildings getBuilding(final String troopName) {
        Buildings thisBuilding = null;
        Buildings card = null;
        Hashtable<String,Buildings> buildingHash = new Hashtable<>();
        Hashtable<String,Cards> cardHash = new Hashtable<>();
        //Set JSON file to string
        //String jsonData = loadJSONFromAsset() ;
        String deployTime = "0";
        String spawnSpeed = "0";
        String range = null;
        String target = null;
        String hitSpeed = null;
        //= loadJSONFromAsset();

        try {
            // set json file to object
            JSONObject fileObj = new JSONObject(json);
            // find character name in json file
            JSONObject characterObj = fileObj.getJSONObject(troopName);
            // find stats in character obj
            if (characterObj.has("spawn_speed")) {
                Log.d("CHECK", "TRUE");
                spawnSpeed = characterObj.getString("spawn_speed");
            }
            if (characterObj.has("deploy_time")) {
                deployTime = characterObj.getString("deploy_time");
            }
            if (characterObj.has("range")) {
                range = characterObj.getString("range");
            }
            if(characterObj.has("target"))  {
                target = characterObj.getString("target");
            }

            if(characterObj.has("hitspeed"))  {
                hitSpeed = characterObj.getString("hit_speed");
            }

            String lifeTime = characterObj.getString("lifetime");
            String cost = characterObj.getString("cost");
            String rarity = characterObj.getString("rarity").toLowerCase();
            String type = characterObj.getString("type");
            JSONArray lvlArray = characterObj.getJSONArray("levels");
            Log.d("LEVEL", lvlArray.toString());

            Hashtable<String, Integer> rarityLevelHash = new Hashtable<>();
            rarityLevelHash.put("common", 12);
            rarityLevelHash.put("rare", 10);
            rarityLevelHash.put("epic", 7);
            rarityLevelHash.put("legendary", 4);
            Integer[] lvlHealth = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlTroop = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlDPS = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlAttack = new Integer[rarityLevelHash.get(rarity)];

            for (Integer i = 0; i + 1 <= rarityLevelHash.get(rarity); i++) {
                JSONObject lvlObj = lvlArray.getJSONObject(i);
                Log.d("iteration", i.toString());
                Log.d("level", lvlObj.toString());
                // select specific lvl stat from lvlObj

                lvlHealth[i] = Integer.parseInt(lvlObj.getString("hitpoints").replace(",", "")); // TODO CONVERT TO INT

                if(characterObj.has("barbarian_level")){
                    lvlTroop[i] = Integer.parseInt(lvlObj.getString("barbarian_level").replace(",", ""));
                }
                if(characterObj.has("spear_goblin_level")){
                    lvlTroop[i] = Integer.parseInt(lvlObj.getString("spear_goblin_level").replace(",", ""));
                }      if(characterObj.has("skeleton_level")){
                    lvlTroop[i] = Integer.parseInt(lvlObj.getString("skeleton_level").replace(",", ""));
                }
                if(characterObj.has("damage")){
                    lvlAttack[i] = Integer.parseInt(lvlObj.getString("damage").replace(",", ""));
                }
                if(characterObj.has("damage_per_second")){
                    lvlDPS[i] = Integer.parseInt(lvlObj.getString("damage_per_second").replace(",", ""));
                }
            }
            thisBuilding = new Buildings(troopName, type, cost, rarity, spawnSpeed, lifeTime, deployTime,hitSpeed,target,range,lvlHealth,lvlAttack,lvlDPS);
            buildingHash = new Hashtable<>();

            buildingHash.put(troopName, thisBuilding);
            card = buildingHash.get(troopName);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
        }
        return card;
    }


    public Spell getSpell(final String troopName) {
        Spell thisSpell = null;
        Spell card = null;
        Hashtable<String,Spell> spellHash = new Hashtable<>();
        Hashtable<String,Cards> cardHash = new Hashtable<>();
        //Set JSON file to string
       // String jsonData = loadJSONFromAsset() ;
        String deployTime = "0";
        String spawnSpeed = "0";
        String range = null;
        String target = null;
        String hitSpeed = null;
        String count = null;
        String radius = null;
        String duration = null;

        try {
            // set json file to object
            JSONObject fileObj = new JSONObject(json);
            // find card name in json file
            JSONObject characterObj = fileObj.getJSONObject(troopName);
            // find default stats in card obj
            String cost = characterObj.getString("cost");
            String rarity = characterObj.getString("rarity").toLowerCase();
            String type = characterObj.getString("type");

            if (characterObj.has("radius")) {
                radius = characterObj.getString("radius");
            }
            if (characterObj.has("duration")) {
                duration = characterObj.getString("duration");
            }
            if (characterObj.has("range")) {
                range = characterObj.getString("range");
            }
            if(characterObj.has("count"))  {
                count = characterObj.getString("count");
            }

            //get level array of card
            JSONArray lvlArray = characterObj.getJSONArray("levels");
            //  Log.d("spell levels", lvlArray.toString());

            Hashtable<String, Integer> rarityLevelHash = new Hashtable<>();
            rarityLevelHash.put("common", 12);
            rarityLevelHash.put("rare", 10);
            rarityLevelHash.put("epic", 7);
            rarityLevelHash.put("legendary", 4);
            Integer[] lvlHealth = new Integer[rarityLevelHash.get(rarity)];
            String[] lvlDuration = new String[rarityLevelHash.get(rarity)];
            Integer[] lvlDPS = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlDamage = new Integer[rarityLevelHash.get(rarity)];
            Integer[] lvlTowerDamage = new Integer[rarityLevelHash.get(rarity)];

            for (Integer i = 0; i + 1 <= rarityLevelHash.get(rarity); i++) {
                JSONObject lvlObj = lvlArray.getJSONObject(i);
                //      Log.d("spell iteration", i.toString());
                //     Log.d("spell level", lvlObj.toString());
                //     Log.d("spell Object", characterObj.toString());

                JSONObject test = lvlArray.getJSONObject(1);
                //     Log.d("spell test", test.toString());

                // select specific lvl stat from lvlObj

                //   lvlHealth[i] = Integer.parseInt(lvlObj.getString("hitpoints").replace(",", "")); // TODO CONVERT TO INT

                if(lvlObj.has("damage")){
                    lvlDamage[i] = Integer.parseInt(lvlObj.getString("damage").replace(",", ""));
                }
                if(lvlObj.has("area_damage")){
                    lvlDamage[i] = Integer.parseInt(lvlObj.getString("area_damage").replace(",", ""));
                }
                if(lvlObj.has("duration")){
                    lvlDuration[i] = (lvlObj.getString("duration"));
                }
                if(lvlObj.has("damage_per_second")){
                    lvlDPS[i] = Integer.parseInt(lvlObj.getString("damage_per_second").replace(",", ""));
                }
                if(lvlObj.has("towerDamage")){
                    lvlTowerDamage[i] = Integer.parseInt(lvlObj.getString("crown_tower_damage").replace(",", ""));
                }
            }
            thisSpell = new Spell(troopName,  cost, count, radius,
                    duration,rarity,type,lvlDamage, lvlDPS, lvlTowerDamage,lvlDuration);

            spellHash = new Hashtable<>();

            spellHash.put(troopName, thisSpell);

            card = spellHash.get(troopName);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
        }
        return card;
    }


}
