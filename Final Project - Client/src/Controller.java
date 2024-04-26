import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableColumn<Item, String> titleCol;
    @FXML
    private TableColumn<Item, String> dateCol;
    @FXML
    private TableColumn<Item, String> typeCol;
    @FXML
    private TableColumn<Item, String> authorCol;
    @FXML
    private TableColumn<Item, String> narratorCol;
    @FXML
    private TableColumn<Item, String> productionCol;
    @FXML
    private TableColumn<Item, String> directorCol;
    @FXML
    private TableColumn<Item, String> gameDesignerCol;
    @FXML
    private TableColumn<Item, String> illustratorCol;
    @FXML
    private TableColumn<Item, Boolean> statusCol;

    @FXML
    private TableView<Item> libraryCatalog;
    @FXML
    private MenuItem refreshMenu;
    @FXML
    private MenuItem logOff;
    private Stage stage;

    private LibraryClient libraryClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set cell value factories for columns
        resizeColumns();
        populateColumns();
        libraryCatalog.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Item selectedItem = libraryCatalog.getSelectionModel().getSelectedItem();
                    System.out.println("Clicked on: " + selectedItem.getTitle());
                    if(libraryClient.getLocalLib().get(selectedItem)){
                        /*libraryClient.oos.writeInt(selectedItem.getTitle().length());
                        libraryClient.oos.writeUTF("/checkout " + selectedItem.getTitle());
                        libraryClient.oos.flush();*/
                        libraryClient.sendMessage("/checkout " + selectedItem.getTitle());
                        System.out.println("/checkout " + selectedItem.getTitle());
                    }
                    else{
                        /*libraryClient.oos.writeInt(selectedItem.getTitle().length());
                        libraryClient.oos.writeUTF("/return " + selectedItem.getTitle());
                        libraryClient.oos.flush();*/
                        libraryClient.sendMessage("/return " + selectedItem.getTitle());
                    }
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
                        updateCheckout();

                    }));
                    timeline.play();
                }
            });
            return row;
        });
    }

    public void populateColumns() {
        titleCol.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getTitle()));
        dateCol.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDateAdded()));
        typeCol.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getType()));
        statusCol.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            boolean status = libraryClient.getLocalLib().getOrDefault(item, false);
            return Bindings.createObjectBinding(() -> status);
        });
        // For author column
        authorCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Book) {
                return Bindings.createStringBinding(() -> ((Book) cellData.getValue()).getAuthor());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });

        // For narrator column
        narratorCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Audiobook) {
                return Bindings.createStringBinding(() -> ((Audiobook) cellData.getValue()).getNarrator());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });

        // For production column
        productionCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof DVD) {
                return Bindings.createStringBinding(() -> ((DVD) cellData.getValue()).getProductionCompany());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });

        // For director column
        directorCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof DVD) {
                return Bindings.createStringBinding(() -> ((DVD) cellData.getValue()).getDirector());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });

        // For gameDesigner column
        gameDesignerCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Game) {
                return Bindings.createStringBinding(() -> ((Game) cellData.getValue()).getGameDesigner());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });

        // For illustrator column
        illustratorCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof comicBook) {
                return Bindings.createStringBinding(() -> ((comicBook) cellData.getValue()).getIllustrator());
            } else {
                return Bindings.createStringBinding(() -> "");
            }
        });
    }

    public void resizeColumns(){
        libraryCatalog.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        titleCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        dateCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        typeCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        authorCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        narratorCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        productionCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        directorCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        gameDesignerCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        illustratorCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));
        statusCol.prefWidthProperty().bind(libraryCatalog.widthProperty().divide(10));

    }
    // Setter method for LibraryClient instance
    public void setLibraryClient(LibraryClient libraryClient) {
        this.libraryClient = libraryClient;
        updateLibraryDisplay();
    }

    // Update UI with localLib data
    public void updateLibraryDisplay() {
        if (libraryClient != null) {
            Map<Item, Boolean> localLib = libraryClient.getLocalLib();
            // Create an ObservableList to hold the items
            ObservableList<Item> itemList = FXCollections.observableArrayList();

            // Add all items from the localLib map to the ObservableList
            itemList.addAll(localLib.keySet());

            // Set the items to the TableView
            libraryCatalog.setItems(itemList);

        }
    }
    public void updateCheckout() {
        statusCol.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            boolean status = libraryClient.getLocalLib().getOrDefault(item, false);
            return Bindings.createObjectBinding(() -> status);
        });
        libraryCatalog.refresh();
    }
    public void setLogOff(){
        libraryClient.sendMessage("bye");
        stage.close();
        Platform.exit();
        System.exit(0);
    }


    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
}
