package net.yewton.asobiba.osunaba.jcp2;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

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

  @SuppressWarnings("TextBlockMigration")
  @Nested
  @DisplayName("可読性を上げる為にテキストブロックを利用するケース")
  class P05 {
    @Test
    @DisplayName("先頭の空白が無い場合")
    void p05_1() {
      var actual =
          """
             SELECT "public"."employee"."first_name" \
             FROM "public"."employee" \
             WHERE "public"."employee"."job_title" = ?\
             """;
      var expected =
          "SELECT \"public\".\"employee\".\"first_name\" FROM "
              + "\"public\".\"employee\" WHERE \"public\".\"employee\".\"job_title\" = ?";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("先頭に空白を含める場合")
    void p05_2() {
      var actual =
          """
  UPDATE "public"."office" \
  SET ("address_first", "address_second", "phone") = \
    (SELECT "public"."employee"."first_name", \
            "public"."employee"."last_name", ? \
     FROM "public"."employee" \
     WHERE "public"."employee"."job_title" = ?\
  """
              .trim()
              .replaceAll(" +", " ");
      var expected =
          "UPDATE \"public\".\"office\""
              + " SET (\"address_first\", \"address_second\", \"phone\") ="
              + " (SELECT \"public\".\"employee\".\"first_name\","
              + " \"public\".\"employee\".\"last_name\", ?"
              + " FROM \"public\".\"employee\""
              + " WHERE \"public\".\"employee\".\"job_title\" = ?";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("行末の空白を保持する場合")
    void p05_3() {
      var actual =
          """
                 An old silent pond...\s\s\s
              A frog jumps into the pond,
                splash!! Silence again.\s\s
              """;
      var expected =
          String.join(
              "\n",
              "   An old silent pond...   ",
              "A frog jumps into the pond,",
              "  splash!! Silence again.  \n");
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("行末の空白を保持する場合 その2")
    void p05_4() {
      var actual =
          """
                 An old silent pond...  \s
              A frog jumps into the pond,
                splash!! Silence again. \s
              """;
      var expected =
          String.join(
              "\n",
              "   An old silent pond...   ",
              "A frog jumps into the pond,",
              "  splash!! Silence again.  \n");
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Nested
  @DisplayName("プログラムでエスケープシーケンスを解釈する")
  class P07 {
    @Test
    @DisplayName("translateEscapes")
    void p07_1() {
      String newline = "\\n".translateEscapes();
      assertThat(newline).isEqualTo("\n");
    }
  }

  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  @SuppressWarnings({"unused", "SameParameterValue"})
  public static class P08Bench {
    @Benchmark
    public void benchConcat(Blackhole bh) {
      bh.consume(concat("Jo", "Kym"));
    }

    @Benchmark
    public void benchStringBuilder(Blackhole bh) {
      bh.consume(stringBuilder("Jo", "Kym"));
    }

    @Benchmark
    public void benchMessageFormat(Blackhole bh) {
      bh.consume(messageFormat("Jo", "Kym"));
    }

    @Benchmark
    public void benchStringFormat(Blackhole bh) {
      bh.consume(stringFormat("Jo", "Kym"));
    }

    @Benchmark
    public void benchStringFormatted(Blackhole bh) {
      bh.consume(stringFormatted("Jo", "Kym"));
    }

    String concat(String fn, String ln) {
      return """
             <user>
                <firstName>
             """
          + fn.indent(4)
          + """
                 </firstName>
                 <lastName>
              """
          + ln.indent(4)
          + """
                 </lastName>
              </user>
              """;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    String stringBuilder(String fn, String ln) {
      StringBuilder sbJson = new StringBuilder();
      sbJson
          .append("""
             <user>
                <firstName>
              """)
          .append(fn.indent(4))
          .append("""
                </firstName>
                <lastName>
             """)
          .append(ln.indent(4))
          .append("""
                </lastName>
             </user>
             """);
      return sbJson.toString();
    }

    String messageFormat(String fn, String ln) {
      return MessageFormat.format(
          """
                            <user>
                                <firstName>
                                 {0}
                                </firstName>
                                <lastName>
                                 {1}
                                </lastName>
                            </user>
                            """,
          fn, ln);
    }

    String stringFormat(String fn, String ln) {
      return String.format(
          """
                           <user>
                               <firstName>
                                %s
                               </firstName>
                               <lastName>
                                %s
                               </lastName>
                           </user>
                           """,
          fn, ln);
    }

    String stringFormatted(String fn, String ln) {
      return """
             <user>
                 <firstName>
                  %s
                 </firstName>
                 <lastName>
                  %s
                 </lastName>
             </user>
             """
          .formatted(fn, ln);
    }
  }

  @Nested
  @DisplayName("変数や式を含むテキストブロック")
  public class P08 {

    @Test
    @DisplayName("様々な方法でベンチマーク")
    void runBench() throws RunnerException {
      var opt =
          new OptionsBuilder()
              .include(P08Bench.class.getSimpleName())
              .forks(1)
              .warmupIterations(3)
              .warmupTime(TimeValue.milliseconds(200))
              .measurementIterations(3)
              .measurementTime(TimeValue.milliseconds(200))
              .build();
      new Runner(opt).run();
    }
  }
}
