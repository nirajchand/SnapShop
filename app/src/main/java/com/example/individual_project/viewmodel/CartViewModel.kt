package com.example.individual_project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.individual_project.model.CartModel
import com.example.individual_project.model.ProductModel
import com.example.individual_project.repository.CartRepository

class CartViewModel(val repo: CartRepository) {
    fun addToCart(cartModel: CartModel,callback:(Boolean,String)-> Unit){
        return repo.addToCart(cartModel,callback)
    }


    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading


    var _getAllCartItems = MutableLiveData<List<CartModel>>()
    var getAllCartItems = MutableLiveData<List<CartModel>>()
        get() = _getAllCartItems
    fun getAllCartItem(userID: String){
        _loading.value = true
        repo.getAllCartItem(userID){
                cartItems,success,message->
            if (success){
                _getAllCartItems.value = cartItems
                _loading.value = false
            }
        }
    }

    fun deleteCartById(productID: String , callback: (Boolean, String)-> Unit){
        return repo.deleteCartById(productID,callback)

    }



}