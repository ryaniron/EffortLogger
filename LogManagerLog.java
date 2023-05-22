package application;

import javafx.beans.property.SimpleStringProperty;

/*******
 * <p> Title: LogManagerLog Class </p>
 * 
 * <p> Description: This class object stores the username, task_name, duration, and project_name that is used
 * 		by the LogManagerPage table view to be displayed.  </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */


public class LogManagerLog {
	private SimpleStringProperty username;
	private SimpleStringProperty task_name;
	private SimpleStringProperty duration;
	private String project_name;
	
	public LogManagerLog(String username, String task_name, String duration, String project_name) {
		this.username = new SimpleStringProperty(username);
		this.task_name = new SimpleStringProperty(task_name);
		this.duration = new SimpleStringProperty(duration);
		this.project_name = project_name;
	}
	
	SimpleStringProperty getUsername() {
		return username;
	}
	
	SimpleStringProperty getTaskName() {
		return task_name;
	}
	
	SimpleStringProperty getDuration() {
		return duration;
	}
	
	String getProjectName() {
		return project_name;
	}
}
