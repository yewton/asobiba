package net.yewton.asobiba.reactivewebapp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public interface JavaInterface {

  @SuppressWarnings("unused")
  @Schema(description = "Javaインタフェースで宣言された共有フィールドです", requiredMode = RequiredMode.REQUIRED)
  String sharedFieldJava();
}
