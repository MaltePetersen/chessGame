package model.pieces;

import controller.Controller;
import model.Rules;

import java.io.Serializable;

public abstract class Piece implements Serializable {

    // nicht private sondern paketsichtbar, da die Variablen in Unterklassen gebraucht werden
    boolean isWhite, hasMoved, slain;
    int x, y;
    Rules rules;
    private String imagePath;

    public Piece(boolean isWhite, int x, int y, Rules rules, String imagePath) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        this.rules = rules;
        this.imagePath = imagePath;
    }

    public boolean move(int x, int y, model.Board savegame) {
        if (canMove(x, y, savegame.getAllPieces())) {
            this.x = x;
            this.y = y;
            setHasMoved(true);
            savegame.setWhitesTurn(!savegame.isWhitesTurn());

            return true;
        } else {
            return false;
        }
    }

    public boolean canMove(int x, int y, Piece[] allPieces) {
        return rules.canMove(this.x, this.y, x, y, isWhite, allPieces);
    }

    public void attack(int x, int y, Piece piece, model.Board savegame, Controller controller) {
        if (move(x, y, savegame)) {
            piece.setSlain(true, controller);
        }
    }

    // theoretisch obsolet, Pawn muss die Methode aber überschreiben können
    public boolean canAttack(int x, int y, Piece[] allPieces) {
        return canMove(x, y, allPieces);
    }

    public boolean standsOn(int x, int y) {
        return this.x == x && this.y == y;
    }


    // Getter & Setter
    public boolean isWhite() {
        return isWhite;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isSlain() {
        return slain;
    }

    // sobald eine Figur geschlagen ist, werden die Koordinaten auf 99/99 gesetzt, damit diese nicht mehr auf dem Spielfeld vorhanden ist
    public void setSlain(boolean slain, Controller controller) {
        this.slain = slain;
        this.x = 99;
        this.y = 99;

        controller.setAlertText((this.isWhite() ? "White " : "Black ") + this.getName() + " \nwas slain!");
    }

    // konvertiert den variablen Teil des Bild-Pfades in einen kompletten Pfad
    public String getImagePath() {
        return "/assets/" + (isWhite ? "white" : "black") + "_" + imagePath + ".png";
    }

    private String getName() {
        return ("" + this.getClass()).replace("class model.pieces.", "");
    }
}