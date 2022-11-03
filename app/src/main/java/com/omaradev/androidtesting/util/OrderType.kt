package com.omaradev.androidtesting.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
