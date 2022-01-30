/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.utilities

import net.yewton.asobiba.list.LinkedList

@Suppress("MISSING_KDOC_TOP_LEVEL")
class StringUtils {
    companion object {
        /**
         * @param source
         */
        fun join(source: LinkedList): String = JoinUtils.join(source)

        /**
         * @param source
         */
        fun split(source: String): LinkedList = SplitUtils.split(source)
    }
}
