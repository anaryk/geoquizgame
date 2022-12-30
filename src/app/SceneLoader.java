package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneLoader {

    public void appStartup(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/geoquiz_mainmenu.fxml"));
        stage.setTitle("Main menu");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void startGame(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/geoquiz_google.fxml"));
        stage.setTitle("Game");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void gameModeSelection(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/geoquiz_gamemode.fxml"));
        stage.setTitle("Gamemode Selection");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void singlePlayerSelection(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/geoquiz_singleplayer.fxml"));
        stage.setTitle("Single player settings");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void MultiPlayerSelection(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/goequiz_multiplayer.fxml"));
        stage.setTitle("Multiplayer settings");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
