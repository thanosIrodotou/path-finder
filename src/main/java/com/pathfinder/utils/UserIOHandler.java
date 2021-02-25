package com.pathfinder.utils;

import static com.pathfinder.boardvalidators.ChessBoardPositionValidator.BOARD_SIZE;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableBiMap;
import com.pathfinder.chessgame.KnightPosition;
import com.pathfinder.chessgame.Position;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UserIOHandler {

    private static final Map<String, Integer> LETTER_MAPPING;
    private static final Map<Integer, String> INVERSE_LETTER_MAPPING;

    static {
        ImmutableBiMap.Builder<String, Integer> columnToRowBimap = ImmutableBiMap.builder();
        columnToRowBimap.put("A", 0);
        columnToRowBimap.put("B", 1);
        columnToRowBimap.put("C", 2);
        columnToRowBimap.put("D", 3);
        columnToRowBimap.put("E", 4);
        columnToRowBimap.put("F", 5);
        columnToRowBimap.put("G", 6);
        columnToRowBimap.put("H", 7);

        final var mappings = columnToRowBimap.build();
        LETTER_MAPPING = mappings;
        INVERSE_LETTER_MAPPING = mappings.inverse();
    }

    private UserIOHandler() {}

    public static Position readPosition(String promptMessage) {
        Scanner scanner = new Scanner(System.in);

        Position knightPosition = null;

        while (knightPosition == null) {
            log.info(promptMessage);
            String position = scanner.nextLine();
            try {
                knightPosition = parsePosition(position);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
            }
        }

        return knightPosition;
    }

    public static String numberToLetter(int number) {
        if (INVERSE_LETTER_MAPPING.containsKey(number)) {
            return INVERSE_LETTER_MAPPING.get(number);
        } else {
            throw new IllegalArgumentException(
                    "Could not map column position: " + number + " to a valid position on the board."
            );
        }
    }

    private static Position parsePosition(String position) {
        var x = letterToNumber(position.substring(0, 1).toUpperCase(Locale.ROOT));
        var y = Integer.parseInt(position.substring(1).toUpperCase(Locale.ROOT));

        if (y >= BOARD_SIZE) {
            throw new IllegalArgumentException("Position: " + position + " is not a valid position!");
        }

        return new KnightPosition(x, y);
    }

    private static int letterToNumber(String letter) {

        if (LETTER_MAPPING.containsKey(letter)) {
            return LETTER_MAPPING.get(letter);
        } else {
            throw new IllegalArgumentException(
                    "Could not map letter: " + letter + " to a valid position on the board.");
        }
    }
}
