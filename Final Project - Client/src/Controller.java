import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableColumn<Item, String> titleCol;
    @FXML
    private TableColumn<Item, String> dateCol;

    @FXML
    private TableView<Item> libraryCatalog;

    private LibraryClient libraryClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize your controller here if needed
        //titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        //dateCol.setCellValueFactory(cellData -> cellData.getValue().dateAddedProperty());
    }

    // Setter method for LibraryClient instance
    public void setLibraryClient(LibraryClient libraryClient) {
        this.libraryClient = libraryClient;
        updateLibraryDisplay();
    }

    // Update UI with localLib data
    private void updateLibraryDisplay() {
        if (libraryClient != null) {
//            Map<Item, Boolean> localLib = libraryClient.getLocalLib();
            /*Map<Item, Boolean> localLib = new HashMap<>();
            localLib.put(new Item("hi", "123"), true);
            localLib.put(new Item("bye", "456"), false);*/
            // Create an ObservableList to hold the items
            ObservableList<Item> itemList = FXCollections.observableArrayList();

            // Add all items from the localLib map to the ObservableList
        //    itemList.addAll(localLib.keySet());

            // Set the items to the TableView
            libraryCatalog.setItems(itemList);
        }
    }
}
