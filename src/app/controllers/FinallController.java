package app.controllers;

import app.SceneLoader;
import app.stores.Winners;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FinallController implements Initializable {

    public FinallController(int playerScore1, int playerScore2,String playerName1,String playerName2) {
        this.playerScore1 = playerScore1;
        this.playerScore2 = playerScore2;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
    }

    private int playerScore1;
    private int playerScore2;
    private String playerName1;
    private String playerName2;
    @FXML
    private TableView<Winners> finallTable;
    @FXML
    private TableColumn<Winners, String> name;
    @FXML
    private TableColumn<Winners, Integer> points;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        if(playerName2.isEmpty()) {
            finallTable.getItems().add(new Winners(playerName1,playerScore1));
        } else {
            finallTable.getItems().add(new Winners(playerName1,playerScore1));
            finallTable.getItems().add(new Winners(playerName2,playerScore2));
        }
    }

    @FXML
    private void goHome(ActionEvent e) throws Exception {
        SceneLoader scene = new SceneLoader();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene.appStartup(stage);
    }
}
