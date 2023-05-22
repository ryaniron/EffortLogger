package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*******
 * <p> Title: SceneController Class </p>
 * 
 * <p> Description: This class contains the functionality to switch scenes.  </p>
 * 
 * 
 * @author Simon Liu
 * 
 * 
 */

public class SceneController {
    // Method to change to basic fxml scene
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root));
        stage.setWidth(((Node) event.getSource()).getScene().getWidth());
        stage.setHeight(((Node) event.getSource()).getScene().getHeight());
        stage.show();
    }
}
