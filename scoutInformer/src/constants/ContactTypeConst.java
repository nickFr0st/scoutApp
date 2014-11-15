package constants;

/**
 * Created by Nathanael on 10/29/2014
 */
public enum ContactTypeConst {
    HOME(10, "Home"),
    MOBILE(20, "Mobile"),
    EMAIL(30, "Email");

    int id;
    String name;

    ContactTypeConst(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getNameById(int id) {
        for (ContactTypeConst typeConst : ContactTypeConst.values()) {
            if (typeConst.getId() == id) {
                return typeConst.getName();
            }
        }

        return null;
    }

    public static int getIdByName(String name) {
        for (ContactTypeConst typeConst : ContactTypeConst.values()) {
            if (typeConst.getName().equals(name)) {
                return typeConst.getId();
            }
        }

        return ContactTypeConst.HOME.getId();
    }
}
