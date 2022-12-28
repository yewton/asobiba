/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MessageUtilsTest {
    @Test fun testGetMessage() {
        assertEquals("Hello      World!", MessageUtils.getMessage())
    }
}
