package com.example.branchapp.mvvm.viewmodles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ownerapp.mvvm.repository.AuthRepository

class AuthViewModel(
    val repository: AuthRepository
) : ViewModel() {
    fun branchLogin(name: String, id: String, pass: String) {
        repository.branchLogin(name,id,pass)
    }

    fun fetchBranchNames(): MutableLiveData<ArrayList<String>> {
        return repository.fetchBranchNames()
    }

//    fun login(email: String, password: String) = CoroutineScope(IO).launch {
//        repository.login(email, password)
//    }
//
//
//
//    fun sendUserToMainActivity(){
//        repository.sendUserToMainActivity()
//    }




}