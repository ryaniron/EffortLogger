package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DefinitionsController implements Initializable{
	public class Data
	{
		private final String data1;
		private final String data2;
		private final String data3;
		public Data(String s, String s1, String s2)
		{
			data1 = s;
			data2 = s1;
			data3 = s2;
		}
		public String getData1() { return data1; }
		public String getData2() { return data2; }
		public String getData3() { return data3; }

		
	}
	
	
	@FXML  private TableView<Data> ProjectNames;
	@FXML  private TableColumn<Data, String> ProjectNames1;
	@FXML  private TableColumn<Data, String> ProjectNames2;
	
	@FXML  private TableView<Data> LifeCycles;
	@FXML  private TableColumn<Data, String> LifeCycles1;
	@FXML  private TableColumn<Data, String> LifeCycles2;
	@FXML  private TableColumn<Data, String> LifeCycles3;
	
	@FXML  private TableView<Data> EffortCategories;
	@FXML  private TableColumn<Data, String> EffortCategories1;
	@FXML  private TableColumn<Data, String> EffortCategories2;

	@FXML  private TableView<Data> Plans;
	@FXML  private TableColumn<Data, String> Plans1;
	@FXML  private TableColumn<Data, String> Plans2;
	
	@FXML  private TableView<Data> Deliverables;
	@FXML  private TableColumn<Data, String> Deliverables1;
	@FXML  private TableColumn<Data, String> Deliverables2;
	
	@FXML  private TableView<Data> Interruptions;
	@FXML  private TableColumn<Data, String> Interruptions1;
	@FXML  private TableColumn<Data, String> Interruptions2;
	
	private ObservableList<Data> projectNameData = FXCollections.observableArrayList(
			new Data("Business Project","17, 18, 19, 20, 21, 22, 23, 24, 25, 26",""),
			new Data("Development Project", "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16","")
	);
	
	private ObservableList<Data> lifeCycleData = FXCollections.observableArrayList(
			new Data("Problem Understanding", "2", "1"),
			new Data("Conceptual Design Plan", "1", "3"),
			new Data("Requirements", "2", "1"),
			new Data("Conceptual Design", "2", "1"),
			new Data("Conceptual Design Review", "2", "1"),
			new Data("Detailed Design Plan", "1", "4"),
			new Data("Detailed Design/Prototype", "2", "2"),
			new Data("Detailed Design Review", "2", "2"),
			new Data("Implementation Plan", "1", "5"),
			new Data("Test Case Generation", "2", "3"),
			new Data("Solution Specification", "2", "4"),
			new Data("Solution Review", "2", "4"),
			new Data("Solution Implementation", "2", "4"),
			new Data("Unit/System Test", "2", "4"),
			new Data("Reflection", "2", "4"),
			new Data("Repository Update", "2", "4"),
			new Data("Planning", "1", "1"),
			new Data("Information Gathering", "2", "1"),
			new Data("Information Understanding", "2", "1"),
			new Data("Verifying", "2", "1"),
			new Data("Outlining", "2", "6"),
			new Data("Drafting", "2", "7"),
			new Data("Finalizing", "2", "8"),
			new Data("Team Meeting", "2", "1"),
			new Data("Coach Meeting", "2", "1"),
			new Data("Stakeholder Meeting", "2", "1")
	);
	
	private ObservableList<Data> effortData = FXCollections.observableArrayList(
			new Data("1", "Plans", ""),
			new Data("2", "Deliverables", ""),
			new Data("3", "Interruptions", ""),
			new Data("4", "Defects", ""),
			new Data("5", "Others", "")
	);
	
	private ObservableList<Data> planData = FXCollections.observableArrayList(
			new Data("1", "Project Plan", ""),
			new Data("2", "Risk Management Plan", ""),
			new Data("3", "Conceptual Design Plan", ""),
			new Data("4", "Detailed Design Plan", ""),
			new Data("5", "Implementation Plan", "")
	);
	
	private ObservableList<Data> deliverableData = FXCollections.observableArrayList(
			new Data("1", "Conceptual Design", ""),
			new Data("2", "Detailed Design", ""),
			new Data("3", "Test Cases", ""),
			new Data("4", "Solution", ""),
			new Data("5", "Reflection", ""),
			new Data("6", "Outline", ""),
			new Data("7", "Draft", ""),
			new Data("8", "Report", ""),
			new Data("9", "User Defined", ""),
			new Data("10", "Other", "")
	);
	
	private ObservableList<Data> interruptionData = FXCollections.observableArrayList(
			new Data("1", "Break", ""),
			new Data("2", "Phone", ""),
			new Data("3", "Teammate", ""),
			new Data("4", "Visitor", ""),
			new Data("5", "Other", "")
	);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		ProjectNames1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		ProjectNames2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		ProjectNames.setItems(projectNameData);
		
		LifeCycles1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		LifeCycles2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		LifeCycles3.setCellValueFactory(new PropertyValueFactory<>("data3"));
		LifeCycles.setItems(lifeCycleData);
		
		EffortCategories1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		EffortCategories2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		EffortCategories.setItems(effortData);
		
		Plans1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		Plans2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		Plans.setItems(planData);
		
		Deliverables1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		Deliverables2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		Deliverables.setItems(deliverableData);
		
		Interruptions1.setCellValueFactory(new PropertyValueFactory<>("data1"));
		Interruptions2.setCellValueFactory(new PropertyValueFactory<>("data2"));
		Interruptions.setItems(interruptionData);
		
		
		
		
		System.out.println("Initiallized");
		
		
		
		
		
	}
	
    
}

