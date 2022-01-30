package net.yewton.asobiba.mondai.chapter01.problem01

import io.kotest.core.spec.style.FunSpec

@Suppress("MISSING_KDOC_TOP_LEVEL")
class CountDuplicateCharsMyImplTest : FunSpec({
    include(countDuplicateCharsTests(CountDuplicateCharsMyImpl()))
})
