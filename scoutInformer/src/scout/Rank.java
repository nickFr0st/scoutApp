package scout;

/**
 * Created by Malloch on 6/7/14
 */
public class Rank {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMG_PATH = "imgPath";

    private int id;
    private String name;
    private String imgPath;

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
}
