package scout;

/**
 * Created by nmalloch on 6/5/2014.
 */
public class Scout {
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String SUFFIX = "suffix";
    public static final String AGE = "age";
    public static final String RANK = "rank";



    private int id;
    private String firstName;
    private String lastName;
    private String suffix;
    private int age;

    public Scout() {
    }

    public Scout(int id, String firstName, String lastName, String suffix, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
