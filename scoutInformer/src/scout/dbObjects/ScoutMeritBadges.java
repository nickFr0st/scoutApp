package scout.dbObjects;

import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class ScoutMeritBadges {
    public static final int COL_REQ_LIST_LENGTH = 254;

    private int id;
    private int scoutId;
    private int meritBadgeId;
    private List<String> reqIdList;

    {
        id = -1;
    }

    public int getScoutId() {
        return scoutId;
    }

    public void setScoutId(int scoutId) {
        this.scoutId = scoutId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeritBadgeId() {
        return meritBadgeId;
    }

    public void setMeritBadgeId(int meritBadgeId) {
        this.meritBadgeId = meritBadgeId;
    }

    public List<String> getReqIdList() {
        return reqIdList;
    }

    public void setReqIdList(List<String> reqIdList) {
        this.reqIdList = reqIdList;
    }
}
