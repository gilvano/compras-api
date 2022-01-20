package com.gilvano.comprasapi.repository

import com.gilvano.comprasapi.model.PurchaseModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface PurchaseRepository: CrudRepository<PurchaseModel, Long> {
    @Query("{ 'cpf': ?0, 'date': { '\$gte': ?1, '\$lte': ?2 } }")
    fun findByCpfAndDateBetween(cpf: String, dateStart: LocalDate, dateEnd: LocalDate): List<PurchaseModel>
    fun findAll(pageable: Pageable): Page<PurchaseModel>
}