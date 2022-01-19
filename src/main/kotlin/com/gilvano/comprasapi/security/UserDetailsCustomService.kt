package com.gilvano.comprasapi.security

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.exception.AuthenticationException
import com.gilvano.comprasapi.repository.RevendedorRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val revendedorRepository: RevendedorRepository
): UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = revendedorRepository.findById(id)
            .orElseThrow { AuthenticationException(Errors.CP998.message, Errors.CP998.code) }

        return UserCustomDetails(customer)
    }
}