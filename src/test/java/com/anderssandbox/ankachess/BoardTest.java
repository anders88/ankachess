package com.anderssandbox.ankachess;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    @org.junit.Test
    public void shouldPutPieceOnBoard() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.KING)).at("e2").create();

        Optional<Piece> piece = board.pieceAt(Square.fromString("e2"));
        assertThat(piece.isPresent()).isTrue();
        assertThat(piece.get().isWhite).isTrue();
        assertThat(piece.get().pieceType).isEqualTo(PieceType.KING);

    }

    @Test
    public void shouldFindLegalMovesForLonelyKing() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.KING)).at("e2").create();

        List<Board> boards = board.legalMoves(true).collect(Collectors.toList());
        assertThat(boards).hasSize(8);
        assertThat(boards.get(0).allPieces()).hasSize(1);
    }

    @Test
    public void shouldHandlePlacementOnEdge() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.KING)).at("a1").create();
        List<Board> boards = board.legalMoves(true).collect(Collectors.toList());
        assertThat(boards).hasSize(3);
    }

    @Test
    public void shouldHandleRock() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.ROCK)).at("a1").create();
        Stream<Board> moves = board.legalMoves(true);

        assertThat(moves.count()).isEqualTo(14);
    }

    @Test
    public void shouldOnlyMoveMyPieces() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.ROCK)).at("a1").create();
        Stream<Board> moves = board.legalMoves(false);

        assertThat(moves.count()).isEqualTo(0);

    }

    @Test
    public void shouldHandleOtherPieces() throws Exception {
        Board board = Board
                .withPiece(Piece.white(PieceType.KING)).at("a2")
                .withPiece(Piece.white(PieceType.ROCK)).at("a1")
                .create();

        List<Board> moves = board.legalMoves(true).collect(Collectors.toList());

        assertThat(moves).hasSize(11);

    }

    @Test
    public void shouldHandleQueens() throws Exception {
        assertThat(Board.withPiece(Piece.white(PieceType.QUEEN)).at("a8").create().legalMoves(true).count()).isEqualTo(21);
    }

    @Test
    public void shouldHandleBishops() throws Exception {
        assertThat(Board.withPiece(Piece.white(PieceType.BISHOP)).at("a8").create().legalMoves(true).count()).isEqualTo(7);
    }

}
