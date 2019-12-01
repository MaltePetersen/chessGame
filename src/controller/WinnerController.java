package controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class WinnerController {
    private Label label = new Label();
    private BorderPane layout = new BorderPane();

    public void setWinner(String winner) {
        layout.setMinSize(400, 200);
        label.setText("The winner is " + winner + ", Congratulations!");
        layout.setCenter(label);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("rootStylesheet.css");
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Winner: " + winner);
        stage.show();
        stage.setAlwaysOnTop(true);
        writeWinnerList(winner);

    }
    //PersitenzFeature
    public void writeWinnerList(String winner){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("daten.txt",true))){


                bw.write("The winner was: "+ winner + " EndTime: " + ZonedDateTime.now());
                bw.newLine();


        }
        catch(IOException ioe) {
            System.err.println("Fehler beim Schreiben!");
        }

    }
}
