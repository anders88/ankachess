package com.anderssandbox.ankachess;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private Set<Piece> pieces;

    private Board(Set<Piece> pieces) {
        this.pieces = new HashSet<>(pieces);
    }

    private Board(Stream<Piece> pieces) {
        this.pieces = pieces.collect(Collectors.toSet());
    }

    public static Board withPiece(Piece piece) {
        Set<Piece> pieces = new HashSet<>();
        pieces.add(piece);
        return new Board(pieces);
    }

    public Optional<Piece> pieceAt(Square square) {
        return pieces.stream().filter(p -> p.square.equals(square)).findAny();
    }

    private Stream<Piece> moveFromPiece(Piece piece) {
        Stream<Piece> res = legalSquareForPiece(piece).filter(Optional::isPresent).map(Optional::get).map(piece::moveMe);
        return res;
    }

    private Stream<Optional<Square>> legalSquareForPiece(Piece piece) {
        return Direction.allDirections().map(piece.square::negighbour);
    }


    private Board moveAPiece(Piece piece) {
        HashSet<Piece> nboa = new HashSet<>(pieces);
        nboa.add(piece);
        return new Board(nboa);
    }

    public Stream<Board> legalMoves() {
        return pieces.stream()
                .map(p -> moveFromPiece(p))
                .flatMap(p -> p)
                .map(pie -> moveAPiece(pie));
    }
}
