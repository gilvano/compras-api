package com.gilvano.comprasapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ComprasApiApplication

fun main(args: Array<String>) {
	runApplication<ComprasApiApplication>(*args)
}
