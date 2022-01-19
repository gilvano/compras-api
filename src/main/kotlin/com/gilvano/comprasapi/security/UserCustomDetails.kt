package com.gilvano.comprasapi.security

import com.gilvano.comprasapi.model.RevendedorModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(
    private val revendedorModel: RevendedorModel
): UserDetails {
    val id = revendedorModel.id
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(revendedorModel.roles.description))
    override fun getPassword(): String = revendedorModel.senha
    override fun getUsername(): String = revendedorModel.id.toString()
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean  = true
    override fun isEnabled(): Boolean = true
}