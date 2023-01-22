/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.utilities

import net.yewton.asobiba.list.LinkedList

@Suppress("UtilityClassWithPublicConstructor")
class JoinUtils {
    companion object {
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
