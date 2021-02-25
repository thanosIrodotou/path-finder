package com.pathfinder.chessgame;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.common.collect.ImmutableList;

@RunWith(Parameterized.class)
public class KnightPositionTest {

    @Parameterized.Parameter
    public String explanation;
    @Parameterized.Parameter(1)
    public int xPosition;
    @Parameterized.Parameter(2)
    public int yPosition;
    @Parameterized.Parameter(3)
    public List<Position> validPositions;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        "left most position can only expand twice",
                        0, 0, ImmutableList.of(
                        new KnightPosition(2, 1),
                        new KnightPosition(1, 2)
                ) },
                {
                        "0,1 expands to 3 positions",
                        0, 1, ImmutableList.of(
                        new KnightPosition(2, 0),
                        new KnightPosition(2, 2),
                        new KnightPosition(1, 3)
                ) },
                {
                        "1,0 expands to 3 positions",
                        1, 0, ImmutableList.of(
                        new KnightPosition(3, 1),
                        new KnightPosition(2, 2),
                        new KnightPosition(0, 2)
                ) },
                {
                        "1,1 expands to 4 positions",
                        1, 1, ImmutableList.of(
                        new KnightPosition(3, 0),
                        new KnightPosition(3, 2),
                        new KnightPosition(2, 3),
                        new KnightPosition(0, 3)
                ) },
                {
                        "4,4 expands to 8 positions",
                        4, 4, ImmutableList.of(
                        new KnightPosition(6, 3),
                        new KnightPosition(6, 5),
                        new KnightPosition(2, 5),
                        new KnightPosition(2, 3),
                        new KnightPosition(5, 6),
                        new KnightPosition(5, 2),
                        new KnightPosition(3, 6),
                        new KnightPosition(3, 2)
                ) }
                // ...

        });
    }

    @Test
    public void buildPossibleMoves() {
        Position knightPosition = new KnightPosition(xPosition, yPosition);

        final var positions = knightPosition.buildPossibleMoves();

        assertThat(explanation, positions, equalTo(validPositions));
    }
}