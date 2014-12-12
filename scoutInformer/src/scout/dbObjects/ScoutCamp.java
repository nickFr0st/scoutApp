package scout.dbObjects;

/**
 * Created by Nathanael on 10/29/2014
 */
public class ScoutCamp {

    private int id;
    private int scoutId;
    private int campId;

    {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScoutId() {
        return scoutId;
    }

    public void setScoutId(int scoutId) {
        this.scoutId = scoutId;
    }

    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }
}
