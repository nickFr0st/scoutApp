package scout;

/**
 * Created by Malloch on 6/7/14
 */
public class Rank {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMG_PATH = "imgPath";
    public static final String SCOUT_ID = "scoutId";
    public static final String RANK_ID = "rankId"; // this refers to the RankConst
    public static final String COMPLETED_RANK_REQUIREMENTS = "completedRequirements";  // will be a string of ids delimited by ',' use rankId to determine which ReqConst to use.

    private int id;
    private String name;
    private String imgPath;
    private int scoutId;
    private int rankId;
    private String completedRequirements;

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

    public int getScoutId() {
        return scoutId;
    }

    public void setScoutId(int scoutId) {
        this.scoutId = scoutId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getCompletedRequirements() {
        return completedRequirements;
    }

    public void setCompletedRequirements(String completedRequirements) {
        this.completedRequirements = completedRequirements;
    }
}
