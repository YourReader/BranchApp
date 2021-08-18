package com.example.branchapp.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.branchapp.R

class MainActivity : AppCompatActivity() {
    var isLogged = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isLoggedIn: SharedPreferences = getSharedPreferences("isLoggedIn", MODE_PRIVATE)

        isLogged=isLoggedIn.getBoolean("isLoggedIn",false)

        if (isLogged){
            Log.d(TAG, "onCreate: Branch Manager Logged IN")
        }else{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
}