/**
 * 共通のテストケースを定義するファイル。
 */

package net.yewton.asobiba.mondai.chapter01.problem01

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.maps.shouldContainExactly

/**
 * テスト対象のインスタンス [sut] に対して実施するテストを返します
 */
fun countDuplicateCharsTests(sut: CountDuplicateChars) = funSpec {
    context(sut::class.simpleName ?: "N/A") {
        test("Ascii 文字列の文字毎の個数を返す") {
            val ascii = "Hello, world!"
            sut.perform(ascii).shouldContainExactly(mapOf(
                "H" to 1, "e" to 1, "l" to 3, "o" to 2, "," to 1, " " to 1,
                "w" to 1, "r" to 1, "d" to 1, "!" to 1
            ))
        }
        test("ユニコードを含む文字列の文字毎の個数を返す") {
            // IntelliJ IDEA では Paste as Plain text しないとユニコードに変換されちゃう
            // see https://stackoverflow.com/a/56490718
            val unicode = "😀 ❤ 🍣🍣🍣"
            sut.perform(unicode).shouldContainExactly(mapOf(
                "😀" to 1, " " to 2, "❤" to 1, "🍣" to 3
            ))
        }
    }
}
