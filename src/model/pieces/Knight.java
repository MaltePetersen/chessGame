package model.pieces;

import model.Rules;

public class Knight extends Piece {
    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(2, 2, 0, true, true), "knight");
    }
}