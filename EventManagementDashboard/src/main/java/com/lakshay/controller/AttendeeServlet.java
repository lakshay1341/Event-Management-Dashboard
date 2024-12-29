package com.lakshay.controller;

import com.google.gson.Gson;
import com.lakshay.dao.AttendeeDAO;
import com.lakshay.dao.AttendeeDAOImpl;
import com.lakshay.model.Attendee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AttendeeServlet", urlPatterns = {"/api/attendees", "/api/attendees/*"})
public class AttendeeServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private AttendeeDAO attendeeDAO = new AttendeeDAOImpl();

    // Handle POST request to create or delete attendees
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for method override (if applicable)
        String methodOverride = request.getParameter("_method");

        if ("delete".equalsIgnoreCase(methodOverride)) {
            // Delegate to doDelete if the _method parameter indicates a delete operation
            doDelete(request, response);
            return;
        }

        // Retrieve form parameters for attendee
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate attendee data
        if (name == null || email == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid attendee data");
            return;
        }

        // Create a new Attendee object
        Attendee attendee = new Attendee();
        attendee.setName(name);
        attendee.setEmail(email);
        attendee.setPassword(password);

        // Add attendee to the database
        attendeeDAO.addAttendee(attendee);

        // Respond with created status and the new attendee details
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(attendee));
    }


    // Handle GET request for fetching attendees
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Attendee> attendees = attendeeDAO.getAllAttendees();
        if (attendees.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("No attendees found");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(attendees));
        }
    }

    // Handle DELETE request to remove attendees
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Attendee ID is required");
            return;
        }

        String[] pathParts = pathInfo.split("/");

        if (pathParts.length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid URL");
            return;
        }

        int attendeeId;
        try {
            attendeeId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid attendee ID");
            return;
        }

        boolean isDeleted = attendeeDAO.deleteAttendee(attendeeId);
        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Attendee not found");
        }
    }
}
