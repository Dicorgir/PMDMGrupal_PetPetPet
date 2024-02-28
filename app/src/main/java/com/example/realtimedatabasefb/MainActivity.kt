package com.example.realtimedatabasefb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.realtimedatabasefb.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            val firstname = binding.firstName.text.toString()
            val lastname = binding.lastName.text.toString()
            val age = binding.age.text.toString()
            val userName = binding.userName.text.toString()

            database = FirebaseDatabase.getInstance("https://realtimedatabasefb-41433-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")

            val user = User(firstname, lastname, age, userName)

            database.child(userName).setValue(user).addOnSuccessListener {
                binding.firstName.text.clear()
                binding.lastName.text.clear()
                binding.age.text.clear()
                binding.userName.text.clear()

                Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {

                Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
            }
        }

    }
}