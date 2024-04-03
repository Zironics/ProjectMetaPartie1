package com.example.interfacemeta;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class heuristicController implements Initializable {


    @FXML
    private ComboBox<String> comboBoxH;

    @FXML
    private Button fileButtonH;
    @FXML
    private Button nextButton;

    private String file;

    public static PerformanceInfo Measure(MKSP mksp,int option){
        PerformanceInfo perf = new PerformanceInfo();
        perf.setTotalValue(mksp.getTotalValue());

        switch (option) {
            case 0: {
                StateSolutionSearch solution = new StateSolutionSearch(mksp);
                long startTime = System.nanoTime();

                ArrayList<Integer> sol = solution.DFS();

                long endTime = System.nanoTime();
                perf.setSolution(sol);
                perf.setTime(endTime  - startTime);
                perf.setNodeNumber(solution.getDevelopedNodes());
                perf.setEvaluation(mksp.getPerformance(sol));

                break;
            }
            case 1: {
                StateSolutionSearch solution = new StateSolutionSearch(mksp);
                long startTime = System.nanoTime();

                ArrayList<Integer> sol = solution.BFS();

                long endTime = System.nanoTime();
                perf.setSolution(sol);
                perf.setTime(endTime  - startTime);
                perf.setNodeNumber(solution.getDevelopedNodes());
                perf.setEvaluation(mksp.getPerformance(sol));

                break;
            }
            case 2: {
                HeuristicSearch solution = new HeuristicSearch(mksp);
                long startTime = System.nanoTime();

                ArrayList<Integer> sol = solution.AStar();


                long endTime = System.nanoTime();
                perf.setSolution(sol);
                perf.setTime(endTime  - startTime);
                perf.setNodeNumber(solution.getDevelopedNodes());
                perf.setEvaluation(mksp.getPerformance(sol));

                break;
            }
        }

        return perf;
    }

    public void SwitchToResult(ActionEvent e) throws IOException {
         if(file != null) {

             csv_handler csv = new csv_handler();
             MKSP mksp = csv.getMkspFromCSV("./Examples/"+file);
             PerformanceInfo perf = Measure(mksp,2);

             FXMLLoader loader = new FXMLLoader(
                     getClass().getResource(
                             "scene2.fxml"
                     )
             );

             Stage stage = new Stage(StageStyle.DECORATED);

             Scene scene = new Scene(loader.load());
             stage.setScene(
                     scene
             );

             String css = this.getClass().getResource("style.css").toExternalForm();

             scene.getStylesheets().add(css);

             Scene2Controller controller = loader.getController();

             controller.initData(mksp,perf,"A_star");
             stage.show();

         }
    }

    public void ChooseFile(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) (nextButton).getScene().getWindow();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV file","*.csv"));
        File initialDirectory = new File("./Examples/"); // Specify your desired initial directory here
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(stage);


        if(selectedFile != null) {
            file = selectedFile.getName();
        }
        else System.out.println("no files");
    }

    public void SwitchToExact() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("exact.fxml"));
        Stage stage = (Stage) (nextButton).getScene().getWindow();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        String [] content = {"exact method","heuristic"};
        comboBoxH.getItems().addAll(content);
        comboBoxH.getSelectionModel().select(1);

        comboBoxH.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public
            void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                // switch to scene 1 for now (exact.fxml)
                try {
                    SwitchToExact();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        fileButtonH.setOnAction(this::ChooseFile);
    }
}
