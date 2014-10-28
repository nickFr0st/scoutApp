package scout.dbObjects;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private List<String> parentNameList;
    private Integer currentAdvancementId;
    private List<Integer> completedAwardIdList;
    private List<Integer> completedMeritBadgeIdList;
    private Map<Integer, List<Integer>> inProgressAwardMap;
    private Map<Integer, List<Integer>> inProgressMeritBadgeMap;

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

    public List<String> getParentNameList() {
        return parentNameList;
    }

    public void setParentNameList(List<String> parentNameList) {
        this.parentNameList = parentNameList;
    }

    public Integer getCurrentAdvancementId() {
        return currentAdvancementId;
    }

    public void setCurrentAdvancementId(Integer currentAdvancementId) {
        this.currentAdvancementId = currentAdvancementId;
    }

    public List<Integer> getCompletedAwardIdList() {
        return completedAwardIdList;
    }

    public void setCompletedAwardIdList(List<Integer> completedAwardIdList) {
        this.completedAwardIdList = completedAwardIdList;
    }

    public List<Integer> getCompletedMeritBadgeIdList() {
        return completedMeritBadgeIdList;
    }

    public void setCompletedMeritBadgeIdList(List<Integer> completedMeritBadgeIdList) {
        this.completedMeritBadgeIdList = completedMeritBadgeIdList;
    }

    public Map<Integer, List<Integer>> getInProgressAwardMap() {
        return inProgressAwardMap;
    }

    public void setInProgressAwardMap(Map<Integer, List<Integer>> inProgressAwardMap) {
        this.inProgressAwardMap = inProgressAwardMap;
    }

    public Map<Integer, List<Integer>> getInProgressMeritBadgeMap() {
        return inProgressMeritBadgeMap;
    }

    public void setInProgressMeritBadgeMap(Map<Integer, List<Integer>> inProgressMeritBadgeMap) {
        this.inProgressMeritBadgeMap = inProgressMeritBadgeMap;
    }
}
