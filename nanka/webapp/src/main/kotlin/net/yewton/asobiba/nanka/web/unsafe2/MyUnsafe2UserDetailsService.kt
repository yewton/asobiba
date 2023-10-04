package net.yewton.asobiba.nanka.web.unsafe2

import net.yewton.asobiba.nanka.web.MyUnsafeAuthUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class MyUnsafe2UserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userBuilder = User.withUsername(username)
        return MyUnsafeAuthUser(
            when (username) {
                "alice" -> userBuilder.password("azalea")
                "bob" -> userBuilder.password("balsam")
                else -> throw UsernameNotFoundException("$username は見つからなかったよ")
            }.build()
        )
    }
}
