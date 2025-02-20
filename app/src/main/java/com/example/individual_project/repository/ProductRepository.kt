package com.example.individual_project.repository

import android.content.Context
import android.net.Uri
import com.example.individual_project.model.ProductModel
import javax.security.auth.callback.Callback

interface ProductRepository {
    fun addProduct(productModel: ProductModel,
                   callback: (Boolean, String) -> Unit)

    fun updateProduct(productID: String, data : MutableMap<String, Any> ,
                      callback: (Boolean, String) -> Unit)

    fun deleteProduct(productID: String,
                      callback: (Boolean, String) -> Unit)

    fun getProductByID(productID: String,
                       callback: (ProductModel?, Boolean, String) -> Unit)

    fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit)


    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?

}