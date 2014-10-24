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

    public static List<Integer> findIdsByParentId(int parentId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Integer> requirementIdList = new ArrayList<Integer>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM requirement WHERE parentId = " + parentId);

            while (rs.next()) {
                requirementIdList.add(rs.getInt(KeyConst.REQUIREMENT_ID.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return requirementIdList;
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

    public static void save(Requirement requirement) {
        if (requirement == null) {
            return;
        }

        if (requirement.getId() < 0) {
            requirement.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO requirement VALUES( " + requirement.getId() + ",'" + requirement.getName() + "', '" + requirement.getDescription() + "'," + requirement.getTypeId() + "," + requirement.getParentId() + ")");
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void updateList(List<Requirement> requirementList, int parentId) {
        for (Requirement requirement : requirementList) {
            if (requirement.getId() < 0) {
                save(requirement);
            } else {
                update(requirement);
            }
        }

        List<Integer> existingRequirementIdList = findIdsByParentId(parentId);
        List<Integer> tempList = new ArrayList<Integer>();

        for (Requirement requirement : requirementList) {
            if (requirement.getId() < 0) {
                continue;
            }

            tempList.add(requirement.getId());
        }

        if (Util.isEmpty(tempList)) {
            deleteList(existingRequirementIdList);
            return;
        }

        List<Integer> deletionList = new ArrayList<Integer>();

        for (Integer id : existingRequirementIdList) {
            if (!tempList.contains(id)) {
                deletionList.add(id);
            }
        }

        deleteList(deletionList);
    }

    private static void update(Requirement requirement) {
        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE requirement SET name = '" + requirement.getName() + "', description = '" + requirement.getDescription() + "', typeId = " + requirement.getTypeId() + ", parentId = " + requirement.getParentId() + " WHERE id = " + requirement.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importReqList(List<Requirement> reqList) {
        if (Util.isEmpty(reqList)) {
            return;
        }

        for (Requirement requirement : reqList) {
            Requirement req = findByNameAndParentId(requirement.getName(), requirement.getParentId());

            if (req != null) {
                requirement.setId(req.getId());
                update(requirement);
            } else {
                save(requirement);
            }
        }
    }

    private static Requirement findByNameAndParentId(String name, int parentId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Requirement requirement = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement WHERE parentId = " + parentId + " AND name LIKE " + name);

            if (rs.next()) {
                requirement = new Requirement();
                requirement.setId(rs.getInt(KeyConst.REQUIREMENT_ID.getName()));
                requirement.setName(rs.getString(KeyConst.REQUIREMENT_NAME.getName()));
                requirement.setDescription(rs.getString(KeyConst.REQUIREMENT_DESCRIPTION.getName()));
                requirement.setParentId(rs.getInt(KeyConst.REQUIREMENT_PARENT_ID.getName()));
                requirement.setTypeId(rs.getInt(KeyConst.REQUIREMENT_TYPE_ID.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return requirement;
    }

    public static ArrayList<String> findAllNamesByParentId(int parentId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        ArrayList<String> reqNameList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM requirement WHERE parentId = " + parentId);

            while (rs.next()) {
                reqNameList.add(rs.getString(KeyConst.REQUIREMENT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return reqNameList;
    }
}
