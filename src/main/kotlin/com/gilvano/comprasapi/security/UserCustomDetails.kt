package com.gilvano.comprasapi.security

import com.gilvano.comprasapi.model.ResellerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(
    private val resellerModel: ResellerModel
): UserDetails {
    val id = resellerModel.id.toString()
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(resellerModel.profile.description))
    override fun getPassword(): String = resellerModel.password
    override fun getUsername(): String = resellerModel.id.toString()
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean  = true
    override fun isEnabled(): Boolean = true
}