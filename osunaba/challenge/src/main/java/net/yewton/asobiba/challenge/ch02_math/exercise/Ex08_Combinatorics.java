package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class Ex08_Combinatorics {

  public record ABC(int a, int b, int c) implements Comparable<ABC> {
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
    final Set<ABC> result = new HashSet<>();
    IntStream.range(1, 100)
        .forEach(
            a ->
                IntStream.range(1, 100)
                    .forEach(
                        b ->
                            IntStream.range(1, 100)
                                .forEach(
                                    c -> {
                                      if (Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2)) {
                                        result.add(new ABC(a, b, c));
                                      }
                                    })));
    return Collections.unmodifiableSet(result);
  }
}
