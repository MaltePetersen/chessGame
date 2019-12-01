package model.pieces;

import controller.Controller;
import model.Rules;

public class Pawn extends Piece {
    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y, new Rules(0, 2, 0, false, false    ),"pawn");
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        super.setHasMoved(hasMoved);
        if (hasMoved) super.rules.setVertical(1);
    }

    @Override
    public void attack(int x, int y, Piece piece, model.Board savegame, Controller controller) {
        super.rules.setPawnAttack();

        if (move(x, y, savegame)) {
            piece.setSlain(true, controller);
        }

        super.rules.unsetPawnAttack(hasMoved);
    }

    public boolean canAttack(int x, int y, Piece[] allPieces) {
        super.rules.setPawnAttack();

        if (canMove(x, y, allPieces)) {
            super.rules.unsetPawnAttack(hasMoved);
            return true;
        }

        super.rules.unsetPawnAttack(hasMoved);

        return false;
    }
}