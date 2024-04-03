package com.example.interfacemeta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.interfacemeta.heuristicController.Measure;


public class Scene2Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private MKSP mksp;
    private PerformanceInfo perf;


    private String method;

    @FXML
    private HBox hBox;

    @FXML
    private Label algoLabel;
    @FXML
    private Label valeurLabel;
    @FXML
    private Label tempsLabel;

    @FXML
    private Button runbtn;

    @FXML
    private Label nodeLabel;

    public void SwitchToScene1(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("exact.fxml"));
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void generateVbox(int i) {
        VBox vBox1 = new VBox();
        vBox1.getStyleClass().add("vBox");
        vBox1.setFillWidth(true);

        vBox1.setAlignment(Pos.TOP_CENTER);
        vBox1.setPrefSize(164, 364);

        int sac_weight = mksp.getSackWeight(i);

        Label sacL = new Label("SAC " + i + " (" + sac_weight + ")");
        sacL.setMaxWidth(Double.MAX_VALUE);
        sacL.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(
                sacL
        );

        ArrayList<Integer> solution = perf.getSolution();
        int somme_weight = 0;
        for(int j = 0; j< solution.size(); j++ ) {
            if(solution.get(j) == i) {
                int obj_value = mksp.getObjectValue(j);
                int obj_weight = mksp.getObjectWeight(j);

                somme_weight += obj_weight;

                Label label = new Label("Obj" + j + "  (" +obj_value + ", " + obj_weight + " )" );
                label.setMaxWidth(Double.MAX_VALUE);
                label.setAlignment(Pos.CENTER);
                vBox1.getChildren().add(label);

            }
        }



        double percentage_sac = ((double) somme_weight / sac_weight) * 100;

        Label label = new Label(String.format("%.2f", percentage_sac) + "% du sac remplit" );
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        vBox1.getChildren().add(label);


        hBox.getChildren().add(vBox1);

    }

    public void populateHbox() {
        int nb_sac = mksp.getSacksCount();
        for (int i = 0; i < nb_sac; i++) {
            generateVbox(i);
        }
    }

    public void ReRun(ActionEvent e) {
        int opt;
        if(method == "A_star") opt = 2;
        else if (method == "DFS") opt = 0;
        else opt = 1;

        this.perf = Measure(mksp,opt);
        hBox.getChildren().clear();
        populateHbox();
        setContent();
    }

    public void setContent() {

        double eval = perf.getEvaluation();
        long time = perf.getTime();
        algoLabel.setText("Algorithme choisie : " + method);
        valeurLabel.setText("valeur totale : " + eval);
        tempsLabel.setText("Temps d'execution : " + String.format("%,d", time));
        nodeLabel.setText("noeud develope : " + perf.getNodes());

    }




    public void initData(MKSP mksp, PerformanceInfo perf,String method) {
        this.mksp = mksp;
        this.perf = perf;
        this.method = method;
        populateHbox();
        setContent();
    }


    public void CollectData() {
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runbtn.setOnAction(this::ReRun);
    }
}
