package net.yewton.asobiba.challenge.ch02_math.intro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExtractDigitsTest {

  @ParameterizedTest(name = "extractDigits({0}) = {1}")
  @MethodSource("argumentProvider")
  void extractDigits(int value, List<Integer> expected) {
    var sut = new ExtractDigits();
    assertEquals(expected, sut.extractDigits(value));
  }

  static Stream<Arguments> argumentProvider() {
    return Stream.of(
        Arguments.of(7, List.of(7)), Arguments.of(1234567, List.of(7, 6, 5, 4, 3, 2, 1)));
  }
}
