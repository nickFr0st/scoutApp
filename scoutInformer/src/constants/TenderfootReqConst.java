package constants;

/**
 * Created by Malloch on 6/7/14
 */
public enum TenderfootReqConst {
    REQ_1("1", "Present yourself to your leader, properly dressed, before going on an overnight camping trip. Show the camping gear you will use. <BR>Show the right way to pack and carry it.", 1),
    REQ_2("2", "Spend at least one night on a patrol or troop campout. Sleep in a tent you have helped pitch.", 1),
    REQ_3("3", "On the campout, assist in preparing and cooking one of your patrol's meals. Tell why it is important for each patrol member to share <BR>in meal preparation and cleanup, and explain the importance of eating together.", 1),
    REQ_4A("4a", "Demonstrate how to whip and fuse the ends of a rope.", 1),
    REQ_4B("4b", "Demonstrate you know how to tie the following knots and tell what their uses are: two half hitches and the taut-line hitch.", 1),
    REQ_4C("4c", "Using the EDGE method, teach another person how to tie the square knot.", 1),
    REQ_5("5", "Explain the rules of safe hiking, both on the highway and cross-country, during the day and at night. Explain what to do if you are lost.", 1),
    REQ_6("6", "Demonstrate how to display, raise, lower, and fold the American flag.", 1),
    REQ_7("7", "Repeat from memory and explain in your own words the Scout Oath, Law, motto, and slogan.", 1),
    REQ_8("8", "Know your patrol name, give the patrol yell, and describe your patrol flag.", 1),
    REQ_9("9", "Explain the importance of the buddy system as it relates to your personal safety on outings and in your neighborhood. <BR>Describe what a bully is and how you should respond to one.", 1),
    REQ_10A("10a", "Record your best in the following tests:<BR>" +
            "Current results<BR>" +
            "Push-ups ________<BR>" +
            "Pull-ups ________<BR>" +
            "Sit-ups ________<BR>" +
            "Standing long jump (______ ft. ______ in.)<BR>" +
            "1/4 mile walk/run _____________<BR>" +
            "30 days later<BR>" +
            "Push-ups ________<BR>" +
            "Pull-ups ________<BR>" +
            "Sit-ups ________<BR>" +
            "Standing long jump (______ ft. ______ in.)<BR>" +
            "1/4 mile walk/run _____________", 1),
    REQ_10B("10b", "Show improvement in the activities listed in requirement 10a after practicing for 30 days.", 1),
    REQ_11("11", "Identify local poisonous plants; tell how to treat for exposure to them.", 1),
    REQ_12A("12a", "Demonstrate how to care for someone who is choking.", 1),
    REQ_12B("12b", "Show first aid for the following:<BR>" +
            "- Simple cuts and scrapes<BR>" +
            "- Blisters on the hand and foot<BR>" +
            "- Minor (thermal/heat) burns or scalds (superficial, or first degree)<BR>" +
            "- Bites or stings of insects and ticks<BR>" +
            "- Venomous snakebite<BR>" +
            "- Nosebleed<BR>" +
            "- Frostbite and sunburn", 1),
    REQ_13("13", "Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. Discuss four specific examples of how <BR>you have lived the points of the Scout Law in your daily life.", 1),
    REQ_14("14", "Participate in a Scoutmaster conference.", 1),
    REQ_15("15", "Complete your board of review.", 1);

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
