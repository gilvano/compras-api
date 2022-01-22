package com.gilvano.comprasapi.gateway

class CashbackResponse(
    var statusCode: Int,
    var body: Body
)

class Body(
    var credit: Double,
)