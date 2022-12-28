package net.yewton.asobiba.mondai.chapter01.problem01

import io.kotest.core.spec.style.FunSpec

class CountDuplicateCharsMyImplTest : FunSpec({
    include(countDuplicateCharsTests(CountDuplicateCharsMyImpl()))
})
