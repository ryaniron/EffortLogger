package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/*******
 * <p> Title: HomeController Class </p>
 * 
 * <p> Description: This contains the button press handler for the Home page after the login screen. </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */

public class LoginPageController {
	@FXML
	private TextField username_field;
	@FXML
	private PasswordField password_field;
	
	@FXML
	public void btn_login(ActionEvent event) {	
		if (SQLUtilities.loginCredentials(username_field.getText(), password_field.getText())) {
			// Sets the username in the static class object instance
			UserHolder holder = UserHolder.getInstance();
			holder.setUsername(username_field.getText());
			SceneController.changeScene(event, "../resources/HomePage.fxml", "Home Page");
		} else {
			username_field.setText("");
			password_field.setText("");
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Incorrect Credendials Entered");
			a.show();
		}
	}
	
}
