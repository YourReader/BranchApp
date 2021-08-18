package com.example.ownerapp.mvvm.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ownerapp.Utils.Constants

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

abstract class BaseRepository(private var contextBase: Context) {


    private var mAuthBase = FirebaseAuth.getInstance()
    var curUser = mAuthBase.currentUser
    private val fDatabaseBase = FirebaseDatabase.getInstance()
    private val branchesNameRefBase = fDatabaseBase.getReference(Constants.BRANCHES_SPINNER)
    val branchesInfoRefBase = fDatabaseBase.getReference(Constants.BRANCH_INFO)

    val branchesList = MutableLiveData<java.util.ArrayList<String>>()





    fun fetchBranchNames(): MutableLiveData<ArrayList<String>> {
        val list = java.util.ArrayList<String>()
        branchesNameRefBase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    list.add(dataSnapshot.value.toString())
                }
                branchesList.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "onCancelled: $error")
            }
        })
        return branchesList
    }




}