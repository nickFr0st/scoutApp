package scout.dbObjects;

import java.util.List;

/**
 * Created by nmalloch on 6/5/2014
 */
public class Scout {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String COMPLETED_REQUIREMENTS = "completedRequirements";
    public static final String CURRENT_RANK_ID = "currentRankId";
    public static final String POSITION = "position";

    private int id;
    private String name;
    private int age;
    private List<String> completedRequirements;
    private int currentRankId;
    private String position;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCompletedRequirements() {
        return completedRequirements;
    }

    public void setCompletedRequirements(List<String> completedRequirements) {
        this.completedRequirements = completedRequirements;
    }

    public int getCurrentRankId() {
        return currentRankId;
    }

    public void setCurrentRankId(int currentRankId) {
        this.currentRankId = currentRankId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
