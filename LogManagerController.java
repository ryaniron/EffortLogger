package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/*******
 * <p> Title: LogManagerController Class </p>
 * 
 * <p> Description: This class controls the LogManagerPage fxml Scene. The LogManagerPage contains the project selection on the left and the displayed logs on the right.
 * 		When a project is selected from the projects_list, the corresponding logs are displayed on the right. The logs are fetched from the SQL database which stores
 * 		the necessary log data. </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */

public class LogManagerController implements Initializable {
	
	// FXML connected components.
	@FXML
	private TableView<LogManagerLog> logs_table;

    @FXML
    private TableColumn<LogManagerLog, String> duration_column;

    @FXML
    private TableColumn<LogManagerLog, String> task_name_column;

    @FXML
    private TableColumn<LogManagerLog, String> username_column;
    
    @FXML
    private ListView<String> projects_list;
    
    
    // Variable Declaration
    private ObservableList<String> items;
    
    private ObservableList<LogManagerLog> all_logs;
    
    private FilteredList<LogManagerLog> filtered_logs;
    
    private ArrayList<Dictionary> fetchedTasks;
    
    private String selectedProject;
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initializes the variables
		items = FXCollections.observableArrayList();
		all_logs = FXCollections.observableArrayList();
		filtered_logs = new FilteredList<>(all_logs);
		selectedProject = "";
		
		// Sets the List selection to only list tasks associated with the selected project.
		projects_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		projects_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    this.selectedProject = newValue;
		    System.out.println("Selected item: " + this.selectedProject);
		    setFilteredLogs();
		});
		
		// Sets each columns' cell value factory to display a portion of the retrieved SQL data.
		duration_column.setCellValueFactory(cellData -> cellData.getValue().getDuration());
		task_name_column.setCellValueFactory(cellData -> cellData.getValue().getTaskName());
		username_column.setCellValueFactory(cellData -> cellData.getValue().getUsername());
		
		// Binds the ObservableArrayLists to the projects_list and the logs_table.
		projects_list.setItems(items);
		logs_table.setItems(filtered_logs);
		
		// Fetches the data from the SQL database and places the data into the ObservableArrayLists.
		fetchedTasks = SQLUtilities.retrieveTaskLogs();
		for (Dictionary task : fetchedTasks) {
			if(!items.contains(task.get("projectName"))) {
				addListItem(task.get("projectName").toString());
			}
			all_logs.add(new LogManagerLog(task.get("lastAlteredBy").toString(), task.get("taskName").toString(), task.get("expectedDuration").toString(), task.get("projectName").toString()));
		}
		
		// Starts the logs_table as empty.
		setFilteredLogs();
	}
	
	// Method to add a project name to the projects_list.
	public void addListItem(String item) {
		items.add(item);
	}
	
	// Method to filter the logs that are displayed when a project is selected.
	public void setFilteredLogs() {
		filtered_logs.setPredicate(log -> log.getProjectName().equals(selectedProject));
	}

}


