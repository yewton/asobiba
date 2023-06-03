package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex07_RomanNumbers {

  enum Roman {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000),
    NULL(0);

    private static final TreeSet<Roman> SEQUENCE = new TreeSet<>(List.of(I, V, X, L, C, D, M));

    private final int value;

    Roman(int value) {
      this.value = value;
    }

    public Roman previous() {
      return Objects.requireNonNullElse(SEQUENCE.lower(this), NULL);
    }

    public Roman next() {
      return Objects.requireNonNullElse(SEQUENCE.higher(this), NULL);
    }
  }

  record Pair(Roman current, Roman next) {}

  public int fromRomanNumber(String romanNumber) {
    final List<String> romanDigits =
        romanNumber.codePoints().mapToObj(cp -> String.valueOf((char) cp)).toList();
    final var size = romanDigits.size();
    final Stream<Pair> romanDigitsZipWithNext =
        IntStream.range(0, size)
            .mapToObj(
                i -> {
                  final var current = Roman.valueOf(romanDigits.get(i));
                  final var nextIndex = i + 1;
                  final Roman next;
                  if (nextIndex < size) {
                    next = Roman.valueOf(romanDigits.get(nextIndex));
                  } else {
                    next = Roman.NULL;
                  }
                  return new Pair(current, next);
                });
    return romanDigitsZipWithNext
        .mapToInt(
            r ->
                switch (r.current) {
                  case I -> switch (r.next) {
                    case V, X -> -(r.current.value);
                    default -> r.current.value;
                  };
                  case X -> switch (r.next) {
                    case L, C, D -> -(r.current.value);
                    default -> r.current.value;
                  };
                  case C -> switch (r.next) {
                    case D, M -> -(r.current.value);
                    default -> r.current.value;
                  };
                  default -> r.current.value;
                })
        .sum();
  }

  public String toRomanNumber(final int arabicNumber) {
    final var sb = new StringBuilder();
    var remainder = arabicNumber;

    for (final var roman : Roman.SEQUENCE.descendingSet()) {
      final var repeater = remainder / roman.value;
      remainder = remainder % roman.value;

      final String s =
          switch (roman) {
            case I, X, C, M -> repeater == 4
                ? roman.name() + roman.next().name()
                : roman.name().repeat(repeater);
            case V, L, D -> {
              if (repeater == 0) {
                yield "";
              } else if (remainder / roman.previous().value == 4) {
                remainder = remainder - roman.previous().value * 4;
                yield roman.previous().name() + roman.next().name(); // IX とか
              } else {
                yield roman.name();
              }
            }
            default -> throw new IllegalArgumentException("こんなの絶対おかしいよ");
          };
      sb.append(s);
    }
    return sb.toString();
  }
}
