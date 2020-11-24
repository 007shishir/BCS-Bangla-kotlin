package com.example.bcsbanglagrammar.loginRegistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bcsbanglagrammar.MainActivity
import com.example.bcsbanglagrammar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var mEdt_emailAddress: EditText? = null
    var mEdt_password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();

        val mTxt_register = findViewById<TextView>(R.id.mTxt_register)
        val mBtn_login = findViewById<Button>(R.id.mBtn_login)
        mEdt_emailAddress = findViewById(R.id.mEdt_emailAddress)
        mEdt_password = findViewById(R.id.mEdt_password)

        mTxt_register.setOnClickListener(clicklistener)
        mBtn_login.setOnClickListener(clicklistener)

    }

    val clicklistener = View.OnClickListener { v ->
        when(v.id){
            R.id.mTxt_register -> {
                val intent = Intent(this, RegisterUser::class.java)
                startActivity(intent)
            }
            R.id.mBtn_login -> userLogin()
            else -> Toast.makeText(this, "Give Input and Press Login button", Toast.LENGTH_SHORT).show()
        }
    }

    private fun userLogin(){
        val email = mEdt_emailAddress?.text.toString().trim()
        val passw = mEdt_password?.text.toString().trim()

        if (email.isEmpty()){
            mEdt_emailAddress?.setError("Provide Valid Email Address")
            mEdt_emailAddress?.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEdt_emailAddress?.setError("Please provide a valid Email Address")
            mEdt_emailAddress?.requestFocus()
            return
        }

        if (passw.isEmpty()){
            mEdt_password?.setError("Please Provide a password")
            mEdt_password?.requestFocus()
            return
        }

        if (passw.length<6){
            mEdt_password?.setError("Password should be 6 character long")
            mEdt_password?.requestFocus()
            return
        }

        mAuth?.signInWithEmailAndPassword(email, passw)?.addOnCompleteListener { it ->
            if (it.isSuccessful) {
                val mUser: FirebaseUser? = mAuth!!.currentUser
                if (mUser != null) {
                    if (mUser.isEmailVerified){
                        Toast.makeText(applicationContext, "Congratulation", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        mUser.sendEmailVerification()
                        Toast.makeText(applicationContext, "Check your Email to verify your account", Toast.LENGTH_SHORT).show()
                    }
                }else Toast.makeText(applicationContext, "No User Found!", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Failed to login: Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}