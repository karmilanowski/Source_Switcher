package pl.solix.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.solix.model.ToScreenConnection;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        VBox anchorPane = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("PZU");
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            ToScreenConnection.sendUdpCommand(ToScreenConnection.MI);
        });

    }
}
