package com.lakshay.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.lakshay.model.Task;
import com.lakshay.utils.ConnectionFactory;

public class TaskDAOImpl implements TaskDAO{
	
    public int createTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (name, deadline, status, attendee_id, event_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDeadline());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getAttendeeId());
            stmt.setInt(5, task.getEventId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public List<Task> getTasksByEvent(int eventId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE event_id = ?";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setName(rs.getString("name"));
                task.setDeadline(rs.getString("deadline"));
                task.setStatus(rs.getString("status"));
                task.setAttendeeId(rs.getInt("attendee_id"));
                task.setEventId(rs.getInt("event_id"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public boolean updateTaskStatus(int taskId, String status) throws SQLException {
        String query = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status); // Set status
            stmt.setInt(2, taskId);    // Set the task ID for updating

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        }
    }



    public boolean deleteTask(int id) throws SQLException {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = ConnectionFactory.requestConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
