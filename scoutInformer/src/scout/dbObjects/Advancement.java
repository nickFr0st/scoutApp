package scout.dbObjects;

/**
 * Created by Malloch on 7/10/14
 */
public class Advancement {
    private int id;
    private String troop;
    private String council;
    private String troopLeader;
    private String troopName;

    public Advancement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTroop() {
        return troop;
    }

    public void setTroop(String troop) {
        this.troop = troop;
    }

    public String getCouncil() {
        return council;
    }

    public void setCouncil(String council) {
        this.council = council;
    }

    public String getTroopLeader() {
        return troopLeader;
    }

    public void setTroopLeader(String troopLeader) {
        this.troopLeader = troopLeader;
    }

    public String getTroopName() {
        return troopName;
    }

    public void setTroopName(String troopName) {
        this.troopName = troopName;
    }
}
