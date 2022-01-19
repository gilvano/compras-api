package com.gilvano.comprasapi.security

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(id: String) =
        Jwts.builder()
            .setSubject(id)
            .setExpiration(Date(System.currentTimeMillis() + expiration!!))
            .signWith(SignatureAlgorithm.HS256, secret!!.toByteArray())
            .compact()

    fun isTokenValid(token: String): Boolean {
        val claims = getAllClaimsFromToken(token)
        if(claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw AuthenticationException(Errors.CP997.message, Errors.CP997.code)
        }
    }

    fun getSubject(token: String) = getAllClaimsFromToken(token).subject

}