package frog.company.subscribercheat.model

import kotlinx.serialization.Serializable

@Serializable
data class Orders(
    var orderId : Int = 0,
    var orderName : String = "",
    var orderUrl : String = "",
    var orderPrice : Int = 0,
    var orderCount : Int = 0,
    var orderType : Int = 0
)