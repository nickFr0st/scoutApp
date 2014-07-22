package util;

import constants.KeyConst;
import scout.dbObjects.Requirement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malloch on 7/12/14
 */
public class LogicRequirement {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }

    public static List<Requirement> findAllByParentId(int parentId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Requirement> requirementList = new ArrayList<Requirement>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement WHERE parentId = " + parentId);

            while (rs.next()) {
                Requirement requirement = new Requirement();

                requirement.setId(rs.getInt(KeyConst.REQUIREMENT_ID.getName()));
                requirement.setName(rs.getString(KeyConst.REQUIREMENT_NAME.getName()));
                requirement.setDescription(rs.getString(KeyConst.REQUIREMENT_DESCRIPTION.getName()));
                requirement.setParentId(rs.getInt(KeyConst.REQUIREMENT_PARENT_ID.getName()));
                requirement.setTypeId(rs.getInt(KeyConst.REQUIREMENT_TYPE_ID.getName()));

                requirementList.add(requirement);
            }

        } catch (Exception e) {
            return null;
        }

        return requirementList;
    }
}
