import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ClientApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Connect to the server and create a LibraryClient instance
        Socket socket = new Socket("localhost", 1234);
        LibraryClient client = new LibraryClient(socket, "username");
        client.listenForMessage();
        //client.sendMessage();

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("CheckoutScreen.fxml"));

        // Load the root node
        Parent root = fxmlLoader.load();

        // Get the controller
        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        // Pass LibraryClient instance to the controller
        controller.setLibraryClient(client);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Set the scene dimensions to match the screen size
        // Create and show the scene
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
