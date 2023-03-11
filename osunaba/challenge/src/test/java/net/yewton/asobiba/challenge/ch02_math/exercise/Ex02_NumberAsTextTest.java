package net.yewton.asobiba.challenge.ch02_math.exercise;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Ex02_NumberAsTextTest {

  Ex02_NumberAsText sut = new Ex02_NumberAsText();

  @ParameterizedTest
  @CsvSource({
    "7, SEVEN",
    "42, FOUR TWO",
    "7271, SEVEN TWO SEVEN ONE",
    "24680, TWO FOUR SIX EIGHT ZERO",
    "13579, ONE THREE FIVE SEVEN NINE"
  })
  void numberAsText(int number, String expected) {
    assertEquals(expected, sut.numberAsText(number));
  }
}
