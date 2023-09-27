package net.yewton.asobiba.nanka.web

import org.springframework.web.servlet.ModelAndView

class IndexModelAndViewBuilder : ModelAndViewBuilder() {

    lateinit var nanka: Nanka

    override fun viewName() = "top"

    override fun setUp(mav: ModelAndView) {
        mav.addObject("nanka", nanka)
    }
}
