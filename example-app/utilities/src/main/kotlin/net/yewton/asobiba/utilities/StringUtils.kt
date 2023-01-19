/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.utilities

import net.yewton.asobiba.list.LinkedList

class StringUtils {
    companion object {
        fun join(source: LinkedList): String = JoinUtils.join(source)

        fun split(source: String): LinkedList = SplitUtils.split(source)
    }
}
