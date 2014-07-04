package constants;

/**
 * Created by Malloch on 7/4/14
 */
public enum  ErrorConst {
    INVALID_SERVER_CREDENTIALS(1045, "Could not connect to server, invalid username or password"),
    DATABASE_NAME_ALREADY_EXISTS(1007, "Database name already in use"),
    UNKNOWN_DATABASE(1049, "Database does not exists");

    private int id;
    private String message;

    ErrorConst(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public final int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
