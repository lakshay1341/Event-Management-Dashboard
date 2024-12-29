package com.lakshay.controller;

import com.google.gson.Gson;
import com.lakshay.dao.EventDAOImpl;
import com.lakshay.model.Event;
import com.lakshay.dao.EventDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventServlet", urlPatterns = {"/api/events", "/api/events/*"})
public class EventServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private EventDAO eventDAO = new EventDAOImpl();

    // Handle POST request for creating events
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for method override
        String methodOverride = request.getParameter("_method");

        if ("delete".equalsIgnoreCase(methodOverride)) {
            // Delegate to doDelete if the _method parameter indicates a delete operation
            doDelete(request, response);
            return;
        }

        // Handle form data submission
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        if (name == null || location == null || date == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid event data");
            return;
        }

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setDate(date); // Assuming date is a string. You may need to convert it to a Date object if needed.

        eventDAO.createEvent(event);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(event));
    }


    // Handle GET request for fetching events
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        List<Event> events;

        if (pathInfo == null || pathInfo.equals("/")) {
            events = eventDAO.getAllEvents();
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid URL");
                return;
            }

            int eventId;
            try {
                eventId = Integer.parseInt(pathParts[1]);
                Event event = eventDAO.getEventById(eventId);
                if (event == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Event not found");
                    return;
                }
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(event));
                return;
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid event ID");
                return;
            }
        }

        if (events.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("No events found");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(events));
        }
    }

    // Handle PUT request for updating events
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Event ID is required");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid URL");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid event ID");
            return;
        }

        BufferedReader reader = request.getReader();
        Event event = gson.fromJson(reader, Event.class);

        eventDAO.updateEvent(eventId, event);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(event));
    }

    // Handle DELETE request for deleting events
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Event ID is required\"}");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid URL\"}");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid event ID\"}");
            return;
        }

        boolean isDeleted = eventDAO.deleteEvent(eventId);

        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Event not found\"}");
        }
    }
}
