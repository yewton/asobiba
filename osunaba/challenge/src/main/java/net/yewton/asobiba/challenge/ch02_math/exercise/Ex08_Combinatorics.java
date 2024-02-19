package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class Ex08_Combinatorics {

  private static final int UPPER = 100;
  private static final int LOWER = 0;
  private static final int INVALID = UPPER + 1;

  public record ABC(int a, int b, int c) implements Comparable<ABC> {

    public boolean isValid() {
      if (IntStream.of(a, b, c).anyMatch(i -> UPPER <= i)) {
        return false;
      }
      return Math.pow(a(), 2) + Math.pow(b(), 2) == Math.pow(c(), 2);
    }

    @Override
    public int compareTo(@NotNull ABC that) {
      var ac = Integer.compare(a(), that.a());
      if (ac != 0) {
        return ac;
      }
      var bc = Integer.compare(b(), that.b());
      if (bc != 0) {
        return bc;
      }
      return Integer.compare(c(), that.c());
    }
  }

  public record ABCD(int a, int b, int c, int d) implements Comparable<ABCD> {

    public static @NotNull ABCD fromABC(int a, int b, int c) {
      final int dVal;

      final var dDouble = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2));
      if (Double.isNaN(dDouble)) {
        dVal = INVALID;
      } else {
        dVal = (int) dDouble;
      }
      return new ABCD(a, b, c, dVal);
    }

    public boolean isValid() {
      if (IntStream.of(a, b, c, d).anyMatch(i -> i <= LOWER || UPPER <= i)) {
        return false;
      }
      return Math.pow(a(), 2) + Math.pow(b(), 2) == Math.pow(c(), 2) + Math.pow(d(), 2);
    }

    @Override
    public int compareTo(@NotNull Ex08_Combinatorics.ABCD that) {
      var abcc = (new ABC(a(), b(), c())).compareTo(new ABC(that.a(), that.b(), that.c()));
      if (abcc != 0) {
        return abcc;
      }
      return Integer.compare(d(), that.d());
    }
  }

  /** a^2 + b^2 = c^2 を満たす a, b, c の組を見つける ( それぞれ 1 以上 100 未満の値まで )。 */
  public Set<ABC> findABCs() {
    return newRange()
        .flatMap(a -> newRange().flatMap(b -> newRange().map(c -> new ABC(a, b, c))))
        .filter(ABC::isValid)
        .collect(Collectors.toSet());
  }

  /** sqrt(a^2 + b^2) = c を利用して O(n^2) にする */
  public Set<ABC> findABCs2() {
    return newRange()
        .flatMap(a -> newRange().map(b -> new ABC(a, b, solveC(a, b))))
        .filter(ABC::isValid)
        .collect(Collectors.toSet());
  }

  /** a^2 + b^2 = c^2 + d^s を満たす a, b, c, d の組を見つける ( それぞれ 1 以上 100 未満の値まで )。 */
  public Set<ABCD> findABCDs() {
    return newRange()
        .flatMap(a -> newRange()
            .flatMap(b -> newRange().flatMap(c -> newRange().map(d -> new ABCD(a, b, c, d)))))
        .filter(ABCD::isValid)
        .collect(Collectors.toSet());
  }

  /** sqrt(a^2 + b^2 - c^2 = d を利用して O(n^3) にする */
  public Set<ABCD> findABCDs2() {
    return newRange()
        .flatMap(a -> newRange().flatMap(b -> newRange().map(c -> ABCD.fromABC(a, b, c))))
        .filter(ABCD::isValid)
        .collect(Collectors.toSet());
  }

  private Stream<Integer> newRange() {
    return IntStream.range(1, UPPER).boxed();
  }

  private int solveC(int a, int b) {
    return (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
  }
}
