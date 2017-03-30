package stats.stats;

public class Troop extends Cards {
    //MainActivity main = new MainActivity();
//    String JsonFile = main.loadJSONFromAsset();

    String name;
    String hitSpeed;
    String speed;
    String deployTime;
    String range;
    String target;
    String cost;
    String count;
    String rarity;
    String type;
    Integer[] health;
    Integer[] attack;
    Integer[] dps;
    Integer[] ctd;
    Integer[] childTroop;


    public Troop(String name, String hitSpeed, String speed, String deployTime, String range, String target,
                 String cost, String count, String rarity, String type, Integer[] health, Integer[] attack, Integer[] dps, Integer[] ctd, Integer[] childTroop) {
        super(name,type,cost,rarity);
        this.name = name;
        this.hitSpeed = hitSpeed;
        this.speed = speed;
        this.deployTime = deployTime;
        this.range = range;
        this.target = target;
        this.cost = cost;
        this.count = count;
        this.rarity = rarity;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.dps = dps;
        this.ctd = ctd;
        this.childTroop = childTroop;

    }

}









