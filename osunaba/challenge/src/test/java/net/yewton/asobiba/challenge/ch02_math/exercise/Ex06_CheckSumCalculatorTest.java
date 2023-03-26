package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Ex06_CheckSumCalculatorTest {

  Ex06_CheckSumCalculator sut = new Ex06_CheckSumCalculator();

  @ParameterizedTest(name = "checksum({0}) = {1}")
  @CsvSource({"11111, 5", "22222, 0", "111111, 1", "12345678, 4", "87654321, 0"})
  void testCalcChecksum(String input, int expected) {
    final int result = sut.calcChecksum(input);
    assertEquals(expected, result);
  }
}
