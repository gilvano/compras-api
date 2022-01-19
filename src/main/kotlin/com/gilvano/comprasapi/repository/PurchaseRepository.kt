package com.gilvano.comprasapi.repository

import com.gilvano.comprasapi.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository: CrudRepository<PurchaseModel, Long> {

}