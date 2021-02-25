package com.pathfinder;

import java.util.Collection;
import java.util.List;

import com.pathfinder.chessgame.Position;
import com.pathfinder.searchstrategies.SearchStrategy;

public class PathFinder {

    private final SearchStrategy searchStrategy;

    public PathFinder(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public Collection<List<Position>> findPathsWithStrategy(
            Position currentPosition, Position targetPosition) {
        return searchStrategy.findPaths(currentPosition, targetPosition);
    }
}
