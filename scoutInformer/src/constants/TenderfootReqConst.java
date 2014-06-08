package constants;

/**
 * Created by Malloch on 6/7/14
 */
public enum TenderfootReqConst {
    REQ_1("1:", "Present yourself to your leader, properly dressed, <BR>before going on an overnight camping trip. Show the camping gear you will use. <BR>Show the right way to pack and carry it.", 1),
    REQ_2("2:", "Spend at least one night on a patrol or troop campout. <BR>Sleep in a tent you have helped pitch.", 1),
    REQ_3("3:", "test", 1),
    REQ_4A("4a:", "test", 1),
    REQ_4B("4b:", "test", 1),
    REQ_4C("4c:", "test", 1),
    REQ_5("5:", "test", 1),
    REQ_6("6:", "test", 1),
    REQ_7("7:", "test", 1),
    REQ_8("8:", "test", 1),
    REQ_9("9:", "test", 1),
    REQ_10A("10a:", "test", 1),
    REQ_10B("10b:", "test", 1),
    REQ_11("11:", "test", 1),
    REQ_12A("12a:", "test", 1),
    REQ_12B("12b:", "test", 1),
    REQ_13("13:", "test", 1),
    REQ_14("14:", "test", 1),
    REQ_15("15:", "test", 1);

    private String name;
    private String description;
    private int type;  // this will become a typeConst

    TenderfootReqConst(String name, String description, int type) {
        this.name =name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }
}
