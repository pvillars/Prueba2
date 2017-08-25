package cl.anpetrus.prueba2.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Petrus on 12-08-2017.
 */

public class Event extends SugarRecord {
    private String name;
    private String description;
    private Date start;

    public Event() {
    }

    public Event(String name, String description, Date start, Date end) {
        this.name = name;
        this.description = description;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

}
