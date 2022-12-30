package app.controllers;

import app.SceneLoader;
import app.stores.SettingsHolder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MultiPlayerController {

    @FXML
    private TextField username1;

    @FXML
    private TextField username2;

    @FXML
    private ChoiceBox difficulty;

    public MultiPlayerController(){
    }

    public void initialize() {
        difficulty.setItems(FXCollections.observableArrayList("Basic States","Major cities","Attractions"));
        difficulty.setValue("Basic States");
    }

    @FXML
    private void startGame(ActionEvent e) throws Exception {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        SettingsHolder holder = (SettingsHolder) stage.getUserData();
        if(!holder.getQuestions().stream().anyMatch(q -> q.getCategory().equals(difficulty.getValue()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Herní kategorie nemá načtenou žádnou otázku !", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (username1.getText().isEmpty() || username2.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Oba uživatele musí být vyplněný!", ButtonType.OK);
            alert.showAndWait();
        } else {
            holder.setUsername1(username1.getText());
            holder.setUsername2(username2.getText());
            holder.setCategory(difficulty.getValue().toString());
            stage.setUserData(holder);
            SceneLoader scene = new SceneLoader();
            scene.startGame(stage);
        }
    }

    @FXML
    private void backMenu(ActionEvent e) throws Exception {
        SceneLoader scene = new SceneLoader();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene.appStartup(stage);
    }

}
