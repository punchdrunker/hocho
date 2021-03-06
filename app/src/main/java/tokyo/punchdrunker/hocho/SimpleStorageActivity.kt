package tokyo.punchdrunker.hocho

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ActivitySimpleStorageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SimpleStorageActivity : AppCompatActivity() {
    private val scope = MainScope()

    private val binding: ActivitySimpleStorageBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_simple_storage)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.data?.also { uri ->
                Timber.w(uri.toString())
                binding.image.setImageBitmap(getBitmapFromUri(uri))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        createFileOnPrivateArea()
        viewFiles()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun createFileOnPrivateArea() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val file = File(getExternalFilesDir(null), "test-image.jpg")
                try {
                    val stream = resources.openRawResource(+R.drawable.img_cat)
                    val os = FileOutputStream(file)
                    val data = ByteArray(stream.available())
                    stream.read(data)
                    os.write(data)
                    stream.close()
                    os.close()
                } catch (e: IOException) {
                    Timber.w(e)
                }
            }
        }
    }

    private fun viewFiles() {
        scope.launch {
            withContext(Dispatchers.Main) {
                getExternalFilesDir(null)?.listFiles()?.let {
                    binding.log.text = it.joinToString()
                }
            }
        }
    }

    fun performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "image/*"
        }

        startForResult.launch(intent)
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor?.fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }
}