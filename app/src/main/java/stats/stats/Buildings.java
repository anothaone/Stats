package stats.stats;


/**
 * Created by Mike on 6/3/2016.
 */


public class Buildings extends Cards {
    //MainActivity main = new MainActivity();
//    String JsonFile = main.loadJSONFromAsset();

    String spawn_speed;
 //   String deployTime;
    String lifeTime;
    String deployTime;
    String hitspeed;
    String targets;
    String range;
    Integer[] health;
    Integer[] attack;
    Integer[] dps;
    //String cost;

    public Buildings(String name, String type, String cost, String rarity, String spawn_speed, String lifeTime, String deployTime,
                     String hitSpeed,String targets,String range, Integer[] health, Integer[] attack, Integer[] dps) {
        //super(spawn_speed,deployTime,lifeTime);
        super(name,type,cost,rarity);
        this.spawn_speed = spawn_speed;
       // this.deployTime = deployTime;
        this.lifeTime = lifeTime;
        this.deployTime = deployTime;
        this.hitspeed = hitSpeed;
        this.targets = targets;
        this.range = range;
        this.health = health;
        this.attack = attack;
        this.dps = dps;


    }
}



