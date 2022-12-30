package app.controllers;

import app.SceneLoader;
import app.stores.Question;
import app.stores.QuestionCSVLoader;
import app.stores.SettingsHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GameModeController {
    public void initialize() {
    }

    public GameModeController(){
    }

    @FXML
    private void runSingleplayer(ActionEvent e) throws Exception {
        SceneLoader scene = new SceneLoader();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        SettingsHolder holder = (SettingsHolder) stage.getUserData();
        //Question nova = QuestionCSVLoader.pickRandomQuestion(holder.getQuestions(), "Attractions");
        scene.singlePlayerSelection(stage);
    }
    @FXML
    private void runMultiplayer(ActionEvent e) throws Exception {
        SceneLoader scene = new SceneLoader();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene.MultiPlayerSelection(stage);
    }
}
