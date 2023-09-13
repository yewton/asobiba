package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.function.TriFunction;

public class Ex09_SpecialArmstrongNumbers {

  public interface CubicFunction extends TriFunction<Integer, Integer, Integer, Integer> {}

  record XYZ(int x, int y, int z, CubicFunction f) {

    boolean isArmstrong() {
      return toInt() == f.apply(x, y, z);
    }

    int toInt() {
      return x * 100 + y * 10 + z;
    }
  }

  public List<Integer> calcArmstrongNumbers() {
    return calcNumbers((xx, yy, zz) -> (int) (Math.pow(xx, 3) + Math.pow(yy, 3) + Math.pow(zz, 3)));
  }

  public List<Integer> calcNumbers(CubicFunction f) {
    return newRange()
        .flatMap(x -> newRange().flatMap(y -> newRange().map(z -> new XYZ(x, y, z, f))))
        .filter(XYZ::isArmstrong)
        .map(XYZ::toInt)
        .toList();
  }

  private Stream<Integer> newRange() {
    return IntStream.rangeClosed(1, 9).boxed();
  }
}
