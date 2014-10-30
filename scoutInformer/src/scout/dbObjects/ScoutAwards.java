package scout.dbObjects;

import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class ScoutAwards {
    public static final int COL_REQ_LIST_LENGTH = 254;

    private int id;
    private int scoutId;
    private int awardId;
    private List<String> reqIdList;

    {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScoutId() {
        return scoutId;
    }

    public void setScoutId(int scoutId) {
        this.scoutId = scoutId;
    }

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public List<String> getReqIdList() {
        return reqIdList;
    }

    public void setReqIdList(List<String> reqIdList) {
        this.reqIdList = reqIdList;
    }
}
