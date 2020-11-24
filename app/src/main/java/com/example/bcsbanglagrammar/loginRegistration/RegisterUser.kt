package com.example.bcsbanglagrammar.loginRegistration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bcsbanglagrammar.MainActivity
import com.example.bcsbanglagrammar.R
import com.example.bcsbanglagrammar.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase as FirebaseDatabase


class RegisterUser : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var mEtxt_firstName: EditText? = null
    var mEtxt_lastName: EditText? = null
    var mEtxt_emailAddress: EditText? = null
    var mEtxt_password: EditText? = null
    var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        mAuth = FirebaseAuth.getInstance();
        val mTxt_banner = findViewById<TextView>(R.id.mTxt_banner)
        mEtxt_firstName = findViewById<EditText>(R.id.mEtxt_firstName)
        mEtxt_lastName = findViewById<EditText>(R.id.mEtxt_lastName)
        mEtxt_emailAddress = findViewById<EditText>(R.id.mEtxt_emailAddress)
        mEtxt_password = findViewById<EditText>(R.id.mEtxt_password)
        val mBtn_submit = findViewById<Button>(R.id.mBtn_submit)


        mTxt_banner.setOnClickListener(clickListener)
        mBtn_submit.setOnClickListener(clickListener)

        firebaseDatabase = FirebaseDatabase.getInstance()

    }

    val clickListener = View.OnClickListener { view ->
        when(view?.id){
            R.id.mTxt_banner -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.mBtn_submit -> registerUser()
            else -> Toast.makeText(applicationContext, "Give Input and Press SUBMIT button", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser() {
        Log.d("RegisterUser", "Button is Clicked and FUN works")
        val firstName = mEtxt_firstName?.text.toString().trim();
        val lastName = mEtxt_lastName?.text.toString().trim();
        val email = mEtxt_emailAddress?.text.toString().trim();
        val password = mEtxt_password?.text.toString().trim();

        if (firstName.isEmpty()) {
            mEtxt_firstName?.setError("First Name Required")
            mEtxt_firstName?.requestFocus()
            return
        }
        if (lastName.isEmpty()) {
            mEtxt_lastName?.setError("Last Name Required")
            mEtxt_lastName?.requestFocus()
            return
        }
        if (email.isEmpty()) {
            mEtxt_emailAddress?.setError("Email Address Required")
            mEtxt_emailAddress?.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEtxt_emailAddress?.setError("Please provide valid email")
            mEtxt_emailAddress?.requestFocus()
            return
        }

        if (password.isEmpty()) {
            mEtxt_password?.setError("Password is required")
            mEtxt_password?.requestFocus()
            return
        }
        if (password.length < 6) {
            mEtxt_password?.setError("Minimum Password Length Should Be 6 Characters")
            mEtxt_password?.requestFocus()
            return
        }

        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
                override fun onComplete(it: Task<AuthResult>) {
                    if (it.isSuccessful){
                        val user = User(firstName, lastName, email, password)

                        firebaseDatabase?.getReference("Users")
                            ?.child(FirebaseAuth.getInstance().currentUser!!.uid)
                            ?.setValue(user)?.addOnCompleteListener(object: OnCompleteListener<Void>{
                                override fun onComplete(p0: Task<Void>) {
                                    if (p0.isSuccessful){
                                        Toast.makeText(applicationContext, "User is successfully registered", Toast.LENGTH_SHORT).show()
                                        //ToDO: redirect to login layout
                                    }else{
                                        Toast.makeText(applicationContext, "User is FAILED to register", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                    }else{
                        Toast.makeText(applicationContext, "User is successfully registered", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

}