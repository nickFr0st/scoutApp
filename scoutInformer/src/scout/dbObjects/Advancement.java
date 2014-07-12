package scout.dbObjects;

/**
 * Created by Malloch on 7/10/14
 */
public class Advancement {
    private int id;
    private String name;
    private String imgPath;

    public Advancement() {
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
}
