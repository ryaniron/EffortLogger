package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;

public class HomePageController implements Initializable {
	
	@FXML
	private Tab log_manager_tab;
	
	private String username;
	
	// This function handles the logout button. When the logout button is pressed, a confirmation dialogue pops up and
	// asks the user to confirm the logout. When the user logs out, the user is taken back to the title page.
	@FXML
	public void btn_logout(ActionEvent event) {	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log Out Confirmation");
		alert.setHeaderText("Are you sure you want to log out?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			SceneController.changeScene(event, "../resources/TitlePage.fxml", "Title Page");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// This sets the HomePageController username
		UserHolder holder = UserHolder.getInstance();
		this.username = holder.getUsername();
		System.out.println(this.username);
		// If the username is "Lynn Carter", then the log manager tab is enabled and the user can access this tab.
		if (this.username.equals("Lynn Carter")) {
			log_manager_tab.setDisable(false);
		} else {
			log_manager_tab.setDisable(true);
		}
	}
}
