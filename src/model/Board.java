package model;

import model.pieces.*;

public class Board {
    private Piece[] allPieces;
    private Piece selectedPiece;
    private boolean whitesTurn;

    public Board() {
        generatePieces();
        whitesTurn = true;
    }

    private void generatePieces() {
        allPieces = new Piece[32];

        // Figurenaufstellung: Weiß
        allPieces[0] = new Rook(true, 0, 0);
        allPieces[1] = new Knight(true, 1, 0);
        allPieces[2] = new Bishop(true, 2, 0);
        allPieces[3] = new Queen(true, 3, 0);
        allPieces[4] = new King(true, 4, 0);
        allPieces[5] = new Bishop(true, 5, 0);
        allPieces[6] = new Knight(true, 6, 0);
        allPieces[7] = new Rook(true, 7, 0);
        allPieces[8] = new Pawn(true, 0, 1);
        allPieces[9] = new Pawn(true, 1, 1);
        allPieces[10] = new Pawn(true, 2, 1);
        allPieces[11] = new Pawn(true, 3, 1);
        allPieces[12] = new Pawn(true, 4, 1);
        allPieces[13] = new Pawn(true, 5, 1);
        allPieces[14] = new Pawn(true, 6, 1);
        allPieces[15] = new Pawn(true, 7, 1);

        // Figurenaufstellung: Schwarz
        allPieces[16] = new Rook(false, 0, 7);
        allPieces[17] = new Knight(false, 1, 7);
        allPieces[18] = new Bishop(false, 2, 7);
        allPieces[19] = new Queen(false, 3, 7);
        allPieces[20] = new King(false, 4, 7);
        allPieces[21] = new Bishop(false, 5, 7);
        allPieces[22] = new Knight(false, 6, 7);
        allPieces[23] = new Rook(false, 7, 7);
        allPieces[24] = new Pawn(false, 0, 6);
        allPieces[25] = new Pawn(false, 1, 6);
        allPieces[26] = new Pawn(false, 2, 6);
        allPieces[27] = new Pawn(false, 3, 6);
        allPieces[28] = new Pawn(false, 4, 6);
        allPieces[29] = new Pawn(false, 5, 6);
        allPieces[30] = new Pawn(false, 6, 6);
        allPieces[31] = new Pawn(false, 7, 6);
    }

    public Piece[] getAllPieces() {
        return allPieces;
    }

    public void setAllPieces(Piece[] allPieces) {
        this.allPieces = allPieces;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }
}