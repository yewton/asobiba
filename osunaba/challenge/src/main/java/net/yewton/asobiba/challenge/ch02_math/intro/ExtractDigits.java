package net.yewton.asobiba.challenge.ch02_math.intro;

import java.util.List;
import java.util.stream.Stream;

public class ExtractDigits {

  List<Integer> extractDigits(final int startValue) {
    return extractDigits1(startValue).toList();
  }

  private Stream<Integer> extractDigits1(final int value) {
    final var digit = Stream.of(value % 10);
    final var remainder = value / 10;
    if (0 < remainder) {
      return Stream.concat(digit, extractDigits1(remainder));
    }
    return digit;
  }
}
