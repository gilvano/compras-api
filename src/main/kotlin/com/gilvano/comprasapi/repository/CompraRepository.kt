package com.gilvano.comprasapi.repository

import com.gilvano.comprasapi.model.CompraModel
import org.springframework.data.repository.CrudRepository

interface CompraRepository: CrudRepository<CompraModel, Long> {

}