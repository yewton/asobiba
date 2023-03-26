package net.yewton.asobiba.challenge.ch02_math.exercise;

import java.util.stream.IntStream;

public class Ex06_CheckSumCalculator {

  public int calcChecksum(String s) {
    return IntStream.range(0, s.length())
            .reduce(0, (acc, idx) -> acc + (idx + 1) * Character.getNumericValue(s.charAt(idx)))
        % 10;
  }
}
