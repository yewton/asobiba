package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Ex03_PerfectNumbersTest {

  Ex03_PerfectNumbers sut = new Ex03_PerfectNumbers();

  @ParameterizedTest(name = "calcPerfectNumbers({0}) = {1}")
  @MethodSource("maxAndPerfectNumbers")
  void calcPerfectNumbers(final int maxExclusive, final List<Integer> expected) {
    assertEquals(expected, sut.calcPerfectNumbers(maxExclusive));
  }

  static Stream<Arguments> maxAndPerfectNumbers() {
    return Stream.of(
        Arguments.of(1000, List.of(6, 28, 496)), Arguments.of(10000, List.of(6, 28, 496, 8128)));
  }
}
