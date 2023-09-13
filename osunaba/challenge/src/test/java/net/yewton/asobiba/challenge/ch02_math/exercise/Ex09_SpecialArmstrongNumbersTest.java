package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import net.yewton.asobiba.challenge.ch02_math.exercise.Ex09_SpecialArmstrongNumbers.CubicFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Ex09_SpecialArmstrongNumbersTest {
  @Test
  @DisplayName("3 桁のアームストロング数を導出する")
  public void calcArmstrongNumbers() {
    final var sut = new Ex09_SpecialArmstrongNumbers();
    final List<Integer> result = sut.calcArmstrongNumbers();

    assertEquals(List.of(153, 371), result);
  }

  @ParameterizedTest(name = "x*100 + y*10 + z = x^{0} + y^{1} + z^{2} を満たすのは {3}")
  @MethodSource({"specialsSource"})
  @DisplayName("3 桁の数字について、")
  public void calcNumbers(int a, int b, int c, List<Integer> expected) {
    CubicFunction special = (x, y, z) -> (int) (Math.pow(x, a) + Math.pow(y, b) + Math.pow(z, c));

    final var sut = new Ex09_SpecialArmstrongNumbers();
    final List<Integer> result = sut.calcNumbers(special);

    assertEquals(expected, result);
  }

  static Stream<Arguments> specialsSource() {
    return Stream.of(
        Arguments.of(3, 3, 3, List.of(153, 371)),
        Arguments.of(1, 2, 3, List.of(135, 175, 518, 598)),
        Arguments.of(3, 2, 1, List.of()));
  }
}
