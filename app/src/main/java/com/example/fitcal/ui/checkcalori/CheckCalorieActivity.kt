package com.example.fitcal.ui.checkcalori

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.fitcal.R
import com.example.fitcal.data.remote.RetrofitClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.fitcal.data.model.CalorieCheckResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import java.io.FileOutputStream

class CheckCalorieActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private val REQUEST_CAMERA_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_calorie)

        imageView = findViewById(R.id.btn_add_photo)
        progressBar = findViewById(R.id.progressBar)
        val buttonCamera: Button = findViewById(R.id.btn_camera)
        val buttonGallery: Button = findViewById(R.id.btn_gallery)
        val buttonCheck: Button = findViewById(R.id.btn_check_calorie2)

        buttonCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                dispatchTakePictureIntent()
            }
        }

        buttonGallery.setOnClickListener {
            dispatchPickPictureIntent()
        }

        buttonCheck.setOnClickListener {
            val drawable = imageView.drawable
            if (drawable == null) {
                Toast.makeText(this, "Silakan masukkan foto terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val bitmap = (drawable as BitmapDrawable).bitmap
                val file = bitmapToFile(bitmap)
                checkCalories(file)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchPickPictureIntent() {
        val pickPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (pickPictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(pickPictureIntent, REQUEST_IMAGE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    val imageUri = data?.data
                    val imageStream = contentResolver.openInputStream(imageUri!!)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    imageView.setImageBitmap(selectedImage)
                }
            }
        }
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        val file = File(cacheDir, "image.jpg")
        val outputStream = file.outputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

    private fun checkCalories(file: File) {
        progressBar.visibility = View.VISIBLE

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        RetrofitClient.instance.checkCalories(body).enqueue(object : Callback<CalorieCheckResponse> {
            override fun onResponse(call: Call<CalorieCheckResponse>, response: Response<CalorieCheckResponse>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val calorieResponse = response.body()
                    val drawable = imageView.drawable as BitmapDrawable
                    val bitmap = drawable.bitmap

                    // Simpan bitmap sebagai file di penyimpanan sementara
                    val file = File(cacheDir, "result_image.png")
                    val stream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    stream.flush()
                    stream.close()

                    val intent = Intent(this@CheckCalorieActivity, CalorieResultActivity::class.java).apply {
                        putExtra("prediction", calorieResponse?.prediction ?: "Unknown")
                        putExtra("calories", calorieResponse?.calories ?: 0)
                        putExtra("recommendation", calorieResponse?.recommendation ?: "No recommendation")
                        putExtra("imageUri", Uri.fromFile(file))
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CheckCalorieActivity, "Failed to get response", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CalorieCheckResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@CheckCalorieActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}