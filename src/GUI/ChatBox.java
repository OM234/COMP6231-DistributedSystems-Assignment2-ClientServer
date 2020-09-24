package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ChatBox extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ChatBoxView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("ChatBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
