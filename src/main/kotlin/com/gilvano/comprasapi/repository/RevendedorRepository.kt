package com.gilvano.comprasapi.repository

import com.gilvano.comprasapi.model.RevendedorModel
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RevendedorRepository: CrudRepository<RevendedorModel, UUID> {
    fun existsByCpf(cpf: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): RevendedorModel?
}