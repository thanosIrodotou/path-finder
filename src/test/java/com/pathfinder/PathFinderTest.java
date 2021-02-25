package com.pathfinder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.pathfinder.chessgame.KnightPosition;
import com.pathfinder.chessgame.Position;
import com.pathfinder.searchstrategies.DepthFirstSearchStrategy;
import com.pathfinder.searchstrategies.SearchStrategy;

public class PathFinderTest {

    private final SearchStrategy searchStrategy = new DepthFirstSearchStrategy(3);
    private final PathFinder pathFinder = new PathFinder(searchStrategy);

    @Test
    public void returnShortestPath_whenDestinationIsWithinOneMove() {
        final Position knightPosition = new KnightPosition(0, 0);
        final Position targetPosition = new KnightPosition(2, 1);

        final Collection<List<Position>> paths = pathFinder.findPathsWithStrategy(knightPosition,
                targetPosition);

        final List<Position> maybePath = paths.stream()
                .filter(path -> path.size() == 2)
                .findFirst()
                .orElse(Collections.emptyList());

        assertThat(maybePath.size(), equalTo(2));
    }

    @Test
    public void returnsShortestPath_whenDestinationReachableAndWithinDepthLimit() {
        final Position startingSquare = new KnightPosition(2, 2);
        final Position endingSquare = new KnightPosition(5, 3);

        final Collection<List<Position>> paths = pathFinder.findPathsWithStrategy(startingSquare,
                endingSquare);
        final List<Position> shortest = paths.stream().min(Comparator.comparing(
                List::size)).get();

        final var expectedPath = List.of(
                startingSquare,
                new KnightPosition(4, 1),
                endingSquare
        );

        assertThat(shortest, equalTo(expectedPath));
    }

    @Test
    public void returnsEmptyPath_whenDestinationIsUnreachable() {
        final Position knightPosition = new KnightPosition(1, 1);
        final Position targetPosition = new KnightPosition(7, 7);

        final Collection<List<Position>> paths = pathFinder.findPathsWithStrategy(knightPosition,
                targetPosition);

        assertThat(paths.isEmpty(), is(true));
    }
}