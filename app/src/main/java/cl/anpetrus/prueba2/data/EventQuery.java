package cl.anpetrus.prueba2.data;

import java.util.Date;
import java.util.List;

import cl.anpetrus.prueba2.models.Event;

/**
 * Created by Petrus on 12-08-2017.
 */

public class EventQuery {

    public List<Event> getAll(){
        return Event.listAll(Event.class);
    }
    public List<Event> getNext(){

        return Event.find(Event.class,"start >= ?", String.valueOf(new Date().getTime()));
    }
    public Event get(long id){
        return Event.findById(Event.class,id);
    }
}
