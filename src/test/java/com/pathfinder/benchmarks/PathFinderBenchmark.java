package com.pathfinder.benchmarks;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

import com.pathfinder.PathFinder;
import com.pathfinder.chessgame.KnightPosition;
import com.pathfinder.chessgame.Position;
import com.pathfinder.searchstrategies.DepthFirstSearchStrategy;

@State(Scope.Benchmark)
public class PathFinderBenchmark {

    private static final int AVERAGE_EXPECTED_TIME_MILLIS = 1;
    private PathFinder pathFinder;
    private Position startPosition;
    private Position endPosition;

    @Test
    public void runTest() throws RunnerException {
        final var options = setupBenchmark();

        Collection<RunResult> results = runBenchmark(options);
        runAssertions(results);
    }

    @Setup
    public void setup() {
        this.startPosition = new KnightPosition(1, 3);
        this.endPosition = new KnightPosition(3, 1);
        this.pathFinder = new PathFinder(new DepthFirstSearchStrategy(3));
    }

    @Benchmark
    public void runSearch() {
        pathFinder.findPathsWithStrategy(startPosition, endPosition);
    }

    private Collection<RunResult> runBenchmark(Options options) throws RunnerException {
        return new Runner(options).run();
    }

    private Options setupBenchmark() {
        return new OptionsBuilder()
                .include(PathFinderBenchmark.class.getSimpleName() + ".*")
                .mode(Mode.AverageTime)
                .verbosity(VerboseMode.NORMAL)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(2)
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(2)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(1)
                .build();
    }

    private void runAssertions(Collection<RunResult> results) {
        results.stream()
                .flatMap(result -> result.getBenchmarkResults().stream())
                .forEach(benchmarkResult -> {
                    final var score = benchmarkResult.getPrimaryResult().getScore();
                    final var scoreUnit = benchmarkResult.getScoreUnit();
                    final StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder
                            .append("Benchmark score of: ")
                            .append(score)
                            .append(" is lower than avg threshold of: ")
                            .append(AVERAGE_EXPECTED_TIME_MILLIS)
                            .append(scoreUnit);

                    final var explanation = stringBuilder.toString();

                    System.out.println(explanation);

                    Assert.assertTrue(explanation, score < AVERAGE_EXPECTED_TIME_MILLIS);
                });
    }
}
