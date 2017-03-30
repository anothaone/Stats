package stats.stats;

/**
 * Created by Mike on 6/11/2016.
 */
public class Spell extends Cards {


    String radius;
    String duration;

    String count;
    Integer[] towerDamage;
    Integer[] damage;
    Integer[] dps;
    String[] lvlDuration;


    public Spell(String name,
                  String cost, String count, String radius, String duration,
                 String rarity, String type, Integer[] attack, Integer[] dps,Integer[] towerDamage, String[] lvlDuration){
        super(name,type,cost,rarity);

        this.count = count;
        this.radius = radius;
        this.duration = duration;
        this.damage = attack;
        this.dps = dps;
        this.towerDamage = towerDamage;
        this.lvlDuration = lvlDuration;


    }


}



