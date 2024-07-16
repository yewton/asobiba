package net.yewton.asobiba.osunaba.jcp2.chapter1;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.apache.bcel.Repository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@SuppressWarnings({"NewClassNamingConvention", "NonAsciiCharacters"})
public class Problems implements WithAssertions {
  @Nested
  class P01_複数行に渡る文字列 {
    @Test
    void case01_同じSQL文字列をテキストブロック有無で書き比べてみる() {
      var joinStyle = String.join(
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
  }

  @Nested
  class P02_テキストブロックのデリミタの振る舞いについて {

    String expected =
        """
                      UPDATE "public"."office"
                      SET ("address_first", "address_second", "phone") =
                        (SELECT "public"."employee"."first_name",
                                "public"."employee"."last_name", ?
                         FROM "public"."employee"
                         WHERE "public"."employee"."job_title" = ?)""";

    @Test
    void case01_インデントサイズは任意() {
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
    void case02_デリミタが右にずれていても影響はない() {
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
    void case03_終端デリミタを単独行に配置すると終端に改行が入る() {
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
    void case04_終端デリミタを単独行に左寄せで配置すると文字列にインデントが入る() {
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

  @Nested
  class P03_テキストブロックのインデントについて {
    String expected =
        """
              I would want to establish strength; root-like,
              anchored in the hopes of solidity.

              Forsake the contamination of instability.
              Prove I’m the poet of each line of prose.""";

    @Test
    void case01_インデントさせつつ改行を抑制できる() {
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
  class P05_可読性を上げる為にテキストブロックを利用するケース {
    @Test
    void case01_先頭の空白が無い場合() {
      var actual =
          """
             SELECT "public"."employee"."first_name" \
             FROM "public"."employee" \
             WHERE "public"."employee"."job_title" = ?\
             """;
      var expected = "SELECT \"public\".\"employee\".\"first_name\" FROM "
          + "\"public\".\"employee\" WHERE \"public\".\"employee\".\"job_title\" = ?";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void case02_先頭に空白を含める場合() {
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
      var expected = "UPDATE \"public\".\"office\""
          + " SET (\"address_first\", \"address_second\", \"phone\") ="
          + " (SELECT \"public\".\"employee\".\"first_name\","
          + " \"public\".\"employee\".\"last_name\", ?"
          + " FROM \"public\".\"employee\""
          + " WHERE \"public\".\"employee\".\"job_title\" = ?";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void case03_行末の空白を保持する場合() {
      var actual =
          """
                 An old silent pond...\s\s\s
              A frog jumps into the pond,
                splash!! Silence again.\s\s
              """;
      var expected = String.join(
          "\n",
          "   An old silent pond...   ",
          "A frog jumps into the pond,",
          "  splash!! Silence again.  \n");
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void case04_行末の空白を保持する場合_その2() {
      var actual =
          """
                 An old silent pond...  \s
              A frog jumps into the pond,
                splash!! Silence again. \s
              """;
      var expected = String.join(
          "\n",
          "   An old silent pond...   ",
          "A frog jumps into the pond,",
          "  splash!! Silence again.  \n");
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Nested
  class P07_プログラムでエスケープシーケンスを解釈する {
    @Test
    void case01_translateEscapes() {
      String newline = "\\n".translateEscapes();
      assertThat(newline).isEqualTo("\n");
    }
  }

  @Nested
  public class P08_変数や式を含むテキストブロック {

    /*
     * Benchmark                                                                  Mode  Cnt  Score   Error  Units
     * Problems.P08_変数や式を含むテキストブロック.P08Bench.benchConcat           avgt    3  0.154 ± 0.043  us/op
     * Problems.P08_変数や式を含むテキストブロック.P08Bench.benchMessageFormat    avgt    3  0.465 ± 0.035  us/op
     * Problems.P08_変数や式を含むテキストブロック.P08Bench.benchStringBuilder    avgt    3  0.187 ± 0.014  us/op
     * Problems.P08_変数や式を含むテキストブロック.P08Bench.benchStringFormat     avgt    3  0.130 ± 0.019  us/op
     * Problems.P08_変数や式を含むテキストブロック.P08Bench.benchStringFormatted  avgt    3  0.135 ± 0.069  us/op
     */
    @Test
    void 様々な方法でベンチマーク() throws RunnerException {
      var opt = new OptionsBuilder()
          .include(P08Bench.class.getSimpleName())
          .forks(1)
          .warmupIterations(3)
          .warmupTime(TimeValue.milliseconds(200))
          .measurementIterations(3)
          .measurementTime(TimeValue.milliseconds(200))
          .build();
      new Runner(opt).run();
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
  }

  @Nested
  class P12_文字列の同型判定 {
    static class IsomorphicChecker {
      public boolean perform(String s1, String s2) {
        if (Objects.isNull(s1) || Objects.isNull(s2) || s1.length() != s2.length()) {
          return false;
        }
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
          char chs1 = s1.charAt(i);
          char chs2 = s2.charAt(i);
          if (map.containsKey(chs1)) {
            if (map.get(chs1) != chs2) {
              return false;
            }
          } else {
            if (map.containsValue(chs2)) {
              return false;
            }
            map.put(chs1, chs2);
          }
        }
        return true;
      }
    }

    @ParameterizedTest(name = "[{index}] ''{0}'' と ''{1}'' は {2}")
    @MethodSource
    void p12(String s1, String s2, boolean expected) {
      assertThat(new IsomorphicChecker().perform(s1, s2)).isEqualTo(expected);
    }

    static Stream<Arguments> p12() {
      var truthy = named("同型である", true);
      var falsy = named("同型でない", false);
      return Stream.of(
          arguments("abbcdd", "qwwerr", truthy),
          arguments("aab", "que", falsy),
          arguments(
              """
                     abbcdd
                       baaaad
                     ccddaa
                     """,
              """
                     qwwerr
                       wqqqqr
                     eerrqq
                     """,
              truthy));
    }
  }

  @Nested
  class P13_文字列結合 {
    @Test
    void 様々な文字列結合処理のバイトコード出力() throws Exception {
      var objectClazz = Repository.lookupClass(
          "net.yewton.asobiba.osunaba.jcp2.chapter1.Problems$P13_文字列結合$Strings");
      Stream.of(
              "concatViaPlus",
              "concatViaStringBuilder",
              "concatListViaPlus",
              "concatListViaStringBuilder")
          .forEach(methodName -> {
            try {
              final var method = methodName.contains("List")
                  ? Strings.class.getMethod(methodName, List.class)
                  : Strings.class.getMethod(
                      methodName, String.class, String.class, String.class, String.class);
              System.out.printf(
                  "## %s\n\n```\n%s\n```\n\n",
                  methodName, objectClazz.getMethod(method).getCode());
            } catch (NoSuchMethodException e) {
              fail("存在しないメソッド: " + methodName, e);
            }
          });
    }

    @SuppressWarnings("unused") // 動的に呼び出されるため
    public static class Strings {

      private Strings() {
        throw new AssertionError("Cannot be instantiated");
      }

      /*
       * Code(maxStack = 4, maxLocals = 4, code_length = 10)
       * 0:    aload_0
       * 1:    aload_1
       * 2:    aload_2
       * 3:    aload_3
       * 4:    invokedynamic	0:makeConcatWithConstants (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; (14)	00
       * 9:    areturn
       */
      public static String concatViaPlus(String str1, String str2, String str3, String str4) {

        return str1 + str2 + str3 + str4;
      }

      /*
       * Code(maxStack = 2, maxLocals = 5, code_length = 34)
       * 0:    new		<java.lang.StringBuilder> (18)
       * 3:    dup
       * 4:    invokespecial	java.lang.StringBuilder.<init> ()V (20)
       * 7:    astore		%4
       * 9:    aload		%4
       * 11:   aload_0
       * 12:   invokevirtual	java.lang.StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder; (21)
       * 15:   aload_1
       * 16:   invokevirtual	java.lang.StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder; (21)
       * 19:   aload_2
       * 20:   invokevirtual	java.lang.StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder; (21)
       * 23:   aload_3
       * 24:   invokevirtual	java.lang.StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder; (21)
       * 27:   pop
       * 28:   aload		%4
       * 30:   invokevirtual	java.lang.StringBuilder.toString ()Ljava/lang/String; (25)
       * 33:   areturn
       */
      public static String concatViaStringBuilder(
          String str1, String str2, String str3, String str4) {

        StringBuilder sb = new StringBuilder();

        sb.append(str1).append(str2).append(str3).append(str4);

        return sb.toString();
      }

      /*
       * Code(maxStack = 2, maxLocals = 4, code_length = 42)
       * 0:    ldc		"" (29)
       * 2:    astore_1
       * 3:    aload_0
       * 4:    invokeinterface	java.util.List.iterator ()Ljava/util/Iterator; (31)	1	0
       * 9:    astore_2
       * 10:   aload_2
       * 11:   invokeinterface	java.util.Iterator.hasNext ()Z (37)	1	0
       * 16:   ifeq		#40
       * 19:   aload_2
       * 20:   invokeinterface	java.util.Iterator.next ()Ljava/lang/Object; (43)	1	0
       * 25:   checkcast		<java.lang.String> (47)
       * 28:   astore_3
       * 29:   aload_1
       * 30:   aload_3
       * 31:   invokedynamic	1:makeConcatWithConstants (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; (49)	00
       * 36:   astore_1
       * 37:   goto		#10
       * 40:   aload_1
       * 41:   areturn
       */
      public static String concatListViaPlus(List<String> strs) {

        String result = "";
        for (String str : strs) {
          result = result + str;
        }

        return result;
      }

      /*
       * Code(maxStack = 2, maxLocals = 4, code_length = 48)
       * 0:    new		<java.lang.StringBuilder> (18)
       * 3:    dup
       * 4:    invokespecial	java.lang.StringBuilder.<init> ()V (20)
       * 7:    astore_1
       * 8:    aload_0
       * 9:    invokeinterface	java.util.List.iterator ()Ljava/util/Iterator; (31)	1	0
       * 14:   astore_2
       * 15:   aload_2
       * 16:   invokeinterface	java.util.Iterator.hasNext ()Z (37)	1	0
       * 21:   ifeq		#43
       * 24:   aload_2
       * 25:   invokeinterface	java.util.Iterator.next ()Ljava/lang/Object; (43)	1	0
       * 30:   checkcast		<java.lang.String> (47)
       * 33:   astore_3
       * 34:   aload_1
       * 35:   aload_3
       * 36:   invokevirtual	java.lang.StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder; (21)
       * 39:   pop
       * 40:   goto		#15
       * 43:   aload_1
       * 44:   invokevirtual	java.lang.StringBuilder.toString ()Ljava/lang/String; (25)
       * 47:   areturn
       */
      public static String concatListViaStringBuilder(List<String> strs) {

        StringBuilder result = new StringBuilder();

        for (String str : strs) {
          result.append(str);
        }

        return result.toString();
      }
    }
  }

  @Nested
  public class P14_整数から文字列への変換 {

    /*
     * Benchmark                                                       Mode  Cnt  Score    Error  Units
     * Problems.P14_整数から文字列への変換.P14Bench.intToStringV1      avgt    3  0.004 ±  0.001  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.intToStringV2      avgt    3  0.004 ±  0.001  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.intToStringV3      avgt    3  0.004 ±  0.001  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.intToStringV4      avgt    3  0.177 ±  0.049  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.integerToStringV2  avgt    3  0.002 ±  0.001  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.integerToStringV3  avgt    3  0.001 ±  0.001  us/op
     * Problems.P14_整数から文字列への変換.P14Bench.integerToStringV4  avgt    3  0.063 ±  0.446  us/op
     */
    @Test
    void 様々な方法でベンチマーク() throws RunnerException {
      var opt = new OptionsBuilder()
          .include(P14_整数から文字列への変換.class.getSimpleName())
          .forks(1)
          .warmupIterations(3)
          .warmupTime(TimeValue.milliseconds(200))
          .measurementIterations(3)
          .measurementTime(TimeValue.milliseconds(200))
          .build();
      new Runner(opt).run();
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @SuppressWarnings({"unused", "SameParameterValue"})
    @State(Scope.Benchmark)
    public static class P14Bench {

      private int v;
      private Integer vo;

      @Setup
      public void setUp() {
        int v = 948822;
        //noinspection UnnecessaryBoxing
        Integer vo = Integer.valueOf(948822);
      }

      @Benchmark
      public String intToStringV1() {
        return Integer.toString(v);
      }

      @Benchmark
      public String intToStringV2() {
        return "" + v;
      }

      @Benchmark
      public String intToStringV3() {
        return String.valueOf(v);
      }

      @Benchmark
      public String intToStringV4() {
        return String.format("%d", v);
      }

      @Benchmark
      public String integerToStringV1() {
        return Integer.toString(vo);
      }

      @Benchmark
      public String integerToStringV2() {
        return "" + vo;
      }

      @Benchmark
      public String integerToStringV3() {
        return String.valueOf(vo);
      }

      @Benchmark
      public String integerToStringV4() {
        return String.format("%d", vo);
      }
    }
  }
}
