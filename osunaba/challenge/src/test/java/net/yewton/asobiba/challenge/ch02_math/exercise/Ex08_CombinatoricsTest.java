package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Ex08_CombinatoricsTest {

  @MethodSource("abcSource")
  @ParameterizedTest(name = "{0}^2 + {1}^2 = {2}^2")
  void findABC(final int a, final int b, final int c) {
    assertEquals(Math.pow(a, 2) + Math.pow(b, 2), Math.pow(c, 2));
  }

  public static Stream<Arguments> abcSource() {
    var sut = new Ex08_Combinatorics();
    return sut.findABCs().stream().sorted().map(abc -> Arguments.of(abc.a(), abc.b(), abc.c()));
  }
}
