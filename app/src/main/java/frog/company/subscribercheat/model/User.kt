package frog.company.subscribercheat.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id : Int = 0,
    var token : String = "",
    var login : String = "",
    var password : String = "",
    var money : Int = 0
)
