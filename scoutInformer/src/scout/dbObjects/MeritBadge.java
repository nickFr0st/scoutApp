package scout.dbObjects;

/**
 * Created by Nathanael on 10/25/2014
 */
public class MeritBadge {
    public static final int COL_NAME_LENGTH = 254;
    public static final int COL_IMG_PATH_LENGTH = 254;

    private int id;
    private String name;
    private String imgPath;
    private boolean requiredForEagle;

    {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isRequiredForEagle() {
        return requiredForEagle;
    }

    public void setRequiredForEagle(boolean requiredForEagle) {
        this.requiredForEagle = requiredForEagle;
    }
}
