package com.anderssandbox.ankachess;

public class Move {
    public final Piece piece;
    public final Square from;
    public final Square to;

    public Move(Piece piece, Square from, Square to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
}
