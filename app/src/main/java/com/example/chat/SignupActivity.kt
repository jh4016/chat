package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chat.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var btn_signUp: Button
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var edt_name: EditText
    private lateinit var mDB: DatabaseReference

    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initializeListener()
    }

    fun initializeView() {  //뷰 초기화
        auth = FirebaseAuth.getInstance()
        btn_signUp = binding.btnSignup
        edt_email = binding.edtEmail
        edt_password = binding.edtPassword
        edt_name = binding.edtOpponentName
    }

    fun initializeListener() {   //버튼 클릭 시 리스너 초기화
        btn_signUp.setOnClickListener()
        {
            signUp()
        }
    }

    fun signUp() {     //회원 가입 실행
        var email = edt_email.text.toString()           //각 입력란 값 String으로 변환
        var password = edt_password.text.toString()
        var name = edt_name.text.toString()
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        auth.createUserWithEmailAndPassword(email, password)      //FirebaseAuth에 회원가입 성공 시
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {     //회원 가입 성공 시
                    val user = auth.currentUser
                    val userId = user?.uid
                    val userIdSt = userId.toString()
                    FirebaseDatabase.getInstance().getReference("User").child("users")
                        .child(userId.toString()).setValue(User(name, userIdSt, email))             //Firebase RealtimeDatabase에 User 정보 추가
                    Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    Log.e("UserId", "$userId")
                    startActivity(Intent(this, LoginActivity::class.java))
                } else
                    Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

    }
}