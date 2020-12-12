package connect4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param primaryStage
     * @throws Exception
     *
     * This method initializes JavaFX application
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Connect4");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @param args
     * Main method, launches the start() method of JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
