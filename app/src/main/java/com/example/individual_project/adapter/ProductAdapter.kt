package com.example.individual_project.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.individual_project.model.ProductModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.individual_project.R
import com.example.individual_project.repository.CartRepositoryImpl
import com.example.individual_project.ui.activity.UpdateProductActivity
import com.example.individual_project.viewmodel.CartViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class ProductAdapter(var context: Context, var data : ArrayList<ProductModel>)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productName : TextView = itemView.findViewById(R.id.displayName)
        var productDes : TextView = itemView.findViewById(R.id.displayDes)
        var productPrice : TextView = itemView.findViewById(R.id.displayPrice)
        var productImage: ImageView = itemView.findViewById(R.id.imageShow)
        var progrssBar: ProgressBar = itemView.findViewById(R.id.progressBar3)
        var btnedit: Button = itemView.findViewById(R.id.Editbtn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_products,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {


        holder.productName.text = data[position].productName
        holder.productDes.text = data[position].productDesc
        holder.productPrice.text = data[position].price.toString()



        Picasso.get().load(data[position].imageUrl).into(holder.productImage,object:Callback{
            override fun onSuccess() {
                holder.progrssBar.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
            }

        })


        holder.btnedit.setOnClickListener{
            var intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("products",data[position].productID)
            context.startActivity(intent)
        }
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getProductId(position: Int): String{
        return data[position].productID
    }
}