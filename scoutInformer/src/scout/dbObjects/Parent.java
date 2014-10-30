package scout.dbObjects;

/**
 * Created by Nathanael on 10/29/2014
 */
public class Parent {
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_RELATION_LENGTH = 254;

    private int id;
    private int scoutId;
    private String name;
    private String relation;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
