package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.collections4.SetUtils;
import org.junit.jupiter.api.Test;
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

  @Test
  void findABC2() {
    var sut = new Ex08_Combinatorics();
    assertEquals(sut.findABCs(), sut.findABCs2());
  }

  @MethodSource("abcdSource")
  @ParameterizedTest(name = "{0}^2 + {1}^2 = {2}^2 + {3}^2")
  void findABCD(final int a, final int b, final int c, final int d) {
    assertEquals(Math.pow(a, 2) + Math.pow(b, 2), Math.pow(c, 2) + Math.pow(d, 2));
  }

  public static Stream<Arguments> abcdSource() {
    var sut = new Ex08_Combinatorics();
    return sut.findABCDs().stream()
        .filter(_abcd -> new Random().nextBoolean()) // 多いので適当に間引いて
        .sorted()
        .limit(100) // 100 個だけ取得する
        .map(abcd -> Arguments.of(abcd.a(), abcd.b(), abcd.c(), abcd.d()));
  }

  @Test
  void findABCD2() {
    var sut = new Ex08_Combinatorics();
    assertEquals(Set.of(), SetUtils.difference(sut.findABCDs(), sut.findABCDs2()));
  }
}
