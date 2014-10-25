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
    ADVANCEMENT_IMG_PATH("imgPath"),

    // Counselor Table
    COUNSELOR_ID("id"),
    COUNSELOR_NAME("name"),
    COUNSELOR_BADGE_ID("badgeId"),
    COUNSELOR_PHONE_NUMBER("phoneNumber"),

    // MeritBadge Table
    MERIT_BADGE_ID("id"),
    MERIT_BADGE_NAME("name"),
    MERIT_BADGE_IMG_PATH("imgPath"),
    MERIT_BADGE_REQ_FOR_EAGLE("requiredForEagle"),

    // Requirement Table
    REQUIREMENT_ID("id"),
    REQUIREMENT_NAME("name"),
    REQUIREMENT_DESCRIPTION("description"),
    REQUIREMENT_TYPE_ID("typeId"),
    REQUIREMENT_PARENT_ID("parentId");

    private String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
