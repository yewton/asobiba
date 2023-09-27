package net.yewton.asobiba.nanka.web

import org.springframework.web.servlet.ModelAndView

abstract class ModelAndViewBuilder {

    protected abstract fun viewName(): String
    protected abstract fun setUp(mav: ModelAndView)

    fun build() = ModelAndView(viewName()).apply(::setUp)
}
