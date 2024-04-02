/*
 * Other comments that will help the TA looking at your code.
 */
//package quiz7;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Starter code for Java FX program to increment and display a counter
 * every time a button is pressed.
 */
public class Counter extends Application {

    // TODO Field here to hold counter value
    private int counter = 0;
    private Label countLabel;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Creating a suitable container to hold the button and the window.
        // GridPane?
        GridPane gP = new GridPane();
        // Define Text Field object, and define its value.
        TextField test = new TextField("hello world");

        // Add the Text Field object to the container defined above.
        gP.add(test, 0, 0);
        countLabel = new Label("count: " + counter);

        // Define Increment Button to press to increment the counter.
        Button inc = new Button("Press to Increment");
        // Add the button to the container.
        gP.add(inc, 150, 150);
        gP.add(countLabel, 0, 500);
        // Add any other label etc.
        // Set an action for the Increment button by calling its setOnAction,
        // updating the counter.
        inc.setOnAction(event -> {
            counter++;
            countLabel.setText("Count: " + counter);
        });
         primaryStage.setScene(new Scene(gP, 300, 250));
        primaryStage.show();
    }
}
