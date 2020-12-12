package connect4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class used to access and controll objects from the FXML
 */
public class Controller {

    Board board;

    public Controller()
    {

    }

    @FXML
    private void initialize()
    {

    }

    /**
     * @param event
     * startGame method changes the scene from main menu to an actual game
     */
    @FXML
    private void startGame(ActionEvent event)
    {
        board = new Board();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(board.createScene()));
        board.discLogic=new DiscLogic(board);
        window.show();
    }

    /**
     * Exits the application from the main menu
     */
    @FXML
    private void endGame()
    {
        System.exit(0);
    }




}
