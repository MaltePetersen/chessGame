package model.pieces;

import controller.Controller;
import controller.WinnerController;
import model.Rules;

public class King extends Piece {
    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(1, 1, 1), "king");
    }

    @Override
    public void setSlain(boolean slain, Controller controller) {
        this.slain = slain;
        this.x = 99;
        this.y = 99;

        new WinnerController().setWinner(this.isWhite ? "black" : "white");
    }
}