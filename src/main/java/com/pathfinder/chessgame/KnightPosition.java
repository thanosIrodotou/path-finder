package com.pathfinder.chessgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.pathfinder.boardvalidators.BoardValidator;
import com.pathfinder.boardvalidators.ChessBoardPositionValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KnightPosition extends Position {

    private static final int TOTAL_KNIGHT_MOVES = 8;
    private static final int[] KNIGHT_ALLOWED_ROW_MOVES = { 2, 2, -2, -2, 1, 1, -1, -1 };
    private static final int[] KNIGHT_ALLOWED_COL_MOVES = { -1, 1, 1, -1, 2, -2, 2, -2 };
    private final BoardValidator boardValidator = new ChessBoardPositionValidator();

    public KnightPosition(int x, int y) {
        super(x, y);
    }

    @Override
    public List<Position> buildPossibleMoves() {
        List<Position> newMoves = new ArrayList<>();

        // The stream is startInclusive - endExclusive
        IntStream.range(0, TOTAL_KNIGHT_MOVES).forEach(moveIndex -> {
            int newX = getX() + KNIGHT_ALLOWED_ROW_MOVES[moveIndex];
            int newY = getY() + KNIGHT_ALLOWED_COL_MOVES[moveIndex];
            final Position knightPosition = new KnightPosition(newX, newY);
            if (boardValidator.test(knightPosition)) {
                newMoves.add(knightPosition);
            }
        });

        return Collections.unmodifiableList(newMoves);
    }

    @Override
    public String toString() {
        return "KnightPosition{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
