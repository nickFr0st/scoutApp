package constants;

/**
 * Created by Nathanael on 10/29/2014
 */
public enum ScoutTypeConst {
    BOY_SCOUT(10),
    VARSITY(20),
    VENTURE(30);

    int id;

    ScoutTypeConst(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
