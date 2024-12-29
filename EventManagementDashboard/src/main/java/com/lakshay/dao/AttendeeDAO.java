package com.lakshay.dao;

import com.lakshay.model.Attendee;
import java.util.List;

public interface AttendeeDAO {
    void addAttendee(Attendee attendee);
    List<Attendee> getAllAttendees();
    boolean deleteAttendee(int id);
}
