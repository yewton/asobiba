package net.yewton.asobiba.mondai.chapter01.problem01

import kotlin.streams.toList

class CountDuplicateCharsKtImpl2 : CountDuplicateChars {
    override fun perform(input: String): Map<String, Long> = input.codePoints()
        .toList()
        .map { String(Character.toChars(it)) }
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }
}
