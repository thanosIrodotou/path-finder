package com.pathfinder.boardvalidators;

import com.pathfinder.chessgame.Position;

public class ChessBoardPositionValidator implements BoardValidator {

    public static final int BOARD_SIZE = 8;

    @Override
    public int getHeight() {
        return BOARD_SIZE;
    }

    @Override
    public int getWidth() {
        return BOARD_SIZE;
    }

    @Override
    public boolean test(Position position) {
        return (
                position.getX() <= getWidth()
                        && position.getY() <= getHeight()
                        && position.getX() >= 0
                        && position.getY() >= 0
        );
    }
}
