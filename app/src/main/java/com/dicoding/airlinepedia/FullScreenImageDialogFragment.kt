//noinspection SuspiciousImport
//import android.R
import com.dicoding.airlinepedia.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ImageView
//import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.transition.Fade
import java.io.File
import java.io.FileOutputStream

class FullScreenImageDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_IMAGE_RES_ID = "image_res_id"

        fun newInstance(imageResId: Int): FullScreenImageDialogFragment {
            val fragment = FullScreenImageDialogFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RES_ID, imageResId)
            fragment.arguments = args
            return fragment
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullscreenDialogTheme) // Set fullscreen theme

        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_screen_image, container, false)

        val imageView: ImageView = view.findViewById(R.id.iv_full_screen_image)
        val btnBack: ImageButton = view.findViewById(R.id.btn_back)
        val btnShare: ImageButton = view.findViewById(R.id.btn_share)
        val btnDownload: ImageButton = view.findViewById(R.id.btn_download)

        // Get image resource ID from arguments
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0
        if (imageResId != 0) {
            imageView.setImageResource(imageResId)
        } else {
            // Handle case when imageResId is not found or invalid
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show()
        }

        // Handle Back button click
        btnBack.setOnClickListener {
            dismiss() // Close the dialog
        }

        // Handle Share button click
        btnShare.setOnClickListener {
            shareImage(imageResId)
        }

        // Handle Download button click
        btnDownload.setOnClickListener {
            downloadImage(imageResId)
        }

        return view
    }

    private fun shareImage(imageResId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, imageResId)
        val file = File(requireContext().cacheDir, "shared_image.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        // Assuming the image is in the drawable folder
        val imageUri = Uri.parse("android.resource://${requireContext().packageName}/drawable/$imageResId")
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image"))

    }

    private fun downloadImage(imageResId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, imageResId)
        val file = File(requireContext().getExternalFilesDir(null), "downloaded_image.jpg")
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            Toast.makeText(requireContext(), "Image saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }
}