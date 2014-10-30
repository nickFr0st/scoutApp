package constants;

/**
 * Created by Nathanael on 10/25/2014
 */
public enum RequirementTypeConst {
    ADVANCEMENT(1),
    MERIT_BADGE(10),
    OTHER(20);

    int id;

    RequirementTypeConst(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
