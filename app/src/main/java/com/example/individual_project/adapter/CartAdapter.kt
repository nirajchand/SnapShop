package com.example.individual_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.individual_project.R
import com.example.individual_project.model.CartModel
import com.example.individual_project.model.ProductModel
import com.example.individual_project.viewmodel.CartViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CartAdapter(var context: Context, var data: ArrayList<CartModel>,var cartViewModel: CartViewModel):
    RecyclerView.Adapter<CartAdapter.CartViewholder>() {

    class CartViewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productName : TextView = itemView.findViewById(R.id.cartproductDisplayName)
        var productDes : TextView = itemView.findViewById(R.id.cartproductDisplayDes)
        var productPrice : TextView = itemView.findViewById(R.id.cartProductDisplayPrice)
        var productImage: ImageView = itemView.findViewById(R.id.cartaddedImage)
        var progrssBar: ProgressBar = itemView.findViewById(R.id.CartImageProgressBar)
        var removeBtn: Button = itemView.findViewById(R.id.removeBtn)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartViewholder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.samplecartproduct,parent,false)
        return CartViewholder(itemView)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewholder, position: Int) {

        holder.productName.text = data[position].productName
        holder.productDes.text = data[position].productDes
        holder.productPrice.text = data[position].price.toString()

        Picasso.get().load(data[position].productImage).into(holder.productImage,object:Callback{
            override fun onSuccess() {
                holder.progrssBar.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
            }

        })

        holder.removeBtn.setOnClickListener{
            val productId = data[position].productID

            cartViewModel.deleteCartById(productId){
                success,message ->
                if (success){
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    data.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, data.size) // Ensure proper indexing

                }else{
                    Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun updateData(products: List<CartModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}