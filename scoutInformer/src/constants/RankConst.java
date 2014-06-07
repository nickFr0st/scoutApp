package constants;

/**
 * Created by nmalloch on 6/7/14
 */
public enum RankConst {
    NEW_SCOUT(1, "New Scout"),
    TENDERFOOT(2, "Tenderfoot"),
    SECOND_CLASS(3, "Second Class"),
    FIRST_CLASS(4, "First Class"),
    STAR(5, "Star"),
    LIFE(6, "Life"),
    EAGLE(7, "Eagle");

    private int id;
    private String name;

    RankConst(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String[] getConstList() {
        return new String[] {NEW_SCOUT.getName(), TENDERFOOT.getName(), SECOND_CLASS.getName(), FIRST_CLASS.getName(), STAR.getName(), LIFE.getName(), EAGLE.getName()};
    }

    public static String getConstNameById(int id) {
        switch (id) {
            case 1:
                return NEW_SCOUT.getName();
            case 2:
                return TENDERFOOT.getName();
            case 3:
                return SECOND_CLASS.getName();
            case 4:
                return FIRST_CLASS.getName();
            case 5:
                return STAR.getName();
            case 6:
                return LIFE.getName();
            case 7:
                return EAGLE.getName();
            default:
                return NEW_SCOUT.getName();
        }
    }
}
