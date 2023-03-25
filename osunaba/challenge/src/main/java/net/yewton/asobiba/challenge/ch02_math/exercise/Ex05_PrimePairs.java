package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ex05_PrimePairs {

  Ex04_PrimeNumber prime = new Ex04_PrimeNumber();

  public record Pair(int first, int second) {
    public static Pair of(int first, int second) {
      return new Pair(first, second);
    }
  }

  public List<Pair> calcPairs(int maxValue, int distance) {
    List<Integer> primes = prime.calcPrimesUpTo(maxValue);
    Set<Integer> primesSet = new HashSet<>(primes);
    return primes.stream()
        .filter(
            n -> {
              if (n + distance <= maxValue) {
                return primesSet.contains(n + distance);
              } else {
                return prime.isPrime(n + distance);
              }
            })
        .map(n -> Pair.of(n, n + distance))
        .toList();
  }
}
