package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.Map;
import java.util.stream.Stream;

public class Ex02_NumberAsText {

  record Result(int quotient, int remainder) {}

  public static final Map<Integer, String> DIGIT_TO_TEXT =
      Map.of(
          0, "ZERO",
          1, "ONE",
          2, "TWO",
          3, "THREE",
          4, "FOUR",
          5, "FIVE",
          6, "SIX",
          7, "SEVEN",
          8, "EIGHT",
          9, "NINE");

  public String numberAsText(final int n) {
    return Stream.iterate(
            new Result(n / 10, n % 10),
            r -> 0 <= r.quotient,
            r -> {
              if (r.quotient == 0) {
                return new Result(-1, -1);
              } else {
                return new Result(r.quotient / 10, r.quotient % 10);
              }
            })
        .map(r -> DIGIT_TO_TEXT.get(r.remainder))
        .reduce((a, b) -> b + " " + a)
        .orElse("");
  }
}
