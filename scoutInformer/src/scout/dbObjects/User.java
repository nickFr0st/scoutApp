package scout.dbObjects;

/**
 * Created by Malloch on 7/4/14
 */
public class User {
    private int id;
    private String troopNumber;
    private String troopLeader;
    private String council;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTroopNumber() {
        return troopNumber;
    }

    public void setTroopNumber(String troopNumber) {
        this.troopNumber = troopNumber;
    }

    public String getTroopLeader() {
        return troopLeader;
    }

    public void setTroopLeader(String troopLeader) {
        this.troopLeader = troopLeader;
    }

    public String getCouncil() {
        return council;
    }

    public void setCouncil(String council) {
        this.council = council;
    }
}
