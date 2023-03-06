package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import net.yewton.asobiba.challenge.ch02_math.exercise.Ex01_Basics.BResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class Ex01_BasicsTest {

  Ex01_Basics sut = new Ex01_Basics();

  @ParameterizedTest
  @CsvSource({"6, 7, 0", "3, 4, 6", "5, 5, 5"})
  void calc(int m, int n, int expected) {
    int result = sut.calc(m, n);
    assertEquals(expected, result);
  }

  @ParameterizedTest(name = "{0} の結果: {1}")
  @MethodSource("argumentProvider")
  void calcSumAndCountAllNumbersDivBy_2_Or_7(int max, BResult expected) {
    var result = sut.calcSumAndCountAllNumbersDivBy_2_Or_7(max);
    assertEquals(result, expected);
  }

  static Stream<Arguments> argumentProvider() {
    return Stream.of(
        Arguments.of(3, new BResult(1, 2)),
        Arguments.of(8, new BResult(4, 19)),
        Arguments.of(15, new BResult(8, 63)));
  }

  @ParameterizedTest
  @CsvSource({"2, true, 3, false, 4, true"})
  void evenOddTest(int n, boolean expected) {
    boolean resultEven = sut.isEven(n);
    boolean resultOdd = sut.isOdd(n);

    assertAll(() -> assertEquals(expected, resultEven), () -> assertEquals(expected, !resultOdd));
  }
}
