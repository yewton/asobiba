package net.yewton.asobiba.nanka.web

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

interface MyUser {
    fun getUsername(): String
}

class MyRememberMeUser(private val delegate: UserDetails) : UserDetails by delegate, MyUser

class MyUnsafeAuthUser(private val delegate: UserDetails) : UserDetails by delegate, MyUser

class MyOauth2User(private val delegate: OAuth2User) : OAuth2User by delegate, UserDetails, MyUser {
    override fun getPassword() = ""

    override fun getUsername() = delegate.getAttribute<String>("email")?.substringBefore("@") ?: "Unknown"

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
