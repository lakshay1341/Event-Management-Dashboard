package com.lakshay.dao;


import java.util.List;

import com.lakshay.model.Event;

public interface EventDAO {
    void createEvent(Event event);
    List<Event> getAllEvents();
    Event getEventById(int id);
    void updateEvent(int eventId, Event event);
    boolean deleteEvent(int id);
}
