package net.yewton.asobiba.nanka.web

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class NankaErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(
        webRequest: WebRequest,
        options: ErrorAttributeOptions
    ): MutableMap<String, Any> {
        val attributes = super.getErrorAttributes(webRequest, options)

        attributes["message"] = "ステータスは" + attributes["status"] + "だったようです"
        attributes["nanka"] = "なんらかのカスタムな属性値"

        return attributes
    }
}
