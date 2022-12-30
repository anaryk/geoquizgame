package app.controllers;

import app.SceneLoader;
import app.stores.QuestionCSVLoader;
import app.stores.SettingsHolder;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainController {
    @FXML
    private Button startgame;

    public MainController() {
    }
    public void initialize() {
        startgame.setDisable(true);
        if(!netIsAvailable()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bez internetu to nepujde :-(", ButtonType.OK);
            alert.showAndWait();
            System.exit(0);
        }
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            System.out.println("Net funguje");
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
    @FXML
    private void startGame(ActionEvent e) throws Exception {
        SceneLoader scene = new SceneLoader();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene.gameModeSelection(stage);
        System.out.println("Start game");
    }
    @FXML
    private void loadQuestion(ActionEvent e) {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null && file.getName().split("\\.")[1].equals("csv")) {
            SettingsHolder settings = new SettingsHolder(QuestionCSVLoader.parseCSV(file));
            stage.setUserData(settings);
            System.out.println(settings.getQuestions());
            if(settings.getQuestions().size() != 0) {
                startgame.setDisable(false);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ze souboru bylo úspěšně načteno " + settings.getQuestions().size() + " otázek", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nahrál jsi nepodporovaný soubor !!!", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
