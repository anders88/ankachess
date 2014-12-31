package com.anderssandbox.ankachess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Board {
    private Set<Piece> pieces;

    private Board(Set<Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board withPiece(Piece piece) {
        Set<Piece> pieces = new HashSet<>();
        pieces.add(piece);
        return new Board(pieces);
    }

    public Optional<Piece> pieceAt(String square) {
        return pieces.stream().filter(p -> p.square.equals(square)).findAny();
    }
}
