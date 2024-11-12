package com.dicoding.airlinepedia

//import android.os.Bundle

//import android.annotation.SuppressLint
//import android.annotation.SuppressLint
import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
//import java.util.Collections.listAirline
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.recyclerviewapp.aircraft


//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat

class ListAircraftAdapter(private var listAirline: ArrayList<aircraft>) : RecyclerView.Adapter<ListAircraftAdapter.ListViewHolder>() {

    // Metode untuk mengurutkan berdasarkan nama secara ascending
    fun sortListByName() {
        val oldList = ArrayList(listAirline) // Copy current list
        listAirline.sortBy { it.name } // Urutkan berdasarkan nama
        // Notify only the changed items or the whole list if needed
        if (oldList != listAirline) {
            notifyItemRangeChanged(0, listAirline.size) // Inform RecyclerView to update the list
        }
    }

    // Fungsi untuk mengurutkan berdasarkan nama secara descending
    fun sortListByNameDescending() {
        val oldList = ArrayList(listAirline) // Copy current list
        listAirline.sortByDescending { it.name } // Urutkan berdasarkan nama secara descending
        // Notify only the changed items or the whole list if needed
        if (oldList != listAirline) {
            notifyItemRangeChanged(0, listAirline.size) // Inform RecyclerView to update the list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_airline, parent, false)
        return ListViewHolder(view)
    }

    //@SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, history, meaningOfLogo, aircraftType, photo) = listAirline[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description
     holder.tvHistory.text = history
   holder.tvMeaningOfLogo.text = meaningOfLogo
        holder.tvAircraftType.text = aircraftType



        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailAirlineActivity::class.java)
            intentDetail.putExtra("key_airline", listAirline[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listAirline.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvHistory: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvMeaningOfLogo: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvAircraftType: TextView = itemView.findViewById(R.id.tv_item_description)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<aircraft>) {
        listAirline.clear()
        listAirline = newList
        notifyDataSetChanged() // Memberitahu adapter bahwa data berubah
    }

    // Fungsi untuk memperbarui daftar item
//    fun updateList(newList: List<aircraft>) {
//        listAirline.clear()
//        listAirline.addAll(newList)
//        notifyDataSetChanged()
//    }
}