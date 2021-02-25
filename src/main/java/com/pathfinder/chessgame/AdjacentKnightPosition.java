package com.pathfinder.chessgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.pathfinder.boardvalidators.BoardValidator;
import com.pathfinder.boardvalidators.ChessBoardPositionValidator;

// Please ignore this class, as it is unused but could be a base for implementing but at the
// moment includes a lot of duplication. In theory it we can implement a solution using
//BiDirectional BFS which should be an improvement and should give a complexity
// of O(start^end/2 +start^end/2)
public class AdjacentKnightPosition {
    private static final int TOTAL_KNIGHT_MOVES = 8;
    private static final int[] KNIGHT_ALLOWED_ROW_MOVES = { 2, 2, -2, -2, 1, 1, -1, -1 };
    private static final int[] KNIGHT_ALLOWED_COL_MOVES = { -1, 1, 1, -1, 2, -2, 2, -2 };
    private final BoardValidator boardValidator = new ChessBoardPositionValidator();

    private final Set<Position> adjacentNodes = new HashSet<>();
    private final int x;
    private final int y;

    public AdjacentKnightPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Position> buildPossibleMoves() {
        List<Position> newMoves = new ArrayList<>();

        // startInclusive - endExclusive
        IntStream.range(0, TOTAL_KNIGHT_MOVES).forEach(moveIndex -> {
            int newX = x + KNIGHT_ALLOWED_ROW_MOVES[moveIndex];
            int newY = y + KNIGHT_ALLOWED_COL_MOVES[moveIndex];
            final AdjacentKnightPosition knightPosition = new AdjacentKnightPosition(newX, newY);
//            if (boardValidator.test(knightPosition)) {
//                newMoves.add(knightPosition);
//            }
        });

        newMoves.forEach(this::addAdjacentPosition);
        return Collections.unmodifiableList(newMoves);
    }

    public Set<Position> getAdjacentNodes() {
        return adjacentNodes;
    }

    public boolean addAdjacentPosition(Position position) {
        return adjacentNodes.add(position);
    }
}
