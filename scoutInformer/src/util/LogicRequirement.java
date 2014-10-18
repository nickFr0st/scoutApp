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

    public static void saveList(List<Requirement> requirementList) {
        if (Util.isEmpty(requirementList)) {
            return;
        }

        for (Requirement requirement : requirementList) {

            if (requirement.getId() < 0) {
                requirement.setId(getNextId());
            }

            try {
                Statement statement = connector.createStatement();
                statement.executeUpdate("INSERT INTO requirement VALUES( " + requirement.getId() + ",'" + requirement.getName() + "', '" + requirement.getDescription() + "'," + requirement.getTypeId() + "," + requirement.getParentId() + ")");
            } catch (Exception e) {
                // save error
            }
        }
    }

    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM requirement");

            if(rs.next()) {
                id = rs.getInt(KeyConst.REQUIREMENT_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static void deleteList(List<Integer> requirementIdList) {
        if (Util.isEmpty(requirementIdList)) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM requirement WHERE id IN (" + Util.listToString(requirementIdList) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
