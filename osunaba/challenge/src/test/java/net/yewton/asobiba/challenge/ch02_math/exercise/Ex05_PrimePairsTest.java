package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import net.yewton.asobiba.challenge.ch02_math.exercise.Ex05_PrimePairs.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Ex05_PrimePairsTest {

  Ex05_PrimePairs sut = new Ex05_PrimePairs();
  int maxValue = 50;

  @ParameterizedTest(name = "primepairs({0}, {1}) = {2}")
  @MethodSource("distanceAndExpectd")
  void calcPairs(final int distance, final String info, final List<Pair> expected) {
    final var result = sut.calcPairs(maxValue, distance);
    assertEquals(expected, result);
  }

  private static Stream<Arguments> distanceAndExpectd() {
    return Stream.of(
        Arguments.of(
            2,
            "twin",
            List.of(
                Pair.of(3, 5),
                Pair.of(5, 7),
                Pair.of(11, 13),
                Pair.of(17, 19),
                Pair.of(29, 31),
                Pair.of(41, 43))),
        Arguments.of(
            4,
            "cousin",
            List.of(
                Pair.of(3, 7),
                Pair.of(7, 11),
                Pair.of(13, 17),
                Pair.of(19, 23),
                Pair.of(37, 41),
                Pair.of(43, 47))),
        Arguments.of(
            6,
            "sexy",
            List.of(
                Pair.of(5, 11),
                Pair.of(7, 13),
                Pair.of(11, 17),
                Pair.of(13, 19),
                Pair.of(17, 23),
                Pair.of(23, 29),
                Pair.of(31, 37),
                Pair.of(37, 43),
                Pair.of(41, 47),
                Pair.of(47, 53))));
  }
}
