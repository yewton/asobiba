package net.yewton.asobiba.mondai.chapter01.problem01;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class CountDuplicateCharsMyImpl implements CountDuplicateChars {

  @Override
  public @NotNull Map<String, Long> perform(@NotNull String input) {
    return input
        .codePoints()
        .mapToObj(c -> String.valueOf(Character.toChars(c)))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }
}
