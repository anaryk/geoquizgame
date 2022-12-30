package app.controllers;

import app.stores.Question;
import app.stores.QuestionCSVLoader;
import app.stores.SettingsHolder;
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GameController implements Initializable {

    @FXML
    private Label latitudeLabel;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label playernameLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Label questionText;

    @FXML
    private Button submit;

    @FXML
    private GoogleMapView googleMapView;

    private GoogleMap map;

    private Task<Void> timerThread;

    private Question actualQuestion;

    private SettingsHolder settings;

    private int globalStage = 0;
    private int roundsCount = 0;

    private boolean multiplayer = false;

    private String actualplayer;

    private int playerOneScore = 0;

    private int playerTwoScore = 0;

    private DecimalFormat formatter = new DecimalFormat("###.00000");

    @FXML
    private void confirmSelect(ActionEvent e) throws IOException {
        timerThread.cancel();
        if(round(Double.valueOf(latitudeLabel.getText().replace(",",".")),1) == round(actualQuestion.getLatitude(),1) && round(Double.valueOf(longitudeLabel.getText().replace(",",".")),1) == round(actualQuestion.getLongtitude(),1)) {
            if(actualplayer == settings.getUsername1()) {
                playerOneScore++;
            } else {
                playerTwoScore++;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Spráááávně !", ButtonType.OK);
            alert.showAndWait();
            nextGame(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Samá voda", ButtonType.OK);
            alert.showAndWait();
            nextGame(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roundsCount = 10;
        try {
            nextGame(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void nextGame(boolean end) throws IOException {
        if(end || globalStage == roundsCount) {
            System.out.println(settings.getUsername1() + " má skore " + playerOneScore);
            System.out.println(settings.getUsername2() + " má skore " + playerTwoScore);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/geoquiz_finalscreen.fxml"));
            FinallController controller = new FinallController(playerOneScore,playerTwoScore, settings.getUsername1(), settings.getUsername2());
            loader.setController(controller);
            Stage stage = (Stage) questionText.getScene().getWindow();
            stage.setTitle("Finish");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else if (globalStage == 0) {
            googleMapView.addMapInitializedListener(() -> configureMap());
            googleMapView.setKey(googleMapsApiKey);
            startTimer(100);
            System.out.println("Running first init");
        } else {
            if(globalStage == 1) {
                if(!settings.getUsername2().isBlank()) {
                    roundsCount=roundsCount*2;
                    multiplayer=true;
                }
            }
            System.out.println(settings.getUsername1());
            Stage stage = (Stage) googleMapView.getScene().getWindow();
            SettingsHolder holder = (SettingsHolder) stage.getUserData();
            Question nova = QuestionCSVLoader.pickRandomQuestion(holder.getQuestions(), holder.getCategory());
            actualQuestion = nova;
            googleMapView.getWebview().getEngine().reload();
            googleMapView.removeReadyListener(this::configureMap);
            googleMapView.removeMapInitializedListener(this::configureMap);
            googleMapView.addMapInitializedListener(() -> configureMap());
            googleMapView.setKey(googleMapsApiKey);
            startTimer(100);
            if(multiplayer) {
                if(actualplayer == settings.getUsername1()) {
                    playernameLabel.setText(settings.getUsername2());
                    actualplayer = settings.getUsername2();
                } else {
                    playernameLabel.setText(settings.getUsername1());
                    actualplayer = settings.getUsername1();
                }
            } else {
                playernameLabel.setText(holder.getUsername1());
                actualplayer = settings.getUsername1();
            }
            globalStage++;
            System.out.println("Running next game ");
        }
        System.out.println(globalStage);

    }

    protected void configureMap() {
        Stage stage = (Stage) questionText.getScene().getWindow();
        SettingsHolder holder = (SettingsHolder) stage.getUserData();
        Question nova = QuestionCSVLoader.pickRandomQuestion(holder.getQuestions(), holder.getCategory());
        actualQuestion = nova;
        settings = holder;

        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(nova.getLatitude(), nova.getLongtitude()))
                .mapType(MapTypeIdEnum.HYBRID)
                .zoom(9);
        map = googleMapView.createMap(mapOptions, false);
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            LatLong latLong = event.getLatLong();
            latitudeLabel.setText(formatter.format(latLong.getLatitude()));
            longitudeLabel.setText(formatter.format(latLong.getLongitude()));
        });
        questionText.setText(nova.getText());
        questionText.setWrapText(true);
        playernameLabel.setText(actualplayer);
        if(globalStage == 0) {
            playernameLabel.setText(settings.getUsername1());
            actualplayer = settings.getUsername1();
            globalStage++;
        }
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public void startTimer(int milis)  {
        Task task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                 final int max = milis;
                for (int i = 1; i <= max; i++) {
                    updateProgress(i, max);
                    Thread.sleep(150);
                }
                return null;
            }
        };
        timerThread = task;
        task.setOnSucceeded(e -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bohužel jsi to nestihl :-( !", ButtonType.OK);
            alert.showAndWait();
            try {
                nextGame(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

}
