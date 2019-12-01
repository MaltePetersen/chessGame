package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.network.Udp;
import view.gui.Board;
import javafx.scene.control.MenuItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

class FinalString {
    String string;
}

public class Controller {
    // Elemente einer View aus JavaFX müssen als public deklariert werden.
    public MenuItem newGame, spectator,previousWinners;
    public BorderPane borderPane;
    public Label turn, alert;
    public Board chessboard;
    private Udp udp;
    private String alertText;

//Persistenz Feature
    public void showPreviousWinners(){
         Label label = new Label();
         BorderPane layout = new BorderPane();
        layout.setMinSize(400, 200);
        layout.setCenter(label);
        try (BufferedReader br = new BufferedReader(new FileReader("daten.txt")) ){
            String line= "";
            String alternativeLine ;
            while ((alternativeLine = br.readLine()) != null) {
                line = line +"\n"+ alternativeLine;
            }
            System.out.println(line);
            label.setText(line);
            Scene scene = new Scene(layout);
            scene.getStylesheets().add("rootStylesheet.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("GameHistory");
            stage.show();
            stage.setAlwaysOnTop(true);
        } catch (FileNotFoundException e) {
            System.err.println("Datei nicht gefunden!");
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen aus Datei");
        }

    }


    // Initialisierung eines neuen Spiels
    public void newGame() {
        chessboard.setSavegame(new model.Board());
        chessboard.setController(this);
        chessboard.setSpectator(false);
        chessboard.updateBoard();
        udp = new Udp(false);
        udp.setSentAllPieces(chessboard.getSavegame().getAllPieces());
        udp.start();
        turn.getStyleClass().add("alertLabel");
        alert.getStyleClass().add("alertLabel");
        turnLabelSwitchThread();
        alertListenerThread();
    }

    // Fenster für den Zuschauer
    public void spectator() {
        udp = new Udp(true);
        udp.start();
//        Thread zum Update der allPieces, für die als Pakete beim Zuschauer ankommen
        Runnable run = () -> {
            while (true) {
                chessboard.getSavegame().setAllPieces(udp.getAllPieces());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(run).start();

        chessboard.setSavegame(new model.Board());
        chessboard.setController(this);
        chessboard.setSpectator(true);
        chessboard.updateBoard();

        // Thread zur Aktualisierung der GUI
        startChessboardUpdating();
    }

    // Thread zum Aktualisieren der Position der Figuren des Zuschauers
    private void startChessboardUpdating() {
        Thread thread = new Thread(chessboard);
        thread.start();
    }

    // Thread zum Anzeigen wer gerade am Zug ist
    private void turnLabelSwitchThread() {
        Thread turnLabel = new Thread(() -> {
            while (true) {
                String whoseTurn = "Waiting for " + (chessboard.getSavegame().isWhitesTurn() ? "White" : "Black");
                Platform.runLater(() -> turn.setText(whoseTurn));

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        turnLabel.start();
    }

    // Thread gibt alle Alerts für 2 Sekunden aus, Alerts werden von dem Model zum String alertText geschickt
    private void alertListenerThread() {
        Thread alertLabel = new Thread(() -> {
            boolean waitLong;

            final FinalString shownAltert = new FinalString();
            shownAltert.string = null;

            while (true) {
                waitLong = alertText != null;

                shownAltert.string = alertText;

                Platform.runLater(() -> alert.setText(Objects.requireNonNullElse(shownAltert.string, "")));
                alertText = null;

                try {
                    Thread.sleep(waitLong ? 2000 : 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        alertLabel.start();
    }

    public Udp getUdp() {
        return udp;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }
}