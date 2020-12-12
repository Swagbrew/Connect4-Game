package connect4;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Controller class used to access and controll objects from the FXML
 */
public class Controller2 {

    @FXML
    private ImageView redWins, yellowWins, draw;

    public Controller2()
    {

    }

    @FXML
    private void initialize()
    {

    }

    /**
     * @return returns ImageView object
     * simple getter
     */
    public ImageView getRedWins() {
        return redWins;
    }

    /**
     *  @return returns ImageView object
     *  simple getter
     */
    public ImageView getYellowWins() {
        return yellowWins;
    }

    /**
     * @return returns ImageView object
     * simple getter
     */
    public ImageView getDraw() {
        return draw;
    }
}
