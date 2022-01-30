/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.utilities

import net.yewton.asobiba.list.LinkedList

@Suppress("MISSING_KDOC_TOP_LEVEL", "KDOC_NO_EMPTY_TAGS")
class JoinUtils {
    companion object {
        /**
         * @param source
         * @return
         */
        fun join(source: LinkedList): String {
            val result = StringBuilder()
            for (i in 0 until source.size()) {
                if (result.isNotEmpty()) {
                    result.append(" ")
                }
                result.append(source.get(i))
            }

            return result.toString()
        }
    }
}
