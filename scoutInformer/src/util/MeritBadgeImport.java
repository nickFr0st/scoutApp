package util;

import scout.dbObjects.Counselor;
import scout.dbObjects.Requirement;

import java.util.List;

/**
 * Created by Nathanael on 10/25/2014
 */
public class MeritBadgeImport {
    List<Counselor> counselorList;
    List<Requirement> requirementsList;

    public List<Counselor> getCounselorList() {
        return counselorList;
    }

    public void setCounselorList(List<Counselor> counselorList) {
        this.counselorList = counselorList;
    }

    public List<Requirement> getRequirementsList() {
        return requirementsList;
    }

    public void setRequirementsList(List<Requirement> requirementsList) {
        this.requirementsList = requirementsList;
    }
}
