package com.dicoding.airlinepedia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.recyclerviewapp.aircraft
import java.util.Locale

//import java.util.Locale.filter

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var rvAirline: RecyclerView
    private lateinit var listAdapter: ListAircraftAdapter
    private var originalList = ArrayList<aircraft>()
    //private val filteredList = ArrayList<aircraft>()

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        originalList= ArrayList()
        originalList.addAll(getListAirline())
        listAdapter = ListAircraftAdapter(originalList)

        rvAirline = findViewById(R.id.rv_Airline)
        rvAirline.setHasFixedSize(true)



      //  filteredList.addAll(getListAirline()) // Isi awal list dengan data dari getListAirline()

       // listAdapter = ListAircraftAdapter(filteredList)
        rvAirline.layoutManager = LinearLayoutManager(this)
        rvAirline.adapter = listAdapter

//        originalList.add(aircraft(name = "Air France",
//            description = "One of the leading airlines in Europe.",
//            history = "Founded in 1933, Air France is a major airline with a rich history.",
//            meaningOfLogo = "The logo represents the elegance and excellence of France.",
//            aircraftType = "Boeing 777, Airbus A320",
//            photo = R.drawable.air_france, // Resource ID for the aircraft image
//            logo = R.drawable.air_france_logo ))

        listAdapter.notifyDataSetChanged()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)


//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_about, menu)
        val searchItem = menu?.findItem(R.id.action_search)
       val searchView: SearchView = searchItem?.getActionView() as SearchView


//        searchView?.queryHint = "Search airlines..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    originalList.clear()
                    originalList.addAll(getListAirline())
                    listAdapter.filterList(ArrayList(originalList))
                } else {
                    filter(newText)
                }

//                newText?.let { searchInList(it) }
            return true
                }



        })

        return true


    }

    private fun filter(text: String) {
        val filteredList = ArrayList<aircraft>()

        for (item in originalList) {
            if (item.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show()
        } else {
            listAdapter.filterList(filteredList)
        }
    }
    //@SuppressLint("NotifyDataSetChanged")
//    private fun searchInList(query: String) {
//        val filteredResults = getListAirline().filter {
//            it.name.contains(query, ignoreCase = true)
//        }
//        filteredList.clear()
//        filteredList.addAll(filteredResults)
//        listAdapter.notifyDataSetChanged()
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
                true
            }
//            R.id.action_sort_ascending -> {
//               listAdapter.sortListByName()
//                true
//            }
//            R.id.action_sort_descending -> {
//                listAdapter.sortListByNameDescending()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun sortListByName() {
//        filteredList.sortBy { it.name } // Urutkan list secara ascending berdasarkan nama
//        listAdapter.updateList(filteredList) // Perbarui adapter dengan data baru
//    }
//
//    private fun sortListByNameDescending() {
//        filteredList.sortByDescending { it.name } // Urutkan list secara descending berdasarkan nama
//        listAdapter.updateList(filteredList)
//    }

    @SuppressLint("Recycle")
    private fun getListAirline(): ArrayList<aircraft> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataHistory = resources.getStringArray(R.array.data_history)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataMeaningOfLogo = resources.getStringArray(R.array.meaning_of_logo)
        val dataAircraftType = resources.getStringArray(R.array.aircraft_type)
        val logoPhoto = resources.obtainTypedArray(R.array.logo_photo)

        val listAirlines = ArrayList<aircraft>()

        val size = minOf(dataName.size, dataDescription.size, dataHistory.size,  dataPhoto.length(), logoPhoto.length(), dataMeaningOfLogo.size, dataAircraftType.size)
        for (i in 0 until size) {
            val airlines = aircraft(dataName[i], dataDescription[i], dataHistory[i], dataMeaningOfLogo[i],  dataAircraftType[i],
                dataPhoto.getResourceId(i, -1), logoPhoto.getResourceId(i, -1))
            listAirlines.add(airlines)
        }
        return listAirlines
    }
}
