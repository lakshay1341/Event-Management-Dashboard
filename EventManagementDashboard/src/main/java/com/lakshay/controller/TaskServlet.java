package com.lakshay.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lakshay.dao.TaskDAO;
import com.lakshay.dao.TaskDAOImpl;
import com.lakshay.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "TaskServlet", urlPatterns = {"/api/tasks", "/api/tasks/*"})
public class TaskServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private TaskDAO taskDAO = new TaskDAOImpl();

    // Create a Task (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check the content type of the request
        String contentType = request.getContentType();
        
        // Initialize the eventId and status
        Integer eventId = null;
        String status = null;

        // Check if the request is trying to trigger a PUT via _method hidden field
        String methodOverride = request.getParameter("_method");
        if ("PUT".equalsIgnoreCase(methodOverride)) {
            // If _method is PUT, redirect to doPut
            doPut(request, response);
            return; // Exit the method after redirecting to doPut
        }

        if (contentType != null && contentType.contains("application/json")) {
            // Handle JSON request
            BufferedReader reader = request.getReader();
            Task task = gson.fromJson(reader, Task.class);
            
            // Extract eventId from the task object
            eventId = task.getEventId();
            status = task.getStatus();
        } else {
            // Handle form data (application/x-www-form-urlencoded)
            String eventIdParam = request.getParameter("eventId");
            
            if (eventIdParam == null || eventIdParam.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Event ID is required");
                return;
            }

            // Convert eventId to Integer
            try {
                eventId = Integer.parseInt(eventIdParam);
            } catch (NumberFormatException e) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Event ID");
                return;
            }

            // Extract status from form data
            status = request.getParameter("status");
        }

        // Check if eventId is still null or status is missing
        if (eventId == null || status == null || status.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Event ID and Status are required");
            return;
        }

        // Create Task object and set data
        Task task = new Task();
        task.setEventId(eventId);
        task.setStatus(status);

        // Now save the Task object using TaskDAO
        try {
            int created = taskDAO.createTask(task); // Assuming you have createTask method in TaskDAO
            if (created > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\":\"Task created successfully\"}");
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create task");
            }
        } catch (SQLException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Get Tasks by Event ID (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/event/")) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid request format");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(pathInfo.substring("/event/".length()));
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
            return;
        }

        List<Task> tasks;
        try {
            tasks = taskDAO.getTasksByEvent(eventId);
        } catch (SQLException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }

        if (tasks.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "No tasks found for the specified event");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(tasks));
        }
    }

    // Update Task Status (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // Validate URL path for task ID
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Task ID is required in the URL");
            return;
        }

        // Extract taskId from the URL
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format. Task ID is required.");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid task ID");
            return;
        }

        // Read the JSON body
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String requestBody = stringBuilder.toString();
        System.out.println("Received JSON: " + requestBody); // Debug log

        // Parse JSON to Task object
        Task task;
        try {
            task = gson.fromJson(requestBody, Task.class);
        } catch (JsonSyntaxException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
            return;
        }

        if (task == null || task.getStatus() == null || task.getStatus().trim().isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Status is required");
            return;
        }

        // Update task status in the database
        boolean updated;
        try {
            updated = taskDAO.updateTaskStatus(taskId, task.getStatus());
        } catch (SQLException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Task status updated successfully\"}");
        } else {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Task not found");
        }
    }


    // Delete Task (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length < 3) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Task ID is required");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(pathInfo.substring(1)); // Extract taskId from URL
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid task ID");
            return;
        }

        boolean deleted;
        try {
            deleted = taskDAO.deleteTask(taskId);
        } catch (SQLException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }

        if (deleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Task not found");
        }
    }

    // Helper method to send error responses
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
