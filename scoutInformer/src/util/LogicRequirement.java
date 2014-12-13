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
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static List<Requirement> findAllByParentIdTypeId(int parentId, int typeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Requirement> requirementList = new ArrayList<Requirement>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement WHERE parentId = " + parentId + " AND typeId = " + typeId);

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

    public static List<Integer> findIdsByParentIdTypeId(int parentId, int typeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Integer> requirementIdList = new ArrayList<Integer>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM requirement WHERE parentId = " + parentId + " AND typeId = " + typeId);

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
            save(requirement);
        }
    }

    public static synchronized void save(Requirement requirement) {
        try {
            synchronized (lock) {
                if (!saveRequirement(requirement)) {
                    lock.wait(Util.WAIT_TIME);
                    connector.checkForDataBaseConnection();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveRequirement(Requirement requirement) {
        if (requirement == null) {
            return true;
        }

        if (requirement.getId() < 0) {
            requirement.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO requirement VALUES( " + requirement.getId() + ",'" + requirement.getName() + "', '" + requirement.getDescription() + "'," + requirement.getTypeId() + "," + requirement.getParentId() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    public static synchronized void deleteList(List<Integer> requirementIdList) {
        try {
            synchronized (lock) {
                if (!deleteRequirementList(requirementIdList)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteRequirementList(List<Integer> requirementIdList) {
        if (Util.isEmpty(requirementIdList)) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM requirement WHERE id IN (" + Util.listToString(requirementIdList) + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void updateList(List<Requirement> requirementList, int parentId, int typeId) {
        for (Requirement requirement : requirementList) {
            if (requirement.getId() < 0) {
                save(requirement);
            } else {
                update(requirement);
            }
        }

        List<Integer> existingRequirementIdList = findIdsByParentIdTypeId(parentId, typeId);
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

    private static synchronized void update(Requirement requirement) {
        try {
            synchronized (lock) {
                if (!updateRequirement(requirement)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateRequirement(Requirement requirement) {
        if (requirement == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE requirement SET name = '" + requirement.getName() + "', description = '" + requirement.getDescription() + "', typeId = " + requirement.getTypeId() + ", parentId = " + requirement.getParentId() + " WHERE id = " + requirement.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void importReqList(List<Requirement> reqList) {
        if (Util.isEmpty(reqList)) {
            return;
        }

        for (Requirement requirement : reqList) {
            Requirement req = findByNameParentIdAndTypeId(requirement.getName(), requirement.getParentId(), requirement.getTypeId());
            if (req != null) {
                requirement.setId(req.getId());
                update(requirement);
            } else {
                save(requirement);
            }
        }
    }

    private static Requirement findByNameParentIdAndTypeId(String name, int parentId, int typeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Requirement requirement = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement WHERE parentId = " + parentId + " AND name LIKE '" + name + "' AND typeId = " + typeId);

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

    public static ArrayList<String> findAllNamesByParentIdAndTypeId(int parentId, int typeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        ArrayList<String> reqNameList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM requirement WHERE parentId = " + parentId + " AND typeId = " + typeId);

            while (rs.next()) {
                reqNameList.add(rs.getString(KeyConst.REQUIREMENT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return reqNameList;
    }

    public static synchronized void deleteAllByParentIdAndTypeId(int parentId, int typeId) {
        try {
            synchronized (lock) {
                if (!deleteAllRequirementsByParentIdAndTypeId(parentId, typeId)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteAllRequirementsByParentIdAndTypeId(int parentId, int typeId) {
        if (!connector.checkForDataBaseConnection()) {
            return false;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM requirement WHERE parentId = " + parentId + " AND typeId = " + typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
