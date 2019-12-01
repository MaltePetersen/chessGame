package model.pieces;

import model.Rules;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(0, 0, 8), "bishop");
    }
}