package model.pieces;

import model.Rules;

public class Rook extends Piece {
    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(8, 8, 0), "rook");
    }
}