package model;

import model.pieces.Piece;

import java.io.Serializable;

public class Rules implements Serializable {

    private int horizontal, vertical, diagonal;
    private boolean reverse, knight;

    public Rules(int horizontal, int vertical, int diagonal, boolean reverse, boolean knight) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.diagonal = diagonal;
        this.reverse = reverse;
        this.knight = knight;
    }

    // Konstruktor mit Standardwerten für reverse und knight
    public Rules(int horizontal, int vertical, int diagonal) {
        this(horizontal, vertical, diagonal, true, false);
    }

    public boolean canMove(int x1, int y1, int x2, int y2, boolean isWhite, Piece[] allPieces) {
        int diffX = x2 - x1;
        int diffY = y2 - y1;

        // Bauern dürfen nicht rückwärts laufen
        if (!reverse && (isWhite ? diffY < 0 : diffY > 0)) return false;

        // überprüft, ob die Figuren so weit laufen dürfen
        if (Math.abs(diffX) > horizontal && diffY == 0) {
            return false;
        } else if (Math.abs(diffY) > vertical && diffX == 0) {
            return false;
        } else if (Math.abs(diffX) == Math.abs(diffY) && Math.abs(diffX) > diagonal) {
            return false;
        } else if (Math.abs(diffX) != Math.abs(diffY) && diffX != 0 && diffY != 0 && !knight) {
            return false;
        }

        // Sonderregeln für den Springer
        if (knight) {
            return Math.abs(diffX) == 1 && Math.abs(diffY) == 2 || Math.abs(diffX) == 2 && Math.abs(diffY) == 1;
        }

        // Überprüft das Überspringen von Figuren
        // horizontal
        if (diffY == 0) {
            int x = (diffX < 0 ? x1 - 1 : x1 + 1);

            while (x2 != x) {
                if (inTheWay(x, y1, allPieces)) return false;
                x = (diffX < 0 ? x - 1 : x + 1);
            }
            // vertikal
        } else if (diffX == 0) {
            int y = (diffY < 0 ? y1 - 1 : y1 + 1);

            while (y2 != y) {
                if (inTheWay(x1, y, allPieces)) return false;
                y = (diffY < 0 ? y - 1 : y + 1);
            }
        }
        // diagonal
        else {
            int y = (diffY < 0 ? y1 - 1 : y1 + 1);
            int x = (diffX < 0 ? x1 - 1 : x1 + 1);

            while (y2 != y && x2 != x) {
                if (inTheWay(x, y, allPieces)) return false;
                y = (diffY < 0 ? y - 1 : y + 1);
                x = (diffX < 0 ? x - 1 : x + 1);
            }
        }
        return true;
    }

    private boolean inTheWay(int x, int y, Piece[] allPieces) {
        for (Piece piece : allPieces) {
            if (piece.standsOn(x, y)) {
                return true;
            }
        }
        return false;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public void setPawnAttack() {
        this.vertical = 0;
        this.diagonal = 1;
    }

    public void unsetPawnAttack(boolean hasMoved) {
        this.vertical = (hasMoved ? 1 : 2);
        this.diagonal = 0;
    }

}