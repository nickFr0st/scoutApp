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
    ADVANCEMENT_DEFAULT_FLAG("defaultFlag"),

    // Scout Table
    SCOUT_ID("id"),
    SCOUT_NAME("name"),
    SCOUT_BIRTH_DATE("birthDate"),
    SCOUT_ADVANCEMENT_ID("advancementId"),
    SCOUT_ADVANCEMENT_DATE("advancementDate"),
    SCOUT_POSITION("position"),
    SCOUT_POSITION_DATE("positionDate"),
    SCOUT_TYPE_ID("typeId"),

    // Scout Camp Table
    SCOUT_CAMP_ID("id"),
    SCOUT_CAMP_CAMP_ID("campId"),
    SCOUT_CAMP_SCOUT_ID("scoutId"),

    // Contact Table
    CONTACT_ID("id"),
    CONTACT_SCOUT_ID("scoutId"),
    CONTACT_TYPE_ID("typeId"),
    CONTACT_NAME("name"),
    CONTACT_RELATION("relation"),
    CONTACT_DATA("data"),

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
    MERIT_BADGE_REVISION_DATE("revisionDate"),

    // Camp Table
    CAMP_ID("id"),
    CAMP_NAME("name"),
    CAMP_LOCATION("location"),
    CAMP_DURATION("duration"),
    CAMP_DATE("date"),
    CAMP_NOTE("note"),

    // Other Award Table
    AWARD_ID("id"),
    AWARD_NAME("name"),
    AWARD_IMG_PATH("imgPath"),

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
