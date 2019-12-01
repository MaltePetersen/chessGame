package view.gui;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.pieces.King;
import model.pieces.Piece;

class Space extends Button {
    private int x, y;

    Space(int x, int y, boolean alternative, boolean emptyBoard, boolean spectator, Controller controller, Board board) {
        super();
        this.setMinSize(80, 80);
        this.x = x;
        this.y = y;

        this.getStyleClass().add(alternative ? "button-alternative" : "button");
        if (!emptyBoard) this.prepare(controller, board, spectator);
    }

    private void prepare(Controller controller, Board board, boolean spectator) {
        for (Piece piece : board.getSavegame().getAllPieces()) {
            if (piece.standsOn(x, y) & !piece.isSlain()) {

                // setzt das Bild
                this.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(piece.getImagePath()))));

                // Funktionalitäten werden bei Zuschauer nicht implementiert
                if (spectator) return;

                // markiert das Feld, falls Figur ausgewählt ist
                if (piece == board.getSavegame().getSelectedPiece()) {
                    this.getStyleClass().add("selected");
                }

                // markiert die Figuren, die geschlagen werden können
                if (board.getSavegame().getSelectedPiece() != null && board.getSavegame().getSelectedPiece().isWhite() != piece.isWhite() && board.getSavegame().getSelectedPiece().canAttack(x, y, board.getSavegame().getAllPieces())) {
                    this.getStyleClass().add("canAttack");

                    if (piece.getClass() == King.class) {
                        controller.setAlertText("Check!");
                    }
                }

                this.setOnAction(e -> {
                    // Versuch zu ziehen obwohl man nicht am Zug ist, wird unterbunden
                    if (board.getSavegame().getSelectedPiece() == null && board.getSavegame().isWhitesTurn() != piece.isWhite()) {
                        controller.setAlertText("It's the turn \nof the other \nplayer!");
                    }

                    // Auswählen einer Figur
                    else if (board.getSavegame().getSelectedPiece() == null) {
                        board.getSavegame().setSelectedPiece(piece);
                        board.updateBoard();
                    }

                    // Abwählen der aktuellen Auswahl
                    else if (board.getSavegame().getSelectedPiece() == piece) {
                        board.getSavegame().setSelectedPiece(null);
                        board.updateBoard();
                    }

                    // Überprüfung, ob zu schlagende Figur einem selbst gehört
                    else if (board.getSavegame().getSelectedPiece().isWhite() == piece.isWhite()) {
                        controller.setAlertText("You are not \nallowed to attack \nyour own pieces!");

                        board.getSavegame().setSelectedPiece(null);
                        board.updateBoard();
                    }

                    // Schlagen einer Figur
                    else {
                        board.getSavegame().getSelectedPiece().attack(x, y, piece, board.getSavegame(), controller);
                        board.getSavegame().setSelectedPiece(null);

                        if (piece.getClass() == King.class) {
                            board.setSpectator(true);
                        }

                        board.updateBoard();
                    }
                });
                return;
            }
        }

        this.setOnAction(e -> {
                    if (board.getSavegame().getSelectedPiece() != null) {
                        if (!board.getSavegame().getSelectedPiece().move(x, y, board.getSavegame()))
                            controller.setAlertText("You are not \nallowed to move \nyour piece \nlike that!");
                        board.getSavegame().setSelectedPiece(null);
                        board.updateBoard();
                    }
                }
        );

        // markiert die Felder, auf die sich bewegt werden kann
        if (board.getSavegame().getSelectedPiece() != null && board.getSavegame().getSelectedPiece().canMove(x, y, board.getSavegame().getAllPieces()))
            this.getStyleClass().add("canMove");
    }
}