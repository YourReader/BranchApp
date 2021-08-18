package com.example.ownerapp.mvvm.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.branchapp.activities.MainActivity
import com.example.ownerapp.Utils.Constants
import com.example.ownerapp.Utils.Constants.BRANCH_ID
import com.example.ownerapp.Utils.Constants.BRANCH_PASS

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AuthRepository(var context: Context) : BaseRepository(context) {

    val fDatabase = FirebaseDatabase.getInstance()

    private var mAuth = FirebaseAuth.getInstance()
    val branchesInfoRef = fDatabase.getReference(Constants.BRANCH_INFO)
    val isLoggedIn: SharedPreferences = context.getSharedPreferences(
        "isLoggedIn",
        AppCompatActivity.MODE_PRIVATE
    )

    val editor = isLoggedIn.edit()

    fun branchLogin(name: String, id: String, pass: String) {
        val finalName = name.lowercase()
        branchesInfoRef.child(finalName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val firebaseID = snapshot.child(BRANCH_ID).value.toString()
                    val firebasePass = snapshot.child(BRANCH_PASS).value.toString()
                    if (firebaseID == id) {
                        if (firebasePass == pass) {
                            editor.putBoolean("isLoggedIn", true)
                            editor.apply()
                            val intent= Intent(context,MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Wrong Wrong Pass", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Wrong ID", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.message}")
                }

            })
    }


}