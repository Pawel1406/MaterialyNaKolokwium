package RozbudowaneStrukturyJava.Kolokwium2022;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        File fxmlFile = new File("view.fxml");
        URL fxmlUrl = fxmlFile.toURI().toURL();
        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        VBox root=loader.load();


        primaryStage.setScene(new Scene(root,400,500));
        primaryStage.setTitle("Client");
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

}
