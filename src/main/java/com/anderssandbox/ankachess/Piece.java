package com.anderssandbox.ankachess;

public class Piece {
    public final boolean isWhite;
    public final PieceType pieceType;
    public final Square square;

    private Piece(Factory factory) {
        this.isWhite = factory.isWhite;
        this.pieceType = factory.pieceType;
        this.square = factory.square;
    }

    public static class Factory {
        private boolean isWhite;
        private PieceType pieceType;
        private Square square;

        private Factory() {
        }
        public Piece at(String squareTxt) {
            return withSquare(Square.fromString(squareTxt)).create();
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

        private Factory withSquare(Square square) {
            this.square = square;
            return this;
        }
    }

    public static Factory white(PieceType pieceType) {
        return new Factory().withWhite(true).withPieceType(pieceType);
    }

    public Piece moveMe(Square toSquare) {
        return new Factory().withPieceType(pieceType).withWhite(isWhite).withSquare(toSquare).create();
    }

}
