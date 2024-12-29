package com.lakshay.dao;

import com.lakshay.model.Attendee;
import com.lakshay.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDAOImpl implements AttendeeDAO {

    @Override
    public void addAttendee(Attendee attendee) {
        String query = "INSERT INTO attendees (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.requestConnection(); 
        		PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, attendee.getName());
            stmt.setString(2, attendee.getEmail());
            stmt.setString(3, attendee.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Attendee> getAllAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        String query = "SELECT * FROM attendees";
        try (Connection conn = ConnectionFactory.requestConnection();
        		Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Attendee attendee = new Attendee();
                attendee.setId(rs.getInt("id"));
                attendee.setName(rs.getString("name"));
                attendee.setEmail(rs.getString("email"));
                attendee.setPassword(rs.getString("password"));
                attendees.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendees;
    }

    @Override
    public boolean deleteAttendee(int id) {
        String query = "DELETE FROM attendees WHERE id = ?";
        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate(); // Get the number of rows affected
            return rowsAffected > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

}
