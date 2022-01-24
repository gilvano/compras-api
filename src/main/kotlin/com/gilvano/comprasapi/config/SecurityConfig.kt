package com.gilvano.comprasapi.config

import com.gilvano.comprasapi.repository.ResellerRepository
import com.gilvano.comprasapi.security.AuthenticationFilter
import com.gilvano.comprasapi.security.AuthorizationFilter
import com.gilvano.comprasapi.security.CustomAuthenticationEntryPoint
import com.gilvano.comprasapi.security.JwtUtil
import com.gilvano.comprasapi.security.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val resellerRepository: ResellerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
): WebSecurityConfigurerAdapter() {


    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/api/v1/resellers"
    )

    private val PUBLIC_MATCHERS = arrayOf(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    )

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeRequests()
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .anyRequest().authenticated()
        http.addFilter(AuthenticationFilter(authenticationManager(), resellerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling().authenticationEntryPoint(customEntryPoint)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

//    override fun configure(web: WebSecurity) {
//        web.ignoring().antMatchers("/swagger-ui/**", "/javainuse-openapi/**")
//    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}