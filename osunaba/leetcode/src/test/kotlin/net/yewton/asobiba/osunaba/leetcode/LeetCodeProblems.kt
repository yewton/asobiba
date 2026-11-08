package net.yewton.asobiba.osunaba.leetcode

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class LeetCodeProblems : FunSpec({
    context("1. Two Sum") {
        abstract class SolutionBase {
            abstract fun twoSum(nums: IntArray, target: Int): IntArray
        }

        class Solution1BruteForce : SolutionBase() {
            override fun twoSum(nums: IntArray, target: Int): IntArray {
                for (i in nums.indices) {
                    for (j in i + 1 until nums.size) {
                        if (nums[i] + nums[j] == target) {
                            return intArrayOf(i, j)
                        }
                    }
                }
                return intArrayOf()
            }
        }

        class Solution2HashMap : SolutionBase() {
            override fun twoSum(nums: IntArray, target: Int): IntArray {
                val memo = nums.withIndex().associate { (index, num) -> num to index }
                nums.withIndex().forEach { (index, num) ->
                    val complement = target - num
                    memo[complement]?.let { complementIndex ->
                        if (complementIndex != index) {
                            return intArrayOf(index, complementIndex)
                        }
                    }
                }
                return intArrayOf()
            }
        }

        @Suppress("ArrayInDataClass")
        data class TestCase(val nums: IntArray, val target: Int, val expected: IntArray)

        withData(
            TestCase(intArrayOf(2, 7, 11, 15), 9, intArrayOf(0, 1)),
            TestCase(intArrayOf(3, 2, 4), 6, intArrayOf(1, 2)),
            TestCase(intArrayOf(3, 3), 6, intArrayOf(0, 1))
        ) { (nums, target, expected) ->
            withData(
                Solution1BruteForce(),
                Solution2HashMap()
            ) { sut ->
                val actual = sut.twoSum(nums, target)
                actual shouldBe expected
            }
        }
    }
    context("Problem 9. Palindrome Number") {
        abstract class SolutionBase {
            abstract fun isPalindrome(x: Int): Boolean
        }

        class Solution1Naive : SolutionBase() {
            override fun isPalindrome(x: Int): Boolean {
                if (x < 0) return false
                val digitsReversed = generateSequence(x) { it / 10 }
                    .takeWhile { it > 0 }
                    .fold(mutableListOf<Int>()) { acc, num -> acc.apply { add(num % 10) } }
                val digits = digitsReversed.reversed()
                return digits == digitsReversed
            }
        }

        class Solution2Halve : SolutionBase() {
            @Suppress("ReturnCount")
            override fun isPalindrome(x: Int): Boolean {
                if (x < 0) return false
                if (x < 10) return true
                if (x % 10 == 0) return false
                var xx = x
                var yy = 0

                while (xx > yy) {
                    yy = yy * 10 + xx % 10
                    xx /= 10
                }
                return xx == yy || xx == yy / 10
            }
        }

        data class TestCase(val x: Int, val expected: Boolean)

        withData(
            TestCase(121, true),
            TestCase(-121, false),
            TestCase(10, false),
            TestCase(1221, true),
            TestCase(12321, true),
            TestCase(1, true),
            TestCase(101, true),
            TestCase(21120, false)
        ) { (x, expected) ->
            withData(
                Solution1Naive(),
                Solution2Halve()
            ) { sut ->
                val actual = sut.isPalindrome(x)
                actual shouldBe expected
            }
        }
    }

    context("13. Roman to Integer") {
        abstract class SolutionBase {
            protected val romanMap = mapOf(
                'I' to 1,
                'V' to 5,
                'X' to 10,
                'L' to 50,
                'C' to 100,
                'D' to 500,
                'M' to 1000
            )
            abstract fun romanToInt(s: String): Int
        }

        class Solution1Naive : SolutionBase() {
            override fun romanToInt(s: String): Int {
                return s.reversed().fold((0 to 'Z')) { (total, prevChar), char ->
                    val value = romanMap[char] ?: 0
                    when (char) {
                        'I' -> {
                            when (prevChar) {
                                'V', 'X' -> (total - value) to char
                                else -> (total + value) to char
                            }
                        }
                        'X' -> {
                            when (prevChar) {
                                'L', 'C' -> (total - value) to char
                                else -> (total + value) to char
                            }
                        }
                        'C' -> {
                            when (prevChar) {
                                'D', 'M' -> (total - value) to char
                                else -> (total + value) to char
                            }
                        }
                        'V', 'L', 'D', 'M' -> (total + value) to char
                        else -> throw IllegalArgumentException("Invalid Roman numeral: $char")
                    }
                }.first
            }
        }

        class Solution2Optimized : SolutionBase() {
            override fun romanToInt(s: String): Int {
                return s.fold(0 to 0) { (total, prevValue), char ->
                    val value = romanMap[char] ?: 0
                    if (value > prevValue) {
                        (total + value - 2 * prevValue) to value
                    } else {
                        (total + value) to value
                    }
                }.first
            }
        }

        data class TestCase(val s: String, val expected: Int)

        withData(
            TestCase("III", 3),
            TestCase("IV", 4),
            TestCase("IX", 9),
            TestCase("LVIII", 58),
            TestCase("MCMXCIV", 1994)
        ) { (s, expected) ->
            withData(
                Solution1Naive(),
                Solution2Optimized()
            ) { sut ->
                val actual = sut.romanToInt(s)
                actual shouldBe expected
            }
        }
    }

    context("14. Longest Common Prefix") {
        abstract class SolutionBase {
            abstract fun longestCommonPrefix(strs: Array<String>): String
        }

        class Solution1Naive : SolutionBase() {
            override fun longestCommonPrefix(strs: Array<String>): String {
                val minimumLength = strs.minOf { it.length }
                var prefix = ""
                for (i in 0 until minimumLength) {
                    val char = strs[0][i]
                    if (strs.all { it[i] == char }) {
                        prefix += char
                    } else {
                        break
                    }
                }
                return prefix
            }
        }

        class Solution2Naive : SolutionBase() {
            override fun longestCommonPrefix(strs: Array<String>): String {
                return strs.minBy { it.length }.let { shortest ->
                    shortest.foldIndexed(("" to true)) { index, (prefix, cont), ch ->
                        if (cont && strs.all { it[index] == ch }) {
                            prefix + ch to true
                        } else {
                            prefix to false
                        }
                    }
                }.first
            }
        }

        data class TestCase(val strs: Array<String>, val expected: String)

        withData(
            TestCase(arrayOf("flower", "flow", "flight"), "fl"),
            TestCase(arrayOf("dog", "racecar", "car"), ""),
            TestCase(arrayOf("interstellar", "internet", "internal"), "inter"),
            TestCase(arrayOf("a"), "a"),
            TestCase(arrayOf("", ""), "")
        ) { (strs, expected) ->
            withData(
                Solution1Naive(),
                Solution2Naive()
            ) { sut ->
                val actual = sut.longestCommonPrefix(strs)
                actual shouldBe expected
            }
        }
    }
})
