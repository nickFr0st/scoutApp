package scout;

/**
 * Created by nmalloch on 6/5/2014.
 */
public class Scout {
    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String SUFFIX = "suffix";
    public static final String AGE = "age";

    private int id;
    private String firstName;
    private String lastName;
    private String suffix;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScoutNameWithSuffix(String firstName, String lastName, String suffix) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.suffix = suffix;
    }

    public void setScoutName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
