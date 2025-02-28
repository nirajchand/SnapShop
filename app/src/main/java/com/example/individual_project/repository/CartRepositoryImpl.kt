package com.example.individual_project.repository

import android.util.Log
import com.example.individual_project.model.CartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartRepositoryImpl : CartRepository {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun addToCart(
        cartModel: CartModel,
        callback: (Boolean, String) -> Unit
    ) {

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId.isNullOrEmpty()) {
                callback(false, "User not authenticated")
                return
            }

            val cartRef = database.reference.child("carts").child(userId).child(cartModel.productID)

            cartRef.setValue(cartModel)
                .addOnSuccessListener {
                    callback(true, "Item added to cart")
                }
                .addOnFailureListener { exception ->
                    callback(false, "Failed to add item: ${exception.message}")
                }

    }

    override fun updateCart(
        userID: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteCartById(
        productID: String,
        callback: (Boolean, String) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId.isNullOrEmpty()) {
            callback(false, "User not authenticated")
            return
        }

        val cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId).child(productID)

        cartRef.removeValue()
            .addOnSuccessListener {
                callback(true, "Item removed from cart")
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to remove item: ${exception.message}")
            }
    }

    override fun getAllCartItem(
        userID: String,
        callback: (List<CartModel>?, Boolean, String) -> Unit
    ) {

        if (userID.isNullOrEmpty()) {
            callback(null, false, "User not authenticated")
            return
        }
        val cartRef = database.reference.child("carts").child(userID)
        cartRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val cartItems = mutableListOf<CartModel>()
                // Loop through all product IDs in the cart and convert them to CartModel objects
                for (productSnapshot in snapshot.children) {
                    val cartModel = productSnapshot.getValue(CartModel::class.java)
                    cartModel?.let {
                        cartItems.add(it)
                    }
                }
                callback(cartItems, true, "Cart items fetched successfully")
            } else {
                callback(null, false, "No items in cart")
            }
        }.addOnFailureListener { exception ->
            callback(null, false, "Failed to fetch cart items: ${exception.message}")
        }
    }
}