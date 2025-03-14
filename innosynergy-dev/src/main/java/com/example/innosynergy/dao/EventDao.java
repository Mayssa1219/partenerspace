package com.example.innosynergy.dao;

import com.example.innosynergy.model.Event;
import java.util.List;

public interface EventDao {
    void insertEvent(Event event);
    List<Event> listEvents();
    Event findEventById(int idEvent);
    List<Event> searchEvents(String keyword);
}