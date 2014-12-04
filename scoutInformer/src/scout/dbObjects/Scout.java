package scout.dbObjects;

import java.util.Date;

/**
 * Created by Malloch on 7/10/14
 */
public class Scout {
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_PARENT_NAME_LENGTH = 90;

    private int id;
    private String name;
    private Date birthDate;
    private int currentAdvancementId;
    private Date advancementDate;
    private String position;
    private Date postionDate;
    private int typeId;

    public Scout() {
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

    public int getCurrentAdvancementId() {
        return currentAdvancementId;
    }

    public void setCurrentAdvancementId(int currentAdvancementId) {
        this.currentAdvancementId = currentAdvancementId;
    }

    public Date getAdvancementDate() {
        return advancementDate;
    }

    public void setAdvancementDate(Date advancementDate) {
        this.advancementDate = advancementDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getPostionDate() {
        return postionDate;
    }

    public void setPostionDate(Date postionDate) {
        this.postionDate = postionDate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
