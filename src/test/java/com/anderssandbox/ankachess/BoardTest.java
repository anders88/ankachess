package com.anderssandbox.ankachess;

import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    @org.junit.Test
    public void shouldPutPieceOnBoard() throws Exception {
        Piece piece = Piece.white(PieceType.KING).at("e2");
        Board board = Board.withPiece(piece);

        assertThat(board.pieceAt(Square.fromString("e2")).get()).isEqualToComparingFieldByField(piece);

    }

    @Test
    public void shouldFindLegalMovesForLonelyKing() throws Exception {
        Piece piece = Piece.white(PieceType.KING).at("e2");
        Board board = Board.withPiece(piece);

        Stream<Board> boards = board.legalMoves();
        assertThat(boards.count()).isEqualTo(8);
    }

    @Test
    public void shouldHandlePlacementOnEdge() throws Exception {
        Board board = Board.withPiece(Piece.white(PieceType.KING).at("a1"));
        List<Board> boards = board.legalMoves().collect(Collectors.toList());
        assertThat(boards).hasSize(3);
    }

    @Test
    public void shouldHandleRock() throws Exception {
        Stream<Board> moves = Board.withPiece(Piece.white(PieceType.ROCK).at("a1")).legalMoves();

        assertThat(moves.count()).isEqualTo(14);
    }
}
