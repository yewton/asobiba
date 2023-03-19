package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class Ex04_PrimeNumber {

  public List<Integer> calcPrimesUpToSimple(final int n) {
    return IntStream.rangeClosed(2, n).filter(this::isPrime).boxed().toList();
  }

  public List<Integer> calcPrimesUpTo(final int n) {
    final var b = new BitSet(n + 1);
    b.set(2, n + 1);

    for (var i = 2; i <= Math.sqrt(n); i = b.nextSetBit(i + 1)) {
      for (var j = i + i; j <= n; j += i) {
        b.clear(j);
      }
    }
    return b.stream().boxed().toList();
  }

  private boolean isPrime(final int potentiallyPrime) {
    for (int i = 2; i <= potentiallyPrime / 2; i++) {
      if (potentiallyPrime % i == 0) {
        return false;
      }
    }
    return true;
  }
}
