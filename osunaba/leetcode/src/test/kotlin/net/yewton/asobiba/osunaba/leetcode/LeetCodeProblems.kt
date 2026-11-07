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
})
