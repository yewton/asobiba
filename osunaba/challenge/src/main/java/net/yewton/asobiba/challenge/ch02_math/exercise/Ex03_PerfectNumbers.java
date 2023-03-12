package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ex03_PerfectNumbers {

  List<Integer> calcPerfectNumbers(int n) {
    return IntStream.range(1, n - 1)
        .filter(this::isPerfectNumber)
        .boxed()
        .collect(Collectors.toList());
  }

  private boolean isPerfectNumber(int n) {
    return IntStream.range(1, n - 1).filter(m -> n % m == 0).sum() == n;
  }
}
