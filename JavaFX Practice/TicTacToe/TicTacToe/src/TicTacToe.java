import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//import java.awt.*;


public class TicTacToe extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gameGrid = new GridPane();
        gameGrid.setHgap(10);
        gameGrid.setVgap(10);
        for(int row = 0; row<3; row++){
            for(int col = 0; col<3; col++){
                Button cell = new Button();
                
                gameGrid.add(cell, col, row);
            }
        }

    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}