package com.example.shcms.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.shcms.R
import com.example.shcms.firebase.FirestoreClass
import com.example.shcms.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
        setupActionBar()

        btn_sign_up.setOnClickListener {
            registerUser()
        }
    }
    fun userRegisteredSuccess(){
        Toast.makeText(
            this,
            "you have successfully registered",
            Toast.LENGTH_SHORT
        ).show()
        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        finish()
    }
    private fun setupActionBar(){
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_button)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener {
            onBackPressed()
        }


    }


    private fun registerUser(){
        val name : String = et_name.text.toString().trim() {it <= ' '}
        val email : String = et_email.text.toString().trim() {it <= ' '}
        val password : String = et_password.text.toString()

        if(validateForm(name, email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, registeredEmail)

                        FirestoreClass().registerUser(this, user)
                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(name : String, email : String, password : String) : Boolean{
        return when {
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please Enter a Name")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please Enter an Email")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please Enter Password")
                false
            } else ->{
                true
            }
        }
    }
}