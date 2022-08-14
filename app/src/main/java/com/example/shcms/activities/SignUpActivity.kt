package com.example.shcms.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.shcms.R
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
            Toast.makeText(
                this@SignUpActivity,
                "Now we can register a new user",
                Toast.LENGTH_SHORT
            ).show()
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