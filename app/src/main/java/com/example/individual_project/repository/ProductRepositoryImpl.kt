package com.example.individual_project.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.individual_project.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl : ProductRepository{

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.reference.child("products")

    override fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = ref.push().key.toString()
        productModel.productID = id
        ref.child(id).setValue(productModel).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Product added successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun updateProduct(
        productID: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {

        ref.child(productID).updateChildren(data).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Product updated successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun deleteProduct(
        productID: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productID).removeValue().addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Product delete successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun getProductByID(
        productID: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productID).addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var model = snapshot.getValue(ProductModel::class.java)
                        callback(model,true,"product fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }

            }
        )
    }

    override fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var products = mutableListOf<ProductModel>()
                        for (eachData in snapshot.children){
                            var model = eachData.getValue(ProductModel::class.java)
                            if (model != null){
                                products.add(model)
                            }
                        }
                        callback(products,true,"product fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }

            }
        )
    }


    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dkveof11h",
            "api_key" to "567832146462918",
            "api_secret" to "6q2XWdyL2glCB0lO8WHavbz8PUk"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }


}