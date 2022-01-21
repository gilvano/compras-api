package com.gilvano.comprasapi.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cashback")
class CashbackCriteriaProperties {
    lateinit var criteria:  MutableList<Criteria>
}

class Criteria() {
    var name: String? = null
    var valueAbove: Double? = null
    var cashbackPercent: Double? = null
}

