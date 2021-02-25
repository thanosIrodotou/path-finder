package com.pathfinder.searchstrategies;

import java.util.Collection;
import java.util.List;

import com.pathfinder.chessgame.Position;

public interface SearchStrategy {

    Collection<List<Position>> findPaths(Position currentPosition, Position targetPosition);

}
