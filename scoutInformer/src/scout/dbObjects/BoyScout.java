package scout.dbObjects;

import java.util.Date;

/**
 * Created by Malloch on 7/10/14
 */
public class BoyScout {
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_PARENT_NAME_LENGTH = 90;
    public static final int COL_PHONE_NUMBER_LENGTH = 20;

    private int id;
    private String name;
    private Date birthDate;
    private String phoneNumber;
    private int currentAdvancementId;
    private int typeId;

    public BoyScout() {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCurrentAdvancementId() {
        return currentAdvancementId;
    }

    public void setCurrentAdvancementId(int currentAdvancementId) {
        this.currentAdvancementId = currentAdvancementId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
