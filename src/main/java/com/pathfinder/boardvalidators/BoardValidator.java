package com.pathfinder.boardvalidators;

import java.util.function.Predicate;

import com.pathfinder.chessgame.Position;

public interface BoardValidator extends Predicate<Position> {

    int getHeight();

    int getWidth();

    boolean test(Position position);
}
