package dev.aman.firebasetest
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.aman.firebasetest.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var imageUri:Uri
    private lateinit var storageReference:StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{

            selectImage()
        }
        binding.button2.setOnClickListener{
            uploadImage()
        }
    }

    private fun uploadImage() {
       val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading File....")
        progressDialog.show()


        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName: String = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                binding.imageView2.setImageURI(imageUri)
                Toast.makeText(this@MainActivity, "Successfully Uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing()) progressDialog.dismiss()
            }.addOnFailureListener {
                if (progressDialog.isShowing()) progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Failed to Upload", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.setType("image/")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,100)

    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.data != null) {

            imageUri = data.data!!;

        }
        }
}