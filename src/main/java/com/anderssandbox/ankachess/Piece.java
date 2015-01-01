package com.anderssandbox.ankachess;

public class Piece {
    public final boolean isWhite;
    public final PieceType pieceType;

    private Piece(boolean isWhite, PieceType pieceType) {
        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    public static Piece white(PieceType pieceType) {
        return new Piece(true,pieceType);
    }


    @Override
    public String toString() {
        return "Piece{" +
                "isWhite=" + isWhite +
                ", pieceType=" + pieceType +
                '}';
    }
}
