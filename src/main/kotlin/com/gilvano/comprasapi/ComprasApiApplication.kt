package com.gilvano.comprasapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableFeignClients
class ComprasApiApplication

fun main(args: Array<String>) {
	runApplication<ComprasApiApplication>(*args)
}
