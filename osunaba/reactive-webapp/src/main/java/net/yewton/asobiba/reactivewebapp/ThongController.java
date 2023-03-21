package net.yewton.asobiba.reactivewebapp;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** ↓で @Tag を指定しなければ、このコメントが description として拾われるっぽい？ */
@Tag(name = "何かしら")
@RestController
@RequestMapping("/thong")
public class ThongController {

  /**
   * summary 扱いになる予定のコメント
   *
   * <p>summary 含めて、コメント全体が description 扱いになる予定です。
   *
   * @param id 識別子
   * @return 何がしか
   */
  @GetMapping("/{id}")
  public Thong get(@PathVariable String id) {
    return new Thong(id, "ダミー", "Java インタフェース", "Kotlinインタフェース");
  }

  /**
   * 何かしら
   *
   * @param id 何かしらの識別子
   * @param name 何かしらの名称
   */
  record Thong(
      @NonNull String id,
      @NonNull String name,
      @Override String sharedFieldJava,
      String sharedFieldKotlin)
      implements JavaInterface, KotlinInterface {
    @NotNull
    @Override
    public String getSharedFieldKotlin() {
      return sharedFieldKotlin;
    }
  }
}
