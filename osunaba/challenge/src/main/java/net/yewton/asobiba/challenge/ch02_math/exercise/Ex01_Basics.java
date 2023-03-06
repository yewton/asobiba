package net.yewton.asobiba.challenge.ch02_math.exercise;

public class Ex01_Basics {

  /** Exercise 1A: Basic Arithmetic Operations */
  public int calc(int m, int n) {
    return ((n * m / 2) % 7);
  }

  /** Exercise 1B: Statistics */
  public BResult calcSumAndCountAllNumbersDivBy_2_Or_7(int max) {
    int count = 0, sum = 0;
    for (int i = 2; i < max; i++) {
      if (i % 2 == 0 || i % 7 == 0) {
        count++;
        sum += i;
      }
    }
    return new BResult(count, sum);
  }

  record BResult(int count, int sum) {}

  public boolean isEven(int n) {
    return n % 2 == 0;
  }

  public boolean isOdd(int n) {
    return !isEven(n);
  }
}
