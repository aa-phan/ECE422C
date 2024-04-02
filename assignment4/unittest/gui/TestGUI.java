package unittest.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;
import unittest.annotations.Order;
import unittest.annotations.Ordered;
import unittest.annotations.Test;
import unittest.driver.TestDriver;
import unittest.listeners.GUITestListener;
import unittest.results.TestClassResult;
import unittest.results.TestMethodResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;


public class TestGUI extends Application {

    @FXML
    public TextArea outputField;
    public TextField directoryAddress;
    public Label dneLabel;
    @FXML
    private TableView<Pair<String, String>> selectTable;
    private ArrayList<ClassModel> classes;

    @FXML
    private TableColumn<Pair<String, String>, String> classesCol;
    @FXML
    private TableColumn<Pair<String, String>, String> methodsCol;
    private ArrayList<String> methodsSelected = new ArrayList<>();
    public static List<TestClassResult> TestResultsGUI = new ArrayList<>();

    private List<GUITestListener> testListeners = new ArrayList<>();

    @Override
    public void start(Stage applicationStage) throws IOException {
        // TODO: Implement this method
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));


        // Set the scene to the stage and display it
        Scene scene = new Scene(root);
        applicationStage.setScene(scene);
        applicationStage.show();

    }

    @FXML
    public void initialize() {

        updateTable(new ActionEvent());

        selectTable.setRowFactory(tv -> {
            TableRow<Pair<String, String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Pair<String, String> rowData = row.getItem();

                    if (rowData.getValue().equals("")) {
                        // if the method col has nothing, we want to list all the methods listed beneath it

                        Class<?> testClass = null;
                        try {
                            testClass = Class.forName(rowData.getKey());

                            Method[] methods = testClass.getDeclaredMethods();
                            Arrays.sort(methods, Comparator.comparing(Method::getName));

                            List<Method> finallist = new ArrayList<>();
                            List<Method> unordered = new ArrayList<>();

                            // if the testclass is an ordered test class, we want to print the correct order of tests
                            if (Arrays.stream(testClass.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().equals(Ordered.class))) {
                                for (int i = 0; i < methods.length; i++) {
                                    Method meth = methods[i];

                                    System.out.println(meth.getName());

                                    if (meth.isAnnotationPresent(Test.class)) {
                                        if (meth.isAnnotationPresent(Order.class)) {
                                            int rank = meth.getAnnotation(Order.class).value();

                                            if (finallist.isEmpty()) {
                                                finallist.add(meth);
                                            } else {

                                                for (int j = 0; j < finallist.size(); j++) {
                                                    if (rank < finallist.get(j).getAnnotation(Order.class).value()) {
                                                        finallist.add(j, meth);
                                                        break;
                                                    }

                                                    if (j == finallist.size() - 1) {
                                                        finallist.add(meth);
                                                        break;
                                                    }
                                                }
                                            }

                                        } else {
                                            unordered.add(meth);
                                        }

                                    }
                                }

                                finallist.addAll(unordered);
                            } else {
                                // if it isnt ordered, we need to sort alphanumerically
                                for (int i = 0; i < methods.length; i++) {
                                    Method meth = methods[i];

                                    System.out.println(meth.getName());

                                    if (meth.isAnnotationPresent(Test.class)) {

                                        if (finallist.isEmpty()) {
                                            finallist.add(meth);
                                        } else {

                                            for (int j = 0; j < finallist.size(); j++) {
                                                if (finallist.get(j).getName().compareTo(meth.getName()) >= 0) {
                                                    finallist.add(j, meth);
                                                    break;
                                                }

                                                if (j == finallist.size() - 1) {
                                                    finallist.add(meth);
                                                    break;
                                                }
                                            }
                                        }

                                    }

                                }
                            }


                            for (Method method : finallist) {
                                if (method.isAnnotationPresent(Test.class)) {
                                    String output = rowData.getKey();
                                    output = output + "." + method.getName() + " ";
                                    outputField.appendText(output);
                                    methodsSelected.add(output.substring(0, output.length() - 1));
                                }
                            }

                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    } else {

                        String output = rowData.getKey();
                        output = output + "." + rowData.getValue() + " ";
                        outputField.appendText(output);
                        methodsSelected.add(output.substring(0, output.length() - 1));
                    }
                }
            });
            return row;
        });

    }

    private void parseMethods() {
        // Queue to hold the methods to be executed
        Queue<String> methodsQueue = new LinkedList<>(methodsSelected);

        // Start the sequential execution of tests
        executeNextTest(methodsQueue);
    }

    private void executeNextTest(Queue<String> methodsQueue) {
        if (methodsQueue.isEmpty()) {
            Platform.runLater(() -> {
                outputField.appendText("\n++++++++FULL RESULTS:+++++++\n");
                outputField.appendText(TestDriver.printResults(TestResultsGUI));
                outputField.appendText("------------------------------------------------------------------------------\n");
            });
            methodsSelected.clear();

            return;
        }

        String method = methodsQueue.poll();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> outputField.appendText("\n" + method + " HAS STARTED\n"));
                GUITestListener obj1 = new GUITestListener();
                obj1.testStarted(method);
                testListeners.add(obj1);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Platform.runLater(() -> outputField.appendText("\nTest execution was interrupted.\n"));
                }

                return null;
            }
            protected void succeeded() {
                super.succeeded();
                outputField.appendText(method + " ");

                if (TestResultsGUI.get(TestResultsGUI.size() - 1).getTestMethodResults().get(0).isPass()) {
                    outputField.appendText("PASSED.\n");
                } else {
                    outputField.appendText("FAILED.\n");
                }
                executeNextTest(methodsQueue);
            }
        };
        new Thread(task).start();
    }

    private ArrayList<Pair<String, String>> getClassesList() {
        ArrayList<Pair<String, String>> result = new ArrayList<>();

        for (ClassModel c : classes) {
            for (String s : c.getMethods()) {
                result.add(new Pair<>(c.getFileName(), s));
            }
        }

        return result;
    }
    public void passedTest(TestClassResult testClassResult, TestMethodResult testMethodResult){
        outputField.appendText(testClassResult.getTestClassName() + "." + testMethodResult.getName() + " : PASS\n");
    }
    public void failedTest(TestClassResult testClassResult, TestMethodResult testMethodResult){
        outputField.appendText(testClassResult.getTestClassName() + "." + testMethodResult.getName() + " : FAIL\n");
    }
    @FXML
    public void startTests() {

        TestResultsGUI.clear();
        if (!methodsSelected.isEmpty()) {
            outputField.appendText("\n------------------------------------------------------------------------------");
            parseMethods();
            System.out.println("yayy!");
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch application
    }

    public void clearOutput(ActionEvent actionEvent) {
        outputField.clear();
        methodsSelected.clear();
    }

    public void updateTable(ActionEvent actionEvent) {

        File directory = new File(directoryAddress.getText());

        if ((directory.exists() && directory.isDirectory())) {
            dneLabel.setVisible(false);
            File[] fileList = directory.listFiles();
            ArrayList<String> fileNames = new ArrayList<>();

            classes = new ArrayList<>();

            for (File file : fileList) {
                if (file.isFile()) {
                    String name = file.getName().substring(0, file.getName().length() - 5);

                    try {
                        String className = directory.getName() + "." + name;
                        Class<?> testClass = Class.forName(className);
                        fileNames.add(name);
                            ClassModel c = new ClassModel(className);

                            Method[] methods = testClass.getDeclaredMethods();

                            for (Method method : methods) {
                                if (method.isAnnotationPresent(Test.class)) {
                                    c.addMethod(method.getName());
                                }
                            }

                            classes.add(c);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(getClassesList());

            ObservableList<Pair<String, String>> observableListOfPairs = FXCollections.observableArrayList(getClassesList());
            selectTable.setItems(observableListOfPairs);
            classesCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
            methodsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
        } else {
            dneLabel.setVisible(true);
        }
    }
}
