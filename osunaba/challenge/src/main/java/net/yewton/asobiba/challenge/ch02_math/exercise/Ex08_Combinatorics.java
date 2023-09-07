package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class Ex08_Combinatorics {

  public record ABC(int a, int b, int c) implements Comparable<ABC> {

    public boolean isValid() {
      return Math.pow(a(), 2) + Math.pow(b(), 2) == Math.pow(c(), 2);
    }

    @Override
    public int compareTo(@NotNull ABC that) {
      var ac = Integer.compare(a, that.a());
      if (ac != 0) {
        return ac;
      }
      var bc = Integer.compare(b, that.b());
      if (bc != 0) {
        return bc;
      }
      return Integer.compare(c, that.c());
    }
  }

  /** a^2 + b^2 = c^2 を満たす a, b, c の組を見つける ( それぞれ 1 以上 100 未満の値まで )。 */
  public Set<ABC> findABCs() {
    final Supplier<Stream<Integer>> newRange = () -> IntStream.range(1, 100).boxed();
    return newRange
        .get()
        .flatMap(a -> newRange.get().flatMap(b -> newRange.get().map(c -> new ABC(a, b, c))))
        .filter(ABC::isValid)
        .collect(Collectors.toSet());
  }
}
