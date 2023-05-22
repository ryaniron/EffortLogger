package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/*******
 * <p> Title: TitlePageController Class </p>
 * 
 * <p> Description: This controls the title page of the EffortLogger program. </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */

public class TitlePageController {
	@FXML
	public void btn_enter(ActionEvent event) {
		SceneController.changeScene(event, "../resources/LoginPage.fxml", "EffortLogger Login");
	}
}
