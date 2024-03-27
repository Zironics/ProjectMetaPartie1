package com.example.interfacemeta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Scene2Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int nb_obj;
    private int nb_sac;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPaneRight;

    @FXML
    private Button testButton;





    public void SwitchToScene1(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("exact.fxml"));
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void generateGridLeft() {


        // Create the GridPane
        GridPane gridPane = new GridPane();

        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col1.setMaxWidth(219.20001220703125);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(126.39997558593751);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col2.setMaxWidth(135.199951171875);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(104.8000244140625);

        gridPane.getColumnConstraints().addAll(col1, col2);

        // Children
        for (int i = 0; i < this.nb_sac; i++) {
            // Row constraints for each row
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(126.60001220703126);
            row.setMinHeight(10.0);
            row.setPrefHeight(41.80000991821289);
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);

            Label label = new Label("Sack " + (i + 1) + ":");
            GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);

            TextField textField = new TextField();
            textField.setPrefSize(86.0, 26.0);
            textField.getStyleClass().add("sackWeight");
            textField.setPromptText("Sack weight");
            GridPane.setHalignment(textField, javafx.geometry.HPos.CENTER);

            // Adding children to new rows
            gridPane.add(label, 0, i);
            gridPane.add(textField, 1, i);
        }

        // Add the GridPane to the left side of the BorderPane
        scrollPane.setContent(gridPane);

    }

    public void generateGridRight() {
        GridPane gridPane = new GridPane();

        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(100.0);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(100.0);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col3.setMinWidth(10.0);
        col3.setPrefWidth(100.0);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);



        // Children generation
        for (int i = 0; i < this.nb_obj; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(126.79999389648438);
            row.setMinHeight(10.0);
            row.setPrefHeight(41.80000991821289);
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);

            Label label = new Label("Object " + i);
            label.setStyle("-fx-font-size: 15px;");
            GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);

            TextField weightTextField = new TextField();
            weightTextField.getStyleClass().add("objWeight");
            weightTextField.setPrefSize(91.0, 26.0);
            weightTextField.setMaxSize(91.0, 26.0);
            weightTextField.setPromptText("Object weight");
            GridPane.setHalignment(weightTextField, javafx.geometry.HPos.CENTER);

            TextField valueTextField = new TextField();
            valueTextField.setPrefSize(85.0, 26.0);
            valueTextField.getStyleClass().add("objValue");
            valueTextField.setMaxSize(85.0, 26.0);
            valueTextField.setPromptText("Object value");
            GridPane.setHalignment(valueTextField, javafx.geometry.HPos.CENTER);

            // Adding children to the grid
            gridPane.add(label, 0, i);
            gridPane.add(weightTextField, 1, i);
            gridPane.add(valueTextField, 2, i);
        }

        scrollPaneRight.setContent(gridPane);
    }

    public void initData(int nb_obj,int nb_sac) {
        this.nb_obj = nb_obj;
        this.nb_sac = nb_sac;
        generateGridRight();
        generateGridLeft();
    }


    public void CollectData() {
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(nb_obj+ " " + nb_sac);
    }
}
