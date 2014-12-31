package com.anderssandbox.ankachess;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    @org.junit.Test
    public void shouldPutPieceOnBoard() throws Exception {
        Piece piece = Piece.white(PieceType.KING).at("e2");
        Board board = Board.withPiece(piece);

        assertThat(board.pieceAt("e2").get()).isEqualToComparingFieldByField(piece);

    }
}
