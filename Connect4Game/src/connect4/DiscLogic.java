package connect4;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static connect4.Board.*;

/**
 * This class contains the logic behind the game
 */
public class DiscLogic {

    Board board;
    private int counter;

    DiscLogic(Board board)
    {
        this.board=board;
    }

    /**
     * this class defines disc objects that are being placed by players
     */
    static class Disc extends Circle {
        private final boolean red;
        public Disc(boolean red) {
            super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
            this.red = red;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

    /**
     * @param column column of the disk we want to get
     * @param row row of the disk we want to get
     * @return returns the disk that we wanted, disk may be null
     */
    Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(board.grid[column][row]);
    }

    /**
     * @param disc forwards the disc object
     * @param column column in which we want to place the disc
     * This method places discs both logically and graphically and animates them. While doing so, it launches other methods that check if the game has ended.
     */
    public void placeDisc(Disc disc, int column){
        int check=0;
        int row = ROWS - 1;
        do {
            if (getDisc(column, row).isEmpty())
                break;

            row--;
        } while (row >= 0);

        if (row < 0)
            return;

        board.grid[column][row] = disc;
        board.discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 8) + TILE_SIZE / 4);

        final int currentRow = row;

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TILE_SIZE + 8) + TILE_SIZE / 4);

        animation.play();
        if (gameEnded(column, currentRow)) {
            check=1;
        }

        counter++;
        int finalCheck = check;
        animation.setOnFinished(e ->{
            if(finalCheck ==1)
            try {
                gameOver();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        board.redMove = !board.redMove;
    }

    /**
     * @param column column of the disc we just placed
     * @param row row of the disc we just placed
     * @return true if the game has ended, false if the game continues]
     * This method calculates IntStreams of discs that are in particular arrangement, vertical, horizontal, or 2 diagonals.
     * Then it launches another method that checks if any arrangement is a winning one.
     */
    private boolean gameEnded(int column, int row) {
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(column, r))
                .collect(Collectors.toList());

        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());

        Point2D topLeft = new Point2D(column - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> topLeft.add(i, i))
                .collect(Collectors.toList());

        Point2D botLeft = new Point2D(column - 3, row + 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> botLeft.add(i, -i))
                .collect(Collectors.toList());

        return checkRange(vertical) || checkRange(horizontal)
                || checkRange(diagonal1) || checkRange(diagonal2);
    }

    /**
     * @param points List of points from the previous method
     * @return boolean, true if the list of points is a 4 point line, false if less than 4
     */
    private boolean checkRange(List<Point2D> points) {
        int chain = 0;

        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!board.redMove));
            if (disc.red == board.redMove) {
                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }

        return false;
    }


    /**
     * @throws IOException
     * Changes a scene after the game has ended to a victory screen
     */
    private void gameOver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample2.fxml"));
        Parent root=loader.load();
        Stage window = (Stage)board.root.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();

        Controller2 controller = loader.getController();

        if(!board.redMove && counter<41)
        {
            controller.getRedWins().setVisible(true);
        }

        else if(board.redMove && counter<41)
        {
            controller.getYellowWins().setVisible(true);
        }

        else if(counter==41)
        {
            controller.getDraw().setVisible(true);
        }


    }

}
