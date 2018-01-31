package pomodoroApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/pomoAppMain.fxml"));
        primaryStage.setTitle("Pomodoro Timer");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:resources/images/PomodoroIcon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}