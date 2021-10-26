package com.example.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.camera.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGaleria.setOnClickListener { requestPermissions() }
    }

    private fun requestPermissions() {


       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

           when{
                ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {

                   pickPhotoFromGallery()

                }

               else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

           }

       }else{

           pickPhotoFromGallery()

       }


    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->

        if(isGranted) {

            pickPhotoFromGallery()

        }else{

            Toast.makeText(this, "Necesitas habilitar los permisos",Toast.LENGTH_LONG).show()
        }

    }

    private val starForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result->

        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            binding.ImgvFoto.setImageURI(data)

        }

    }

    private fun pickPhotoFromGallery() {
       val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        starForActivityGallery.launch(intent)
    }





}