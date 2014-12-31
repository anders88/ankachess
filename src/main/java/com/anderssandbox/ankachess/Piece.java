package com.anderssandbox.ankachess;

public class Piece {
    public final boolean isWhite;
    public final PieceType pieceType;
    public final String square;

    private Piece(Factory factory) {
        this.isWhite = factory.isWhite;
        this.pieceType = factory.pieceType;
        this.square = factory.square;
    }

    public static class Factory {
        private boolean isWhite;
        private PieceType pieceType;
        private String square;

        private Factory() {
        }
        public Piece at(String square) {
            return withSquare(square).create();
        }

        private Piece create() {
            return new Piece(this);
        }

        private Factory withWhite(boolean isWhite) {
            this.isWhite = isWhite;
            return this;
        }

        private Factory withPieceType(PieceType pieceType) {
            this.pieceType = pieceType;
            return this;
        }

        private Factory withSquare(String square) {
            this.square = square;
            return this;
        }
    }

    public static Factory white(PieceType pieceType) {
        return new Factory().withWhite(true).withPieceType(pieceType);
    }
}
