<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.lakshay.model.Event"%>
<%@ page import="com.lakshay.dao.EventDAO"%>
<%@ page import="com.lakshay.dao.EventDAOImpl"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Event Management</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background-color: #f8f9fa;
        color: #343a40;
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    .main-container {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding: 20px;
    }

    .form-container {
        width: 100%;
        max-width: 600px;
        margin-bottom: 30px;
        background: #ffffff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .event-list {
        width: 100%;
        max-width: 1000px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        gap: 20px;
        margin-top: 30px;
    }

    .event-item {
        background: #ffffff;
        padding: 15px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        width: calc(50% - 10px); /* Two items per row with some gap */
        margin-bottom: 20px;
    }

    .event-details {
        margin-bottom: 10px;
    }

    .footer {
        background-color: #343a40;
        color: #ffffff;
        text-align: center;
        padding: 15px 0;
    }

    .btn-danger {
        margin-top: 10px;
    }

    .btn-info {
        margin-top: 10px;
    }
</style>
</head>
<body>
    <!-- Main Content -->
    <div class="container main-container">
        <div class="form-container">
            <h2 class="text-center">Add Event</h2>
            <form action="<%= request.getContextPath() %>/api/events" method="post">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <input type="text" id="description" name="description" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="location" class="form-label">Location</label>
                    <input type="text" id="location" name="location" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="date" class="form-label">Date</label>
                    <input type="date" id="date" name="date" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Add Event</button>
            </form>
        </div>

        <!-- List of Events Heading -->
        <h2 class="text-center mt-4">List of Events</h2>

        <div class="event-list">
            <%
                EventDAO eventDAO = new EventDAOImpl();
                List<Event> events = eventDAO.getAllEvents(); // Fetch all events from the database
                if (events != null && !events.isEmpty()) {
                    for (Event event : events) {
            %>
            <div class="event-item">
                <div class="event-details">
                    <p><strong>Event ID:</strong> <%= event.getId() %></p>
                    <p><strong>Name:</strong> <%= event.getName() %></p>
                    <p><strong>Description:</strong> <%= event.getDescription() %></p>
                    <p><strong>Location:</strong> <%= event.getLocation() %></p>
                    <p><strong>Date:</strong> <%= event.getDate() %></p>
                </div>
                <form action="<%= request.getContextPath() %>/api/events/<%= event.getId() %>" method="post">
                    <input type="hidden" name="_method" value="delete">
                    <input type="hidden" name="eventId" value="<%= event.getId() %>">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
            <%
                    }
                } else {
            %>
            <p class="text-center">No events found.</p>
            <%
                }
            %>
        </div>

        <a href="dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Event Management Dashboard. All rights reserved.</p>
        </div>
    </footer>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
