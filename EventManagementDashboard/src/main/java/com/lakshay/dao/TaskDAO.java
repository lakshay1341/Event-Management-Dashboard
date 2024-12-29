package com.lakshay.dao;

import java.sql.SQLException;
import java.util.List;

import com.lakshay.model.Task;

public interface TaskDAO {
	
	public int createTask(Task task) throws SQLException;
	public List<Task> getTasksByEvent(int eventId) throws SQLException;
	public boolean updateTaskStatus(int id, String status) throws SQLException;
	public boolean deleteTask(int id) throws SQLException;
	

}
