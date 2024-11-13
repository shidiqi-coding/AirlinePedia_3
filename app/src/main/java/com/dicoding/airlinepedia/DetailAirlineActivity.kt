package com.dicoding.airlinepedia

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Build
import android.net.Uri
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.recyclerviewapp.aircraft
import java.io.File
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
//import android.os.Handler
//import android.os.Looper
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailAirlineActivity : AppCompatActivity() {

    companion object {
        const val key_airline = "key_airline"
    }

//    private val handler = Handler(Looper.getMainLooper())
//    private var imageIndex = 0
    private var currentImages: List<Int> = emptyList()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_airline)

//    val airlineNames = intent.getStringExtra("key_airline") ?: "Air France"
//        Log.d("DetailAirlineActivity", "airlineName: $airlineNames")
       // Default
//      val ivAircraft: ImageView = findViewById(R.id.iv_aircraft)




        // Share button setup
        val shareButton: ImageButton = findViewById(R.id.btn_share)
        shareButton.setOnClickListener {
            shareImage()
        }

        // Back arrow setup
        val backArrow: ImageView = findViewById(R.id.action_back)
        backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Retrieve the airline data from the intent
        val dataAirline = if (Build.VERSION.SDK_INT < 30) {
            intent.getParcelableExtra<aircraft>(key_airline, aircraft::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<aircraft>(key_airline)
        }
        // Get the images based on the airline name
        val airlineName = dataAirline?.name
        currentImages = getAircraftImages(airlineName)

        // Set up RecyclerView for gallery
        val recyclerView: RecyclerView = findViewById(R.id.rv_gallery)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns for the grid
        recyclerView.adapter = GalleryAdapter(this, currentImages) { imageResId ->
            // Show the image in full screen when clicked
            showFullScreenImage(imageResId)
        }

        // Set airline details on the UI
        val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
        val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)
        val tvDetailHistory: TextView = findViewById(R.id.tv_detail_history)
        val ivDetailPhoto: ImageView = findViewById(R.id.iv_detail_photo)
        val ivDetailLogo: ImageView = findViewById(R.id.iv_detail_logo)
        val tvDetailMeaningOfLogo: TextView = findViewById(R.id.tv_detail_meaningOfLogo)
        val tvDetailaircraftType: TextView = findViewById(R.id.tv_detail_aircraftType)

        tvDetailName.text = dataAirline?.name
        tvDetailDescription.text = dataAirline?.description
        tvDetailHistory.text = dataAirline?.history
        ivDetailPhoto.setImageResource(dataAirline?.photo ?: 0)
        ivDetailLogo.setImageResource(dataAirline?.logo ?: 0)
        tvDetailMeaningOfLogo.text = dataAirline?.meaningOfLogo
        tvDetailaircraftType.text = dataAirline?.aircraftType

        supportActionBar?.hide()
    }

    private fun getAircraftImages(airlineName: String?): List<Int> {
        val airlineImagesMap = mapOf(
            "Air France" to R.array.airfrance_planes,
            "Air Asia" to R.array.airasia_planes,
            "All Nippon Airways (ANA)" to R.array.ana_planes,
            "Batik Air" to R.array.batik_planes,
            "British Airways" to R.array.british_planes,  // New airline
            "Lion Air" to R.array.lion_planes,
            "Emirates Airlines" to R.array.emirates_planes,
            "Etihad Airways" to R.array.etihad_planes,
            "Garuda Indonesia" to R.array.garuda_planes,
            "Korean Air" to R.array.korean_planes,
            "Lufthansa" to R.array.lufthansa_planes,
            "Qantas" to R.array.qantas_planes,
            "Qatar Airways" to R.array.qatar_planes,
            "Singapore Airlines" to R.array.singapore_planes,
            "Turkish Airlines" to R.array.turkish_planes

//            "Emirates" to R.array.emirates_planes  // New airline
        )

        val imageArrayResId = airlineImagesMap[airlineName] ?: R.array.default_plane_image // Default images if not found
        val typedArray = resources.obtainTypedArray(imageArrayResId)

        val images = mutableListOf<Int>()
        for (i in 0 until typedArray.length()) {
            val imageResId = typedArray.getResourceId(i, 0)
            if (imageResId != 0) {
                images.add(imageResId)
            }
        }
        typedArray.recycle()

        Log.d("DetailAirlineActivity", "Airline Name: $airlineName")
        return images.ifEmpty { listOf(R.drawable.default_aircraft_image) }
    }

//    private fun getAircraftImages(airlineName: String?): List<Int> {
//        val typedArray = when mapOf{
//            "Air France" -> R.array.airfrance_planes,
//            "Air Asia" -> R.array.airasia_planes
//            else -> null
//        }
//
//        val images = mutableListOf<Int>()
//        typedArray?.let {
//            for (i in 0 until it.length()) {
//                val imageResId = it.getResourceId(i, 0)
//                if (imageResId != 0) {
//                    images.add(imageResId)
//                }
//            }
//            it.recycle()
//        }
//        Log.d("DetailAirlineActivity", "Airline Name: $airlineName")
//        return images.ifEmpty { listOf(R.drawable.default_aircraft_image) }
//    }

    private fun showFullScreenImage(imageResId: Int) {
        // Show image in full-screen dialog
        val dialog = FullScreenImageDialogFragment.newInstance(imageResId)
        dialog.show(supportFragmentManager, "FullScreenImage")
    }

    private fun shareImage() {
        val imageView = findViewById<ImageView>(R.id.iv_detail_photo)
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val uri = getImageUriFromBitmap(bitmap)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val file = File(cacheDir, "share_image.png")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            it.flush()
        }
        return FileProvider.getUriForFile(this, "${packageName}.provider", file)
    }
}