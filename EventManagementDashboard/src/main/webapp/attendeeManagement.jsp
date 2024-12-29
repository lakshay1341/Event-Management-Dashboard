<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lakshay.model.Attendee" %>
<%@ page import="com.lakshay.dao.AttendeeDAO" %>
<%@ page import="com.lakshay.dao.AttendeeDAOImpl" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendee Management</title>
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
            <a class="navbar-brand" href="#">Attendee Management</a>
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
        <h1 class="text-center mb-4">Attendee Management</h1>

        <!-- Add Attendee Form -->
        <div class="card p-4 mb-5">
            <h2 class="card-title">Add Attendee</h2>
            <form action="<%= request.getContextPath() %>/api/attendees" method="post">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Attendee</button>
            </form>
        </div>

        <!-- List of Attendees -->
        <h2 class="mb-4">List of Attendees</h2>

        <% 
            AttendeeDAO attendeeDAO = new AttendeeDAOImpl();
            List<Attendee> attendees = attendeeDAO.getAllAttendees(); // Fetch attendees from the database
            if (attendees != null && !attendees.isEmpty()) {
        %>
        <div class="row">
            <% for (Attendee attendee : attendees) { %>
            <div class="col-md-6 mb-4">
                <div class="card p-3">
                    <div class="card-body">
                        <p><strong>Name:</strong> <%= attendee.getName() %></p>
                        <p><strong>Email:</strong> <%= attendee.getEmail() %></p>
                        <form action="<%= request.getContextPath() %>/api/attendees/<%= attendee.getId() %>" method="post">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <p>No attendees found.</p>
        <% } %>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Attendee Management. All rights reserved.</p>
        </div>
    </footer>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
