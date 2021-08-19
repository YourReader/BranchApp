package com.example.branchapp.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.branchapp.R
import com.example.branchapp.databinding.ActivityLoginBinding
import com.example.ownerapp.di.components.DaggerFactoryComponent
import com.example.ownerapp.di.modules.FactoryModule
import com.example.ownerapp.di.modules.RepositoryModule
import com.example.ownerapp.mvvm.repository.AuthRepository
import com.example.branchapp.mvvm.viewmodles.AuthViewModel


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth
    var list = ArrayList<String>()
    private var currentuser: FirebaseUser? = null
    private var verifiedboolean = false


    private lateinit var component: DaggerFactoryComponent

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        viewModel.fetchBranchNames().observe(this, {
            Log.d(TAG, "onCreateView: Size ${it.size}")
            val arrayAdapter = ArrayAdapter(
                this, R.layout.dropdownitem,
                it.toArray()
            )
            list = it
            arrayAdapter.notifyDataSetChanged()
            binding.branchData.setAdapter(arrayAdapter)
        })
        binding.btnLoginLg.setOnClickListener {
            val name = binding.branchData.text.toString()
            val id = binding.logEmailEdit.text.toString()
            val pass = binding.logPassEdit.text.toString()

            if (id.isNotBlank() && pass.isNotBlank()) {
                if (list.contains(name)) {
                    viewModel.branchLogin(name, id, pass)
                } else {
                    Toast.makeText(this, "UnVerified Branch", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill the Fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.my_statusbar_color)


        mAuth = FirebaseAuth.getInstance()
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(AuthRepository(this)))
            .build() as DaggerFactoryComponent

        viewModel =
            ViewModelProviders.of(this, component.getFactory())
                .get(AuthViewModel::class.java)
    }

}