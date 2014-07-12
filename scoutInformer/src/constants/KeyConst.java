package constants;

/**
 * Created by Malloch on 6/9/14
 */
public enum KeyConst {
    // DB
    DB_URL("dbUrl"),
    DB_NAME("dbName"),
    DB_USER_NAME("dbUserName"),
    DB_PASSWORD("dbPassword"),
    SAVED_USER("savedUser"),

    // User Table
    USER_TROOP_LEADER("troopLeader"),
    USER_TROOP_NUMBER("troop"),
    USER_ID("id"),
    USER_COUNCIL("council"),
    USER_TROOP_NAME("troopName"),

    // Advancement Table
    ADVANCEMENT_ID("id"),
    ADVANCEMENT_NAME("name"),
    ADVANCEMENT_IMG_PATH("imgPath");

    private String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
