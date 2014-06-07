package constants;

/**
 * Created by nmalloch on 6/7/14
 */
public enum RankConst {
    NEW_SCOUT(1, "New Scout", "/images/badge_new_scout.png"),
    TENDERFOOT(2, "Tenderfoot", "/images/badge_tenderfoot.png"),
    SECOND_CLASS(3, "Second Class", "/images/badge_second_class.png"),
    FIRST_CLASS(4, "First Class", "/images/badge_first_class.png"),
    STAR(5, "Star", "/images/badge_star.png"),
    LIFE(6, "Life", "/images/badge_life.png"),
    EAGLE(7, "Eagle", "/images/badge_eagle.png");

    private static final String NEW_SCOUT_NAME = "New Scout";
    private static final String TENDERFOOT_NAME = "Tenderfoot";
    private static final String SECOND_CLASS_NAME = "Second Class";
    private static final String FIRST_CLASS_NAME = "First Class";
    private static final String STAR_NAME = "Star";
    private static final String LIFE_NAME = "Life";
    private static final String EAGLE_NAME = "Eagle";

    private int id;
    private String name;
    private String imgPath;

    RankConst(int id, String name, String imgPath) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public static String[] getConstList() {
        return new String[] {NEW_SCOUT.getName(), TENDERFOOT.getName(), SECOND_CLASS.getName(), FIRST_CLASS.getName(), STAR.getName(), LIFE.getName(), EAGLE.getName()};
    }

    public static RankConst getConstById(int id) {
        switch (id) {
            case 1:
                return NEW_SCOUT;
            case 2:
                return TENDERFOOT;
            case 3:
                return SECOND_CLASS;
            case 4:
                return FIRST_CLASS;
            case 5:
                return STAR;
            case 6:
                return LIFE;
            case 7:
                return EAGLE;
            default:
                return NEW_SCOUT;
        }
    }

    public static RankConst getConstByName(String rankName) {
        if (rankName.equals(NEW_SCOUT_NAME)) {
            return NEW_SCOUT;
        } else if (rankName.equals(TENDERFOOT_NAME)) {
            return TENDERFOOT;
        } else if (rankName.equals(SECOND_CLASS_NAME)) {
            return SECOND_CLASS;
        } else if (rankName.equals(FIRST_CLASS_NAME)) {
            return FIRST_CLASS;
        } else if (rankName.equals(STAR_NAME)) {
            return STAR;
        } else if (rankName.equals(LIFE_NAME)) {
            return LIFE;
        } else if (rankName.equals(EAGLE_NAME)) {
            return EAGLE;
        } else {
            return NEW_SCOUT;
        }
    }
}
