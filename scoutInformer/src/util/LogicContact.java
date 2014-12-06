package util;

import constants.KeyConst;
import scout.dbObjects.Contact;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class LogicContact {
    private static DBConnector connector;
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static List<Contact> findAllByScoutId(int scoutId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Contact> contactList = new ArrayList<Contact>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM contact WHERE scoutId = " + scoutId);

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(KeyConst.CONTACT_ID.getName()));
                contact.setScoutId(rs.getInt(KeyConst.CONTACT_SCOUT_ID.getName()));
                contact.setTypeId(rs.getInt(KeyConst.CONTACT_TYPE_ID.getName()));
                contact.setName(rs.getString(KeyConst.CONTACT_NAME.getName()));
                contact.setRelation(rs.getString(KeyConst.CONTACT_RELATION.getName()));
                contact.setData(rs.getString(KeyConst.CONTACT_DATA.getName()));

                contactList.add(contact);
            }

        } catch (Exception e) {
            return null;
        }

        return contactList;
    }

    public static void saveList(List<Contact> contactList) {
        if (Util.isEmpty(contactList) || !connector.checkForDataBaseConnection()) {
            return;
        }

        for (Contact contact : contactList) {
            save(contact);
        }
    }

    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM contact");

            if(rs.next()) {
                id = rs.getInt(KeyConst.CONTACT_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void deleteList(List<Integer> contactList) {
        try {
            synchronized (lock) {
                if (!deleteContactList(contactList)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteContactList(List<Integer> contactList) {
        if (Util.isEmpty(contactList)) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM contact WHERE id IN (" + Util.listToString(contactList) + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Integer findByNameTypeIdAndScoutId(String name, int typeId, int scoutId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Integer id = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM contact WHERE name LIKE '" + name + "' AND typeId = " + typeId + " AND scoutId = " + scoutId);

            if (rs.next()) {
                id = rs.getInt(KeyConst.CONTACT_ID.getName());
            }

        } catch (Exception e) {
            return null;
        }

        return id;
    }

    public static void updateList(List<Contact> contactList, int scoutId) {
        for (Contact contact : contactList) {
            if (contact.getId() < 0) {
                save(contact);
            } else {
                update(contact);
            }
        }

        List<Integer> existingRequirementIdList = findIdsByScoutId(scoutId);
        List<Integer> tempList = new ArrayList<Integer>();

        for (Contact contact : contactList) {
            if (contact.getId() < 0) {
                continue;
            }

            tempList.add(contact.getId());
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

    private static List<Integer> findIdsByScoutId(int scoutId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Integer> contactIdList = new ArrayList<Integer>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM contact WHERE scoutId = " + scoutId);

            while (rs.next()) {
                contactIdList.add(rs.getInt(KeyConst.CONTACT_ID.getName()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contactIdList;
    }

    private static synchronized void update(Contact contact) {
        try {
            synchronized (lock) {
                if (!updateContact(contact)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateContact(Contact contact) {
        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE contact SET name = '" + contact.getName() + "', relation = '" + contact.getRelation() + "', data = '" + contact.getData() + "' WHERE id = " + contact.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static synchronized void save(Contact contact) {
        try {
            synchronized (lock) {
                if (!saveContact(contact)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveContact(Contact contact) {
        if (contact == null) {
            return true;
        }

        if (contact.getId() < 0) {
            contact.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO contact VALUES( " + contact.getId() + "," + contact.getScoutId() + "," + contact.getTypeId() + ",'" + contact.getName() + "', '" + contact.getRelation() + "','" + contact.getData() + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
