/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.list

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@Suppress(
    "MISSING_KDOC_TOP_LEVEL",
    "KDOC_NO_EMPTY_TAGS",
    "MISSING_KDOC_CLASS_ELEMENTS",
    "MISSING_KDOC_ON_FUNCTION"
)
class LinkedListTest {
    @Test fun testConstructor() {
        val list = LinkedList()
        assertEquals(0, list.size())
    }

    @Test fun testAdd() {
        val list = LinkedList()

        list.add("one")
        assertEquals(1, list.size())
        assertEquals("one", list.get(0))

        list.add("two")
        assertEquals(2, list.size())
        assertEquals("two", list.get(1))
    }

    @Test fun testRemove() {
        val list = LinkedList()

        list.add("one")
        list.add("two")
        assertTrue(list.remove("one"))

        assertEquals(1, list.size())
        assertEquals("two", list.get(0))

        assertTrue(list.remove("two"))
        assertEquals(0, list.size())
    }

    @Test fun testRemoveMissing() {
        val list = LinkedList()

        list.add("one")
        list.add("two")
        assertFalse(list.remove("three"))
        assertEquals(2, list.size())
    }
}
