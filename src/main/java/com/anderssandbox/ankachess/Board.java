package com.anderssandbox.ankachess;

import java.util.*;
import java.util.stream.Stream;

public class Board {
    private Map<Square,Piece> pieces;

    private Board(Map<Square, Piece> pieces) {
        this.pieces = pieces;
    }




    public static class Factory {
        private Piece currentPiece = null;
        private Factory() {

        }
        private Map<Square,Piece> squarePieceMap = new HashMap<>();

        public Factory withPiece(Piece piece) {
            if (currentPiece != null) {
                throw new IllegalStateException("Place the piece");
            }
            currentPiece = piece;
            return this;
        }

        public Factory at(String squareText) {
            if (currentPiece == null) {
                throw new IllegalStateException("Give the piece");
            }
            Square square = Square.fromString(squareText);
            squarePieceMap.put(square,currentPiece);
            currentPiece = null;
            return this;
        }

        public Board create() {
            return new Board(squarePieceMap);
        }
    }



    public Map<Square,Piece> allPieces() {
        return new HashMap<>(pieces);
    }

    public static Factory withPiece(Piece piece) {
        return new Factory().withPiece(piece);
    }

    public Optional<Piece> pieceAt(Square square) {
        return Optional.ofNullable(pieces.get(square));
    }

    private Stream<Move> possibleMovesFrom(Piece piece, Square from) {
        Stream<Square> possMoves;
        switch (piece.pieceType) {
            case KING:
                possMoves = legalForKing(piece, from);
                break;
            case ROCK:
                possMoves = legalForRock(piece, from);
                break;
            default:
                throw new RuntimeException("Unknown pice type " + piece.pieceType);
        }
        return possMoves.map(pm -> new Move(piece, from, pm));
    }

    private Stream<Square> legalForRock(Piece piece,Square square) {
        List<Square> res = new ArrayList<>();
        res.addAll(legalsFrom(square,Direction.UP,piece.isWhite));
        res.addAll(legalsFrom(square,Direction.RIGHT,piece.isWhite));
        res.addAll(legalsFrom(square,Direction.DOWN,piece.isWhite));
        res.addAll(legalsFrom(square,Direction.LEFT,piece.isWhite));
        return res.stream();
    }

    private List<Square> legalsFrom(Square square,Direction direction, boolean whitePiece) {
        List<Square> res = new ArrayList<>();
        for (Optional<Square> sq = square.negighbour(direction);sq.isPresent();sq = sq.get().negighbour(direction)) {
            if (pieceAt(sq.get()).filter(pi -> pi.isWhite == whitePiece).isPresent()) {
                break;
            }
            res.add(sq.get());
        }
        return res;
    }

    private Stream<Square> legalForKing(Piece piece,Square square) {
        return Direction.allDirections()
                .map(square::negighbour)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(sq -> !pieceAt(sq).filter(pi -> pi.isWhite == piece.isWhite).isPresent())
                ;
    }


    private Board moveAPiece(Move move) {
        Map<Square,Piece> nboa = new HashMap<>(pieces);
        nboa.remove(move.from);
        nboa.put(move.to, move.piece);
        return new Board(nboa);
    }

    public Stream<Board> legalMoves(boolean whiteToMove) {
        return pieces.entrySet().stream()
                .filter(en -> en.getValue().isWhite == whiteToMove)
                .map(entr -> possibleMovesFrom(entr.getValue(), entr.getKey()))
                .flatMap(p -> p)
                .map(this::moveAPiece);
    }

    @Override
    public String toString() {
        return "Board{" +
                "pieces=" + pieces +
                '}';
    }
}
