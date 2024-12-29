<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.lakshay.model.Task"%>
<%@ page import="com.lakshay.dao.TaskDAO"%>
<%@ page import="com.lakshay.dao.TaskDAOImpl"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Tracker</title>
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

        .navbar {
            background-color: #343a40;
        }

        .navbar-brand, .nav-link {
            color: #ffffff !important;
        }

        .nav-link:hover {
            color: #adb5bd !important;
        }

        .main-container {
            flex: 1;
            margin-top: 50px;
            margin-bottom: 50px;
        }

        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: none;
        }

        .card:hover {
            transform: scale(1.02);
            transition: transform 0.3s;
        }

        .footer {
            background-color: #343a40;
            color: #ffffff;
            text-align: center;
            padding: 15px 0;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Task Tracker</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container main-container">
        <h1 class="text-center mb-4">Task Tracker</h1>

        <!-- Event ID Form -->
        <div class="card p-4 mb-5">
            <h2 class="card-title">Enter Event ID to View Tasks</h2>
            <form action="taskTracker.jsp" method="get">
                <div class="mb-3">
                    <label for="eventId" class="form-label">Event ID</label>
                    <input type="number" id="eventId" name="eventId" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary">Fetch Tasks</button>
            </form>
        </div>

        <!-- Task List -->
        <% 
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam != null) {
            int eventId = Integer.parseInt(eventIdParam); // Parse event ID from request
            TaskDAO taskDAO = new TaskDAOImpl();
            List<Task> tasks = taskDAO.getTasksByEvent(eventId); // Fetch tasks from the database based on selected event

            if (tasks != null && !tasks.isEmpty()) {
        %>
        <h2 class="mb-4">Tasks for Event ID: <%= eventId %></h2>
        <div class="row">
            <% for (Task task : tasks) { %>
            <div class="col-md-6 mb-4">
                <div class="card p-3">
                    <div class="card-body">
                        <p><strong>Name:</strong> <%= task.getName() %></p>
                        <p><strong>Status:</strong> <%= task.getStatus() %></p>

                        <!-- Update Status Form -->
                        <form action="<%= request.getContextPath() %>/api/tasks/<%= task.getId() %>" method="post">
                            <input type="hidden" name="_method" value="PUT">
                            <div class="mb-3">
                                <label for="status" class="form-label">Update Status</label>
                                <select name="status" id="status" class="form-control">
                                    <option value="Pending" <%= task.getStatus().equals("Pending") ? "selected" : "" %>>Pending</option>
                                    <option value="Completed" <%= task.getStatus().equals("Completed") ? "selected" : "" %>>Completed</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">Update Status</button>
                        </form>

                        <!-- Delete Task Form -->
                        <form action="<%= request.getContextPath() %>/api/tasks/<%= task.getId() %>" method="post">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit" class="btn btn-danger mt-2">Delete Task</button>
                        </form>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <p>No tasks found for this event.</p>
        <% } } %>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Task Tracker. All rights reserved.</p>
        </div>
    </footer>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
