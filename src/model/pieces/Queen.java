package model.pieces;

import model.Rules;

public class Queen extends Piece {
    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(8, 8, 8), "queen");
    }
}