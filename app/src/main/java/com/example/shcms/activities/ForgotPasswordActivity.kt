package com.example.shcms.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.shcms.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        fullScreenMode()

        btn_forgot_password.setOnClickListener{
            resetPassword()
        }
        setupActionBar()
    }
    private fun resetPassword(){
        val email : String = et_email_forgot_password.text.toString().trim() {it <= ' '}

        if (email.isNotEmpty()){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    hideProgressDialog()
                    if (task.isSuccessful){
                        Toast.makeText(this,
                            "Reset password mail is send. Reset your password",
                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(this,
                "Enter Your Email Address", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupActionBar(){
        setSupportActionBar(toolbar_forgot_password_activity)
        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_button)
        }

        toolbar_forgot_password_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}