/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.list

@Suppress(
    "MISSING_KDOC_TOP_LEVEL",
    "KDOC_NO_EMPTY_TAGS",
    "MISSING_KDOC_CLASS_ELEMENTS",
    "MISSING_KDOC_ON_FUNCTION"
)
class LinkedList {
    private var head: Node? = null

    fun add(element: String) {
        val newNode = Node(element)

        val it = tail(head)
        it?.let {
            it.next = newNode
        }
            ?: run {
                head = newNode
            }
    }

    private fun tail(head: Node?): Node? {
        var it: Node?

        it = head
        while (it?.next != null) {
            it = it.next
        }

        return it
    }

    @Suppress("FUNCTION_BOOLEAN_PREFIX")
    fun remove(element: String): Boolean {
        var result = false
        var previousIt: Node? = null
        var it: Node? = head
        while (!result && it != null) {
            if (0 == element.compareTo(it.data)) {
                result = true
                unlink(previousIt, it)
                break
            }
            previousIt = it
            it = it.next
        }

        return result
    }

    private fun unlink(previousIt: Node?, currentIt: Node) {
        if (currentIt == head) {
            head = currentIt.next
        } else {
            previousIt?.next = currentIt.next
        }
    }

    fun size(): Int {
        var size = 0

        var it = head
        while (it != null) {
            ++size
            it = it.next
        }

        return size
    }

    fun get(idx: Int): String {
        var index = idx
        var it = head
        while (index > 0 && it != null) {
            it = it.next
            index--
        }

        it ?: run {
            throw IndexOutOfBoundsException("Index is out of range")
        }

        return it.data
    }

    /**
     * @property data
     */
    private data class Node(val data: String) {
        var next: Node? = null
    }
}
