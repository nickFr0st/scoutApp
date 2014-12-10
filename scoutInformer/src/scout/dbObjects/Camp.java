package scout.dbObjects;

import java.util.Date;

/**
 * Created by Nathanael on 10/29/2014
 */
public class Camp {
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_LOCATION_LENGTH = 255;

    private int id;
    private String name;
    private String location;
    private int duration;
    private Date date;
    private String note;

    {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
