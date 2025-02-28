package com.example.individual_project.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.individual_project.model.ProductModel
import com.example.individual_project.repository.ProductRepository

class ProductViewModel(val repo: ProductRepository) {

    fun addProduct(productModel: ProductModel,
                   callback: (Boolean, String) -> Unit){
        return repo.addProduct(productModel,callback)
    }

    fun updateProduct(productID: String, data : MutableMap<String, Any> ,
                      callback: (Boolean, String) -> Unit){
        return repo.updateProduct(productID,data,callback)
    }

    fun deleteProduct(productID: String,
                      callback: (Boolean, String) -> Unit){
        return repo.deleteProduct(productID,callback)

    }

    var _products = MutableLiveData<ProductModel>()
    var products = MutableLiveData<ProductModel>()
        get() = _products

    var _getAllPRoducts = MutableLiveData<List<ProductModel>>()
    var getAllPRoducts = MutableLiveData<List<ProductModel>>()
        get() = _getAllPRoducts

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading


    fun getProductByID(productID: String){
        repo.getProductByID(productID){
            products,success,message ->
            if (success){
                _products.value = products
            }
        }

    }

    fun getAllProductsFunc(){
        _loading.value = true
        repo.getAllProducts{
            allProducts,success,message->
            if (success){
                _getAllPRoducts.value = allProducts
                _loading.value = false
            }
        }
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context, imageUri, callback)
    }

}