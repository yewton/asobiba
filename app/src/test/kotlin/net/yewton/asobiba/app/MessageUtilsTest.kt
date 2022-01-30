/*
    Copyright (c) Your Company Name Here. 2010-2022
*/

package net.yewton.asobiba.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress(
        "MISSING_KDOC_TOP_LEVEL",
        "AVOID_USING_UTILITY_CLASS",
        "MISSING_KDOC_CLASS_ELEMENTS"
)
class MessageUtilsTest {
    @Test fun testGetMessage() {
        assertEquals("Hello      World!", MessageUtils.getMessage())
    }
}
