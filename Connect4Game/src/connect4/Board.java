package connect4;

import javafx.scene.Parent;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;


/**
 * Board class stores and initializes the board that we use to play
 */
public class Board {
    public static final int TILE_SIZE=100;
    public static final int COLUMNS=7;
    public static final int ROWS=6;

    public DiscLogic.Disc[][] grid = new DiscLogic.Disc[COLUMNS][ROWS];
    public boolean redMove = true;
    public Pane discRoot = new Pane();

    DiscLogic discLogic;

    Pane root;

    /**
     * @return returns root Pane to apply it to a scene
     * Creates root Pane and adds objects to it
     */
    Parent createScene(){
        root = new Pane();
        Shape board = makeBoard();
        root.getChildren().add(discRoot);
        root.getChildren().add(board);
        root.getChildren().addAll(makeColumns());
        return root;
    }

    /**
     * @return returns the board
     * Makes the board in which we insert the circles later
     */
    private Shape makeBoard()
    {
        Shape shape = new Rectangle((COLUMNS+1)*TILE_SIZE, (ROWS+1)*TILE_SIZE);

        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                Circle circle = new Circle(TILE_SIZE/2);
                circle.setCenterX(TILE_SIZE/2);
                circle.setCenterY(TILE_SIZE/2);
                circle.setTranslateX(j*(TILE_SIZE+8) + TILE_SIZE/4);
                circle.setTranslateY(i*(TILE_SIZE+8) + TILE_SIZE/4);

                shape= Shape.subtract(shape, circle);
            }
        }
        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        shape.setEffect(lighting);
        shape.setFill(Color.BLACK);

        return shape;
    }


    /**
     * @return returns list of columns
     * Makes the columns that highlight themselves when hovered with a mouse, and are used to add circles to them
     */
    private List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 8) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(0, 200, 200, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            rect.setOnMouseClicked(e -> discLogic.placeDisc(new DiscLogic.Disc(redMove), column));

            list.add(rect);
        }

        return list;
    }



}