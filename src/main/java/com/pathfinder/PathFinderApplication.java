package com.pathfinder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pathfinder.chessgame.Position;
import com.pathfinder.searchstrategies.DepthFirstSearchStrategy;
import com.pathfinder.utils.UserIOHandler;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PathFinderApplication implements CommandLineRunner {

    private static final Comparator<List<Position>> PATH_LENGTH_COMPARATOR = Comparator.comparing(
            List::size);

    public static void main(String[] args) {
        SpringApplication.run(PathFinderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        PathFinder pathFinder = new PathFinder(new DepthFirstSearchStrategy(3));

        Position knightPosition = UserIOHandler.readPosition(
                "Provide starting position, i.e. A1 (case insensitive)");
        Position targetPosition = UserIOHandler.readPosition(
                "Provide target position, i.e. C2 (case insensitive)");

        final var paths = pathFinder.findPathsWithStrategy(knightPosition, targetPosition);

        if (paths.isEmpty()) {
            log.info("Could not find a path from {} -> {}", knightPosition, targetPosition);
            return;
        }

        final List<Position> shortestPath = paths.stream().min(PATH_LENGTH_COMPARATOR)
                .orElse(Collections.emptyList());

        String pathString = shortestPath.stream()
                .map(position -> UserIOHandler.numberToLetter(position.getX())
                        + position.getY())
                .collect(Collectors.joining(" -> "));

        log.info("Shortest path: {}", pathString);

        paths.remove(shortestPath);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Found ")
                .append(paths.size())
                .append(" alternative paths: ")
                .append("\n");

        // This is somewhat redundant as we've already somewhat streamed and sorted the paths above
        // to get the shortest path, so we can achieve both in 1 step.
        paths
                .stream()
                .sorted(PATH_LENGTH_COMPARATOR)
                .forEach(alternativePaths -> {
                            stringBuilder.append(alternativePaths.stream()
                                    .map(position -> UserIOHandler.numberToLetter(position.getX())
                                            + position.getY())
                                    .collect(Collectors.joining(" -> ")));
                            stringBuilder.append("\n");
                        }
                );

        log.info("{}", stringBuilder.toString());
    }
}
