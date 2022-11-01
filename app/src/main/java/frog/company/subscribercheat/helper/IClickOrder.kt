package frog.company.subscribercheat.helper

import frog.company.subscribercheat.model.Orders

interface IClickOrder {
    fun onSelectOrder(order: Orders)
}