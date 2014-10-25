package scout.dbObjects;

/**
 * Created by Malloch on 7/10/14
 */
public class Counselor {
    public static final int COL_NAME_LENGTH = 89;
    public static final int COL_PHONE_NUMBER_LENGTH = 19;

    private int id;
    private String name;
    private String phoneNumber;

    public Counselor() {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
