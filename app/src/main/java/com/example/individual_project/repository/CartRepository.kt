package com.example.individual_project.repository

import com.example.individual_project.model.CartModel

interface CartRepository {

    fun addToCart(cartModel: CartModel,callback:(Boolean,String)-> Unit)

    fun updateCart(userID: String, data: MutableMap<String, Any>, callback: (Boolean,String) -> Unit)

    fun deleteCartById(productID: String , callback: (Boolean, String)-> Unit)

    fun getAllCartItem(userID: String,callback: (List<CartModel>?, Boolean, String) -> Unit)

}