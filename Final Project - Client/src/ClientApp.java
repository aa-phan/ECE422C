import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("CheckoutScreen.fxml"));

        // Load the root node
        Parent root = fxmlLoader.load();

        // Get the controller
        Controller controller = fxmlLoader.getController();

        // Pass LibraryClient instance to the controller
        controller.setLibraryClient(client);

        // Create and show the scene
        Scene scene = new Scene(root, 787, 535);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
