package net.yewton.asobiba.nanka.web.unsafe2

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails

class MyUnsafe2AuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {

    private val userDetailsService = MyUnsafe2UserDetailsService()

    override fun retrieveUser(
        username: String,
        authentication: UsernamePasswordAuthenticationToken
    ): UserDetails = userDetailsService.loadUserByUsername(username)

    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        authentication: UsernamePasswordAuthenticationToken
    ) {
        if (userDetails.password != authentication.credentials.toString()) {
            throw BadCredentialsException("パスワードちがうよ")
        }
    }
}
