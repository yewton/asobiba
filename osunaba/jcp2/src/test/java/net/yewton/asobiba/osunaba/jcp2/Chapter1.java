package net.yewton.asobiba.osunaba.jcp2;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class Chapter1 implements WithAssertions {
  @Test
  @DisplayName("複数行に渡る文字列をつくる")
  void p01() {
    var joinStyle =
        String.join(
            "\n",
            "UPDATE \"public\".\"office\"",
            "SET (\"address_first\", \"address_second\", \"phone\") =",
            "  (SELECT \"public\".\"employee\".\"first_name\",",
            "          \"public\".\"employee\".\"last_name\", ?",
            "   FROM \"public\".\"employee\"",
            "   WHERE \"public\".\"employee\".\"job_title\" = ?;");
    var textBlockStyle =
        """
           UPDATE "public"."office"
           SET ("address_first", "address_second", "phone") =
             (SELECT "public"."employee"."first_name",
                     "public"."employee"."last_name", ?
              FROM "public"."employee"
              WHERE "public"."employee"."job_title" = ?;""";
    assertThat(textBlockStyle).isEqualTo(joinStyle);
  }

  @Nested
  @DisplayName("テキストブロックのデリミタの振る舞いについて")
  class P02 {

    String expected =
        """
                      UPDATE "public"."office"
                      SET ("address_first", "address_second", "phone") =
                        (SELECT "public"."employee"."first_name",
                                "public"."employee"."last_name", ?
                         FROM "public"."employee"
                         WHERE "public"."employee"."job_title" = ?)""";

    @Test
    @DisplayName("インデントサイズは任意")
    void p02_1() {
      var actual =
          """
          UPDATE "public"."office"
          SET ("address_first", "address_second", "phone") =
            (SELECT "public"."employee"."first_name",
                    "public"."employee"."last_name", ?
             FROM "public"."employee"
             WHERE "public"."employee"."job_title" = ?)""";
      assertThat(actual).isEqualTo(expected);
    }

    @SuppressWarnings("TrailingWhitespacesInTextBlock")
    @Test
    @DisplayName("デリミタが右にずれていても影響はない")
    void p02_2() {
      var actual =
          """
  UPDATE "public"."office"
  SET ("address_first", "address_second", "phone") =
    (SELECT "public"."employee"."first_name",
            "public"."employee"."last_name", ?
     FROM "public"."employee"
     WHERE "public"."employee"."job_title" = ?)       """;
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("終端デリミタを単独行に配置すると終端に改行が入る")
    void p02_3() {
      var actual =
          """
  UPDATE "public"."office"
  SET ("address_first", "address_second", "phone") =
    (SELECT "public"."employee"."first_name",
            "public"."employee"."last_name", ?
     FROM "public"."employee"
     WHERE "public"."employee"."job_title" = ?)
  """;
      assertThat(actual).isEqualTo(expected + "\n");
    }

    @Test
    @DisplayName("終端デリミタを単独行に左寄せで配置すると、文字列にインデントが入る")
    void p02_4() {
      var actual =
          """
  UPDATE "public"."office"
  SET ("address_first", "address_second", "phone") =
    (SELECT "public"."employee"."first_name",
            "public"."employee"."last_name", ?
     FROM "public"."employee"
     WHERE "public"."employee"."job_title" = ?)
""";
      assertThat(actual).isEqualTo(expected.indent(2));
    }
  }

  @DisplayName("テキストブロックのインデントについて")
  @Nested
  class P03 {
    String expected =
        """
              I would want to establish strength; root-like,
              anchored in the hopes of solidity.

              Forsake the contamination of instability.
              Prove I’m the poet of each line of prose.""";

    @Test
    @DisplayName("インデントさせつつ改行を抑制できる")
    void p03_1() {
      var actual =
          """
              I would want to establish strength; root-like,
              anchored in the hopes of solidity.

              Forsake the contamination of instability.
              Prove I’m the poet of each line of prose.\
   """;
      assertThat(actual)
          .isEqualTo(expected.indent(11).replaceAll("(?m)^\\s+$", "").stripTrailing());
    }
  }
}
