package com.pathfinder.searchstrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Stopwatch;
import com.pathfinder.chessgame.Position;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
public class DepthFirstSearchStrategy implements SearchStrategy {

    private final int maxDepth;

    public DepthFirstSearchStrategy(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Collection<List<Position>> findPaths(Position currentPosition, Position targetPosition) {
        final Collection<List<Position>> possiblePaths = new HashSet<>();
        final LinkedList<Position> currentPath = new LinkedList<>();

        runTimedDepthFirstSearch(possiblePaths, currentPosition, targetPosition, currentPath);

        return possiblePaths;
    }

    private void runTimedDepthFirstSearch(
            Collection<List<Position>> possiblePaths,
            Position currentPosition,
            Position targetPosition,
            LinkedList<Position> currentPath
    ) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        depthFirstSearch(possiblePaths, currentPosition, targetPosition, currentPath, 0);
        log.info("Search took: {}", stopwatch.stop());
    }

    private void depthFirstSearch(
            Collection<List<Position>> possiblePaths,
            Position currentPosition,
            Position targetPosition,
            LinkedList<Position> currentPath,
            int currentDepth
    ) {
        if (currentDepth > maxDepth) {
            log.debug("Depth restriction reached.\nCurrent depth is: {}", currentDepth);
            return;
        }

        currentPath.add(currentPosition);

        if (currentPosition.equals(targetPosition)) {
            possiblePaths.add(new ArrayList<>(currentPath));
            log.debug("Found target position. Distance was: {}, path was: {}", currentDepth,
                    currentPath);
            // Minor optimisation to break ourselves out of the recursive loop and save some stack
            // if a solution is found.
            // Another more aggressive alternative is to throw an Exception
            // although I'm not convinced throwing is much better as it'll probably lead to extra
            // stack traversals.
            return;
        }

        List<Position> possibleMoves = currentPosition.buildPossibleMoves();
        possibleMoves.forEach(move -> depthFirstSearch(possiblePaths, move, targetPosition, currentPath, currentDepth + 1));

        currentPath.removeLastOccurrence(currentPosition);
    }
}
