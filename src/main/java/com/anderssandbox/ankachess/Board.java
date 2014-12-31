package com.anderssandbox.ankachess;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private Map<Square,Piece> pieces;

    private Board(Collection<Piece> pieces) {
        Map<Square, Piece> pieceMap = pieces.stream()
                .collect(Collectors.groupingBy(p -> p.square))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entr -> entr.getValue().get(0)));
        this.pieces = pieceMap;
    }

    private Board(Map<Square, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board withPiece(Piece piece) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(piece);
        return new Board(pieces);
    }

    public Optional<Piece> pieceAt(Square square) {
        return Optional.ofNullable(pieces.get(square));
    }

    private Stream<Piece> moveFromPiece(Piece piece) {
        Stream<Piece> res = legalSquareForPiece(piece).filter(Optional::isPresent).map(Optional::get).map(piece::moveMe);
        return res;
    }

    private Stream<Optional<Square>> legalSquareForPiece(Piece piece) {
        switch (piece.pieceType) {
            case KING:
                return legalForKing(piece);
            case ROCK:
                return legalForRock(piece);
        }
        throw new RuntimeException("Unknown pice type " + piece.pieceType);
    }

    private Stream<Optional<Square>> legalForRock(Piece piece) {
        List<Optional<Square>> res = new ArrayList<>();
        res.addAll(legalsFrom(piece.square,Direction.UP));
        res.addAll(legalsFrom(piece.square,Direction.RIGHT));
        res.addAll(legalsFrom(piece.square,Direction.DOWN));
        res.addAll(legalsFrom(piece.square,Direction.LEFT));
        return res.stream();
    }

    private List<Optional<Square>> legalsFrom(Square square,Direction direction) {
        List<Optional<Square>> res = new ArrayList<>();
        for (Optional<Square> sq = square.negighbour(direction);sq.isPresent();sq = sq.get().negighbour(direction)) {
            res.add(sq);
        }
        return res;
    }

    private Stream<Optional<Square>> legalForKing(Piece piece) {
        return Direction.allDirections().map(piece.square::negighbour);
    }


    private Board moveAPiece(Piece piece) {
        Map<Square,Piece> nboa = new HashMap<>(pieces);
        nboa.put(piece.square, piece);
        return new Board(nboa);
    }

    public Stream<Board> legalMoves() {
        return pieces.values().stream()
                .map(p -> moveFromPiece(p))
                .flatMap(p -> p)
                .map(pie -> moveAPiece(pie));
    }
}
