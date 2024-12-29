package com.lakshay.dao;

import com.lakshay.model.Event; 
import com.lakshay.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements EventDAO {

    @Override
    public void createEvent(Event event) {
        String sql = "INSERT INTO events (name, description, location, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setString(3, event.getLocation());
            stmt.setDate(4, Date.valueOf(event.getDate()));
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                event.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception as needed
        }
    }

    @Override
    public List<Event> getAllEvents() {
        String sql = "SELECT * FROM events";
        List<Event> events = new ArrayList<>();

        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getDate("date").toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception as needed
        }

        return events;
    }

    @Override
    public Event getEventById(int id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        Event event = null;

        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    event = new Event(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("location"),
                            rs.getDate("date").toString()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception as needed
        }

        return event;
    }

    @Override
    public void updateEvent(int eventId, Event event) {
        String sql = "UPDATE events SET name = ?, description = ?, location = ?, date = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setString(3, event.getLocation());
            stmt.setDate(4, Date.valueOf(event.getDate()));
            stmt.setInt(5, eventId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception as needed
        }
    }

    @Override
    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id = ?";
        boolean isDeleted = false;

        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            isDeleted = (rowsAffected > 0); // True if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception as needed
        }

        return isDeleted;
    }

}
