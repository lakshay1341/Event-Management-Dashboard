<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Management Dashboard</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: none;
            margin: 15px;
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

        .card-deck {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            gap: 30px;
        }

        @media (min-width: 768px) {
            .card-deck {
                flex-direction: row;
            }
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Event Dashboard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="eventManagement.jsp">Event Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="attendeeManagement.jsp">Attendee Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="taskTracker.jsp">Task Tracker</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container main-container">
        <div class="card-deck">
            <div class="card p-3">
                <div class="card-body text-center">
                    <h5 class="card-title">Event Management</h5>
                    <p class="card-text">Manage all events, CRUD operations.</p>
                    <a href="eventManagement.jsp" class="btn btn-primary">Go to Events</a>
                </div>
            </div>

            <div class="card p-3">
                <div class="card-body text-center">
                    <h5 class="card-title">Attendee Management</h5>
                    <p class="card-text">Track and manage attendees for all events.</p>
                    <a href="attendeeManagement.jsp" class="btn btn-primary">Manage Attendees</a>
                </div>
            </div>

            <div class="card p-3">
                <div class="card-body text-center">
                    <h5 class="card-title">Task Tracker</h5>
                    <p class="card-text">Assign and track tasks related to events.</p>
                    <a href="taskTracker.jsp" class="btn btn-primary">Track Tasks</a>
                </div>
            </div>
        </div>
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
