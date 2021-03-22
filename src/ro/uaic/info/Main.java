package ro.uaic.info;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();*/

        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("Application");
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
