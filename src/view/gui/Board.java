package view.gui;

import controller.Controller;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class Board extends GridPane implements Runnable {
    private Space space[][] = new Space[8][8];
    private model.Board savegame;
    private Controller controller;
    private boolean spectator;

    public Board() {
        super();
        updateBoard(true);
    }

    // Methode zum Generieren des Schachbretts
    private void updateBoard(boolean emptyBoard) {
        boolean alternativeSpace = false;
        for (int x = 0; x < space[0].length; x++) {
            alternativeSpace = !alternativeSpace;
            for (int y = 0; y < space[1].length; y++) {
                space[x][y] = new Space(x, y, alternativeSpace, emptyBoard, spectator, controller, this);
                alternativeSpace = !alternativeSpace;
                GridPane.setConstraints(space[x][y], x, y);
                getChildren().add(space[x][y]);
            }
        }
    }

    public void updateBoard() {
        updateBoard(false);
    }

    // Thread zum Update der GUI des Zuschauers
    @Override
    public void run() {
        while (controller.getUdp() != null && controller.getUdp().getAllPieces() != null) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(this::updateBoard);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public model.Board getSavegame() {
        return savegame;
    }

    public void setSavegame(model.Board savegame) {
        this.savegame = savegame;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }
}