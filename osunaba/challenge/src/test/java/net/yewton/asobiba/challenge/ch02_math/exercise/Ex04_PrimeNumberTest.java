package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Ex04_PrimeNumberTest {

  Ex04_PrimeNumber sut = new Ex04_PrimeNumber();

  @ParameterizedTest(name = "calcPrimes({0}) = {1}")
  @MethodSource("argumentProvider")
  void calcPrimesUpToSimple(final int n, final List<Integer> expected) {
    assertEquals(expected, sut.calcPrimesUpToSimple(n));
  }

  @ParameterizedTest(name = "calcPrimes({0}) = {1}")
  @MethodSource("argumentProvider")
  void calcPrimesUpto(final int n, final List<Integer> expected) {
    assertEquals(expected, sut.calcPrimesUpTo(n));
  }

  static Stream<Arguments> argumentProvider() {
    return Stream.of(
        Arguments.of(2, List.of(2)),
        Arguments.of(3, List.of(2, 3)),
        Arguments.of(10, List.of(2, 3, 5, 7)),
        Arguments.of(15, List.of(2, 3, 5, 7, 11, 13)),
        Arguments.of(25, List.of(2, 3, 5, 7, 11, 13, 17, 19, 23)),
        Arguments.of(50, List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)));
  }
}
