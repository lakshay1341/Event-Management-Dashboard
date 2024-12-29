# Event Management Dashboard Documentation

## Project Overview
This project is a web application built using Java, Servlets, JSP, CSS, JDBC, and MySQL, designed for managing events. Users can add, view, and delete events through a user-friendly interface. It follows the MVC (Model-View-Controller) architecture, ensuring a clean separation of concerns.

PPT Link: https://app.presentations.ai/view/TIrTJs

## Technologies Used
- **Frontend**: JSP, HTML, CSS
- **Backend**: Java, Servlets, JDBC
- **Database**: MySQL
- **Architecture**: MVC

---

## Instructions to Set Up and Run the Project

### Prerequisites
Before you begin, ensure you have the following:
- **Java 11 or later** installed.
- **Apache Tomcat** or any other servlet container for deploying the project.
- **MySQL Database** for storing event information.

### Steps for Setting Up

1. **Clone the Repository**
   ```bash
   git clone https://github.com/lakshay1341/Event-Management-Dashboard.git
   ```

2. **Import the Project into Eclipse**
   - Open Eclipse and select `File > Import`.
   - Choose `Existing Projects into Workspace` and navigate to the cloned directory.
   - Import the project as a **Dynamic Web Project**.

3. **Set Up the Database**
   - Create a database in MySQL (e.g., `event_management`).
   - Import the provided schema for the event management system into the database.
   - The schema includes tables for storing event information, such as the event name, description, location, and date.

4. **Configure Database Connection**
   - Open `src/main/webapp/WEB-INF/web.xml` and ensure that the database connection details (like the database URL, username, and password) are configured correctly in the `DBUtils` class or similar.

5. **Run the Project**
   - Deploy the project on Apache Tomcat or your preferred servlet container.
   - Access the application by navigating to `http://localhost:8080/EventManagementDashboard`.

---

## APIs Developed

### 1. **Add Event API** (`POST /api/events`)
- **Description**: Allows the user to add a new event.
- **Request Body**:
   ```json
   {
     "name": "Event Name",
     "description": "Event Description",
     "location": "Event Location",
     "date": "Event Date"
   }
   ```
- **Response**: 
   - Status: 201 Created if the event is successfully added.
   - Status: 400 Bad Request if required fields are missing.

### 2. **Get All Events API** (`GET /api/events`)
- **Description**: Fetches a list of all events stored in the database.
- **Response**:
   ```json
   [
     {
       "id": 1,
       "name": "Event 1",
       "description": "Description 1",
       "location": "Location 1",
       "date": "2024-12-01"
     },
     {
       "id": 2,
       "name": "Event 2",
       "description": "Description 2",
       "location": "Location 2",
       "date": "2024-12-02"
     }
   ]
   ```

### 3. **Delete Event API** (`DELETE /api/events/{eventId}`)
- **Description**: Deletes a specified event.
- **Parameters**: 
   - `eventId` in the URL path.
- **Response**: 
   - Status: 200 OK if the event is deleted.
   - Status: 404 Not Found if the event with the given ID does not exist.

   ---

#### 4. PUT /api/tasks/{taskId}
**Description**: Updates the status of a task based on the given `taskId`. This request allows the user to modify the task's status, such as marking it as "Completed."

**Request Parameters**:
- `taskId`: The ID of the task being updated (path parameter).
- `status`: The new status of the task (e.g., "Pending", "Completed").

**Request Body**:
```json
{
  "status": "Completed"
}
```

**Response**:
- **200 OK**: Task status updated successfully.
- **400 Bad Request**: Invalid task ID or status.

---
## How to Contribute
Feel free to fork this repository, create branches for new features or bug fixes, and submit pull requests. Ensure that your contributions follow the existing coding conventions and pass the necessary tests.

---

## License
This project is open-source and available under the MIT License.
